/*
 * DownloadedItemManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.gl.downloadeditem

import org.dbtools.query.shared.CompareType
import org.dbtools.query.shared.filter.InFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemConst
import org.lds.ldssa.model.database.types.CatalogItemSourceType
import java.util.ArrayList
import javax.inject.Inject


@javax.inject.Singleton
class DownloadedItemManager @Inject constructor(databaseManager: DatabaseManager, val itemManager: ItemManager) : DownloadedItemBaseManager(databaseManager) {

    fun findAllInstalledSecureItemIds(): List<Long> {
        return findAllValuesBySelection(valueType = Long::class.java,
                column = DownloadedItemConst.C_CONTENT_ITEM_ID,
                selection = DownloadedItemConst.C_CATALOG_ITEM_SOURCE_TYPE + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(CatalogItemSourceType.SECURE.ordinal))
    }

    fun findAllInstalledIds(): List<Long> {
        return findAllValuesBySelection(valueType = Long::class.java,
                column = DownloadedItemConst.C_CONTENT_ITEM_ID)
    }

    fun findContentItemIdExists(contentItemId: Long): Boolean {
        return findCountBySelection(selection = DownloadQueueItemConst.C_CONTENT_ITEM_ID + " = ? ",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(contentItemId)) > 0
    }

    fun findByContentItemId(contentItemId: Long): DownloadedItem? {
        return findBySelection(selection = DownloadQueueItemConst.C_CONTENT_ITEM_ID + " = ? ",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(contentItemId))
    }

    fun deleteByContentItemId(contentItemId: Long) {
        delete(DownloadedItemConst.C_CONTENT_ITEM_ID + " = ? ", SQLQueryBuilder.toSelectionArgs(contentItemId))
    }

    fun findContentItemIdsInstalled(itemIds: List<Long>): List<Long> {
        return findItemsInstalledOrNotInstalled(itemIds, true)
    }

    private fun findItemsInstalledOrNotInstalled(itemIds: List<Long>, installed: Boolean): List<Long> {
        val mutableItemIds = itemIds.toMutableList()
        if (mutableItemIds.isEmpty()) {
            return ArrayList()
        }

        val query = SQLQueryBuilder.build()
                .table(DownloadedItemConst.TABLE)
                .field(DownloadedItemConst.C_CONTENT_ITEM_ID)
                .filter(DownloadedItemConst.C_INSTALLED_VERSION, CompareType.GREATERTHAN, 0)
                .filter(InFilter.create(DownloadedItemConst.C_CONTENT_ITEM_ID, true, mutableItemIds))
                .buildQuery()

        val ids = findAllValuesByRawQuery(Long::class.java, query, null)

        if (!installed) {
            mutableItemIds.removeAll(ids)
            return mutableItemIds
        }

        return ids
    }

    private fun findAllInstalled(): List<DownloadedItem> {
        return findAllBySelection(DownloadedItemConst.C_INSTALLED_VERSION + " > 0")
    }

    private fun findAllByContentItemIds(itemIds: List<Long>): List<DownloadedItem> {
        val query = SQLQueryBuilder()
                .table(DownloadedItemConst.TABLE)
                .filter(InFilter.create(DownloadedItemConst.C_CONTENT_ITEM_ID, true, itemIds))
                .buildQuery()

        return findAllByRawQuery(query, null)
    }

    fun findAllContentItemIdsToInstallOrUpdate(): List<Long> {
        return findAllContentItemIdsToInstallOrUpdate(ArrayList())
    }

    fun findAllContentItemIdsToInstallOrUpdate(contentItemIds: List<Long>): List<Long> {
        val mutableContentItemIds = contentItemIds.toMutableList()
        val needsUpdateContentItemIds = ArrayList<Long>()

        // add all downloaded items to a map by contentItemId
        val downloadedItems = if (mutableContentItemIds.isEmpty()) findAllInstalled() else findAllByContentItemIds(mutableContentItemIds)
        val downloadedItemMap = mutableMapOf<Long, DownloadedItem>() // <contentItemId, DownloadedItem>
        for (downloadedItem in downloadedItems) {
            downloadedItemMap.put(downloadedItem.contentItemId, downloadedItem)
        }

        // Remove contentItemIds for anything that is already downloaded
        mutableContentItemIds.removeAll(downloadedItemMap.keys)
        needsUpdateContentItemIds.addAll(mutableContentItemIds)

        // add back all of the contentItemIds that may need updating (items already installed)
        val downloadedContentItemIds = ArrayList(downloadedItemMap.keys)

        // check each already downloaded content item to see if it needs updating (if the catalog version != the downloaded version)
        if (!downloadedContentItemIds.isEmpty()) {
            val contentItemsToDownload = itemManager.findAllByRowIds(downloadedContentItemIds)
            for (contentItem in contentItemsToDownload) {
                val downloadedItem = downloadedItemMap[contentItem.id]
                if (downloadedItem != null && contentItem.version.toLong() != downloadedItem.installedVersion) {
                    needsUpdateContentItemIds.add(downloadedItem.contentItemId)
                }
            }
        }

        // list of items NOT yet downloaded and items that need to be UPDATED
        return needsUpdateContentItemIds
    }
}