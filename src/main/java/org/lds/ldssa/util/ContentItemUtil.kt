package org.lds.ldssa.util

import android.app.Application
import android.app.DownloadManager
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import org.apache.commons.io.FileUtils
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.download.GLDownloadManager
import org.lds.ldssa.download.InstallProgress
import org.lds.ldssa.download.InstallStatus
import org.lds.ldssa.event.catalog.CatalogReloadEvent
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.download.DownloadManagerHelper
import org.lds.mobile.download.DownloadProgress
import org.lds.mobile.ui.setVisible
import pocketbus.Bus
import timber.log.Timber
import java.io.File
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.experimental.CoroutineContext

@Singleton
class ContentItemUtil
@Inject constructor(private val application: Application,
                    private val fileUtil: GLFileUtil,
                    private val downloadedItemManager: DownloadedItemManager,
                    private val databaseManager: DatabaseManager,
                    private val bus: Bus,
                    private val analytics: Analytics,
                    private val downloadQueueItemManager: DownloadQueueItemManager,
                    private val downloadManager: GLDownloadManager,
                    private val screenUtil: ScreenUtil,
                    private val itemManager: ItemManager,
                    private val cc: CoroutineContextProvider,
                    private val downloadManagerHelper: DownloadManagerHelper) {

    internal enum class ContentItemStatus {
        OPEN, NOT_DOWNLOADED, MISSING_FILE, FAILED_TO_OPEN, CORRUPT
    }

    fun isItemDownloadedAndOpen(contentItemId: Long): Boolean {
        val itemDatabaseName = getDatabaseName(contentItemId)
        val contentItemStatus = openItem(contentItemId, itemDatabaseName)
        return when (contentItemStatus) {
            ContentItemUtil.ContentItemStatus.OPEN -> true
            ContentItemUtil.ContentItemStatus.NOT_DOWNLOADED, ContentItemUtil.ContentItemStatus.MISSING_FILE, ContentItemUtil.ContentItemStatus.FAILED_TO_OPEN, ContentItemUtil.ContentItemStatus.CORRUPT -> {

                removeDatabase(contentItemId, itemDatabaseName)
                false
            }
        }
    }

    private fun openItem(contentItemId: Long, itemDatabaseName: String): ContentItemStatus {
        if (databaseManager.containsDatabase(itemDatabaseName)) {
            return ContentItemStatus.OPEN
        }

        // get the database file and make sure it exists
        val contentItemDatabase = fileUtil.getContentItemDatabase(contentItemId)
        if (!contentItemDatabase.exists()) {
            Timber.w("Content database [%s] file no longer exists.. removing", itemDatabaseName)
            return ContentItemStatus.MISSING_FILE
        }

        // GLA-2576 even though we have content, if missing from downloadedItems table, restore missing records
        //todo discover why all downloaded items go missing sometimes
        downloadManager.repairDownloadedItemsIfAnyMissing(contentItemId)

        // Add the database to the DatabaseManager and open the database
        databaseManager.addDatabase(itemDatabaseName, contentItemDatabase.absolutePath, 0, 0)
        val databaseOpened = databaseManager.openDatabase(itemDatabaseName)
        if (!databaseOpened) {
            Timber.w("Could not open database [%s]... removing", itemDatabaseName)
            return ContentItemStatus.NOT_DOWNLOADED
        }

        // Check for corruption
        // todo: look at function header for notes... remove this all together OR check after opening a database and read errors occur??
        //        if (!databaseUtil.validateDatabase(itemDatabaseName)) {
        //            return ContentItemStatus.CORRUPT;
        //        }

        return ContentItemStatus.OPEN
    }

    private fun removeDatabase(contentItemId: Long, itemDatabaseName: String) {
        // remove reference of this database from DBTools
        databaseManager.removeDatabase(itemDatabaseName)

        // Content Item is made up of a database and directory of files... delete all of them
        deleteContentItem(contentItemId)

        // remove reference that this content/database is downloaded
        downloadedItemManager.deleteByContentItemId(contentItemId)
    }

    fun closeItem(contentItemId: Long) {
        if (databaseManager.containsDatabase(getDatabaseName(contentItemId))) {
            databaseManager.removeDatabase(getDatabaseName(contentItemId))
        }
    }

    fun getOpenedDatabaseName(contentItemId: Long): String {
        val databaseName = getDatabaseName(contentItemId)
        openItem(contentItemId, databaseName) // if the database is already open, this will be a quick call

        return databaseName
    }

    fun getDatabaseName(contentItemId: Long): String {
        return CONTENT_DATABASE_PREFIX + contentItemId
    }

    fun deleteContentItem(contentItemId: Long) {
        Timber.i("Deleting contentItem file: %d", contentItemId)

        // make sure the database is closed and removed
        databaseManager.removeDatabase(getDatabaseName(contentItemId))

        // delete content directory
        val contentDir = fileUtil.getContentItemDir(contentItemId)
        FileUtils.deleteQuietly(contentDir)
    }


    /**
     * User selects "Remove" of a content item
     * Requirements:
     * - Post Analytics
     * - Close any tabs that are viewing this content
     * - Cancel any downloads in progress
     * - Remove any downloaded files
     * - Notify refresh catalog
     */
    fun userRemoveDownloadedContentItems(contentItemIds: List<Long>?) {
        launch(cc.commonPool) {
            if (contentItemIds == null || contentItemIds.isEmpty()) {
                return@launch
            }

            var itemFile: File
            for (contentItemId in contentItemIds) {
                itemFile = fileUtil.getContentItemDatabase(contentItemId)
                if (itemFile.exists()) {
                    deleteContentItem(contentItemId)

                    // clean up database references
                    downloadedItemManager.deleteByContentItemId(contentItemId)
                    logAnalytics(contentItemId)
                } else {
                    val downloadQueueItem = downloadQueueItemManager.findByContentItemIdAndType(contentItemId, ItemMediaType.CONTENT)
                    if (downloadQueueItem != null) {
                        downloadManager.cancelDownload(downloadQueueItem.androidDownloadId)
                    }
                }

                screenUtil.closeScreenTasks(contentItemId)
            }


            bus.post(CatalogReloadEvent())
        }
    }

    private fun logAnalytics(contentItemId: Long) {
        val item = itemManager.findByRowId(contentItemId) ?: return

        val attributes = HashMap<String, String>()
        attributes.put(Analytics.Attribute.CONTENT_LANGUAGE, item.languageId.toString())
        attributes.put(Analytics.Attribute.CONTENT_URI, item.uri)
        attributes.put(Analytics.Attribute.CONTENT_TYPE, ItemMediaType.CONTENT.name)
        analytics.postEvent(Analytics.Event.ITEM_UNINSTALLED, attributes)
    }

    fun getInstallStatusLiveData(contentItemId: Long, coroutineContext: CoroutineContext = cc.commonPool): LiveData<InstallProgress> {
        return MediatorInstallStatusLiveData(contentItemId, coroutineContext, this, downloadManagerHelper, downloadQueueItemManager).asLiveData()
    }

    private class MediatorInstallStatusLiveData(
            val contentItemId: Long,
            val coroutineContext: CoroutineContext,
            val contentItemUtil: ContentItemUtil,
            val downloadManagerHelper: DownloadManagerHelper,
            val downloadQueueItemManager: DownloadQueueItemManager) {
        private val installProgress = MediatorLiveData<InstallProgress>()

        init {
            startDownloadWatch()
        }

        private fun startDownloadWatch() {
            //Timber.d("Download WATCH START for contentItemId: [$contentItemId]  ")

            val downloadQueLiveData = downloadQueueItemManager.findByContentItemIdAndTypeLiveData(contentItemId)
            installProgress.addSource(downloadQueLiveData) { downloadQueueItem ->
                downloadQueueItem ?: return@addSource
                //Timber.d("Download WATCH for contentItemId: [$contentItemId] hasObservers: [${installProgress.hasObservers()}]")

                installProgress.value = InstallProgress(InstallStatus.DOWNLOAD_RUNNING, 0)
                installProgress.removeSource(downloadQueLiveData)
                startDownloadPolling(downloadQueueItem.androidDownloadId)
            }
        }

        private fun startDownloadPolling(androidDownloadId: Long) {
            //Timber.d("Download PROGRESS START for contentItemId: [$contentItemId]")

            val downloadProgressLiveData = downloadManagerHelper.getDownloadProgressLiveData(androidDownloadId, coroutineContext)
            installProgress.addSource(downloadProgressLiveData) { downloadProgress ->
                //Timber.d("Download PROGRESS for contentItemId: [$contentItemId] status: [${downloadProgress?.statusId}]  hasObservers: [${installProgress.hasObservers()}]")

                installProgress.value = contentItemUtil.convertDownloadProgressToInstallProgress(downloadProgress)
                when (downloadProgress?.statusId) {
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        //Timber.d("START Install Progress for contentItemId: [$contentItemId]")
                        installProgress.removeSource(downloadProgressLiveData)
                        startInstallPolling()
                    }
                    DownloadManagerHelper.GL_DOWNLOAD_STATUS_DOES_NOT_EXIST -> {
                        // cancelled... go back to watch
                        // Timber.d("Download PROGRESS for contentItemId: [$contentItemId] CANCELLED")
                        installProgress.removeSource(downloadProgressLiveData)

                        // make sure the DownloadQueue item is removed
                        downloadQueueItemManager.deleteByAndroidDownloadId(androidDownloadId)

                        startDownloadWatch()
                    }
                }
            }
        }

        private fun startInstallPolling() {
            //Timber.d("Download INSTALL START for contentItemId: [$contentItemId]")
            val installProgressLiveData = contentItemUtil.getInstallationStatusLiveData(contentItemId, coroutineContext)
            installProgress.addSource(installProgressLiveData) { progress ->
                progress ?: return@addSource
                //Timber.d("Download INSTALL for contentItemId: [$contentItemId]  hasObservers: [${installProgress.hasObservers()}]")

                installProgress.value = progress

                when (progress.status) {
                    InstallStatus.INSTALL_SUCCESSFUL, InstallStatus.DOWNLOAD_FAILED, InstallStatus.DOWNLOAD_DOES_NOT_EXIST, InstallStatus.INSTALL_FAILED -> {
                        //Timber.d("Download INSTALL FINISHED status: [${progress.status}] contentItemId: [$contentItemId]  hasObservers: [${installProgress.hasObservers()}]")
                        installProgress.removeSource(installProgressLiveData)
                        startDownloadWatch()
                    }
                    else -> {
                        // continue to poll install
                    }
                }
            }
        }

        fun asLiveData(): LiveData<InstallProgress> = installProgress
    }

    private fun getInstallationStatusLiveData(contentItemId: Long,
                                              coroutineContext: CoroutineContext = CommonPool,
                                              initialDelay: Long = DEFAULT_POLL_INITIAL_DELAY_MS,
                                              pollInterval: Long = DEFAULT_POLL_INTERVAL_MS): LiveData<InstallProgress> {
        return object : LiveData<InstallProgress>() {

            private lateinit var job: Job

            override fun onActive() {
                job = Job()
                getData()
            }

            override fun onInactive() {
                job.cancel()
            }

            private fun getData() {
                launch(coroutineContext + job) {
                    delay(initialDelay)
                    hasActiveObservers()

                    var installProgress = getCurrentInstallStatus(contentItemId)
                    postValue(installProgress)

                    while (isInstalling(installProgress.status)) {
                        //Timber.d("Install Progress for contentItemId: [$contentItemId]")
                        delay(pollInterval)

                        installProgress = getCurrentInstallStatus(contentItemId)
                        postValue(installProgress)
                    }
                }
            }

            private fun isInstalling(installStatus: InstallStatus): Boolean {
                return when (installStatus) {
                    InstallStatus.INSTALL_RUNNING -> true
                    InstallStatus.INSTALL_FAILED -> false
                    InstallStatus.INSTALL_SUCCESSFUL -> false
                    else -> false
                }
            }
        }
    }

    private fun getCurrentInstallStatus(contentItemId: Long): InstallProgress {
        // Check to see if the item has been inserted into the database
        val downloadedItemExists = downloadedItemManager.findContentItemIdExists(contentItemId)
        if (downloadedItemExists) {
            // If the item is in the database then make sure the file exists
            val file = fileUtil.getContentItemDatabase(contentItemId)
            return if (file.exists()) {
                InstallProgress(InstallStatus.INSTALL_SUCCESSFUL, InstallProgress.INDETERMINATE_PROGRESS)
            } else {
                InstallProgress(InstallStatus.INSTALL_FAILED, InstallProgress.INDETERMINATE_PROGRESS)
            }
        }

        return InstallProgress(InstallStatus.INSTALL_RUNNING, InstallProgress.INDETERMINATE_PROGRESS)
    }

    private fun convertDownloadProgressToInstallProgress(downloadProgress: DownloadProgress?): InstallProgress {
        if (downloadProgress == null) {
            return InstallProgress(InstallStatus.DOWNLOAD_RUNNING, INDETERMINATE_PROGRESS)
        }

        return when (downloadProgress.statusId) {
            DownloadManager.STATUS_SUCCESSFUL -> InstallProgress(InstallStatus.INSTALL_RUNNING, INDETERMINATE_PROGRESS)
            DownloadManager.STATUS_PAUSED -> InstallProgress(InstallStatus.DOWNLOAD_PAUSED, downloadProgress.progress, downloadProgress.failureReasonId)
            DownloadManager.STATUS_PENDING -> InstallProgress(InstallStatus.DOWNLOAD_PENDING, downloadProgress.progress)
            DownloadManager.STATUS_FAILED -> InstallProgress(InstallStatus.DOWNLOAD_FAILED, downloadProgress.progress, downloadProgress.failureReasonId)
            DownloadManagerHelper.GL_DOWNLOAD_STATUS_DOES_NOT_EXIST -> InstallProgress(InstallStatus.DOWNLOAD_DOES_NOT_EXIST, downloadProgress.progress, downloadProgress.failureReasonId)
            else -> InstallProgress(InstallStatus.DOWNLOAD_RUNNING, downloadProgress.progress)
        }
    }

    fun showInstallProgress(installProgress: InstallProgress, statusProgressBar: ProgressBar,
                            statusTextView: TextView? = null,
                            installFinishedCallback: (success: Boolean) -> Unit = {}) {
        when (installProgress.status) {
            InstallStatus.DOWNLOAD_PAUSED, InstallStatus.DOWNLOAD_PENDING -> {
                statusProgressBar.isIndeterminate = true
                statusProgressBar.setVisible(true)

                val failureText = when (installProgress.failureReasonId) {
                    DownloadManager.PAUSED_WAITING_FOR_NETWORK, DownloadManager.PAUSED_QUEUED_FOR_WIFI -> application.getString(R.string.requested_paused_network_ellipsis)
                    DownloadManager.PAUSED_WAITING_TO_RETRY -> application.getString(R.string.requested_paused_retry_ellipsis)
                    else -> ""
                }

                statusTextView?.text = failureText
            }
            InstallStatus.DOWNLOAD_RUNNING -> {
                statusProgressBar.setVisible(true)
                statusProgressBar.isIndeterminate = installProgress.downloadProgress <= MIN_PROGRESS_DISPLAY_AMOUNT
                statusProgressBar.progress = installProgress.downloadProgress

                statusTextView?.text = application.getString(R.string.downloading_ellipsis)
            }
            InstallStatus.DOWNLOAD_FAILED -> {
                statusProgressBar.setVisible(false)
                statusProgressBar.isIndeterminate = true
                statusProgressBar.setVisible(true)
            }
            InstallStatus.DOWNLOAD_DOES_NOT_EXIST -> { // probably cancelled
                statusProgressBar.setVisible(false)
            }
            InstallStatus.INSTALL_RUNNING -> {
                statusProgressBar.isIndeterminate = true
                statusProgressBar.setVisible(true)

                statusTextView?.text = application.getString(R.string.installing_ellipsis)
            }
            InstallStatus.INSTALL_SUCCESSFUL -> {
                installFinishedCallback(true)
                statusProgressBar.setVisible(false)

                statusTextView?.text = application.getString(R.string.finalizing_ellipsis)
            }
            else -> statusProgressBar.setVisible(false)
        }
    }

    companion object {
        //Used so the user will always be able to see some sort of progress (indeterminate, or at least 5%)
        private val MIN_PROGRESS_DISPLAY_AMOUNT = (DownloadManagerHelper.MAX_PROGRESS * .05).toInt()

        val INDETERMINATE_PROGRESS = -1
        private const val DEFAULT_POLL_INITIAL_DELAY_MS: Long = 0
        private const val DEFAULT_POLL_INTERVAL_MS: Long = 100
        const val CONTENT_DATABASE_PREFIX = "item-"
        const val CORRUPTION_CHECK_PASSED = "ok"
    }
}
