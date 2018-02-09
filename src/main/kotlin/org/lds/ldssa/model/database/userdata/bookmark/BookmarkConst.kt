/*
 * BookmarkBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.bookmark

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object BookmarkConst  {

    const val DATABASE = "userdata"
    const val TABLE = "bookmark"
    const val FULL_TABLE = "userdata.bookmark"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "bookmark._id"
    const val C_ANNOTATION_ID = "annotation_id"
    const val FULL_C_ANNOTATION_ID = "bookmark.annotation_id"
    const val C_PARAGRAPH_AID = "paragraph_aid"
    const val FULL_C_PARAGRAPH_AID = "bookmark.paragraph_aid"
    const val C_OFFSET = "offset"
    const val FULL_C_OFFSET = "bookmark.offset"
    const val C_DISPLAY_ORDER = "display_order"
    const val FULL_C_DISPLAY_ORDER = "bookmark.display_order"
    const val C_NAME = "name"
    const val FULL_C_NAME = "bookmark.name"
    const val C_CITATION = "citation"
    const val FULL_C_CITATION = "bookmark.citation"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS bookmark (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "annotation_id INTEGER NOT NULL," + 
        "paragraph_aid TEXT," + 
        "offset INTEGER NOT NULL," + 
        "display_order INTEGER DEFAULT 0 NOT NULL," + 
        "name TEXT NOT NULL," + 
        "citation TEXT NOT NULL" + 
        ");" + 
        "" + 
        "CREATE INDEX IF NOT EXISTS bookmarkannotation_id_IDX ON bookmark (annotation_id);" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS bookmark;"
    const val INSERT_STATEMENT = "INSERT INTO bookmark (annotation_id,paragraph_aid,offset,display_order,name,citation) VALUES (?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE bookmark SET annotation_id=?, paragraph_aid=?, offset=?, display_order=?, name=?, citation=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_ANNOTATION_ID,
        C_PARAGRAPH_AID,
        C_OFFSET,
        C_DISPLAY_ORDER,
        C_NAME,
        C_CITATION)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_ANNOTATION_ID,
        FULL_C_PARAGRAPH_AID,
        FULL_C_OFFSET,
        FULL_C_DISPLAY_ORDER,
        FULL_C_NAME,
        FULL_C_CITATION)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getAnnotationId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ANNOTATION_ID))
    }

    fun getParagraphAid(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_PARAGRAPH_AID))) cursor.getString(cursor.getColumnIndexOrThrow(C_PARAGRAPH_AID)) else null
    }

    fun getOffset(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_OFFSET))
    }

    fun getDisplayOrder(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_DISPLAY_ORDER))
    }

    fun getName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }

    fun getCitation(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_CITATION))
    }


}