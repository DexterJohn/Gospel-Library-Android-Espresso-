package org.lds.ldssa.util

import org.apache.commons.io.FileUtils
import org.lds.ldssa.download.GLDownloadManager
import org.lds.ldssa.download.TipsDownloader
import org.lds.ldssa.event.StartupProgressEvent
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.DatabaseManagerConst
import org.lds.ldssa.model.database.tips.tip.TipManager
import org.lds.ldssa.model.database.tips.tipsmetadata.TipsMetaDataManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.mobile.util.LdsNetworkUtil
import org.lds.mobile.util.LdsZipUtil
import pocketbus.Bus
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class TipsUpdateUtil @Inject
constructor(// Injected
        private val prefs: Prefs, private val bus: Bus, private val fileUtil: GLFileUtil, private val networkUtil: LdsNetworkUtil, private val tipsUtil: TipsUtil, private val tipsMetaDataManager: TipsMetaDataManager,
        private val downloadManager: GLDownloadManager, private val tipManager: TipManager, private val databaseManager: DatabaseManager,
        private val tipsDownloaderProvider: Provider<TipsDownloader>, private val ldsZipUtil: LdsZipUtil) {

    /**
     * If a tips file does not exist, prepare tips from:
     * 1. already existing previous download zip file
     * 2. zip file bundled with app in assets
     *
     * @return true if the tips are ready
     */
    fun prepareTipsDatabase(): Boolean {
        Timber.i("Preparing Tips Database...")

        val tipsFile = fileUtil.tipsFile

        // if there is already a tips file, verify that the file is OK
        if (tipsFile.exists() && !verifyTips()) {
            Timber.e("Current tips could not be verified... deleting....")
            removeExistingTips(fileUtil.tipsDir)
        }

        // check to see if there are pending/downloaded tips awaiting swap
        val downloadedTipZipFile = fileUtil.tipsZipDownloadFile
        if (downloadedTipZipFile != null && downloadedTipZipFile.exists()) {
            installDownloadedTips()
        }

        // if there is still NO tips.... use that last downloaded archived tips OR the bundled tips
        if (!tipsFile.exists()) {
            // use the last downloaded tips (if it exists)
            val existingTipsArchiveZipFile = fileUtil.tipsZipArchiveLatestFile
            if (existingTipsArchiveZipFile.exists()) {
                installArchivedTips(existingTipsArchiveZipFile)
            } else {
                installBundledTips()
            }
        }

        return tipsFile.exists()
    }

    fun downloadTips(tipsVersion: Int) {
        if (downloadManager.networkUsable(false)) {
            tipsDownloaderProvider.get().init(tipsVersion).execute()
        }
    }

    fun updateTips() {
        if (!checkInProgress.compareAndSet(false, true)) {
            Timber.w("Tips Update skipped (already in process)")
            return
        }

        try {
            Timber.i("Tips Update...")
            performUpdateTips()
        } finally {
            checkInProgress.set(false)
        }
    }

    private fun installBundledTips() {
        Timber.d("Installing tips database from bundle")

        val tipsFile = fileUtil.tipsFile
        val tipsDir = tipsFile.parentFile

        // cleanup any existing tips
        removeExistingTips(tipsDir)

        // unzip the tips from bundled assets
        val assetTipsFile = fileUtil.unZipAssetCatalog(tipsDir, GLFileUtil.BUNDLE_TIPS_DB_NAME)
        if (assetTipsFile == null || !assetTipsFile.exists()) {
            return
        }

        // the tips.zip file contains "Catalog.sqlite" rename it to the proper name
        assetTipsFile.renameTo(tipsFile)

        // add the new database to DatabaseManager and open the database
        prepareNewTips(tipsFile)
    }

    fun removeExistingTips(tipsDir: File) {
        // close existing tips database
        databaseManager.closeDatabase(DatabaseManagerConst.TIPS_DATABASE_NAME)

        // delete existing tips (there is more than just a sqlite file)
        try {
            FileUtils.deleteDirectory(tipsDir)
        } catch (e: IOException) {
            Timber.w("Unable to delete tips directory.")
        }
    }

    private fun prepareNewTips(tipsFile: File) {
        // Add the new tips database to the DatabaseManager
        // update database items for changes in the new tips
        databaseManager.addDatabase(DatabaseManagerConst.TIPS_DATABASE_NAME,
                tipsFile.path,
                DatabaseManager.TIPS_VERSION,
                DatabaseManager.TIPS_VIEWS_VERSION)

        // make sure the tips database is open
        // and also create the views
        val database = databaseManager.getDatabase(DatabaseManagerConst.TIPS_DATABASE_NAME)
        if (database != null) {
            databaseManager.openDatabase(database)
            databaseManager.onCreateViews(database)
        }
    }

    private fun performUpdateTips() {
        if (!networkUtil.isConnected()) {
            return
        }

        prefs.updateLastTipsUpdateTime()

        var onlineVersion = tipsUtil.fetchTipsVersion()

        // DEVELOPER DEBUG: allow override of online version
        if (prefs.isDeveloperModeEnabled && prefs.developerTipsVersion > 0) {
            onlineVersion = prefs.developerTipsVersion
        }

        var currentTipsVersion = 0
        if (fileUtil.tipsFile.exists()) {
            currentTipsVersion = tipsMetaDataManager.findVersion()
        }

        val updateAvailable = onlineVersion != currentTipsVersion
        val newVersion = onlineVersion.toInt() // always take the online version, even if it is lower (rollback)

        if (updateAvailable) {
            downloadOrSwapTips(newVersion)
        }
    }

    private fun downloadOrSwapTips(newVersion: Int) {
        val tipsZipDownloadFile = fileUtil.tipsZipDownloadFile
        if (tipsZipDownloadFile != null && tipsZipDownloadFile.exists()) {
            installDownloadedTips()
        } else {
            downloadTips(newVersion)
        }
    }

    /**
     * Verify that the tips are in good working order (tables exist, etc)
     * @return true if tips are good
     */
    fun verifyTips(): Boolean {
        return try {
            tipManager.findCount() > 0
        } catch (e: Exception) {
            Timber.e(e, "Failed to verify tips")
            false
        }

    }

    fun installDownloadedTips(): Boolean {
        Timber.d("Installing tips from download/update")
        bus.post(StartupProgressEvent("Swapping tips", true))

        val tempTipsDirectory = fileUtil.tempTipsDir
        val tempTipsFile = fileUtil.tempTipsFile
        val newTipsZipFile = fileUtil.tipsZipDownloadFile

        // make sure there is a Tips file to install
        if (newTipsZipFile == null || !newTipsZipFile.exists()) {
            return false
        }

        // Extract the db file from the zip to a temp zip file
        val unzipSuccessful = ldsZipUtil.unZip(newTipsZipFile.absolutePath, tempTipsDirectory.absolutePath)

        // Rename the new temp tips database (it is zipped as Catalog.sqlite)
        val zipDbFile = File(fileUtil.tempTipsDir, GLFileUtil.ZIP_ENTRY_CATALOG_DB_NAME)
        zipDbFile.renameTo(tempTipsFile)

        // verify that the temp tips file got created
        if (!unzipSuccessful || !tempTipsFile.exists()) {
            Timber.e("Failed to unzip tips (tips database does NOT exist)... deleting downloaded tips zip file...")
            FileUtils.deleteQuietly(newTipsZipFile)
            return false
        }

        // cleanup any existing tips
        val tipsDirectory = fileUtil.tipsDir
        removeExistingTips(tipsDirectory)

        // move the temp tips to the final location
        try {
            FileUtils.moveDirectory(tempTipsDirectory, tipsDirectory)
        } catch (e: IOException) {
            Timber.e(e, "Failed to move temp tips")
            deleteTempTips(newTipsZipFile)
            return false
        }

        // add the new tips to dbtools
        prepareNewTips(fileUtil.tipsFile)

        // don't archive if the database is bad
        return if (verifyTips()) {
            // Archive latest tips zip file
            fileUtil.archiveTipsZip(newTipsZipFile, fileUtil.tipsZipArchiveLatestFile)
            true
        } else {
            Timber.e("Failed to verify new tips... deleting downloaded tips zip file and extracted tips database...")
            removeExistingTips(tipsDirectory)
            deleteTempTips(newTipsZipFile)
            false
        }
    }

    private fun deleteTempTips(newTipsZipFile: File) {
        FileUtils.deleteQuietly(newTipsZipFile)
        try {
            FileUtils.deleteDirectory(fileUtil.tempTipsDir)
        } catch (e: IOException) {
            Timber.w("Failed to delete temp tips directory")
        }

    }

    private fun installArchivedTips(archiveTipsZipFile: File) {
        Timber.d("Installing tips from archive")
        bus.post(StartupProgressEvent("Swapping tips", true))

        val tipsFile = fileUtil.tipsFile
        val tipsDir = fileUtil.tipsDir

        // cleanup any existing tips
        removeExistingTips(tipsDir)

        // extract tips from archive zip
        ldsZipUtil.unZip(archiveTipsZipFile.absolutePath, tipsDir.absolutePath)

        // Rename the new tips database (it is zipped as Catalog.sqlite)
        val zipDbFile = File(fileUtil.tipsDir, GLFileUtil.ZIP_ENTRY_CATALOG_DB_NAME)
        zipDbFile.renameTo(tipsFile)

        prepareNewTips(tipsFile)
    }

    companion object {

        // State Variables
        private val checkInProgress = AtomicBoolean(false)
    }
}
