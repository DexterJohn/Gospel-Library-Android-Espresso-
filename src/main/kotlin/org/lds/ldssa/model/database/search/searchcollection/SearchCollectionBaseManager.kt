/*
 * SearchCollectionBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.search.searchcollection

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class SearchCollectionBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<SearchCollection>(databaseManager) {

    override val allColumns: Array<String> = SearchCollectionConst.ALL_COLUMNS
    override val primaryKey = "NO_PRIMARY_KEY"
    override val dropSql = SearchCollectionConst.DROP_TABLE
    override val createSql = SearchCollectionConst.CREATE_TABLE
    override val insertSql = SearchCollectionConst.INSERT_STATEMENT
    override val updateSql = SearchCollectionConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return SearchCollectionConst.DATABASE
    }

    override fun newRecord() : SearchCollection {
        return SearchCollection()
    }

    override fun getTableName() : String {
        return SearchCollectionConst.TABLE
    }


}