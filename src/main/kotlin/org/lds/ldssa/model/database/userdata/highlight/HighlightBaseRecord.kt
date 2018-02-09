/*
 * HighlightBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.highlight

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class HighlightBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var annotationId: Long = 0
    open var paragraphAid: String? = null
    open var offsetStart: Int = -1
    open var offsetEnd: Int = -1
    open var color: String? = null
    open var style: String? = null

    constructor() {
    }

    override fun getIdColumnName() : String {
        return HighlightConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return HighlightConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return HighlightConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(HighlightConst.C_ANNOTATION_ID, annotationId)
        values.put(HighlightConst.C_PARAGRAPH_AID, paragraphAid)
        values.put(HighlightConst.C_OFFSET_START, offsetStart.toLong())
        values.put(HighlightConst.C_OFFSET_END, offsetEnd.toLong())
        values.put(HighlightConst.C_COLOR, color)
        values.put(HighlightConst.C_STYLE, style)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            annotationId,
            paragraphAid,
            offsetStart.toLong(),
            offsetEnd.toLong(),
            color,
            style)
    }

    open fun copy() : Highlight {
        val copy = Highlight()
        copy.id = id
        copy.annotationId = annotationId
        copy.paragraphAid = paragraphAid
        copy.offsetStart = offsetStart
        copy.offsetEnd = offsetEnd
        copy.color = color
        copy.style = style
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, annotationId)
        if (paragraphAid != null) {
            statement.bindString(2, paragraphAid!!)
        } else {
            statement.bindNull(2)
        }
        statement.bindLong(3, offsetStart.toLong())
        statement.bindLong(4, offsetEnd.toLong())
        if (color != null) {
            statement.bindString(5, color!!)
        } else {
            statement.bindNull(5)
        }
        if (style != null) {
            statement.bindString(6, style!!)
        } else {
            statement.bindNull(6)
        }
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, annotationId)
        if (paragraphAid != null) {
            statement.bindString(2, paragraphAid!!)
        } else {
            statement.bindNull(2)
        }
        statement.bindLong(3, offsetStart.toLong())
        statement.bindLong(4, offsetEnd.toLong())
        if (color != null) {
            statement.bindString(5, color!!)
        } else {
            statement.bindNull(5)
        }
        if (style != null) {
            statement.bindString(6, style!!)
        } else {
            statement.bindNull(6)
        }
        statement.bindLong(7, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        annotationId = values.getAsLong(HighlightConst.C_ANNOTATION_ID)
        paragraphAid = values.getAsString(HighlightConst.C_PARAGRAPH_AID)
        offsetStart = values.getAsInteger(HighlightConst.C_OFFSET_START)
        offsetEnd = values.getAsInteger(HighlightConst.C_OFFSET_END)
        color = values.getAsString(HighlightConst.C_COLOR)
        style = values.getAsString(HighlightConst.C_STYLE)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(HighlightConst.C_ID))
        annotationId = cursor.getLong(cursor.getColumnIndexOrThrow(HighlightConst.C_ANNOTATION_ID))
        paragraphAid = if (!cursor.isNull(cursor.getColumnIndexOrThrow(HighlightConst.C_PARAGRAPH_AID))) cursor.getString(cursor.getColumnIndexOrThrow(HighlightConst.C_PARAGRAPH_AID)) else null
        offsetStart = cursor.getInt(cursor.getColumnIndexOrThrow(HighlightConst.C_OFFSET_START))
        offsetEnd = cursor.getInt(cursor.getColumnIndexOrThrow(HighlightConst.C_OFFSET_END))
        color = if (!cursor.isNull(cursor.getColumnIndexOrThrow(HighlightConst.C_COLOR))) cursor.getString(cursor.getColumnIndexOrThrow(HighlightConst.C_COLOR)) else null
        style = if (!cursor.isNull(cursor.getColumnIndexOrThrow(HighlightConst.C_STYLE))) cursor.getString(cursor.getColumnIndexOrThrow(HighlightConst.C_STYLE)) else null
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}