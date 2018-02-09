/*
 * TabBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.gl.tab

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class TabBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<Tab>(databaseManager) {

    override val allColumns: Array<String> = TabConst.ALL_COLUMNS
    override val primaryKey = TabConst.PRIMARY_KEY_COLUMN
    override val dropSql = TabConst.DROP_TABLE
    override val createSql = TabConst.CREATE_TABLE
    override val insertSql = TabConst.INSERT_STATEMENT
    override val updateSql = TabConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return TabConst.DATABASE
    }

    override fun newRecord() : Tab {
        return Tab()
    }

    override fun getTableName() : String {
        return TabConst.TABLE
    }


}