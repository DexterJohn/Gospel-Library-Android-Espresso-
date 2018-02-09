/*
 * ScreenHistoryItemBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.userdata.screenhistoryitem

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class ScreenHistoryItemBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<ScreenHistoryItem>(databaseManager) {

    override val allColumns: Array<String> = ScreenHistoryItemConst.ALL_COLUMNS
    override val primaryKey = ScreenHistoryItemConst.PRIMARY_KEY_COLUMN
    override val dropSql = ScreenHistoryItemConst.DROP_TABLE
    override val createSql = ScreenHistoryItemConst.CREATE_TABLE
    override val insertSql = ScreenHistoryItemConst.INSERT_STATEMENT
    override val updateSql = ScreenHistoryItemConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return ScreenHistoryItemConst.DATABASE
    }

    override fun newRecord() : ScreenHistoryItem {
        return ScreenHistoryItem()
    }

    override fun getTableName() : String {
        return ScreenHistoryItemConst.TABLE
    }


}