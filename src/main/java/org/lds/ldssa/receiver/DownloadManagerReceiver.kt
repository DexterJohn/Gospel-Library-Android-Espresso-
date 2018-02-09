package org.lds.ldssa.receiver

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.support.v4.content.WakefulBroadcastReceiver
import com.crashlytics.android.Crashlytics
import org.lds.ldssa.R
import org.lds.ldssa.download.DownloadReceivedTask
import org.lds.ldssa.event.download.DownloadCompletedEvent
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.ldssa.util.CatalogUpdateUtil
import org.lds.ldssa.util.ToastUtil
import org.lds.mobile.download.DownloadManagerHelper
import org.lds.mobile.download.DownloadMetaData
import pocketbus.Bus
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class DownloadManagerReceiver : WakefulBroadcastReceiver() {
    @Inject
    lateinit var application: Application
    @Inject
    lateinit var bus: Bus
    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var downloadReceivedTaskProvider: Provider<DownloadReceivedTask>
    @Inject
    lateinit var downloadManagerHelper: DownloadManagerHelper
    @Inject
    lateinit var downloadQueueItemManager: DownloadQueueItemManager
    @Inject
    lateinit var catalogUpdateUtil: CatalogUpdateUtil
    @Inject
    lateinit var toastUtil: ToastUtil

    override fun onReceive(context: Context, intent: Intent) {
        Injector.init(context.applicationContext as Application)
        Injector.get().inject(this)

        val action = intent.action
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
            val androidDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0L)
            val downloadMetaData = downloadManagerHelper.getDownloadMetaData(androidDownloadId)

            if (downloadMetaData == null) {
                // this download does not exist in the OS, so delete from the queue
                downloadQueueItemManager.deleteByAndroidDownloadId(androidDownloadId)
                return
            }

            val statusId = downloadMetaData.statusId
            val downloadUri = downloadMetaData.uri
            val destinationUri = downloadMetaData.localUri
            Timber.i("Download Completed for [%s] -> [ %s] status [%d]  reason [%d]", downloadUri, destinationUri, statusId, downloadMetaData.failureReasonId)
            when (statusId) {
                DownloadManager.STATUS_FAILED -> handleDownloadFailure(androidDownloadId, downloadMetaData)
                DownloadManager.STATUS_PAUSED -> Timber.w("Download PAUSED for [%s] -> [%s]", downloadUri, destinationUri)
                DownloadManager.STATUS_PENDING -> Timber.w("Download PENDING for [%s] -> [%s]", downloadUri, destinationUri)
                DownloadManager.STATUS_RUNNING -> Timber.d("Download RUNNING for [%s] -> [%s]", downloadUri, destinationUri)
                DownloadManager.STATUS_SUCCESSFUL -> downloadReceivedTaskProvider.get().init(androidDownloadId).execute()
            }

        } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED == action) {
            internalIntents.showCurrentDownloads(context)
        }
    }

    private fun handleDownloadFailure(androidDownloadId: Long, downloadMetaData: DownloadMetaData) {
        val downloadQueueItem = downloadQueueItemManager.findByAndroidDownloadId(androidDownloadId)
        if (downloadQueueItem != null) {
            if (downloadQueueItem.type == ItemMediaType.CATALOG_DIFF) {
                catalogUpdateUtil.downloadCoreCatalog(downloadQueueItem.version, true)
                downloadQueueItemManager.delete(downloadQueueItem)
                return
            }

            when (downloadMetaData.failureReasonId) {
                DownloadManager.ERROR_INSUFFICIENT_SPACE -> toastUtil.showShort(R.string.download_file_failed_insufficient_space, downloadQueueItem.title)
                else -> {
                    if (downloadQueueItem.title.isNotBlank()) {
                        toastUtil.showShort(R.string.download_file_failed, downloadQueueItem.title)
                    }
                }}

            downloadQueueItemManager.deleteByAndroidDownloadId(androidDownloadId)

            Crashlytics.log(1, "download-failed", "Download Failed TITLE [${downloadQueueItem.title}]  SOURCE_URI [${downloadQueueItem.sourceUri}] FAILURE REASON ID [${downloadMetaData.failureReasonId}]")
            Timber.e("Download FAILED for [%s]", downloadQueueItem.sourceUri)

            bus.post(DownloadCompletedEvent(downloadQueueItem.contentItemId, false, androidDownloadId))
        }
    }
}
