/*
 * SyncContentItemAnnotationsQueueItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.synccontentitemannotationsqueueitem

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SyncContentItemAnnotationsQueueItemBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var contentItemId: Long = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return SyncContentItemAnnotationsQueueItemConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return SyncContentItemAnnotationsQueueItemConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SyncContentItemAnnotationsQueueItemConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SyncContentItemAnnotationsQueueItemConst.C_CONTENT_ITEM_ID, contentItemId)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            contentItemId)
    }

    open fun copy() : SyncContentItemAnnotationsQueueItem {
        val copy = SyncContentItemAnnotationsQueueItem()
        copy.id = id
        copy.contentItemId = contentItemId
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, contentItemId)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, contentItemId)
        statement.bindLong(2, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        contentItemId = values.getAsLong(SyncContentItemAnnotationsQueueItemConst.C_CONTENT_ITEM_ID)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(SyncContentItemAnnotationsQueueItemConst.C_ID))
        contentItemId = cursor.getLong(cursor.getColumnIndexOrThrow(SyncContentItemAnnotationsQueueItemConst.C_CONTENT_ITEM_ID))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}