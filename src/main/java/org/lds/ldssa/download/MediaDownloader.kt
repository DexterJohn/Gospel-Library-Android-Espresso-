package org.lds.ldssa.download

import org.lds.ldssa.model.database.content.subitem.SubItemManager
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItem
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.mobile.task.RxTask

import javax.inject.Inject

class MediaDownloader @Inject
constructor(private val subItemManager: SubItemManager) : RxTask<Boolean>() {

    private lateinit var glDownloadManager: GLDownloadManager

    private lateinit var title: String
    private lateinit var sourceUri: String
    private lateinit var type: ItemMediaType

    private var contentItemId: Long = 0
    private var subItemId: Long = 0
    private lateinit var tertiaryId: String

    fun init(glDownloadManager: GLDownloadManager, contentItemId: Long, subItemId: Long, tertiaryId: String, title: String, url: String, mediaType: ItemMediaType): MediaDownloader {
        this.glDownloadManager = glDownloadManager
        this.contentItemId = contentItemId
        this.subItemId = subItemId
        this.tertiaryId = tertiaryId
        this.title = title
        this.sourceUri = url
        this.type = mediaType
        return this
    }

    override fun run(): Boolean {
        if (sourceUri.isNotBlank()) {
            handleSingleDownload()
        }

        return true
    }

    override fun onResult(result: Boolean?) {
        unSubscribe()
    }

    private fun handleSingleDownload() {
        title = if (title.isNotBlank()) title else subItemManager.findTitleById(contentItemId, subItemId)
        val sourceUri = this.sourceUri
        val destinationUri = glDownloadManager.getMediaDestinationUri(contentItemId, subItemId, tertiaryId, type).toURI().toString()

        val queueItem = DownloadQueueItem()
        queueItem.type = type
        queueItem.title = title
        queueItem.contentItemId = contentItemId
        queueItem.subItemId = subItemId
        queueItem.tertiaryId = tertiaryId
        queueItem.sourceUri = sourceUri

        glDownloadManager.enqueueDownload(queueItem, destinationUri)
    }

}
