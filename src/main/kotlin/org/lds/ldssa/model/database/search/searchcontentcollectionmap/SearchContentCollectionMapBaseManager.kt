/*
 * SearchContentCollectionMapBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.search.searchcontentcollectionmap

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class SearchContentCollectionMapBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<SearchContentCollectionMap>(databaseManager) {

    override val allColumns: Array<String> = SearchContentCollectionMapConst.ALL_COLUMNS
    override val primaryKey = "NO_PRIMARY_KEY"
    override val dropSql = SearchContentCollectionMapConst.DROP_TABLE
    override val createSql = SearchContentCollectionMapConst.CREATE_TABLE
    override val insertSql = SearchContentCollectionMapConst.INSERT_STATEMENT
    override val updateSql = SearchContentCollectionMapConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return SearchContentCollectionMapConst.DATABASE
    }

    override fun newRecord() : SearchContentCollectionMap {
        return SearchContentCollectionMap()
    }

    override fun getTableName() : String {
        return SearchContentCollectionMapConst.TABLE
    }


}