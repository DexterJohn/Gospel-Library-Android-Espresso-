package org.lds.ldssa.download

import android.app.Application
import android.app.DownloadManager
import org.lds.ldsaccount.LDSAccountAuth
import org.lds.ldssa.R
import org.lds.ldssa.event.download.DownloadCancelAllEvent
import org.lds.ldssa.event.download.DownloadRequestedEvent
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItem
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItem
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.util.GLFileUtil
import org.lds.ldssa.util.ToastUtil
import org.lds.mobile.download.DownloadManagerHelper
import org.lds.mobile.util.LdsNetworkUtil
import pocketbus.Bus
import timber.log.Timber
import java.io.File
import java.net.URI
import java.net.URISyntaxException
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class GLDownloadManager @Inject
constructor(private val application: Application,
            private val bus: Bus,
            private val downloadedItemManager: DownloadedItemManager,
            private val downloadedMediaManager: DownloadedMediaManager,
            private val downloadQueueItemManager: DownloadQueueItemManager,
            private val ldsAccountAuth: LDSAccountAuth,
            private val downloadManagerHelper: DownloadManagerHelper,
            private val ldsNetworkUtil: LdsNetworkUtil,
            private val fileUtil: GLFileUtil,
            private val prefs: Prefs,
            private val toastUtil: ToastUtil,
            private val processContentRunnableProvider: Provider<DownloadedContentProcessor>,
            private val downloadedMediaProcessorProvider: Provider<DownloadedMediaProcessor>,
            private val downloadedCatalogProcessorProvider: Provider<DownloadedCatalogProcessor>,
            private val downloadedTipsProcessorProvider: Provider<DownloadedTipsProcessor>,
            private val mediaDownloaderProvider: Provider<MediaDownloader>,
            private val allAudioDownloaderProvider: Provider<AllAudioDownloader>,
            private val contentDownloaderProvider: Provider<ContentDownloader>,
            private val cancelDownloadsTaskProvider: Provider<CancelDownloadsTask>,
            private val directDownloadTaskProvider: Provider<DirectDownloadTask>,
            private val repairMissingDownloadedItems: Provider<RepairMissingDownloadedItems>) {

    private var contentDownloadExecutor: ThreadPoolExecutor? = null

    /**
     * Used to determine if downloads are being processed
     */
    fun getActiveDownloadProcessorCount() = contentDownloadExecutor?.activeCount ?: 0

    fun cancelDownload(androidDownloadId: Long) {
        cancelDownloadsTaskProvider.get().init(listOf(androidDownloadId)).execute()
    }

    fun cancelAllDownloads() {
        bus.post(DownloadCancelAllEvent())
        cancelDownloadsTaskProvider.get().execute()
    }

    fun downloadContent(contentItemId: Long) = downloadContent(listOf(contentItemId))

    fun downloadContent(contentItemIds: List<Long>) {
        if (networkUsable()) {
            contentDownloaderProvider.get().init(this, contentItemIds).execute()
        }
    }

    fun downloadContentDirect(contentItemId: Long) {
        if (networkUsable()) {
            contentDownloaderProvider.get().init(this, listOf(contentItemId)).run()
        }
    }

    fun repairDownloadedItemsIfAnyMissing(contentItemId: Long) {
        if (!downloadedItemManager.findContentItemIdExists(contentItemId)) {
            repairMissingDownloadedItems.get().execute()
            //todo Use the following lines to force the bug to happen for testing, but remove this when the cause of the bug is completely fixed.
            //        } else {
            //            repairMissingDownloadedItems.get().forceBugCondition();
        }
    }

    fun updateContent() {
        if (networkUsable(false)) {
            val contentItemIds = downloadedItemManager.findAllContentItemIdsToInstallOrUpdate()
            contentDownloaderProvider.get().init(this, contentItemIds).execute()
        }
    }

    fun downloadMedia(contentItemId: Long, subItemId: Long, tertiaryId: String, title: String, url: String, mediaType: ItemMediaType) {
        if (networkUsable()) {
            mediaDownloaderProvider.get().init(this, contentItemId, subItemId, tertiaryId, title, url, mediaType).execute()
        }
    }

    fun downloadAllAudioItems(contentItemId: Long, downloadAudioItems: List<RelatedAudioItem>) {
        if (networkUsable()) {
            allAudioDownloaderProvider.get().init(this, contentItemId, downloadAudioItems).execute()
        }
    }

    fun processDownloadedQueueItem(downloadQueueItem: DownloadQueueItem) {
        val androidDownloadId = downloadQueueItem.androidDownloadId
        when (downloadQueueItem.type) {
            ItemMediaType.CONTENT -> {
                downloadQueueItemManager.updateProcessingDownload(downloadQueueItem.id, true)
                executeDownloadedContentProcessor(androidDownloadId)
            }
            ItemMediaType.AUDIO, ItemMediaType.VIDEO -> {
                downloadQueueItemManager.updateProcessingDownload(downloadQueueItem.id, true)
                downloadedMediaProcessorProvider.get().init(androidDownloadId).execute()
            }
            ItemMediaType.CATALOG, ItemMediaType.CATALOG_DIFF -> {
                downloadQueueItemManager.updateProcessingDownload(downloadQueueItem.id, true)
                downloadedCatalogProcessorProvider.get().init(androidDownloadId).execute()
            }
            ItemMediaType.TIPS -> {
                downloadQueueItemManager.updateProcessingDownload(downloadQueueItem.id, true)
                downloadedTipsProcessorProvider.get().init(androidDownloadId).execute()
            }
            else -> Timber.e("Ignoring unrecognized download type %s", downloadQueueItem.type)
        }
    }

    /**
     * Determines how we should handle the download request using the
     * users network preferences and the devices current network connectivity.
     *
     * @param showNetworkMessages If the network required messages when not available
     * @return True if we are connected to an allowed network type
     */
    @JvmOverloads
    fun networkUsable(showNetworkMessages: Boolean = true): Boolean {
        //Determines if we have any (WiFi, LTE, etc.) network connectivity
        val connected = ldsNetworkUtil.isConnected()
        if (!connected) {
            if (showNetworkMessages) {
                toastUtil.showLong(R.string.network_required)
            }
            return false
        }

        val connectedToWifi = ldsNetworkUtil.isConnected(false)
        if (!connectedToWifi) {
            //If we can only use WiFi for downloads then inform the user
            if (prefs.isMobileNetworkLimited) {
                if (showNetworkMessages) {
                    toastUtil.showLong(R.string.network_wifi_required)
                }
                return false
            }
        }

        //We are connected to wifi so we can always download
        return true
    }

    internal fun getMediaDestinationUri(contentItemId: Long, subItemId: Long, tertiaryId: String, type: ItemMediaType): File {
        val baseDir = when (type) {
            ItemMediaType.AUDIO -> fileUtil.contentAudioTempDownloadDirectory
            ItemMediaType.VIDEO -> fileUtil.contentVideoTempDownloadDirectory
            else -> fileUtil.contentMediaTempDownloadDirectory
        }

        //Creates the actual file name
        return File(baseDir, """$contentItemId.$subItemId.${tertiaryId.hashCode()}""")
    }

    private fun executeDownloadedContentProcessor(androidDownloadId: Long) {
        if (prefs.isDirectDownload) {
            // used by unit tests
            processContentRunnableProvider.get().init(androidDownloadId).run()
            return
        }

        if (contentDownloadExecutor == null) {
            contentDownloadExecutor = ThreadPoolExecutor(POOL_SIZE_MIN, POOL_SIZE_MAX, KEEP_ALIVE_TIMEOUT, TimeUnit.MILLISECONDS, LinkedBlockingQueue())
        }
        contentDownloadExecutor?.execute(processContentRunnableProvider.get().init(androidDownloadId))
    }

    /**
     * Request download based on DownloadQueueItem... if download is not started,
     * then the DownloadQueueItem is not saved
     * @param queueItem GL queueItem
     */
    fun enqueueDownload(queueItem: DownloadQueueItem, destinationUri: String) {
//        Timber.d("Enqueueing download for id: [%d]  type: [%s] source [%s] dest: [%s]", queueItem.contentItemId, queueItem.type, queueItem.destinationUri)

        // make sure that we are not already downloading this item
        if (isItemAlreadyDownloadingOrDownloaded(queueItem)) {
            Timber.d("Skipping already existing download [%s] source uri: [%s]", queueItem.title, queueItem.sourceUri)
            return
        }

        // make sure that the temp destination download file does not already exist
        val destinationFile: File
        try {
            destinationFile = File(URI(destinationUri))
        } catch (e: URISyntaxException) {
            Timber.e(e, "Failed to parse DestinationUri [%s]", destinationUri)
            return
        }

        if (destinationFile.exists()) {
            Timber.e("Destination download file ALREADY exists for id: [%d]  type: [%s] path: [%s]", queueItem.contentItemId, queueItem.type, destinationFile.absolutePath)

            // try to delete the file (if the file does not delete... move on... the Android Download Manager will created a different destinationUri)
            destinationFile.delete()
        }

        // use a transaction to ensure that the androidDownload gets set correctly
        downloadQueueItemManager.beginTransaction()

        // save before download starts... just in case the download completes prior to the save
        downloadQueueItemManager.save(queueItem)

        // start the download
        val androidDownloadId: Long
        if (prefs.isDirectDownload) {
            androidDownloadId = downloadQueueItemManager.findNextAndroidDownloadId()
            queueItem.androidDownloadId = androidDownloadId

            // save state
            downloadQueueItemManager.updateAndroidDownloadId(queueItem.id, androidDownloadId)
            downloadQueueItemManager.endTransaction(true)

            directDownload(queueItem, destinationUri)
        } else {
            androidDownloadId = androidDownload(queueItem, destinationUri)
            // save state
            downloadQueueItemManager.updateAndroidDownloadId(queueItem.id, androidDownloadId)
            downloadQueueItemManager.endTransaction(true)
        }

        // notify
        bus.post(DownloadRequestedEvent(androidDownloadId, queueItem.type, queueItem.contentItemId))
    }

    private fun androidDownload(queueItem: DownloadQueueItem, destinationUri: String): Long {
        val androidDownloadId = downloadManagerHelper.enqueue(queueItem.toAndroidDownloadRequest(application, prefs, ldsAccountAuth, destinationUri))

        Timber.i("Downloading: [%s] androidDownloadId [%d]", queueItem.sourceUri, androidDownloadId)
        // save state
        downloadQueueItemManager.updateAndroidDownloadId(queueItem.id, androidDownloadId)

        return androidDownloadId
    }

    private fun directDownload(queueItem: DownloadQueueItem, destinationUri: String) {
        Timber.i("Downloading: [%s] DIRECT", queueItem.sourceUri)
        Thread(directDownloadTaskProvider.get().init(this, queueItem, destinationUri)).run()
    }

    private fun isItemAlreadyDownloadingOrDownloaded(queueItem: DownloadQueueItem): Boolean {
        return when (queueItem.type) {
            ItemMediaType.CATALOG, ItemMediaType.CATALOG_DIFF, ItemMediaType.TIPS -> isCatalogItemAlreadyDownloading(queueItem)
            ItemMediaType.CONTENT -> isContentItemAlreadyDownloadingOrDownloaded(queueItem.contentItemId, queueItem.version.toLong())
            ItemMediaType.AUDIO, ItemMediaType.VIDEO -> isMediaItemAlreadyDownloadingOrDownloaded(queueItem)
            else -> {
                Timber.e("Unexpected queue item type [%s]", queueItem.type)
                false
            }
        }
    }

    private fun isCatalogItemAlreadyDownloading(queueItem: DownloadQueueItem): Boolean {
        // Downloading check...
        // Make sure we don't have the item already downloading... and verify that the download is valid
        val existingQueueItem = downloadQueueItemManager.findCatalogDownload(queueItem.version.toLong(), queueItem.catalogItemSourceType, queueItem.type)
        return existingQueueItem != null && isDownloadItemInProgress(existingQueueItem.androidDownloadId)
    }

    private fun isContentItemAlreadyDownloadingOrDownloaded(contentItemId: Long, itemVersion: Long): Boolean {
        // Downloading check...
        // Make sure we don't have the item already downloading... and verify that the download is valid
        val existingQueueItem = downloadQueueItemManager.findByContentItemIdAndType(contentItemId, ItemMediaType.CONTENT)
        if (existingQueueItem != null && isDownloadItemInProgress(existingQueueItem.androidDownloadId)) {
            return true
        }

        // Downloaded check...
        // Make sure the item of the same version isn't already downloaded AND if database file exists
        val downloadedItem = downloadedItemManager.findByContentItemId(contentItemId)
        return downloadedItem != null && downloadedItem.installedVersion == itemVersion && fileUtil.getContentItemDatabase(contentItemId).exists()
    }

    private fun isMediaItemAlreadyDownloadingOrDownloaded(queueItem: DownloadQueueItem): Boolean {
        // Downloading check...
        // Make sure we don't have the item already downloading
        val existingQueueItem = downloadQueueItemManager.findMediaDownload(queueItem.tertiaryId, queueItem.type)
        if (existingQueueItem != null && isDownloadItemInProgress(existingQueueItem.androidDownloadId)) {
            return true
        }

        // Downloaded check...
        // Make sure the item isn't already downloaded AND if file exists
        val downloadedMedia = downloadedMediaManager.findByIds(queueItem.contentItemId, queueItem.subItemId, queueItem.tertiaryId, queueItem.type)
        val mediaFilename = downloadedMedia?.file
        return downloadedMedia != null && mediaFilename != null && fileUtil.getContentMediaFile(mediaFilename, downloadedMedia.type).exists()
    }

    /**
     * Check to see if a download is in progress based on the queue item androidDownloadId
     */
    private fun isDownloadItemInProgress(androidDownloadId: Long): Boolean {
        val statusId = downloadManagerHelper.getDownloadProgress(androidDownloadId).statusId
        return when (statusId) {
            DownloadManager.STATUS_PENDING, DownloadManager.STATUS_RUNNING, DownloadManager.STATUS_PAUSED -> true
            else -> {
                // orphan DownloadQueue item.... delete it
                Timber.e("Orphan DownloadQueue item existed... deleting DownloadQueueItem")
                downloadQueueItemManager.deleteByAndroidDownloadId(androidDownloadId)
                false
            }
        }
    }

    /**
     * Cancel download and remove them from the download manager. Download will be stopped if it was running,
     * and it will no longer be accessible through the download manager. If there is a downloaded file, partial or complete, it is deleted.
     * @param androidDownloadId DownloadManager Id
     */
    fun removeDownload(androidDownloadId: Long) {
        downloadManagerHelper.cancel(androidDownloadId)
    }

    companion object {
        private const val POOL_SIZE_MIN = 2
        private const val POOL_SIZE_MAX = 2
        private const val KEEP_ALIVE_TIMEOUT: Long = 5000
    }
}