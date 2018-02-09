/*
 * AnnotationBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.annotation

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class AnnotationBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var uniqueId: String = ""
    open var contentVersion: Int = 0
    open var device: String? = "android"
    open var source: String? = null
    open var status: org.lds.ldssa.model.database.types.AnnotationStatusType = org.lds.ldssa.model.database.types.AnnotationStatusType.ACTIVE
    open var docId: String? = null
    open var lastModified: org.threeten.bp.LocalDateTime = org.threeten.bp.LocalDateTime.now()
    open var citation: String? = null
    open var scope: String? = null

    constructor() {
    }

    override fun getIdColumnName() : String {
        return AnnotationConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return AnnotationConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return AnnotationConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(AnnotationConst.C_UNIQUE_ID, uniqueId)
        values.put(AnnotationConst.C_CONTENT_VERSION, contentVersion.toLong())
        values.put(AnnotationConst.C_DEVICE, device)
        values.put(AnnotationConst.C_SOURCE, source)
        values.put(AnnotationConst.C_STATUS, status.ordinal.toLong())
        values.put(AnnotationConst.C_DOC_ID, docId)
        values.put(AnnotationConst.C_LAST_MODIFIED, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
        values.put(AnnotationConst.C_CITATION, citation)
        values.put(AnnotationConst.C_SCOPE, scope)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            uniqueId,
            contentVersion.toLong(),
            device,
            source,
            status.ordinal.toLong(),
            docId,
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!,
            citation,
            scope)
    }

    open fun copy() : Annotation {
        val copy = Annotation()
        copy.id = id
        copy.uniqueId = uniqueId
        copy.contentVersion = contentVersion
        copy.device = device
        copy.source = source
        copy.status = status
        copy.docId = docId
        copy.lastModified = lastModified
        copy.citation = citation
        copy.scope = scope
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, uniqueId)
        statement.bindLong(2, contentVersion.toLong())
        if (device != null) {
            statement.bindString(3, device!!)
        } else {
            statement.bindNull(3)
        }
        if (source != null) {
            statement.bindString(4, source!!)
        } else {
            statement.bindNull(4)
        }
        statement.bindLong(5, status.ordinal.toLong())
        if (docId != null) {
            statement.bindString(6, docId!!)
        } else {
            statement.bindNull(6)
        }
        statement.bindLong(7, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
        if (citation != null) {
            statement.bindString(8, citation!!)
        } else {
            statement.bindNull(8)
        }
        if (scope != null) {
            statement.bindString(9, scope!!)
        } else {
            statement.bindNull(9)
        }
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, uniqueId)
        statement.bindLong(2, contentVersion.toLong())
        if (device != null) {
            statement.bindString(3, device!!)
        } else {
            statement.bindNull(3)
        }
        if (source != null) {
            statement.bindString(4, source!!)
        } else {
            statement.bindNull(4)
        }
        statement.bindLong(5, status.ordinal.toLong())
        if (docId != null) {
            statement.bindString(6, docId!!)
        } else {
            statement.bindNull(6)
        }
        statement.bindLong(7, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
        if (citation != null) {
            statement.bindString(8, citation!!)
        } else {
            statement.bindNull(8)
        }
        if (scope != null) {
            statement.bindString(9, scope!!)
        } else {
            statement.bindNull(9)
        }
        statement.bindLong(10, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        uniqueId = values.getAsString(AnnotationConst.C_UNIQUE_ID)
        contentVersion = values.getAsInteger(AnnotationConst.C_CONTENT_VERSION)
        device = values.getAsString(AnnotationConst.C_DEVICE)
        source = values.getAsString(AnnotationConst.C_SOURCE)
        status = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.AnnotationStatusType::class.java, values.getAsInteger(AnnotationConst.C_STATUS), org.lds.ldssa.model.database.types.AnnotationStatusType.ACTIVE)
        docId = values.getAsString(AnnotationConst.C_DOC_ID)
        lastModified = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(values.getAsLong(AnnotationConst.C_LAST_MODIFIED))!!
        citation = values.getAsString(AnnotationConst.C_CITATION)
        scope = values.getAsString(AnnotationConst.C_SCOPE)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(AnnotationConst.C_ID))
        uniqueId = cursor.getString(cursor.getColumnIndexOrThrow(AnnotationConst.C_UNIQUE_ID))
        contentVersion = cursor.getInt(cursor.getColumnIndexOrThrow(AnnotationConst.C_CONTENT_VERSION))
        device = if (!cursor.isNull(cursor.getColumnIndexOrThrow(AnnotationConst.C_DEVICE))) cursor.getString(cursor.getColumnIndexOrThrow(AnnotationConst.C_DEVICE)) else null
        source = if (!cursor.isNull(cursor.getColumnIndexOrThrow(AnnotationConst.C_SOURCE))) cursor.getString(cursor.getColumnIndexOrThrow(AnnotationConst.C_SOURCE)) else null
        status = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.AnnotationStatusType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(AnnotationConst.C_STATUS)), org.lds.ldssa.model.database.types.AnnotationStatusType.ACTIVE)
        docId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(AnnotationConst.C_DOC_ID))) cursor.getString(cursor.getColumnIndexOrThrow(AnnotationConst.C_DOC_ID)) else null
        lastModified = if (!cursor.isNull(cursor.getColumnIndexOrThrow(AnnotationConst.C_LAST_MODIFIED))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(AnnotationConst.C_LAST_MODIFIED)))!! else null!!
        citation = if (!cursor.isNull(cursor.getColumnIndexOrThrow(AnnotationConst.C_CITATION))) cursor.getString(cursor.getColumnIndexOrThrow(AnnotationConst.C_CITATION)) else null
        scope = if (!cursor.isNull(cursor.getColumnIndexOrThrow(AnnotationConst.C_SCOPE))) cursor.getString(cursor.getColumnIndexOrThrow(AnnotationConst.C_SCOPE)) else null
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}