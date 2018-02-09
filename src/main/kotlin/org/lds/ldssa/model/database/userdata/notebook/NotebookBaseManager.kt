/*
 * NotebookBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.userdata.notebook

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class NotebookBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<Notebook>(databaseManager) {

    override val allColumns: Array<String> = NotebookConst.ALL_COLUMNS
    override val primaryKey = NotebookConst.PRIMARY_KEY_COLUMN
    override val dropSql = NotebookConst.DROP_TABLE
    override val createSql = NotebookConst.CREATE_TABLE
    override val insertSql = NotebookConst.INSERT_STATEMENT
    override val updateSql = NotebookConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return NotebookConst.DATABASE
    }

    override fun newRecord() : Notebook {
        return Notebook()
    }

    override fun getTableName() : String {
        return NotebookConst.TABLE
    }


}