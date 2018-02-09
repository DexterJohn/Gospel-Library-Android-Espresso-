/*
 * SearchCountContentManager.kt
 *
 * Generated on: 03/09/2017 11:46:28
 *
 */



package org.lds.ldssa.model.database.search.searchcountcontent

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.search.searchcontentcollectionmap.SearchContentCollectionMapConst
import javax.inject.Inject


@javax.inject.Singleton
class SearchCountContentManager @Inject constructor(databaseManager: DatabaseManager) : SearchCountContentBaseManager(databaseManager) {

    private val ALL_BY_COLLECTION_QUERY = SQLQueryBuilder()
            .table(SearchCountContentConst.TABLE)
            .join(SearchContentCollectionMapConst.TABLE, SearchContentCollectionMapConst.FULL_C_CONTENT_ITEM_ID, SearchCountContentConst.FULL_C_CONTENT_ITEM_ID)
            .filter(SearchCountContentConst.FULL_C_SCREEN_ID, "?")
            .filter(SearchContentCollectionMapConst.FULL_C_SCREEN_ID, "?")
            .filter(SearchContentCollectionMapConst.FULL_C_COLLECTION_ID, "?")
            .orderBy(SearchCountContentConst.FULL_C_POSITION)
            .buildQuery()

    fun findAllResultsByCollection(screenId: Long, collectionId: Long): List<SearchCountContent> {
        return findAllByRawQuery(ALL_BY_COLLECTION_QUERY, SQLQueryBuilder.toSelectionArgs(screenId, screenId, collectionId))
    }

    fun deleteAllByTabId(screenId: Long) {
        delete("${SearchCountContentConst.C_SCREEN_ID} = $screenId")
    }

    fun findCountByTabId(screenId: Long): Long {
        return findCountBySelection(selection = "${SearchCountContentConst.C_SCREEN_ID} = $screenId")
    }
}