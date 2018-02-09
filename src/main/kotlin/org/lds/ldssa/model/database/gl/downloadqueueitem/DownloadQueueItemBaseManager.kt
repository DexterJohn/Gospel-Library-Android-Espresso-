/*
 * DownloadQueueItemBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.gl.downloadqueueitem

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class DownloadQueueItemBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<DownloadQueueItem>(databaseManager) {

    override val allColumns: Array<String> = DownloadQueueItemConst.ALL_COLUMNS
    override val primaryKey = DownloadQueueItemConst.PRIMARY_KEY_COLUMN
    override val dropSql = DownloadQueueItemConst.DROP_TABLE
    override val createSql = DownloadQueueItemConst.CREATE_TABLE
    override val insertSql = DownloadQueueItemConst.INSERT_STATEMENT
    override val updateSql = DownloadQueueItemConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return DownloadQueueItemConst.DATABASE
    }

    override fun newRecord() : DownloadQueueItem {
        return DownloadQueueItem()
    }

    override fun getTableName() : String {
        return DownloadQueueItemConst.TABLE
    }


}