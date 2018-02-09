package org.lds.ldssa.util

import android.app.Application
import com.crashlytics.android.Crashlytics
import org.apache.commons.io.FileUtils
import org.lds.ldsaccount.LDSAccountPrefs
import org.lds.ldssa.BuildConfig
import org.lds.ldssa.model.database.DatabaseManagerConst
import org.lds.ldssa.model.database.gl.tab.TabManager
import org.lds.ldssa.model.database.types.HighlightAnnotationStyle
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.highlight.HighlightManager
import org.lds.ldssa.model.database.userdata.screen.ScreenManager
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItemManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.mobile.util.EncryptUtil
import org.lds.mobile.util.LdsTimeUtil
import org.lds.mobile.util.LdsZipUtil
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class AppUpgradeUtil @Inject
constructor(private val application: Application, private val prefs: Prefs, private val ldsAccountPrefs: LDSAccountPrefs,
            private val encryptUtil: EncryptUtil, private val fileUtil: GLFileUtil, private val ldsTimeUtil: LdsTimeUtil,
            private val accountUtil: AccountUtil, private val ldsZipUtil: LdsZipUtil, private val annotationManager: AnnotationManager,
            private val highlightManager: HighlightManager, private val tabManager: TabManager,
            private val screenManager: ScreenManager, private val screenHistoryItemManager: ScreenHistoryItemManager, private val userdataDbUtil: UserdataDbUtil) {

    fun upgradeApp() {
        val lastInstalledVersionCode = prefs.lastInstalledVersionCode
        Timber.i("Checking for app upgrade from [%d]", lastInstalledVersionCode)

        if (lastInstalledVersionCode < 40013) { // NOSONAR - known version number (has meaning)
            Timber.w("Upgrading app from [%d]", lastInstalledVersionCode)

            // Migrate the credentials and clear data
            migrateFromGL3x()
        }

        if (lastInstalledVersionCode < 40305) {
            Timber.w("Upgrading app from [%d]", lastInstalledVersionCode)
            // version numbers were removed from content directory names
            migrateContentDirectoriesWithVersionNumber()
        }

        if (lastInstalledVersionCode >= 40501 && lastInstalledVersionCode <= 40505) {
            Timber.e("Force signing out and deleting userdata because of note truncation issue")
            // Annotation notes were being cut off to 256 characters... force the user to redownload annotations
            accountUtil.signOut(true)
        }

        if (lastInstalledVersionCode < 41011) {
            Timber.w("Upgrading app from [%d]", lastInstalledVersionCode)
            // If there is an archive zip file, unzip the database inside
            migrateArchiveFromZip()
        }

        if (lastInstalledVersionCode < 42103) {
            Timber.w("Upgrading app from [%d]", lastInstalledVersionCode)
            // if there is any highlights with "selection" as the color, then fix
            // GLA-2516 Adding a selection to a notebook saves "selection" as the highlight color
            fixSelectionHighlights()
        }

        if (lastInstalledVersionCode < 43003) {
            Timber.w("Upgrading app from [%d]", lastInstalledVersionCode)
            // rename tabs to screens and move to userdata database
            migrateTabsToScreens()
        }

        // set the current version
        prefs.lastInstalledVersionCode = BuildConfig.VERSION_CODE
    }

    private fun migrateTabsToScreens() {
        userdataDbUtil.openCurrentDatabase()
        screenManager.beginTransaction()
        var success = false
        try {
            screenManager.createTable(screenManager.getDatabaseName())
            screenHistoryItemManager.createTable(screenHistoryItemManager.getDatabaseName())

            tabManager.convertTabsToScreens(screenManager)

            success = true
        } catch (e: Exception) {
            Timber.e(e, "Failed to migrate tabs to screens")
        } finally {
            screenManager.endTransaction(success)
        }
    }

    /**
     * Migrate legacy credentials
     * Remove old content
     */
    private fun migrateFromGL3x() {
        // Get the account information from the 3.x version (if it exists)
        try {
            val legacyUsername = prefs.legacyUsername
            val legacyPassword: String

            // if possible, decrypt the password
            val encryptedLegacyPassword = prefs.legacyPassword
            if (encryptedLegacyPassword.isNotBlank()) {
                legacyPassword = encryptUtil.decrypt(prefs.legacyPassword) ?: error("Invalid password")
            } else {
                legacyPassword = ""
            }

            // Clear out all OLD files, databases, preferences
            clearData()

            // Set the new account preferences if we retrieved them from the previous version
            if (legacyUsername.isNotBlank() && legacyPassword.isNotBlank()) {
                ldsAccountPrefs.saveCredentials(legacyUsername, legacyPassword)
                Timber.e("GL3x Legacy Credentials migrated")
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to read legacy credentials")

            // MAKE SURE to Clear out all files, databases, preferences if the above fails
            clearData()
        }
    }


    /**
     * Migrate content directories with version numbers in the name
     */
    private fun migrateContentDirectoriesWithVersionNumber() {
        Timber.w("Migrate content directories...")

        val startTs = System.currentTimeMillis()
        var moveCount = 0
        var deleteCount = 0
        try {
            val contentItemBaseDirectory = fileUtil.contentItemBaseDirectory

            // make sure the dir exists
            if (!contentItemBaseDirectory.exists()) {
                return
            }

            // get list of directories
            val contentItemDirs = contentItemBaseDirectory.listFiles() ?: return

            for (contentItemDir in contentItemDirs) {
                val dirName = contentItemDir.name

                if (!dirName.contains(".")) {
                    // no rename necessary
                    continue
                }

                val newDirname = dirName.substring(0, dirName.indexOf("."))
                val newContentItemDir = File(contentItemBaseDirectory, newDirname)

                if (newContentItemDir.exists()) {
                    // delete the directory if the target directory already exists
                    FileUtils.deleteDirectory(contentItemDir)
                    deleteCount++
                } else {
                    // move the directory to the new location
                    FileUtils.moveDirectory(contentItemDir, newContentItemDir)
                    moveCount++
                }
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to migrate content directories with version numbers")
        } finally {
            Timber.w("Migrate content directories complete.  Move count [%d]  Delete Count [%d]  Time [%s]", moveCount, deleteCount, ldsTimeUtil.formatTimeElapsedFromNow(startTs))
        }
    }

    private fun clearData() {
        Timber.i("Performing CLEAR DATA...")

        // delete all preferences
        prefs.deleteAll()

        // delete all databases (catalog / content / userdata / feedback / tips)
        // files directory
        FileUtils.deleteQuietly(fileUtil.getDirectory(null))
        // database directory
        FileUtils.deleteQuietly(application.getDatabasePath(DatabaseManagerConst.GL_DATABASE_NAME).parentFile)
        // cache directory
        FileUtils.deleteQuietly(application.cacheDir)
    }

    private fun migrateArchiveFromZip() {
        val existingCatalogArchiveZipFile = fileUtil.catalogZipArchiveLatestCoreFile
        if (existingCatalogArchiveZipFile.exists()) {
            ldsZipUtil.extractZipEntryFromFile(existingCatalogArchiveZipFile, GLFileUtil.ZIP_ENTRY_CATALOG_DB_NAME, fileUtil.catalogArchiveFile)
            FileUtils.deleteQuietly(existingCatalogArchiveZipFile)
        }
    }

    private fun fixSelectionHighlights() {
        val annotationIds = highlightManager.findAllSelectionHighlightAnnotationIds()
        val count = annotationIds.size
        Timber.d("Fixing Selection Highlights (count: %d )", count)

        if (count > 0) {
            Crashlytics.log(1, "selection highlight errors", count.toString())
            Timber.e("FixedSelectionHighlights")

            for (annotationId in annotationIds) {
                val annotation = annotationManager.findFullAnnotationByRowId(annotationId)
                if (annotation != null) {
                    annotation.setAllHighlightColors(HighlightColor.YELLOW, HighlightAnnotationStyle.FILL)
                    annotationManager.save(annotation)
                }
            }
        }
    }
}
