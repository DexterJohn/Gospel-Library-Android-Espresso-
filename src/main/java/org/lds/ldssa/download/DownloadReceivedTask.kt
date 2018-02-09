package org.lds.ldssa.download

import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager
import org.lds.mobile.download.DownloadManagerHelper
import org.lds.mobile.task.RxTask
import timber.log.Timber
import javax.inject.Inject

class DownloadReceivedTask @Inject
constructor(private val glDownloadManager: GLDownloadManager,
            private val downloadManagerHelper: DownloadManagerHelper,
            private val downloadQueueItemManager: DownloadQueueItemManager) : RxTask<Boolean>() {

    private var androidDownloadId: Long = 0

    fun init(androidDownloadId: Long): DownloadReceivedTask {
        this.androidDownloadId = androidDownloadId
        return this
    }

    override fun run(): Boolean {
        val downloadQueueItem = downloadQueueItemManager.findByAndroidDownloadId(androidDownloadId)

        if (downloadQueueItem == null) {
            logDownloadProgress(androidDownloadId)
            return true
        }

        glDownloadManager.processDownloadedQueueItem(downloadQueueItem)

        return true
    }

    private fun logDownloadProgress(androidDownloadId: Long) {
        val downloadProgress = downloadManagerHelper.getDownloadProgress(androidDownloadId)
        val uri = downloadManagerHelper.getUri(androidDownloadId)
        Timber.d("Ignoring unrelated download - androidDownloadId: [%d] statusId: [%d] failure Reason: [%d] uri: [%s]", androidDownloadId, downloadProgress.statusId, downloadProgress.failureReasonId, uri)
    }

    override fun onResult(aVoid: Boolean?) {
        // nothing needed here
    }
}