/*
 * BookmarkQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.bookmarkquery

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class BookmarkQueryBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var annotationId: Long = 0
    open var docId: String = ""
    open var paragraphAid: String? = null
    open var name: String = ""
    open var citation: String = ""
    open var displayOrder: Int = 0
    open var lastModified: org.threeten.bp.LocalDateTime = org.threeten.bp.LocalDateTime.now()

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
        return BookmarkQueryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return BookmarkQueryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(BookmarkQueryConst.C_ID, id)
        values.put(BookmarkQueryConst.C_ANNOTATION_ID, annotationId)
        values.put(BookmarkQueryConst.C_DOC_ID, docId)
        values.put(BookmarkQueryConst.C_PARAGRAPH_AID, paragraphAid)
        values.put(BookmarkQueryConst.C_NAME, name)
        values.put(BookmarkQueryConst.C_CITATION, citation)
        values.put(BookmarkQueryConst.C_DISPLAY_ORDER, displayOrder.toLong())
        values.put(BookmarkQueryConst.C_LAST_MODIFIED, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            annotationId,
            docId,
            paragraphAid,
            name,
            citation,
            displayOrder.toLong(),
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
    }

    open fun copy() : BookmarkQuery {
        val copy = BookmarkQuery()
        copy.id = id
        copy.annotationId = annotationId
        copy.docId = docId
        copy.paragraphAid = paragraphAid
        copy.name = name
        copy.citation = citation
        copy.displayOrder = displayOrder
        copy.lastModified = lastModified
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, annotationId)
        statement.bindString(3, docId)
        if (paragraphAid != null) {
            statement.bindString(4, paragraphAid!!)
        } else {
            statement.bindNull(4)
        }
        statement.bindString(5, name)
        statement.bindString(6, citation)
        statement.bindLong(7, displayOrder.toLong())
        statement.bindLong(8, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, annotationId)
        statement.bindString(3, docId)
        if (paragraphAid != null) {
            statement.bindString(4, paragraphAid!!)
        } else {
            statement.bindNull(4)
        }
        statement.bindString(5, name)
        statement.bindString(6, citation)
        statement.bindLong(7, displayOrder.toLong())
        statement.bindLong(8, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        id = values.getAsLong(BookmarkQueryConst.C_ID)
        annotationId = values.getAsLong(BookmarkQueryConst.C_ANNOTATION_ID)
        docId = values.getAsString(BookmarkQueryConst.C_DOC_ID)
        paragraphAid = values.getAsString(BookmarkQueryConst.C_PARAGRAPH_AID)
        name = values.getAsString(BookmarkQueryConst.C_NAME)
        citation = values.getAsString(BookmarkQueryConst.C_CITATION)
        displayOrder = values.getAsInteger(BookmarkQueryConst.C_DISPLAY_ORDER)
        lastModified = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(values.getAsLong(BookmarkQueryConst.C_LAST_MODIFIED))!!
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(BookmarkQueryConst.C_ID))
        annotationId = cursor.getLong(cursor.getColumnIndexOrThrow(BookmarkQueryConst.C_ANNOTATION_ID))
        docId = cursor.getString(cursor.getColumnIndexOrThrow(BookmarkQueryConst.C_DOC_ID))
        paragraphAid = if (!cursor.isNull(cursor.getColumnIndexOrThrow(BookmarkQueryConst.C_PARAGRAPH_AID))) cursor.getString(cursor.getColumnIndexOrThrow(BookmarkQueryConst.C_PARAGRAPH_AID)) else null
        name = cursor.getString(cursor.getColumnIndexOrThrow(BookmarkQueryConst.C_NAME))
        citation = cursor.getString(cursor.getColumnIndexOrThrow(BookmarkQueryConst.C_CITATION))
        displayOrder = cursor.getInt(cursor.getColumnIndexOrThrow(BookmarkQueryConst.C_DISPLAY_ORDER))
        lastModified = if (!cursor.isNull(cursor.getColumnIndexOrThrow(BookmarkQueryConst.C_LAST_MODIFIED))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(BookmarkQueryConst.C_LAST_MODIFIED)))!! else null!!
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}