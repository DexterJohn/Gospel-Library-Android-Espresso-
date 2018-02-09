/*
 * SearchGotoBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.catalog.searchgoto

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class SearchGotoBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<SearchGoto>(databaseManager) {

    override val allColumns: Array<String> = SearchGotoConst.ALL_COLUMNS
    override val primaryKey = "NO_PRIMARY_KEY"
    override val dropSql = SearchGotoConst.DROP_TABLE
    override val createSql = SearchGotoConst.CREATE_TABLE
    override val insertSql = SearchGotoConst.INSERT_STATEMENT
    override val updateSql = SearchGotoConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return SearchGotoConst.DATABASE
    }

    override fun newRecord() : SearchGoto {
        return SearchGoto()
    }

    override fun getTableName() : String {
        return SearchGotoConst.TABLE
    }


}