/*
 * HighlightBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.highlight

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object HighlightConst  {

    const val DATABASE = "userdata"
    const val TABLE = "highlight"
    const val FULL_TABLE = "userdata.highlight"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "highlight._id"
    const val C_ANNOTATION_ID = "annotation_id"
    const val FULL_C_ANNOTATION_ID = "highlight.annotation_id"
    const val C_PARAGRAPH_AID = "paragraph_aid"
    const val FULL_C_PARAGRAPH_AID = "highlight.paragraph_aid"
    const val C_OFFSET_START = "offset_start"
    const val FULL_C_OFFSET_START = "highlight.offset_start"
    const val C_OFFSET_END = "offset_end"
    const val FULL_C_OFFSET_END = "highlight.offset_end"
    const val C_COLOR = "color"
    const val FULL_C_COLOR = "highlight.color"
    const val C_STYLE = "style"
    const val FULL_C_STYLE = "highlight.style"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS highlight (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "annotation_id INTEGER NOT NULL," + 
        "paragraph_aid TEXT," + 
        "offset_start INTEGER DEFAULT -1 NOT NULL," + 
        "offset_end INTEGER DEFAULT -1 NOT NULL," + 
        "color TEXT," + 
        "style TEXT" + 
        ");" + 
        "" + 
        "CREATE INDEX IF NOT EXISTS highlightannotation_id_IDX ON highlight (annotation_id);" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS highlight;"
    const val INSERT_STATEMENT = "INSERT INTO highlight (annotation_id,paragraph_aid,offset_start,offset_end,color,style) VALUES (?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE highlight SET annotation_id=?, paragraph_aid=?, offset_start=?, offset_end=?, color=?, style=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_ANNOTATION_ID,
        C_PARAGRAPH_AID,
        C_OFFSET_START,
        C_OFFSET_END,
        C_COLOR,
        C_STYLE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_ANNOTATION_ID,
        FULL_C_PARAGRAPH_AID,
        FULL_C_OFFSET_START,
        FULL_C_OFFSET_END,
        FULL_C_COLOR,
        FULL_C_STYLE)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getAnnotationId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ANNOTATION_ID))
    }

    fun getParagraphAid(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_PARAGRAPH_AID))) cursor.getString(cursor.getColumnIndexOrThrow(C_PARAGRAPH_AID)) else null
    }

    fun getOffsetStart(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_OFFSET_START))
    }

    fun getOffsetEnd(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_OFFSET_END))
    }

    fun getColor(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_COLOR))) cursor.getString(cursor.getColumnIndexOrThrow(C_COLOR)) else null
    }

    fun getStyle(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_STYLE))) cursor.getString(cursor.getColumnIndexOrThrow(C_STYLE)) else null
    }


}