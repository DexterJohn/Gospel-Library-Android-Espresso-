/*
 * SearchCountContentBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.search.searchcountcontent

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class SearchCountContentBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<SearchCountContent>(databaseManager) {

    override val allColumns: Array<String> = SearchCountContentConst.ALL_COLUMNS
    override val primaryKey = "NO_PRIMARY_KEY"
    override val dropSql = SearchCountContentConst.DROP_TABLE
    override val createSql = SearchCountContentConst.CREATE_TABLE
    override val insertSql = SearchCountContentConst.INSERT_STATEMENT
    override val updateSql = SearchCountContentConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return SearchCountContentConst.DATABASE
    }

    override fun newRecord() : SearchCountContent {
        return SearchCountContent()
    }

    override fun getTableName() : String {
        return SearchCountContentConst.TABLE
    }


}