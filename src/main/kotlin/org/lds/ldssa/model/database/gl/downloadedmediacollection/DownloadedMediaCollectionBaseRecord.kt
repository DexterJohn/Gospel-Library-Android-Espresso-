/*
 * DownloadedMediaCollectionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.downloadedmediacollection

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class DownloadedMediaCollectionBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var itemCount: Long = 0
    open var totalSize: Long = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return ""
    }

    override fun getPrimaryKeyId() : Long {
        return 0
    }

    override fun setPrimaryKeyId(id: Long) {
    }

    override fun getAllColumns() : Array<String> {
        return DownloadedMediaCollectionConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return DownloadedMediaCollectionConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(DownloadedMediaCollectionConst.C_ID, id)
        values.put(DownloadedMediaCollectionConst.C_ITEM_COUNT, itemCount)
        values.put(DownloadedMediaCollectionConst.C_TOTAL_SIZE, totalSize)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            itemCount,
            totalSize)
    }

    open fun copy() : DownloadedMediaCollection {
        val copy = DownloadedMediaCollection()
        copy.id = id
        copy.itemCount = itemCount
        copy.totalSize = totalSize
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, itemCount)
        statement.bindLong(3, totalSize)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, itemCount)
        statement.bindLong(3, totalSize)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        id = values.getAsLong(DownloadedMediaCollectionConst.C_ID)
        itemCount = values.getAsLong(DownloadedMediaCollectionConst.C_ITEM_COUNT)
        totalSize = values.getAsLong(DownloadedMediaCollectionConst.C_TOTAL_SIZE)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadedMediaCollectionConst.C_ID))
        itemCount = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadedMediaCollectionConst.C_ITEM_COUNT))
        totalSize = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadedMediaCollectionConst.C_TOTAL_SIZE))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}