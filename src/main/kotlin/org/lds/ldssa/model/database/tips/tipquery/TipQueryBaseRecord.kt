/*
 * TipQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.tips.tipquery

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class TipQueryBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var iso6393: String = ""
    open var title: String = ""
    open var versionId: Long = 0
    open var versionName: String = ""
    open var viewed: Boolean = false

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
        return TipQueryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return TipQueryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(TipQueryConst.C_ID, id)
        values.put(TipQueryConst.C_ISO6393, iso6393)
        values.put(TipQueryConst.C_TITLE, title)
        values.put(TipQueryConst.C_VERSION_ID, versionId)
        values.put(TipQueryConst.C_VERSION_NAME, versionName)
        values.put(TipQueryConst.C_VIEWED, if (viewed) 1L else 0L)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            iso6393,
            title,
            versionId,
            versionName,
            if (viewed) 1L else 0L)
    }

    open fun copy() : TipQuery {
        val copy = TipQuery()
        copy.id = id
        copy.iso6393 = iso6393
        copy.title = title
        copy.versionId = versionId
        copy.versionName = versionName
        copy.viewed = viewed
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindString(2, iso6393)
        statement.bindString(3, title)
        statement.bindLong(4, versionId)
        statement.bindString(5, versionName)
        statement.bindLong(6, if (viewed) 1L else 0L)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindString(2, iso6393)
        statement.bindString(3, title)
        statement.bindLong(4, versionId)
        statement.bindString(5, versionName)
        statement.bindLong(6, if (viewed) 1L else 0L)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        id = values.getAsLong(TipQueryConst.C_ID)
        iso6393 = values.getAsString(TipQueryConst.C_ISO6393)
        title = values.getAsString(TipQueryConst.C_TITLE)
        versionId = values.getAsLong(TipQueryConst.C_VERSION_ID)
        versionName = values.getAsString(TipQueryConst.C_VERSION_NAME)
        viewed = values.getAsBoolean(TipQueryConst.C_VIEWED)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(TipQueryConst.C_ID))
        iso6393 = cursor.getString(cursor.getColumnIndexOrThrow(TipQueryConst.C_ISO6393))
        title = cursor.getString(cursor.getColumnIndexOrThrow(TipQueryConst.C_TITLE))
        versionId = cursor.getLong(cursor.getColumnIndexOrThrow(TipQueryConst.C_VERSION_ID))
        versionName = cursor.getString(cursor.getColumnIndexOrThrow(TipQueryConst.C_VERSION_NAME))
        viewed = cursor.getInt(cursor.getColumnIndexOrThrow(TipQueryConst.C_VIEWED)) != 0
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}