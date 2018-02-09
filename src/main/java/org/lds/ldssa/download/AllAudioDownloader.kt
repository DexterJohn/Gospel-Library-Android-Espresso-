package org.lds.ldssa.download

import android.net.Uri
import org.lds.ldssa.event.download.DownloadCancelAllEvent
import org.lds.ldssa.model.database.content.navitem.NavItemManager
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItem
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItem
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.mobile.task.RxTask
import pocketbus.Bus
import pocketbus.Subscribe
import javax.inject.Inject

class AllAudioDownloader @Inject
constructor(private val bus: Bus,
            private val navItemManager: NavItemManager) : RxTask<Boolean>() {

    private lateinit var glDownloadManager: GLDownloadManager
    private var contentItemId: Long = 0
    private var cancelRequested = false

    //Only used when user selects "Download all audio for [book]"
    private lateinit var relatedAudioItems: List<RelatedAudioItem>

    fun init(downloadManager: GLDownloadManager, contentItemId: Long, downloadAudioItems: List<RelatedAudioItem>): AllAudioDownloader {
        this.glDownloadManager = downloadManager
        this.contentItemId = contentItemId
        this.relatedAudioItems = downloadAudioItems
        return this
    }

    override fun run(): Boolean {
        bus.register(this)
        downloadMultipleAudioItems()

        return true
    }

    override fun onResult(result: Boolean?) {
        bus.unregister(this)
        unSubscribe()
    }

    @Subscribe
    fun handle(event: DownloadCancelAllEvent) {
        cancelRequested = true
    }

    private fun downloadMultipleAudioItems() {
        relatedAudioItems.forEach { relatedAudioItem ->
            if (cancelRequested) {
                return
            }

            val tertiaryId = relatedAudioItem.mediaUrl
            val sourceUri = relatedAudioItem.mediaUrl
            val destinationUri = Uri.fromFile(glDownloadManager.getMediaDestinationUri(contentItemId, relatedAudioItem.subitemId, tertiaryId, ItemMediaType.AUDIO)).toString()

            val queueItem = DownloadQueueItem()
            queueItem.type = ItemMediaType.AUDIO
            queueItem.title = navItemManager.findById(contentItemId, relatedAudioItem.subitemId)?.titleHtml ?: ""
            queueItem.contentItemId = contentItemId
            queueItem.subItemId = relatedAudioItem.subitemId
            queueItem.tertiaryId = tertiaryId
            queueItem.sourceUri = sourceUri

            glDownloadManager.enqueueDownload(queueItem, destinationUri)
        }
    }

}
