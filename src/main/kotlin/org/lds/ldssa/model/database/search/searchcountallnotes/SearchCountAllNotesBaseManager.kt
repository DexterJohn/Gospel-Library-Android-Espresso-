/*
 * SearchCountAllNotesBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.search.searchcountallnotes

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class SearchCountAllNotesBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<SearchCountAllNotes>(databaseManager) {

    override val allColumns: Array<String> = SearchCountAllNotesConst.ALL_COLUMNS
    override val primaryKey = "NO_PRIMARY_KEY"
    override val dropSql = SearchCountAllNotesConst.DROP_TABLE
    override val createSql = SearchCountAllNotesConst.CREATE_TABLE
    override val insertSql = SearchCountAllNotesConst.INSERT_STATEMENT
    override val updateSql = SearchCountAllNotesConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return SearchCountAllNotesConst.DATABASE
    }

    override fun newRecord() : SearchCountAllNotes {
        return SearchCountAllNotes()
    }

    override fun getTableName() : String {
        return SearchCountAllNotesConst.TABLE
    }


}