/*
 * SearchPreviewSubitemManager.kt
 *
 * Generated on: 03/09/2017 12:15:57
 *
 */



package org.lds.ldssa.model.database.search.searchpreviewsubitem

import org.lds.ldssa.model.database.DatabaseManager
import javax.inject.Inject


@javax.inject.Singleton
class SearchPreviewSubitemManager @Inject constructor(databaseManager: DatabaseManager) : SearchPreviewSubitemBaseManager(databaseManager) {
    fun findAllForPreviewByContentItemId(screenId: Long, contentItemId: Long): List<SearchPreviewSubitem> {
        return findAllBySelection(
                selection = "${SearchPreviewSubitemConst.C_SCREEN_ID} = $screenId AND ${SearchPreviewSubitemConst.C_CONTENT_ITEM_ID} = $contentItemId",
                orderBy = SearchPreviewSubitemConst.C_SEARCH_RESULT_COUNT_TYPE + ", " + SearchPreviewSubitemConst.C_POSITION)
    }

    fun findCountByContentItemId(screenId: Long, contentItemId: Long): Long {
        return findCountBySelection(selection = "${SearchPreviewSubitemConst.C_SCREEN_ID} = $screenId AND ${SearchPreviewSubitemConst.C_CONTENT_ITEM_ID} = $contentItemId")
    }

    fun saveViewed(screenId: Long, subItemId: Long) {
        val contentValues = createNewDBToolsContentValues()
        contentValues.put(SearchPreviewSubitemConst.C_VISITED, 1)
        update(contentValues, "${SearchPreviewSubitemConst.C_SCREEN_ID} = $screenId AND ${SearchPreviewSubitemConst.C_SUB_ITEM_ID} = $subItemId")
    }

    fun deleteAllByTabId(screenId: Long) {
        delete("${SearchPreviewSubitemConst.C_SCREEN_ID} = $screenId")
    }
}