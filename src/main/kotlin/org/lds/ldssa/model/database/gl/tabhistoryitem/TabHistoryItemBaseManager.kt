/*
 * TabHistoryItemBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.gl.tabhistoryitem

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class TabHistoryItemBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<TabHistoryItem>(databaseManager) {

    override val allColumns: Array<String> = TabHistoryItemConst.ALL_COLUMNS
    override val primaryKey = TabHistoryItemConst.PRIMARY_KEY_COLUMN
    override val dropSql = TabHistoryItemConst.DROP_TABLE
    override val createSql = TabHistoryItemConst.CREATE_TABLE
    override val insertSql = TabHistoryItemConst.INSERT_STATEMENT
    override val updateSql = TabHistoryItemConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return TabHistoryItemConst.DATABASE
    }

    override fun newRecord() : TabHistoryItem {
        return TabHistoryItem()
    }

    override fun getTableName() : String {
        return TabHistoryItemConst.TABLE
    }


}