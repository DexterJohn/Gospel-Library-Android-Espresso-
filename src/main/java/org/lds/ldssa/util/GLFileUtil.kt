package org.lds.ldssa.util

import android.app.Application
import android.os.Environment
import org.apache.commons.io.FileUtils
import org.lds.ldssa.R
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.mobile.util.LdsZipUtil
import timber.log.Timber
import java.io.File
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GLFileUtil @Inject
constructor(private val application: Application, private val zipUtil: LdsZipUtil, private val toastUtil: ToastUtil) {

    val contentAudioTempDownloadDirectory: File
        get() {
            val dir = File(application.externalCacheDir, CONTENT_AUDIO_DIRECTORY_NAME)
            checkOrMakeDirectories(dir)
            return dir
        }

    val contentVideoTempDownloadDirectory: File
        get() {
            val dir = File(application.externalCacheDir, CONTENT_VIDEO_DIRECTORY_NAME)
            checkOrMakeDirectories(dir)
            return dir
        }

    val contentMediaTempDownloadDirectory: File
        get() {
            val dir = File(application.externalCacheDir, CONTENT_MEDIA_DIRECTORY_NAME)
            checkOrMakeDirectories(dir)
            return dir
        }

    /**
     * Directory where temp sync files go
     */
    val annotationSyncDir: File
        get() = getDirectory(SYNC_DIRECTORY_NAME)

    /**
     * Directory where user specific databases go
     */
    val userdataDir: File
        get() = getDirectory(USERDATA_DIRECTORY_NAME)

    val syncAnnotations1OutFile: File
        get() = File(annotationSyncDir, SYNC_ANNOTATION_1_OUT_FILENAME)

    val syncAnnotations2InFile: File
        get() = File(annotationSyncDir, SYNC_ANNOTATION_2_IN_FILENAME)


    val syncAnnotations3OutFile: File
        get() = File(annotationSyncDir, SYNC_ANNOTATION_3_OUT_FILENAME)

    val syncAnnotations4InFile: File
        get() = File(annotationSyncDir, SYNC_ANNOTATION_4_IN_FILENAME)


    val syncFolders1OutFile: File
        get() = File(annotationSyncDir, SYNC_FOLDER_1_OUT_FILENAME)

    val syncFolders2InFile: File
        get() = File(annotationSyncDir, SYNC_FOLDER_2_IN_FILENAME)


    val syncFoldersTest1OutFile: File
        get() = File(annotationSyncDir, SYNC_FOLDER_TEST1_OUT_FILENAME)

    val syncFoldersTest2InFile: File
        get() = File(annotationSyncDir, SYNC_FOLDER_TEST2_IN_FILENAME)

    val syncAnnotationsTest1OutFile: File
        get() = File(annotationSyncDir, SYNC_ANNOTATION_TEST1_OUT_FILENAME)

    val syncAnnotationsTest2InFile: File
        get() = File(annotationSyncDir, SYNC_ANNOTATION_TEST2_IN_FILENAME)

    val contentItemBaseDirectory: File
        get() = getDirectory(CONTENT_DATABASES_DIRECTORY_NAME)

    // ********** Tips *************
    val tipsDir: File
        get() = getDirectory(TIPS_DIRECTORY_NAME)

    val tipsFile: File
        get() = File(tipsDir, DEFAULT_TIPS_DB_NAME)

    val tipsZipDownloadFile: File?
        get() = getDownloadFile(DEFAULT_TIPS_ZIP_FILENAME)

    val tempTipsFile: File
        get() = File(tempTipsDir, DEFAULT_TIPS_DB_NAME)

    val tempTipsDir: File
        get() = getDirectory(DEFAULT_TEMP_TIPS_DIRECTORY_NAME)

    private val tipsArchiveDir: File
        get() = getDirectory(TIPS_ARCHIVE_DIRECTORY_NAME)

    val tipsZipArchiveLatestFile: File
        get() = File(tipsArchiveDir, "tips-latest.zip")

    // note: Environment.DIRECTORY_DOWNLOADS is null in a JUnit runtime
    val downloadsDir: File?
        get() {
            val externalDownloadDir = application.getExternalFilesDir("Download")

            if (externalDownloadDir == null) {
                Timber.e("Failed to getDownloadsDir() (external dir == null) Storage State: [%s]", Environment.getExternalStorageState())
                toastUtil.showLong(R.string.download_dir_error)
            }

            return externalDownloadDir
        }

    private val catalogDir: File
        get() = getDirectory(CATALOG_DIRECTORY_NAME)

    private val catalogArchiveDir: File
        get() = getDirectory(CATALOG_ARCHIVE_DIRECTORY_NAME)

    val catalogFile: File
        get() = File(catalogDir, DEFAULT_CATALOG_DB_NAME)

    val tempCatalogFile: File
        get() = File(catalogDir, DEFAULT_TEMP_CATALOG_DB_NAME)

    val tempCatalogDiffFile: File
        get() = File(catalogDir, DEFAULT_TEMP_CATALOG_DIFF_NAME)

    val catalogZipArchiveLatestCoreFile: File
        get() = File(catalogArchiveDir, "core-latest.zip")

    val catalogArchiveFile: File
        get() = File(catalogArchiveDir, DEFAULT_ARCHIVE_CATALOG_DB_NAME)

    private val feedbackDir: File
        get() = getDirectory(FEEDBACK_DIRECTORY_NAME)

    val feedbackLastSyncErrorFile: File
        get() {
            val dir = feedbackDir
            checkOrMakeDirectories(dir)
            return File(dir, LAST_SYNC_LOG_FILENAME)
        }

    /**
     * Get a File reference on either the SD Card if it is available or in the databases directory of the application
     * data.
     *
     * @param fileName The name of the file
     * @return The File with all directories created.
     */
    fun getDbFile(fileName: String): File {
        val file = application.getDatabasePath(fileName)
        val dir = File(file.parent)
        checkOrMakeDirectories(dir)
        return file
    }

    /**
     * Directory safely used by the system DownloadManager (in shared space)
     */
    private fun getDownloadFile(filename: String): File? {
        val dir = downloadsDir

        return if (dir != null) {
            checkOrMakeDirectories(dir)
            File(dir, filename)
        } else {
            null
        }
    }

    fun getContentMediaFile(fileName: String, mediaType: ItemMediaType): File {
        return File(getContentMediaDirectory(mediaType), fileName)
    }

    fun getContentMediaDirectory(type: ItemMediaType): File {
        return when (type) {
            ItemMediaType.AUDIO -> getDirectory(CONTENT_AUDIO_DIRECTORY_NAME)
            ItemMediaType.VIDEO -> getDirectory(CONTENT_VIDEO_DIRECTORY_NAME)
            else -> getDirectory(CONTENT_MEDIA_DIRECTORY_NAME)
        }
    }

    fun getDirectory(baseDirName: String?): File {
        val dir: File
        if (baseDirName != null) {
            dir = File(application.filesDir, baseDirName)
            checkOrMakeDirectories(dir)
        } else {
            dir = application.filesDir
        }
        return dir
    }

    fun getThumbsFile(screenId: Long): File {
        val dir = getDirectory(THUMBNAILS_DIRECTORY_NAME)
        return File(dir, screenId.toString() + ".png")
    }

    // ********** Content Items  **********
    fun getContentItemTempDirname(contentItemId: Long): String {
        return contentItemId.toString() + ".tmp"
    }

    fun getContentItemZipDownloadFile(contentItemId: Long): File? {
        return getDownloadFile(contentItemId.toString() + ".zip")
    }

    fun getContentItemTempDir(contentItemId: Long): File {
        return File(contentItemBaseDirectory, getContentItemTempDirname(contentItemId))
    }

    fun getContentItemDir(contentItemId: Long): File {
        return File(contentItemBaseDirectory, contentItemId.toString())
    }

    fun getContentItemTempDatabase(contentItemId: Long): File {
        return File(getContentItemTempDir(contentItemId), CONTENT_ITEM_DB_NAME)
    }

    fun getContentItemDatabase(contentItemId: Long): File {
        return File(getContentItemDir(contentItemId), CONTENT_ITEM_DB_NAME)
    }

    // ********** Catalog **********

    fun getZipCatalogDiffFilename(currentCatalogVersion: Long): String {
        return currentCatalogVersion.toString() + ZIP_CATALOG_DIFF_EXTENSION
    }

    /**
     * @param name Name of the catalog: "core", "local-leader",ect
     */
    fun getCatalogZipDownloadFile(name: String): File? {
        return getDownloadFile("catalog.$name.zip")
    }

    fun getRoleCatalogZipArchiveFile(catalogName: String): File {
        return File(catalogArchiveDir, "role-$catalogName.zip")
    }

    /**
     * @param name Name of the catalog: "core", "local-leader",ect
     */
    fun getCatalogDiffZipDownloadFile(name: String): File? {
        return getDownloadFile("catalog.diff.$name.zip")
    }

    /**
     * Unzip catalog.zip file
     *
     * @return catalog sqlite file
     */
    fun unZipAssetCatalog(fileDir: File, fileName: String): File? {
        Timber.i("Extracting catalog from assets...")

        zipUtil.unZipAsset(fileName, fileDir.path)
        val files = fileDir.listFiles() ?: return null

        files.forEach { file ->
            if (file.name.endsWith(".sqlite")) {
                return file
            }
        }

        return null
    }

    private fun checkOrMakeDirectories(dir: File?) {
        if (dir != null && !dir.exists()) {
            createDirectories(dir)
        }
    }

    @Synchronized private fun createDirectories(dir: File) {
        if (!dir.exists() && !dir.mkdirs()) {
            throw IllegalStateException("""Cannot create directory: [${dir.absolutePath}]""")
        }
    }

    /**
     * Copy the internal sync error log to external
     *
     * @return Newly create file in external directory
     */
    fun copyFeedbackLastSyncErrorFileToExternal(): File? {
        // Android M note:
        // Starting in API level 19, this permission is not required to read/write files in your application-specific directories
        // returned by getExternalFilesDir(String) and getExternalCacheDir().
        // http://developer.android.com/reference/android/Manifest.permission.html#WRITE_EXTERNAL_STORAGE

        return try {
            val dir = application.externalCacheDir
            checkOrMakeDirectories(dir)
            val externalFile = File(dir, LAST_SYNC_LOG_FILENAME)

            FileUtils.copyFile(feedbackLastSyncErrorFile, externalFile)
            externalFile
        } catch (e: IOException) {
            Timber.e(e, "Failed to copy feedback last sync error file to external")
            null
        }

    }

    fun moveFileWithRetry(sourceFile: File, destDirectory: File): File? {
        var newFile: File? = File(destDirectory, sourceFile.name)

        if (newFile!!.exists()) {
            FileUtils.deleteQuietly(newFile)
        }

        try {
            FileUtils.moveFile(sourceFile, newFile)
        } catch (e: IOException) {
            try {
                FileUtils.moveFile(sourceFile, newFile)
            } catch (e1: IOException) {
                newFile = null
            }

        }

        return newFile
    }

    fun getRoleCatalogDbName(catalogName: String): File {
        return File(catalogDir, "role-" + catalogName)
    }

    fun archiveCatalog(srcFile: File, destFile: File) {
        archiveFile(srcFile, destFile, catalogArchiveDir, "catalog")
    }

    fun archiveTipsZip(srcFile: File, destFile: File) {
        archiveFile(srcFile, destFile, tipsArchiveDir, "tips")
    }

    private fun archiveFile(srcFile: File, destFile: File, archiveDir: File, archiveType: String) {
        // skip if this is the same file
        if (srcFile.absolutePath == destFile.absolutePath) {
            return
        }

        if (!archiveDir.exists() && !archiveDir.mkdirs()) {
            Timber.e("Cannot create %s archive directory", archiveType)
            return
        }

        if (destFile.exists()) {
            FileUtils.deleteQuietly(destFile)
        }

        if (!srcFile.exists()) {
            Timber.e("Cannot archive %s: Source file [%s] does not exist", archiveType, srcFile.absolutePath)
        }

        try {
            FileUtils.moveFile(srcFile, destFile)
        } catch (e: IOException) {
            Timber.e(e, "Failed to archive %s [%s]", archiveType, srcFile.absolutePath)
        }
    }

    companion object {
        const val CATALOG_DIRECTORY_NAME = "catalog"
        const val CATALOG_ARCHIVE_DIRECTORY_NAME = "catalog-archive"
        const val TIPS_ARCHIVE_DIRECTORY_NAME = "tips-archive"
        const val TIPS_DIRECTORY_NAME = "tips"
        const val CONTENT_DIRECTORY_NAME = "content"
        const val CONTENT_DATABASES_DIRECTORY_NAME = CONTENT_DIRECTORY_NAME + "/content-databases"
        const val CONTENT_MEDIA_DIRECTORY_NAME = CONTENT_DIRECTORY_NAME + "/content-media" // fallback folder
        const val CONTENT_AUDIO_DIRECTORY_NAME = CONTENT_DIRECTORY_NAME + "/content-audio"
        const val CONTENT_VIDEO_DIRECTORY_NAME = CONTENT_DIRECTORY_NAME + "/content-video"
        const val THUMBNAILS_DIRECTORY_NAME = "thumb"
        const val USERDATA_DIRECTORY_NAME = "userdata"
        const val SYNC_DIRECTORY_NAME = "sync"
        const val SYNC_ANNOTATION_1_OUT_FILENAME = "annotation-1-out.json"
        const val SYNC_ANNOTATION_2_IN_FILENAME = "annotation-2-changes-in.json"
        const val SYNC_ANNOTATION_3_OUT_FILENAME = "annotation-3-requested-out.json"
        const val SYNC_ANNOTATION_4_IN_FILENAME = "annotation-4-in.json"
        const val SYNC_ANNOTATION_TEST1_OUT_FILENAME = "annotation-test1-out.json"
        const val SYNC_ANNOTATION_TEST2_IN_FILENAME = "annotation-test2-in.json"
        const val SYNC_FOLDER_1_OUT_FILENAME = "folder-1-out.json"
        const val SYNC_FOLDER_2_IN_FILENAME = "folder-2-in.json"
        const val SYNC_FOLDER_TEST1_OUT_FILENAME = "folder-test1-out.json"
        const val SYNC_FOLDER_TEST2_IN_FILENAME = "folder-test2-in.json"
        const val CONTENT_ITEM_DB_NAME = "package.sqlite"
        const val ZIP_ENTRY_CATALOG_DB_NAME = "Catalog.sqlite"
        const val DEFAULT_CATALOG_DB_NAME = "catalog"
        const val DEFAULT_ARCHIVE_CATALOG_DB_NAME = "catalog-archive"
        const val DEFAULT_TEMP_CATALOG_DB_NAME = "catalog.tmp"
        const val ZIP_CATALOG_DIFF_EXTENSION = ".diff"
        const val DEFAULT_TEMP_CATALOG_DIFF_NAME = "catalog.diff"
        const val DEFAULT_TIPS_DB_NAME = "tips"
        const val DEFAULT_TIPS_ZIP_FILENAME = "tips.zip"
        const val DEFAULT_TEMP_TIPS_DIRECTORY_NAME = "tips-temp"
        const val BUNDLE_CATALOG_DB_NAME = "catalog.zip"
        const val BUNDLE_TIPS_DB_NAME = "tips.zip"
        const val FEEDBACK_DIRECTORY_NAME = "feedback"
        const val LAST_SYNC_LOG_FILENAME = "last-sync-error.txt"
    }
}
