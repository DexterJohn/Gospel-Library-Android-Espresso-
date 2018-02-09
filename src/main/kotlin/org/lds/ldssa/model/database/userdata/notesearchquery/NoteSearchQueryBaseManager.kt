/*
 * NoteSearchQueryBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.userdata.notesearchquery

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class NoteSearchQueryBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<NoteSearchQuery>(databaseManager) {

    override val allColumns: Array<String> = NoteSearchQueryConst.ALL_COLUMNS
    override val primaryKey = "<NO_PRIMARY_KEY_ON_QUERIES>"
    override val dropSql = ""
    override val createSql = ""
    override val insertSql = ""
    override val updateSql = ""

    override fun getDatabaseName() : String {
        return NoteSearchQueryConst.DATABASE
    }

    override fun newRecord() : NoteSearchQuery {
        return NoteSearchQuery()
    }

    abstract fun getQuery() : String

    override fun getTableName() : String {
        return getQuery()
    }


}