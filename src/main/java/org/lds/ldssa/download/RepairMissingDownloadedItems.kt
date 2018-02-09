package org.lds.ldssa.download

import com.crashlytics.android.Crashlytics
import kotlinx.coroutines.experimental.launch
import org.lds.ldssa.model.database.catalog.catalogsource.CatalogSourceManager
import org.lds.ldssa.model.database.catalog.item.Item
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.content.contentmetadata.ContentMetaDataManager
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager
import org.lds.ldssa.util.ContentItemUpdateUtil
import org.lds.ldssa.util.ContentItemUtil
import org.lds.ldssa.util.GLFileUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.util.LdsTimeUtil
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Utility to fix missing downloadedItem records.
 */
@Singleton
class RepairMissingDownloadedItems
@Inject constructor(private val catalogSourceManager: CatalogSourceManager,
                    private val contentItemUpdateUtil: ContentItemUpdateUtil,
                    private val contentItemUtil: ContentItemUtil,
                    private val contentMetaDataManager: ContentMetaDataManager,
                    private val cc: CoroutineContextProvider,
                    private val downloadedItemManager: DownloadedItemManager,
                    private val downloadManager: GLDownloadManager,
                    private val fileUtil: GLFileUtil,
                    private val itemManager: ItemManager,
                    private val ldsTimeUtil: LdsTimeUtil) {
    private var running = AtomicBoolean()

    fun execute() = launch(cc.commonPool) {
        // ensure this runs on only one thread
        if (running.compareAndSet(false, true)) {
            // log a common error message for tracking in Crashlytics
            Timber.d("downloadedItems repair started")
            val startMs = System.currentTimeMillis()
            var success = false
            try {
                downloadedItemManager.beginTransaction()
                val itemIdsToDownload = ArrayList<Long>()

                // contentIds which exists on disk
                val existingContentIds = findExistingItemIds()

                // find items which match the contentIds
                val itemList = itemManager.findAllByRowIds(existingContentIds)

                // identify items that need to be repaired
                val itemsToBeRepaired = itemList.filter { item ->
                    // filter out those for which we have a download record
                    !downloadedItemManager.findContentItemIdExists(item.id)
                }

                Crashlytics.log(1, "count", itemsToBeRepaired.size.toString())
                itemsToBeRepaired.forEach { item ->
                    // if the content matches the catalog
                    if (isContentSameVersion(item)) {
                        // simply restore the downloadedItem record
                        contentItemUpdateUtil.saveDownloadItem(item, catalogSourceManager.findItemSourceTypeById(item.id))
                    } else {
                        // else queue the item for download
                        itemIdsToDownload.add(item.id)
                    }
                    contentItemUtil.closeItem(item.id)
                }

                if (itemIdsToDownload.isNotEmpty()) {
                    // request download for content which did not match current catalog version
                    downloadManager.downloadContent(itemIdsToDownload)
                }

                success = true
                Timber.e("downloadedItems repair completed successfully (%s)", ldsTimeUtil.formatTimeElapsedFromNow(startMs))
            } catch (e: Exception) {
                Timber.e(e, "Exception while trying to repair missing downloadedItem records (%s)", ldsTimeUtil.formatTimeElapsedFromNow(startMs))
            } finally {
                downloadedItemManager.endTransaction(success)
                running.set(false)
            }
        }
    }

    /**
     * Return a list of itemIds from folder names on disk.
     */
    private fun findExistingItemIds() = fileUtil.contentItemBaseDirectory.list().mapNotNull { itemId ->
        itemId.toLongOrNull()   // if a folder can't be parsed as an int, it will be null and then filtered out with mapNotNull (which should never happen)
    }

    /**
     * Does the file content version match the catalog version?
     */
    private fun isContentSameVersion(item: Item): Boolean {
        val catalogVersion = item.version
        val contentVersion = contentMetaDataManager.findItemPackageVersion(item.id)?.toIntOrNull() ?: 0
        return catalogVersion == contentVersion
    }

    @Deprecated("Caution: use for testing only!")
    fun forceBugCondition() {
        downloadedItemManager.deleteAll()
    }
}
