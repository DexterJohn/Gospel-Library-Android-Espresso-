/*
 * NoteBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.userdata.note

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class NoteBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<Note>(databaseManager) {

    override val allColumns: Array<String> = NoteConst.ALL_COLUMNS
    override val primaryKey = NoteConst.PRIMARY_KEY_COLUMN
    override val dropSql = NoteConst.DROP_TABLE
    override val createSql = NoteConst.CREATE_TABLE
    override val insertSql = NoteConst.INSERT_STATEMENT
    override val updateSql = NoteConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return NoteConst.DATABASE
    }

    override fun newRecord() : Note {
        return Note()
    }

    override fun getTableName() : String {
        return NoteConst.TABLE
    }


}