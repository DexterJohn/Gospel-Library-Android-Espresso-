package org.lds.ldssa.download

import org.apache.commons.io.FileUtils
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItem
import timber.log.Timber
import java.io.File
import java.net.URI
import java.net.URL
import javax.inject.Inject

class DirectDownloadTask @Inject
constructor() : Runnable {
    private lateinit var glDownloadManager: GLDownloadManager
    private lateinit var queueItem: DownloadQueueItem
    private lateinit var destinationUri: String

    fun init(glDownloadManager: GLDownloadManager, queueItem: DownloadQueueItem, destinationUri: String): DirectDownloadTask {
        this.glDownloadManager = glDownloadManager
        this.queueItem = queueItem
        this.destinationUri = destinationUri
        return this
    }

    override fun run() {
        try {
            FileUtils.copyURLToFile(URL(queueItem.sourceUri), File(URI(destinationUri)))
            glDownloadManager.processDownloadedQueueItem(queueItem)
        } catch (e: Exception) {
            Timber.e(e, "Failed to download Directly")
        }

    }
}
