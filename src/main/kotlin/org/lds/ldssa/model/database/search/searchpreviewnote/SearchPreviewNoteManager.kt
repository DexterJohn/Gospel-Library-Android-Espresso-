/*
 * SearchPreviewNoteManager.kt
 *
 * Generated on: 03/14/2017 07:19:11
 *
 */



package org.lds.ldssa.model.database.search.searchpreviewnote

import org.lds.ldssa.model.database.DatabaseManager
import javax.inject.Inject


@javax.inject.Singleton
class SearchPreviewNoteManager @Inject constructor(databaseManager: DatabaseManager) : SearchPreviewNoteBaseManager(databaseManager) {

    fun findAllForPreview(screenId: Long): List<SearchPreviewNote> {
        return findAllBySelection(
                selection = "${SearchPreviewNoteConst.C_SCREEN_ID} = $screenId",
                orderBy = SearchPreviewNoteConst.C_SEARCH_RESULT_COUNT_TYPE + ", " + SearchPreviewNoteConst.C_POSITION)
    }

    fun saveViewed(screenId: Long, annotationId: Long) {
        val contentValues = createNewDBToolsContentValues()
        contentValues.put(SearchPreviewNoteConst.C_VISITED, 1)
        update(contentValues, "${SearchPreviewNoteConst.C_SCREEN_ID} = $screenId AND ${SearchPreviewNoteConst.C_ANNOTATION_ID} = $annotationId")
    }

    fun deleteAllByScreenId(screenId: Long) {
        delete("${SearchPreviewNoteConst.C_SCREEN_ID} = $screenId")
    }

    fun findCountByScreenId(screenId: Long): Long {
        return findCountBySelection("${SearchPreviewNoteConst.C_SCREEN_ID} = $screenId")

    }
}