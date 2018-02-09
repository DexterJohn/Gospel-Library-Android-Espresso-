/*
 * TagViewBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.tagview

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class TagViewBaseRecord  : AndroidBaseRecord {

    open var name: String = ""
    open var count: Int = 0
    open var lastModified: org.threeten.bp.LocalDateTime = org.threeten.bp.LocalDateTime.now()
    open var selected: Boolean = false

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
        return TagViewConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return TagViewConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(TagViewConst.C_NAME, name)
        values.put(TagViewConst.C_COUNT, count.toLong())
        values.put(TagViewConst.C_LAST_MODIFIED, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
        values.put(TagViewConst.C_SELECTED, if (selected) 1L else 0L)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            name,
            count.toLong(),
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!,
            if (selected) 1L else 0L)
    }

    open fun copy() : TagView {
        val copy = TagView()
        copy.name = name
        copy.count = count
        copy.lastModified = lastModified
        copy.selected = selected
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, name)
        statement.bindLong(2, count.toLong())
        statement.bindLong(3, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
        statement.bindLong(4, if (selected) 1L else 0L)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, name)
        statement.bindLong(2, count.toLong())
        statement.bindLong(3, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
        statement.bindLong(4, if (selected) 1L else 0L)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        name = values.getAsString(TagViewConst.C_NAME)
        count = values.getAsInteger(TagViewConst.C_COUNT)
        lastModified = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(values.getAsLong(TagViewConst.C_LAST_MODIFIED))!!
        selected = values.getAsBoolean(TagViewConst.C_SELECTED)
    }

    override fun setContent(cursor: Cursor) {
        name = cursor.getString(cursor.getColumnIndexOrThrow(TagViewConst.C_NAME))
        count = cursor.getInt(cursor.getColumnIndexOrThrow(TagViewConst.C_COUNT))
        lastModified = if (!cursor.isNull(cursor.getColumnIndexOrThrow(TagViewConst.C_LAST_MODIFIED))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(TagViewConst.C_LAST_MODIFIED)))!! else null!!
        selected = cursor.getInt(cursor.getColumnIndexOrThrow(TagViewConst.C_SELECTED)) != 0
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}