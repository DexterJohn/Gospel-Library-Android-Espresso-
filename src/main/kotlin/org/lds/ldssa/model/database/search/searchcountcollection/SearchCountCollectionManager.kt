/*
 * SearchCountCollectionManager.kt
 *
 * Generated on: 03/09/2017 11:46:28
 *
 */



package org.lds.ldssa.model.database.search.searchcountcollection

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.search.searchcollection.SearchCollectionConst
import org.lds.ldssa.model.database.search.searchcontentcollectionmap.SearchContentCollectionMapConst
import org.lds.ldssa.model.database.search.searchcountcontent.SearchCountContentConst
import javax.inject.Inject

@javax.inject.Singleton
class SearchCountCollectionManager @Inject constructor(databaseManager: DatabaseManager) : SearchCountCollectionBaseManager(databaseManager) {

    companion object {
        const val DROP_VIEW: String = "DROP VIEW IF EXISTS " + SearchCountCollectionConst.TABLE

        val COLLECTION_CONTENT_ITEM_COUNT_QUERY = "SELECT count(1) FROM ${SearchContentCollectionMapConst.TABLE} WHERE ${SearchContentCollectionMapConst.FULL_C_COLLECTION_ID} = ${SearchCollectionConst.FULL_C_COLLECTION_ID}"
        val CREATE_VIEW: String = "CREATE VIEW IF NOT EXISTS " + SearchCountCollectionConst.TABLE + " AS " +
                SQLQueryBuilder()
                        .field(SearchCollectionConst.FULL_C_SCREEN_ID, SearchCountCollectionConst.C_SCREEN_ID)
                        .field(SearchCollectionConst.FULL_C_COLLECTION_ID, SearchCountCollectionConst.C_COLLECTION_ID)
                        .field(SearchCollectionConst.FULL_C_TITLE, SearchCountCollectionConst.C_COLLECTION_TITLE)
                        .field(SearchCollectionConst.FULL_C_PARENT_COLLECTION_ID, SearchCountCollectionConst.C_PARENT_COLLECTION_ID)
                        .field(SearchCollectionConst.FULL_C_PARENT_COLLECTION_TITLE, SearchCountCollectionConst.C_PARENT_COLLECTION_TITLE)
                        .field("($COLLECTION_CONTENT_ITEM_COUNT_QUERY)", SearchCountCollectionConst.C_CONTENT_ITEM_COUNT)
                        .field("sum(${SearchCountContentConst.FULL_C_PHRASE_COUNT})", SearchCountCollectionConst.C_PHRASE_COUNT)
                        .field("sum(${SearchCountContentConst.FULL_C_KEYWORD_COUNT})", SearchCountCollectionConst.C_KEYWORD_COUNT)
                        .field(SearchCollectionConst.FULL_C_POSITION, SearchCountCollectionConst.C_POSITION)
                        .table(SearchCollectionConst.TABLE)
                        .join(SearchContentCollectionMapConst.TABLE, SearchContentCollectionMapConst.FULL_C_COLLECTION_ID, SearchCollectionConst.FULL_C_COLLECTION_ID)
                        .join(SearchCountContentConst.TABLE, SearchCountContentConst.FULL_C_CONTENT_ITEM_ID, SearchContentCollectionMapConst.FULL_C_CONTENT_ITEM_ID)
                        .filter(SearchCountContentConst.FULL_C_SCREEN_ID, SearchCollectionConst.FULL_C_SCREEN_ID)
                        .filter(SearchContentCollectionMapConst.FULL_C_SCREEN_ID, SearchCollectionConst.FULL_C_SCREEN_ID)
                        .groupBy(SearchCollectionConst.FULL_C_SCREEN_ID + ", " + SearchCollectionConst.FULL_C_COLLECTION_ID)
                        .buildQuery()
    }
}