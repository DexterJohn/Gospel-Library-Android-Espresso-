/*
 * SearchAllCountManager.kt
 *
 * Generated on: 03/16/2017 01:30:01
 *
 */



package org.lds.ldssa.model.database.search.searchallcount

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.search.searchcountallnotes.SearchCountAllNotesConst
import org.lds.ldssa.model.database.search.searchcountcollection.SearchCountCollectionConst
import org.lds.ldssa.search.SearchAllType
import javax.inject.Inject

@javax.inject.Singleton
class SearchAllCountManager @Inject constructor(databaseManager: DatabaseManager) : SearchAllCountBaseManager(databaseManager) {

    companion object {
         const val DROP_VIEW: String = "DROP VIEW IF EXISTS ${SearchAllCountConst.TABLE}"
         val NOTE_COUNT_QUERY: String = SQLQueryBuilder()
            .field(SearchCountAllNotesConst.FULL_C_SCREEN_ID, SearchAllCountConst.C_SCREEN_ID)
            .field("0", SearchAllCountConst.C_ID)
            .field(SearchAllType.NOTE.ordinal.toString(), SearchAllCountConst.C_TYPE)
            .field("'Notes'", SearchAllCountConst.C_TITLE) // note:  this value should be overridden in adapter
            .field("''", SearchAllCountConst.C_SUBTITLE)
            .field(SearchCountAllNotesConst.FULL_C_NOTE_COUNT, SearchAllCountConst.C_ITEM_COUNT)
            .field(SearchCountAllNotesConst.FULL_C_PHRASE_COUNT, SearchAllCountConst.C_PHRASE_COUNT)
            .field(SearchCountAllNotesConst.FULL_C_KEYWORD_COUNT, SearchAllCountConst.C_KEYWORD_COUNT)
            .field("0", SearchAllCountConst.C_POSITION)
            .table(SearchCountAllNotesConst.TABLE)
            .buildQuery()

         val COLLECTION_COUNT_QUERY: String = SQLQueryBuilder()
            .field(SearchCountCollectionConst.FULL_C_SCREEN_ID, SearchAllCountConst.C_SCREEN_ID)
            .field(SearchCountCollectionConst.FULL_C_COLLECTION_ID, SearchAllCountConst.C_ID)
            .field(SearchAllType.COLLECTION.ordinal.toString(), SearchAllCountConst.C_TYPE)
            .field(SearchCountCollectionConst.FULL_C_COLLECTION_TITLE, SearchAllCountConst.C_TITLE)
            .field(SearchCountCollectionConst.FULL_C_PARENT_COLLECTION_TITLE, SearchAllCountConst.C_SUBTITLE)
            .field(SearchCountCollectionConst.FULL_C_CONTENT_ITEM_COUNT, SearchAllCountConst.C_ITEM_COUNT)
            .field(SearchCountCollectionConst.FULL_C_PHRASE_COUNT, SearchAllCountConst.C_PHRASE_COUNT)
            .field(SearchCountCollectionConst.FULL_C_KEYWORD_COUNT, SearchAllCountConst.C_KEYWORD_COUNT)
            .field(SearchCountCollectionConst.FULL_C_POSITION, SearchAllCountConst.C_POSITION)
            .table(SearchCountCollectionConst.TABLE)
            .buildQuery()

         val CREATE_VIEW: String = "CREATE VIEW IF NOT EXISTS ${SearchAllCountConst.TABLE} AS " +
                 "$NOTE_COUNT_QUERY UNION $COLLECTION_COUNT_QUERY"
    }

    fun findAllResults(screenId: Long): List<SearchAllCount> {
        return findAllBySelection(selection = "${SearchAllCountConst.C_SCREEN_ID} = $screenId", orderBy = "${SearchAllCountConst.C_TYPE}, ${SearchAllCountConst.C_POSITION}")
                .filter { it.hasResults() }
                .toList()
    }
}