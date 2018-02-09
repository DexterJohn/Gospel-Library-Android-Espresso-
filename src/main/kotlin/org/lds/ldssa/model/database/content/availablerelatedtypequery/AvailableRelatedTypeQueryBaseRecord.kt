/*
 * AvailableRelatedTypeQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.availablerelatedtypequery

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class AvailableRelatedTypeQueryBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var subitemId: Int = 0
    open var audioAvailable: Boolean = false
    open var pdfAvailable: Boolean = false

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
        return AvailableRelatedTypeQueryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return AvailableRelatedTypeQueryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(AvailableRelatedTypeQueryConst.C_ID, id)
        values.put(AvailableRelatedTypeQueryConst.C_SUBITEM_ID, subitemId.toLong())
        values.put(AvailableRelatedTypeQueryConst.C_AUDIO_AVAILABLE, if (audioAvailable) 1L else 0L)
        values.put(AvailableRelatedTypeQueryConst.C_PDF_AVAILABLE, if (pdfAvailable) 1L else 0L)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            subitemId.toLong(),
            if (audioAvailable) 1L else 0L,
            if (pdfAvailable) 1L else 0L)
    }

    open fun copy() : AvailableRelatedTypeQuery {
        val copy = AvailableRelatedTypeQuery()
        copy.id = id
        copy.subitemId = subitemId
        copy.audioAvailable = audioAvailable
        copy.pdfAvailable = pdfAvailable
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, subitemId.toLong())
        statement.bindLong(3, if (audioAvailable) 1L else 0L)
        statement.bindLong(4, if (pdfAvailable) 1L else 0L)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, subitemId.toLong())
        statement.bindLong(3, if (audioAvailable) 1L else 0L)
        statement.bindLong(4, if (pdfAvailable) 1L else 0L)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        id = values.getAsLong(AvailableRelatedTypeQueryConst.C_ID)
        subitemId = values.getAsInteger(AvailableRelatedTypeQueryConst.C_SUBITEM_ID)
        audioAvailable = values.getAsBoolean(AvailableRelatedTypeQueryConst.C_AUDIO_AVAILABLE)
        pdfAvailable = values.getAsBoolean(AvailableRelatedTypeQueryConst.C_PDF_AVAILABLE)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(AvailableRelatedTypeQueryConst.C_ID))
        subitemId = cursor.getInt(cursor.getColumnIndexOrThrow(AvailableRelatedTypeQueryConst.C_SUBITEM_ID))
        audioAvailable = cursor.getInt(cursor.getColumnIndexOrThrow(AvailableRelatedTypeQueryConst.C_AUDIO_AVAILABLE)) != 0
        pdfAvailable = cursor.getInt(cursor.getColumnIndexOrThrow(AvailableRelatedTypeQueryConst.C_PDF_AVAILABLE)) != 0
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}