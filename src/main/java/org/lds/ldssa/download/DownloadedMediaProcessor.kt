package org.lds.ldssa.download

import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.event.download.DownloadCompletedEvent
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMedia
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItem
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager
import org.lds.ldssa.util.GLFileUtil
import org.lds.mobile.download.DownloadManagerHelper
import org.lds.mobile.task.RxTask
import pocketbus.Bus
import java.io.File
import java.net.URI
import java.util.HashMap
import javax.inject.Inject

class DownloadedMediaProcessor @Inject
constructor(private val bus: Bus,
            private val analytics: Analytics,
            private val downloadQueueItemManager: DownloadQueueItemManager,
            private val downloadManagerHelper: DownloadManagerHelper,
            private val downloadedMediaManager: DownloadedMediaManager,
            private val fileUtil: GLFileUtil) : RxTask<DownloadQueueItem>() {

    private var androidDownloadId: Long = 0

    fun init(androidDownloadId: Long): DownloadedMediaProcessor {
        this.androidDownloadId = androidDownloadId
        return this
    }

    override fun run(): DownloadQueueItem {
        var queueItem = downloadQueueItemManager.findByAndroidDownloadId(androidDownloadId)
        if (queueItem == null) {
            queueItem = DownloadQueueItem()
        }

        // Validate there is already downloaded media
        val downloadedUri = downloadManagerHelper.getDestinationUri(androidDownloadId)
        val downloadedFile = if (downloadedUri.isNotBlank()) File(URI.create(downloadedUri)) else null
        if (downloadedFile == null || !downloadedFile.exists()) {
            return queueItem
        }

        // Move the item to the media storage directory
        if (!moveDownload(downloadedFile, saveDownloadedItem(queueItem))) {
            return queueItem
        }

        logAnalytics(queueItem)
        return queueItem
    }

    override fun onResult(queueItem: DownloadQueueItem) {
        downloadQueueItemManager.deleteByAndroidDownloadId(androidDownloadId)
        bus.post(DownloadCompletedEvent(androidDownloadId, queueItem.contentItemId, queueItem.subItemId, queueItem.type, true))
        unSubscribe()
    }

    private fun moveDownload(sourceFile: File, downloadedMedia: DownloadedMedia): Boolean {
        val destDirectory = fileUtil.getContentMediaDirectory(downloadedMedia.type)

        //Tries to move the file, retrying once if there was an issue
        val newFile = fileUtil.moveFileWithRetry(sourceFile, destDirectory)

        downloadedMedia.file = if (newFile != null) newFile.name else ""
        downloadedMedia.size = newFile?.length()?.toInt() ?: 0

        // Saves the download
        // This call is in a transaction because multiple threads could be hitting the database at the same time if downloading multiple items.
        // In that scenario some of the records were never being saved even though save() was called.
        downloadedMediaManager.inTransaction {
            downloadedMediaManager.save(downloadedMedia)
        }

        return newFile != null
    }

    private fun saveDownloadedItem(downloadQueueItem: DownloadQueueItem): DownloadedMedia {
        var downloadedMedia = downloadedMediaManager.findByIds(downloadQueueItem.contentItemId, downloadQueueItem.subItemId, downloadQueueItem.tertiaryId, downloadQueueItem.type)

        if (downloadedMedia == null) {
            downloadedMedia = DownloadedMedia().apply {
                contentItemId = downloadQueueItem.contentItemId
                subItemId = downloadQueueItem.subItemId
                tertiaryId = downloadQueueItem.tertiaryId
                type = downloadQueueItem.type
                title = downloadQueueItem.title
            }
        }

        return downloadedMedia
    }

    private fun logAnalytics(queueItem: DownloadQueueItem) {
        val attributes = HashMap<String, String>()
        attributes.put(Analytics.Attribute.URL, queueItem.tertiaryId ?: "")
        attributes.put(Analytics.Attribute.TITLE, queueItem.title)
        attributes.put(Analytics.Attribute.CONTENT_TYPE, queueItem.type.name)
        analytics.postEvent(Analytics.Event.ITEM_INSTALLED, attributes)
    }
}
