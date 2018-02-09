/*
 * BookmarkQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.bookmarkquery

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object BookmarkQueryConst  {

    const val DATABASE = "userdata"
    const val C_ID = "_id"
    const val FULL_C_ID = "bookmark_query._id"
    const val C_ANNOTATION_ID = "annotation_id"
    const val FULL_C_ANNOTATION_ID = "bookmark_query.annotation_id"
    const val C_DOC_ID = "doc_id"
    const val FULL_C_DOC_ID = "bookmark_query.doc_id"
    const val C_PARAGRAPH_AID = "paragraph_aid"
    const val FULL_C_PARAGRAPH_AID = "bookmark_query.paragraph_aid"
    const val C_NAME = "name"
    const val FULL_C_NAME = "bookmark_query.name"
    const val C_CITATION = "citation"
    const val FULL_C_CITATION = "bookmark_query.citation"
    const val C_DISPLAY_ORDER = "display_order"
    const val FULL_C_DISPLAY_ORDER = "bookmark_query.display_order"
    const val C_LAST_MODIFIED = "last_modified"
    const val FULL_C_LAST_MODIFIED = "bookmark_query.last_modified"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_ANNOTATION_ID,
        C_DOC_ID,
        C_PARAGRAPH_AID,
        C_NAME,
        C_CITATION,
        C_DISPLAY_ORDER,
        C_LAST_MODIFIED)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_ANNOTATION_ID,
        FULL_C_DOC_ID,
        FULL_C_PARAGRAPH_AID,
        FULL_C_NAME,
        FULL_C_CITATION,
        FULL_C_DISPLAY_ORDER,
        FULL_C_LAST_MODIFIED)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getAnnotationId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ANNOTATION_ID))
    }

    fun getDocId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_DOC_ID))
    }

    fun getParagraphAid(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_PARAGRAPH_AID))) cursor.getString(cursor.getColumnIndexOrThrow(C_PARAGRAPH_AID)) else null
    }

    fun getName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }

    fun getCitation(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_CITATION))
    }

    fun getDisplayOrder(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_DISPLAY_ORDER))
    }

    fun getLastModified(cursor: Cursor) : org.threeten.bp.LocalDateTime {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_LAST_MODIFIED))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_LAST_MODIFIED)))!! else null!!
    }


}