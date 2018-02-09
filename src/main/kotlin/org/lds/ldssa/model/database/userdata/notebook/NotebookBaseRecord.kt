/*
 * NotebookBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.notebook

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class NotebookBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var uniqueId: String = ""
    open var name: String = ""
    open var description: String = ""
    open var status: org.lds.ldssa.model.database.types.AnnotationStatusType = org.lds.ldssa.model.database.types.AnnotationStatusType.ACTIVE
    open var lastModified: org.threeten.bp.LocalDateTime = org.threeten.bp.LocalDateTime.now()

    constructor() {
    }

    override fun getIdColumnName() : String {
        return NotebookConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return NotebookConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return NotebookConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(NotebookConst.C_UNIQUE_ID, uniqueId)
        values.put(NotebookConst.C_NAME, name)
        values.put(NotebookConst.C_DESCRIPTION, description)
        values.put(NotebookConst.C_STATUS, status.ordinal.toLong())
        values.put(NotebookConst.C_LAST_MODIFIED, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            uniqueId,
            name,
            description,
            status.ordinal.toLong(),
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
    }

    open fun copy() : Notebook {
        val copy = Notebook()
        copy.id = id
        copy.uniqueId = uniqueId
        copy.name = name
        copy.description = description
        copy.status = status
        copy.lastModified = lastModified
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, uniqueId)
        statement.bindString(2, name)
        statement.bindString(3, description)
        statement.bindLong(4, status.ordinal.toLong())
        statement.bindLong(5, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, uniqueId)
        statement.bindString(2, name)
        statement.bindString(3, description)
        statement.bindLong(4, status.ordinal.toLong())
        statement.bindLong(5, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
        statement.bindLong(6, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        uniqueId = values.getAsString(NotebookConst.C_UNIQUE_ID)
        name = values.getAsString(NotebookConst.C_NAME)
        description = values.getAsString(NotebookConst.C_DESCRIPTION)
        status = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.AnnotationStatusType::class.java, values.getAsInteger(NotebookConst.C_STATUS), org.lds.ldssa.model.database.types.AnnotationStatusType.ACTIVE)
        lastModified = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(values.getAsLong(NotebookConst.C_LAST_MODIFIED))!!
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(NotebookConst.C_ID))
        uniqueId = cursor.getString(cursor.getColumnIndexOrThrow(NotebookConst.C_UNIQUE_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(NotebookConst.C_NAME))
        description = cursor.getString(cursor.getColumnIndexOrThrow(NotebookConst.C_DESCRIPTION))
        status = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.AnnotationStatusType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(NotebookConst.C_STATUS)), org.lds.ldssa.model.database.types.AnnotationStatusType.ACTIVE)
        lastModified = if (!cursor.isNull(cursor.getColumnIndexOrThrow(NotebookConst.C_LAST_MODIFIED))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(NotebookConst.C_LAST_MODIFIED)))!! else null!!
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}