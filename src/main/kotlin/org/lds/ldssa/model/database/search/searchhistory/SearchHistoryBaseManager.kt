/*
 * SearchHistoryBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.search.searchhistory

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class SearchHistoryBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<SearchHistory>(databaseManager) {

    override val allColumns: Array<String> = SearchHistoryConst.ALL_COLUMNS
    override val primaryKey = "NO_PRIMARY_KEY"
    override val dropSql = SearchHistoryConst.DROP_TABLE
    override val createSql = SearchHistoryConst.CREATE_TABLE
    override val insertSql = SearchHistoryConst.INSERT_STATEMENT
    override val updateSql = SearchHistoryConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return SearchHistoryConst.DATABASE
    }

    override fun newRecord() : SearchHistory {
        return SearchHistory()
    }

    override fun getTableName() : String {
        return SearchHistoryConst.TABLE
    }


}