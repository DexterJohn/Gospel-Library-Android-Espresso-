/*
 * HistoryBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.gl.history

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class HistoryBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<History>(databaseManager) {

    override val allColumns: Array<String> = HistoryConst.ALL_COLUMNS
    override val primaryKey = HistoryConst.PRIMARY_KEY_COLUMN
    override val dropSql = HistoryConst.DROP_TABLE
    override val createSql = HistoryConst.CREATE_TABLE
    override val insertSql = HistoryConst.INSERT_STATEMENT
    override val updateSql = HistoryConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return HistoryConst.DATABASE
    }

    override fun newRecord() : History {
        return History()
    }

    override fun getTableName() : String {
        return HistoryConst.TABLE
    }


}