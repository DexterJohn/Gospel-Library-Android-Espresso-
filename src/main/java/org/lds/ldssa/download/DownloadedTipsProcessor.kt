package org.lds.ldssa.download

import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager
import org.lds.mobile.task.RxTask

import javax.inject.Inject

class DownloadedTipsProcessor @Inject
constructor(private val downloadQueueItemManager: DownloadQueueItemManager) : RxTask<Boolean>() {

    // non-injected variables
    private var androidDownloadId: Long = 0

    fun init(androidDownloadId: Long): DownloadedTipsProcessor {
        this.androidDownloadId = androidDownloadId
        return this
    }

    override fun run(): Boolean {
        downloadQueueItemManager.deleteByAndroidDownloadId(androidDownloadId)
        return true
    }

    override fun onResult(result: Boolean?) {
        unSubscribe()
    }

}
