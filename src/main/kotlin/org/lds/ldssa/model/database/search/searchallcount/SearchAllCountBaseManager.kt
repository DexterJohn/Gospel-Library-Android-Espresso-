/*
 * SearchAllCountBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.search.searchallcount

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class SearchAllCountBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<SearchAllCount>(databaseManager) {

    override val allColumns: Array<String> = SearchAllCountConst.ALL_COLUMNS
    override val primaryKey = "<NO_PRIMARY_KEY_ON_VIEWS>"
    override val dropSql = SearchAllCountManager.DROP_VIEW
    override val createSql = SearchAllCountManager.CREATE_VIEW
    override val insertSql = ""
    override val updateSql = ""

    override fun getDatabaseName() : String {
        return SearchAllCountConst.DATABASE
    }

    override fun newRecord() : SearchAllCount {
        return SearchAllCount()
    }

    override fun getTableName() : String {
        return SearchAllCountConst.TABLE
    }


}