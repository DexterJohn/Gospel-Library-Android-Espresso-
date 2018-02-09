/*
 * ContentMetaDataBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.contentmetadata

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class ContentMetaDataBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var key: String = ""
    open var value: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return ContentMetaDataConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return ContentMetaDataConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return ContentMetaDataConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(ContentMetaDataConst.C_KEY, key)
        values.put(ContentMetaDataConst.C_VALUE, value)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            key,
            value)
    }

    open fun copy() : ContentMetaData {
        val copy = ContentMetaData()
        copy.id = id
        copy.key = key
        copy.value = value
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, key)
        statement.bindString(2, value)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, key)
        statement.bindString(2, value)
        statement.bindLong(3, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        key = values.getAsString(ContentMetaDataConst.C_KEY)
        value = values.getAsString(ContentMetaDataConst.C_VALUE)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(ContentMetaDataConst.C_ID))
        key = cursor.getString(cursor.getColumnIndexOrThrow(ContentMetaDataConst.C_KEY))
        value = cursor.getString(cursor.getColumnIndexOrThrow(ContentMetaDataConst.C_VALUE))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}