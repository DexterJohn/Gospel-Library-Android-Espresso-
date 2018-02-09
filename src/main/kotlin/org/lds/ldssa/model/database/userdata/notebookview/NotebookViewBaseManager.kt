/*
 * NotebookViewBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.userdata.notebookview

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class NotebookViewBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<NotebookView>(databaseManager) {

    override val allColumns: Array<String> = NotebookViewConst.ALL_COLUMNS
    override val primaryKey = "<NO_PRIMARY_KEY_ON_VIEWS>"
    override val dropSql = NotebookViewManager.DROP_VIEW
    override val createSql = NotebookViewManager.CREATE_VIEW
    override val insertSql = ""
    override val updateSql = ""

    override fun getDatabaseName() : String {
        return NotebookViewConst.DATABASE
    }

    override fun newRecord() : NotebookView {
        return NotebookView()
    }

    override fun getTableName() : String {
        return NotebookViewConst.TABLE
    }


}