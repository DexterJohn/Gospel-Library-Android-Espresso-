/*
 * SyncContentItemAnnotationsQueueItemBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.userdata.synccontentitemannotationsqueueitem

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class SyncContentItemAnnotationsQueueItemBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<SyncContentItemAnnotationsQueueItem>(databaseManager) {

    override val allColumns: Array<String> = SyncContentItemAnnotationsQueueItemConst.ALL_COLUMNS
    override val primaryKey = SyncContentItemAnnotationsQueueItemConst.PRIMARY_KEY_COLUMN
    override val dropSql = SyncContentItemAnnotationsQueueItemConst.DROP_TABLE
    override val createSql = SyncContentItemAnnotationsQueueItemConst.CREATE_TABLE
    override val insertSql = SyncContentItemAnnotationsQueueItemConst.INSERT_STATEMENT
    override val updateSql = SyncContentItemAnnotationsQueueItemConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return SyncContentItemAnnotationsQueueItemConst.DATABASE
    }

    override fun newRecord() : SyncContentItemAnnotationsQueueItem {
        return SyncContentItemAnnotationsQueueItem()
    }

    override fun getTableName() : String {
        return SyncContentItemAnnotationsQueueItemConst.TABLE
    }


}