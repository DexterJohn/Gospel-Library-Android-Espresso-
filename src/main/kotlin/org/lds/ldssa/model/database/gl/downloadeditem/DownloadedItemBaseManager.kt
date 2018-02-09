/*
 * DownloadedItemBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.gl.downloadeditem

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class DownloadedItemBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<DownloadedItem>(databaseManager) {

    override val allColumns: Array<String> = DownloadedItemConst.ALL_COLUMNS
    override val primaryKey = DownloadedItemConst.PRIMARY_KEY_COLUMN
    override val dropSql = DownloadedItemConst.DROP_TABLE
    override val createSql = DownloadedItemConst.CREATE_TABLE
    override val insertSql = DownloadedItemConst.INSERT_STATEMENT
    override val updateSql = DownloadedItemConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return DownloadedItemConst.DATABASE
    }

    override fun newRecord() : DownloadedItem {
        return DownloadedItem()
    }

    override fun getTableName() : String {
        return DownloadedItemConst.TABLE
    }


}