/*
 * DownloadQueueItemManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.gl.downloadqueueitem

import android.arch.lifecycle.LiveData
import org.dbtools.android.domain.DBToolsLiveData
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.types.CatalogItemSourceType
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.ldssa.util.CatalogUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import javax.inject.Inject
import kotlin.coroutines.experimental.CoroutineContext


@javax.inject.Singleton
class DownloadQueueItemManager @Inject constructor(databaseManager: DatabaseManager, val cc: CoroutineContextProvider) : DownloadQueueItemBaseManager(databaseManager) {
    fun findByAndroidDownloadId(androidDownloadId: Long): DownloadQueueItem? {
        return findBySelection("""${DownloadQueueItemConst.C_ANDROID_DOWNLOAD_ID} = ? """, SQLQueryBuilder.toSelectionArgs(androidDownloadId))
    }

    fun findByContentItemIdAndType(contentItemId: Long, mediaType: ItemMediaType = ItemMediaType.CONTENT): DownloadQueueItem? {
        return findBySelection("""${DownloadQueueItemConst.C_CONTENT_ITEM_ID} = ? AND ${DownloadQueueItemConst.C_TYPE} = ?""",
                SQLQueryBuilder.toSelectionArgs(contentItemId, mediaType.ordinal))
    }

    fun findCatalogDownload(version: Long, sourceType: CatalogItemSourceType, mediaType: ItemMediaType): DownloadQueueItem? {
        return findBySelection("""${DownloadQueueItemConst.C_TYPE} = ? AND ${DownloadQueueItemConst.FULL_C_CATALOG_ITEM_SOURCE_TYPE} = ? AND ${DownloadQueueItemConst.C_VERSION} = ? """,
                SQLQueryBuilder.toSelectionArgs(mediaType.ordinal, sourceType.ordinal, version))
    }

    fun findMediaDownload(tertiaryId: String?, mediaType: ItemMediaType): DownloadQueueItem? {
        tertiaryId ?: return null
        return findBySelection("""${DownloadQueueItemConst.C_TYPE} = ? AND ${DownloadQueueItemConst.FULL_C_TERTIARY_ID} = ?""",
                SQLQueryBuilder.toSelectionArgs(mediaType.ordinal, tertiaryId))
    }

    fun findByContentItemIdAndTypeLiveData(contentItemId: Long, mediaType: ItemMediaType = ItemMediaType.CONTENT, coroutineContext: CoroutineContext = cc.commonPool): LiveData<DownloadQueueItem?> {
        return DBToolsLiveData.toLiveData(coroutineContext, this) {
            return@toLiveData findByContentItemIdAndType(contentItemId, mediaType)
        }
    }

    fun isDownloading(contentItemId: Long, mediaType: ItemMediaType): Boolean {
        return findCountBySelection("""${DownloadQueueItemConst.C_CONTENT_ITEM_ID} = ? AND ${DownloadQueueItemConst.C_TYPE} = ?""",
                SQLQueryBuilder.toSelectionArgs(contentItemId, mediaType.ordinal)) > 0
    }

    fun findAllByContentItemAndPage(contentItemId: Long, subItemId: Long): List<DownloadQueueItem> {
        return findAllBySelection("""${DownloadQueueItemConst.C_CONTENT_ITEM_ID} = ? AND ${DownloadQueueItemConst.C_SUB_ITEM_ID} = ?""",
                SQLQueryBuilder.toSelectionArgs(contentItemId, subItemId))
    }

    fun findAllForDisplayLiveData(coroutineContext: CoroutineContext = cc.commonPool): LiveData<List<DownloadQueueItem>> {
        return DBToolsLiveData.toLiveData(coroutineContext,this) {
            findAllBySelection(orderBy = DownloadQueueItemConst.C_TITLE)
        }
    }

    fun deleteByAndroidDownloadId(androidDownloadId: Long): Int {
        return delete("${DownloadQueueItemConst.C_ANDROID_DOWNLOAD_ID} = ?", SQLQueryBuilder.toSelectionArgs(androidDownloadId))
    }

    fun findAllAndroidDownloadIds(): List<Long> {
        return findAllValuesBySelection(Long::class.java, DownloadQueueItemConst.C_ANDROID_DOWNLOAD_ID, null, null)
    }

    fun isCoreCatalogByAndroidDownloadId(androidDownloadId: Long): Boolean {
        return findCountBySelection("""${DownloadQueueItemConst.C_ANDROID_DOWNLOAD_ID} = ? AND ${DownloadQueueItemConst.C_CATALOG_NAME} = ?""",
                SQLQueryBuilder.toSelectionArgs(androidDownloadId, CatalogUtil.CORE_CATALOG_NAME)) > 0
    }

    fun deleteAllCatalogs() {
        delete(where = "${DownloadQueueItemConst.C_TYPE} == ${ItemMediaType.CATALOG.ordinal}", whereArgs = null)
    }

    fun deleteAllTips() {
        delete(where = "${DownloadQueueItemConst.C_TYPE} == ${ItemMediaType.TIPS.ordinal}", whereArgs = null)
    }

    fun updateProcessingDownload(id: Long, b: Boolean) {
        val values = createNewDBToolsContentValues()
        values.put(DownloadQueueItemConst.C_PROCESSING_DOWNLOADED_ITEM, b)
        update(values, id)
    }

    fun isItemBeingProcessed(id: Long): Boolean {
        return findCountBySelection("""${DownloadQueueItemConst.C_ID} = ? AND ${DownloadQueueItemConst.C_PROCESSING_DOWNLOADED_ITEM} = 1""",
                SQLQueryBuilder.toSelectionArgs(id)) > 0
    }

    fun updateAndroidDownloadId(id: Long, androidDownloadId: Long) {
        val contentValues = createNewDBToolsContentValues()
        contentValues.put(DownloadQueueItemConst.C_ANDROID_DOWNLOAD_ID, androidDownloadId)
        update(contentValues, id)
    }

    /**
     * Used for Unit tests
     */
    @Synchronized
    fun findNextAndroidDownloadId(): Long {
        return findValueByRawQuery(Long::class.java, rawQuery = "SELECT MAX(${DownloadQueueItemConst.C_ANDROID_DOWNLOAD_ID}) + 1 FROM ${DownloadQueueItemConst.TABLE}", defaultValue = 1L)
    }
}