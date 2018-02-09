/*
 * SearchPreviewNoteBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.search.searchpreviewnote

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class SearchPreviewNoteBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<SearchPreviewNote>(databaseManager) {

    override val allColumns: Array<String> = SearchPreviewNoteConst.ALL_COLUMNS
    override val primaryKey = "NO_PRIMARY_KEY"
    override val dropSql = SearchPreviewNoteConst.DROP_TABLE
    override val createSql = SearchPreviewNoteConst.CREATE_TABLE
    override val insertSql = SearchPreviewNoteConst.INSERT_STATEMENT
    override val updateSql = SearchPreviewNoteConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return SearchPreviewNoteConst.DATABASE
    }

    override fun newRecord() : SearchPreviewNote {
        return SearchPreviewNote()
    }

    override fun getTableName() : String {
        return SearchPreviewNoteConst.TABLE
    }


}