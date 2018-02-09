package org.lds.ldssa.download

import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager
import org.lds.mobile.download.DownloadManagerHelper
import org.lds.mobile.task.RxTask
import javax.inject.Inject

class CancelDownloadsTask @Inject
constructor(private val downloadQueueItemManager: DownloadQueueItemManager,
            private val downloadManagerHelper: DownloadManagerHelper) : RxTask<Boolean>() {

    private lateinit var androidDownloadIds: List<Long>
    private var cancelAll = true

    fun init(androidDownloadIds: List<Long>): CancelDownloadsTask {
        cancelAll = false
        this.androidDownloadIds = androidDownloadIds
        return this
    }

    override fun run(): Boolean {
        if (cancelAll) {
            androidDownloadIds = downloadQueueItemManager.findAllAndroidDownloadIds()
        }

        downloadQueueItemManager.beginTransaction()
        try {
            if (androidDownloadIds.size == 1) {
                cancel(androidDownloadIds[0])
            } else {
                androidDownloadIds.forEach { androidDownloadId -> cancel(androidDownloadId) }
            }
        } finally {
            downloadQueueItemManager.endTransaction(true)
        }

        return true
    }

    private fun cancel(androidDownloadId: Long) {
        downloadQueueItemManager.deleteByAndroidDownloadId(androidDownloadId)
        downloadManagerHelper.cancel(androidDownloadId)
    }

    override fun onResult(result: Boolean?) {
        unSubscribe()
    }
}
