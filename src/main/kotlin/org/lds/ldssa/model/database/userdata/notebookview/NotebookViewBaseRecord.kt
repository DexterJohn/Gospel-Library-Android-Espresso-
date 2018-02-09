/*
 * NotebookViewBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.notebookview

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class NotebookViewBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var uniqueId: String = ""
    open var name: String = ""
    open var count: Int = 0
    open var status: org.lds.ldssa.model.database.types.AnnotationStatusType = org.lds.ldssa.model.database.types.AnnotationStatusType.ACTIVE
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
        return NotebookViewConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return NotebookViewConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(NotebookViewConst.C_ID, id)
        values.put(NotebookViewConst.C_UNIQUE_ID, uniqueId)
        values.put(NotebookViewConst.C_NAME, name)
        values.put(NotebookViewConst.C_COUNT, count.toLong())
        values.put(NotebookViewConst.C_STATUS, status.ordinal.toLong())
        values.put(NotebookViewConst.C_LAST_MODIFIED, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
        values.put(NotebookViewConst.C_SELECTED, if (selected) 1L else 0L)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            uniqueId,
            name,
            count.toLong(),
            status.ordinal.toLong(),
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!,
            if (selected) 1L else 0L)
    }

    open fun copy() : NotebookView {
        val copy = NotebookView()
        copy.id = id
        copy.uniqueId = uniqueId
        copy.name = name
        copy.count = count
        copy.status = status
        copy.lastModified = lastModified
        copy.selected = selected
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindString(2, uniqueId)
        statement.bindString(3, name)
        statement.bindLong(4, count.toLong())
        statement.bindLong(5, status.ordinal.toLong())
        statement.bindLong(6, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
        statement.bindLong(7, if (selected) 1L else 0L)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindString(2, uniqueId)
        statement.bindString(3, name)
        statement.bindLong(4, count.toLong())
        statement.bindLong(5, status.ordinal.toLong())
        statement.bindLong(6, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
        statement.bindLong(7, if (selected) 1L else 0L)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        id = values.getAsLong(NotebookViewConst.C_ID)
        uniqueId = values.getAsString(NotebookViewConst.C_UNIQUE_ID)
        name = values.getAsString(NotebookViewConst.C_NAME)
        count = values.getAsInteger(NotebookViewConst.C_COUNT)
        status = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.AnnotationStatusType::class.java, values.getAsInteger(NotebookViewConst.C_STATUS), org.lds.ldssa.model.database.types.AnnotationStatusType.ACTIVE)
        lastModified = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(values.getAsLong(NotebookViewConst.C_LAST_MODIFIED))!!
        selected = values.getAsBoolean(NotebookViewConst.C_SELECTED)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(NotebookViewConst.C_ID))
        uniqueId = cursor.getString(cursor.getColumnIndexOrThrow(NotebookViewConst.C_UNIQUE_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(NotebookViewConst.C_NAME))
        count = cursor.getInt(cursor.getColumnIndexOrThrow(NotebookViewConst.C_COUNT))
        status = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.AnnotationStatusType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(NotebookViewConst.C_STATUS)), org.lds.ldssa.model.database.types.AnnotationStatusType.ACTIVE)
        lastModified = if (!cursor.isNull(cursor.getColumnIndexOrThrow(NotebookViewConst.C_LAST_MODIFIED))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(NotebookViewConst.C_LAST_MODIFIED)))!! else null!!
        selected = cursor.getInt(cursor.getColumnIndexOrThrow(NotebookViewConst.C_SELECTED)) != 0
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}