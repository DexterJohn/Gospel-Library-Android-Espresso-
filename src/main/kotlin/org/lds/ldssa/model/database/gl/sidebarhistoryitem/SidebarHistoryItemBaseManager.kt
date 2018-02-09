/*
 * SidebarHistoryItemBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.gl.sidebarhistoryitem

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class SidebarHistoryItemBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<SidebarHistoryItem>(databaseManager) {

    override val allColumns: Array<String> = SidebarHistoryItemConst.ALL_COLUMNS
    override val primaryKey = SidebarHistoryItemConst.PRIMARY_KEY_COLUMN
    override val dropSql = SidebarHistoryItemConst.DROP_TABLE
    override val createSql = SidebarHistoryItemConst.CREATE_TABLE
    override val insertSql = SidebarHistoryItemConst.INSERT_STATEMENT
    override val updateSql = SidebarHistoryItemConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return SidebarHistoryItemConst.DATABASE
    }

    override fun newRecord() : SidebarHistoryItem {
        return SidebarHistoryItem()
    }

    override fun getTableName() : String {
        return SidebarHistoryItemConst.TABLE
    }


}