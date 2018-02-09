/*
 * DownloadedMediaManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.gl.downloadedmedia

import org.dbtools.query.shared.filter.CompareFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.types.ItemMediaType
import javax.inject.Inject


@javax.inject.Singleton
class DownloadedMediaManager @Inject constructor(databaseManager: DatabaseManager) : DownloadedMediaBaseManager(databaseManager) {

    private val FIND_SELECTION = CompareFilter.create(DownloadedMediaConst.C_CONTENT_ITEM_ID, "?")
            .and(DownloadedMediaConst.C_SUB_ITEM_ID, "?")
            .and(DownloadedMediaConst.C_TERTIARY_ID, "?")
            .and(DownloadedMediaConst.C_TYPE, "?")
            .toString()

    fun findCountForContentItem(contentItemId: Long): Long {
        return findCountBySelection("${DownloadedMediaConst.C_CONTENT_ITEM_ID} = ?", SQLQueryBuilder.toSelectionArgs(contentItemId))
    }

    fun findByIds(contentItemId: Long, subItemId: Long, tertiaryId: String?, type: ItemMediaType): DownloadedMedia? {
        return findBySelection(selection = FIND_SELECTION,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(contentItemId, subItemId, tertiaryId, type.ordinal))
    }

    fun findAllByContentItem(contentItemId: Long, sortBySize: Boolean): List<DownloadedMedia> {
        return findAllBySelection(selection = DownloadedMediaConst.C_CONTENT_ITEM_ID + "=?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(contentItemId),
                orderBy = if (sortBySize) DownloadedMediaConst.C_SIZE + " DESC" else DownloadedMediaConst.C_SUB_ITEM_ID)
    }

    fun findAllByContentItem(contentItemId: Long): List<DownloadedMedia> {
        return findAllBySelection(DownloadedMediaConst.C_CONTENT_ITEM_ID + "=?", SQLQueryBuilder.toSelectionArgs(contentItemId))
    }

    fun findAllByContentItemAndPage(contentItemId: Long, subItemId: Long): List<DownloadedMedia> {
        val selection = DownloadedMediaConst.C_CONTENT_ITEM_ID + "=? AND " + DownloadedMediaConst.C_SUB_ITEM_ID + "=?"
        return findAllBySelection(selection, SQLQueryBuilder.toSelectionArgs(contentItemId, subItemId))
    }

    fun deleteAllForContentItem(contentItemId: Long) {
        delete(DownloadedMediaConst.C_CONTENT_ITEM_ID + "=?", SQLQueryBuilder.toSelectionArgs(contentItemId))
    }

    fun getTotalDownloadsSize(): Long {
        val query = "SELECT SUM(" + DownloadedMediaConst.C_SIZE + ") AS TOTAL_SIZE FROM " + DownloadedMediaConst.TABLE
        return findValueByRawQuery(Long::class.java, rawQuery = query, defaultValue = 0L)
    }

    fun deleteAllInIds(ids: List<Long>) {
        if (ids.isEmpty()) {
            return
        }

        val builder = StringBuilder(DownloadedMediaConst.FULL_C_ID)
        builder.append(" IN (")

        //Adds all but the last item to the in statement
        for (i in 0 until ids.size - 1) {
            builder.append(ids[i]).append(", ")
        }

        //Adds the last item and finishes the in statement
        builder.append(ids[ids.size - 1])
        builder.append(")")

        delete(where = builder.toString(), whereArgs = null)
    }
}