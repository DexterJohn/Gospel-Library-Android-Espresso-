/*
 * SearchPreviewSubitemBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.search.searchpreviewsubitem

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class SearchPreviewSubitemBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<SearchPreviewSubitem>(databaseManager) {

    override val allColumns: Array<String> = SearchPreviewSubitemConst.ALL_COLUMNS
    override val primaryKey = "NO_PRIMARY_KEY"
    override val dropSql = SearchPreviewSubitemConst.DROP_TABLE
    override val createSql = SearchPreviewSubitemConst.CREATE_TABLE
    override val insertSql = SearchPreviewSubitemConst.INSERT_STATEMENT
    override val updateSql = SearchPreviewSubitemConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return SearchPreviewSubitemConst.DATABASE
    }

    override fun newRecord() : SearchPreviewSubitem {
        return SearchPreviewSubitem()
    }

    override fun getTableName() : String {
        return SearchPreviewSubitemConst.TABLE
    }


}