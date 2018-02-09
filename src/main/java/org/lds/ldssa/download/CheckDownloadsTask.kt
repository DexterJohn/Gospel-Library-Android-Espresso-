package org.lds.ldssa.download

import android.app.DownloadManager
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItem
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.ldssa.util.CatalogUpdateUtil
import org.lds.mobile.download.DownloadManagerHelper
import org.lds.mobile.task.RxTask
import timber.log.Timber
import javax.inject.Inject

/**
 * Verify and Restart any failed downloads and force reprocessing on already downloaded items
 */
class CheckDownloadsTask @Inject
constructor(private val downloadQueueItemManager: DownloadQueueItemManager,
            private val downloadManagerHelper: DownloadManagerHelper,
            private val glDownloadManager: GLDownloadManager,
            private val catalogUpdateUtil: CatalogUpdateUtil) : RxTask<Boolean>() {

    override fun run(): Boolean {
        val downloadQueueItems = downloadQueueItemManager.findAll()
        Timber.i("Found unfinished downloads count: %d", downloadQueueItems.size)

        for (downloadQueueItem in downloadQueueItems) {
            val downloadProgress = downloadManagerHelper.getDownloadProgress(downloadQueueItem.androidDownloadId)

            val statusId = downloadProgress.statusId
            val failureReasonId = downloadProgress.failureReasonId
            Timber.i("Checking Download Id: %d  statusId: %d  failure reasonId: %d", downloadQueueItem.androidDownloadId, statusId, failureReasonId)

            when (statusId) {
                DownloadManager.STATUS_PENDING, DownloadManager.STATUS_RUNNING, DownloadManager.STATUS_PAUSED -> {
                }
                DownloadManager.STATUS_SUCCESSFUL -> handleDownloadSuccess(downloadQueueItem)
                DownloadManager.STATUS_FAILED -> handleFailedDownload(downloadQueueItem)
                else -> handleFailedDownload(downloadQueueItem)
            }
        }

        return true
    }

    private fun handleDownloadSuccess(downloadQueueItem: DownloadQueueItem) {
        if (downloadQueueItemManager.isItemBeingProcessed(downloadQueueItem.id)) {
            if (glDownloadManager.getActiveDownloadProcessorCount() == 0) {
                // process of this item might have failed (the database has it is marked "in process", but there are no active processors)
                glDownloadManager.cancelDownload(downloadQueueItem.androidDownloadId)
            }
        } else {
            // At this point... the download was successful, but it is not being processed (the download could have finished with GL not running)
            // Let's try to process the download now...
            glDownloadManager.processDownloadedQueueItem(downloadQueueItem)
        }
    }

    private fun handleFailedDownload(downloadQueueItem: DownloadQueueItem) {
        if (downloadQueueItem.type === ItemMediaType.CATALOG_DIFF) {
            Timber.e("Catalog diff update failed. Downloading full catalog. Version = %d", downloadQueueItem.version)
            catalogUpdateUtil.downloadCoreCatalog(downloadQueueItem.version, true)
            downloadQueueItemManager.deleteByAndroidDownloadId(downloadQueueItem.androidDownloadId)
            return
        }

        when (downloadManagerHelper.getFailureReasonId(downloadQueueItem.androidDownloadId)) {
            DownloadManager.ERROR_INSUFFICIENT_SPACE -> {
                logError(downloadQueueItem, "Insufficient Space")
                downloadQueueItemManager.deleteByAndroidDownloadId(downloadQueueItem.androidDownloadId)
            }
            DownloadManager.ERROR_FILE_ALREADY_EXISTS -> {
                logError(downloadQueueItem, "File already exists")
                // try to process the downloaded file
                handleDownloadSuccess(downloadQueueItem)
            }
            DownloadManager.ERROR_CANNOT_RESUME -> {
                logError(downloadQueueItem, "Cannot Resume")
                glDownloadManager.cancelDownload(downloadQueueItem.androidDownloadId)
            }
            DownloadManager.ERROR_DEVICE_NOT_FOUND -> {
                logError(downloadQueueItem, "Device not found")
                glDownloadManager.cancelDownload(downloadQueueItem.androidDownloadId)
            }
            DownloadManager.ERROR_FILE_ERROR -> {
                logError(downloadQueueItem, "File Error")
                downloadQueueItemManager.deleteByAndroidDownloadId(downloadQueueItem.androidDownloadId)
            }
            DownloadManager.ERROR_HTTP_DATA_ERROR -> {
                logError(downloadQueueItem, "Data error")
                downloadQueueItemManager.deleteByAndroidDownloadId(downloadQueueItem.androidDownloadId)
            }
            else -> {
                logError(downloadQueueItem, "Unknown")
                downloadQueueItemManager.deleteByAndroidDownloadId(downloadQueueItem.androidDownloadId)
            }
        }
    }

    private fun logError(downloadQueueItem: DownloadQueueItem, message: String) {
        Timber.e("Failed to download [%s] androidDownloadId [%d] REASON [%s]", downloadQueueItem.title, downloadQueueItem.androidDownloadId, message)
    }

    override fun onResult(result: Boolean?) {
        unSubscribe()
    }
}
