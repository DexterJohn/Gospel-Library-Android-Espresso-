/*
 * RelatedContentItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.relatedcontentitem

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object RelatedContentItemConst  {

    const val DATABASE = "content"
    const val TABLE = "related_content_item"
    const val FULL_TABLE = "content.related_content_item"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "related_content_item._id"
    const val C_SUBITEM_ID = "subitem_id"
    const val FULL_C_SUBITEM_ID = "related_content_item.subitem_id"
    const val C_REF_ID = "ref_id"
    const val FULL_C_REF_ID = "related_content_item.ref_id"
    const val C_LABEL_HTML = "label_html"
    const val FULL_C_LABEL_HTML = "related_content_item.label_html"
    const val C_ORIGIN_ID = "origin_id"
    const val FULL_C_ORIGIN_ID = "related_content_item.origin_id"
    const val C_CONTENT_HTML = "content_html"
    const val FULL_C_CONTENT_HTML = "related_content_item.content_html"
    const val C_WORD_OFFSET = "word_offset"
    const val FULL_C_WORD_OFFSET = "related_content_item.word_offset"
    const val C_BYTE_LOCATION = "byte_location"
    const val FULL_C_BYTE_LOCATION = "related_content_item.byte_location"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS related_content_item (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "subitem_id INTEGER NOT NULL," + 
        "ref_id TEXT NOT NULL," + 
        "label_html TEXT NOT NULL," + 
        "origin_id TEXT NOT NULL," + 
        "content_html TEXT NOT NULL," + 
        "word_offset INTEGER NOT NULL," + 
        "byte_location INTEGER NOT NULL," + 
        "UNIQUE(subitem_id, byte_location)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS related_content_item;"
    const val INSERT_STATEMENT = "INSERT INTO related_content_item (subitem_id,ref_id,label_html,origin_id,content_html,word_offset,byte_location) VALUES (?,?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE related_content_item SET subitem_id=?, ref_id=?, label_html=?, origin_id=?, content_html=?, word_offset=?, byte_location=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_SUBITEM_ID,
        C_REF_ID,
        C_LABEL_HTML,
        C_ORIGIN_ID,
        C_CONTENT_HTML,
        C_WORD_OFFSET,
        C_BYTE_LOCATION)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_SUBITEM_ID,
        FULL_C_REF_ID,
        FULL_C_LABEL_HTML,
        FULL_C_ORIGIN_ID,
        FULL_C_CONTENT_HTML,
        FULL_C_WORD_OFFSET,
        FULL_C_BYTE_LOCATION)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getSubitemId(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SUBITEM_ID))
    }

    fun getRefId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_REF_ID))
    }

    fun getLabelHtml(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_LABEL_HTML))
    }

    fun getOriginId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_ORIGIN_ID))
    }

    fun getContentHtml(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_CONTENT_HTML))
    }

    fun getWordOffset(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_WORD_OFFSET))
    }

    fun getByteLocation(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_BYTE_LOCATION))
    }


}