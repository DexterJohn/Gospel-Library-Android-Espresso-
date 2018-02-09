package org.lds.ldssa.download


import android.app.Application
import android.net.Uri
import org.lds.ldssa.R
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItem
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.ldssa.util.GLFileUtil
import org.lds.ldssa.util.TipsUtil
import org.lds.mobile.task.RxTask
import timber.log.Timber
import javax.inject.Inject

class TipsDownloader @Inject
constructor(private val application: Application,
            private val tipsUtil: TipsUtil,
            private val fileUtil: GLFileUtil,
            private val downloadQueueItemManager: DownloadQueueItemManager,
            private val downloadManager: GLDownloadManager) : RxTask<Boolean>() {

    private var tipsVersion = 0

    fun init(tipsVersion: Int): TipsDownloader {
        this.tipsVersion = tipsVersion
        return this
    }

    override fun run(): Boolean {
        Timber.i("Downloading tips version: %d", tipsVersion)
        downloadQueueItemManager.deleteAllTips()
        downloadTips()
        return true
    }

    private fun downloadTips() {
        val tipsZipDownloadFile = fileUtil.tipsZipDownloadFile
        if (tipsZipDownloadFile == null) {
            Timber.e("Failed to download tips.  tipsZipDownloadFile == null")
            return
        }

        val sourceUri = tipsUtil.getTipsDownloadUri(tipsVersion)
        val destinationUri = Uri.fromFile(tipsZipDownloadFile).toString()

        val queueItem = DownloadQueueItem()
        queueItem.title = application.getString(R.string.tips)
        queueItem.type = ItemMediaType.TIPS
        queueItem.version = tipsVersion
        queueItem.sourceUri = sourceUri

        // start the download
        downloadManager.enqueueDownload(queueItem, destinationUri)
    }

    override fun onResult(result: Boolean?) {
        unSubscribe()
    }
}
