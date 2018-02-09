/*
 * BookmarkBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.bookmark

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class BookmarkBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var annotationId: Long = 0
    open var paragraphAid: String? = null
    open var offset: Int = 0
    open var displayOrder: Int = 0
    open var name: String = ""
    open var citation: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return BookmarkConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return BookmarkConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return BookmarkConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(BookmarkConst.C_ANNOTATION_ID, annotationId)
        values.put(BookmarkConst.C_PARAGRAPH_AID, paragraphAid)
        values.put(BookmarkConst.C_OFFSET, offset.toLong())
        values.put(BookmarkConst.C_DISPLAY_ORDER, displayOrder.toLong())
        values.put(BookmarkConst.C_NAME, name)
        values.put(BookmarkConst.C_CITATION, citation)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            annotationId,
            paragraphAid,
            offset.toLong(),
            displayOrder.toLong(),
            name,
            citation)
    }

    open fun copy() : Bookmark {
        val copy = Bookmark()
        copy.id = id
        copy.annotationId = annotationId
        copy.paragraphAid = paragraphAid
        copy.offset = offset
        copy.displayOrder = displayOrder
        copy.name = name
        copy.citation = citation
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
        statement.bindLong(3, offset.toLong())
        statement.bindLong(4, displayOrder.toLong())
        statement.bindString(5, name)
        statement.bindString(6, citation)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, annotationId)
        if (paragraphAid != null) {
            statement.bindString(2, paragraphAid!!)
        } else {
            statement.bindNull(2)
        }
        statement.bindLong(3, offset.toLong())
        statement.bindLong(4, displayOrder.toLong())
        statement.bindString(5, name)
        statement.bindString(6, citation)
        statement.bindLong(7, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        annotationId = values.getAsLong(BookmarkConst.C_ANNOTATION_ID)
        paragraphAid = values.getAsString(BookmarkConst.C_PARAGRAPH_AID)
        offset = values.getAsInteger(BookmarkConst.C_OFFSET)
        displayOrder = values.getAsInteger(BookmarkConst.C_DISPLAY_ORDER)
        name = values.getAsString(BookmarkConst.C_NAME)
        citation = values.getAsString(BookmarkConst.C_CITATION)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(BookmarkConst.C_ID))
        annotationId = cursor.getLong(cursor.getColumnIndexOrThrow(BookmarkConst.C_ANNOTATION_ID))
        paragraphAid = if (!cursor.isNull(cursor.getColumnIndexOrThrow(BookmarkConst.C_PARAGRAPH_AID))) cursor.getString(cursor.getColumnIndexOrThrow(BookmarkConst.C_PARAGRAPH_AID)) else null
        offset = cursor.getInt(cursor.getColumnIndexOrThrow(BookmarkConst.C_OFFSET))
        displayOrder = cursor.getInt(cursor.getColumnIndexOrThrow(BookmarkConst.C_DISPLAY_ORDER))
        name = cursor.getString(cursor.getColumnIndexOrThrow(BookmarkConst.C_NAME))
        citation = cursor.getString(cursor.getColumnIndexOrThrow(BookmarkConst.C_CITATION))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}