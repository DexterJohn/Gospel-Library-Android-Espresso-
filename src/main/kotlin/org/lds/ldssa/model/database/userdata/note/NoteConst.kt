/*
 * NoteBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.note

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object NoteConst  {

    const val DATABASE = "userdata"
    const val TABLE = "note"
    const val FULL_TABLE = "userdata.note"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "note._id"
    const val C_ANNOTATION_ID = "annotation_id"
    const val FULL_C_ANNOTATION_ID = "note.annotation_id"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "note.title"
    const val C_CONTENT = "content"
    const val FULL_C_CONTENT = "note.content"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS note (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "annotation_id INTEGER NOT NULL," + 
        "title TEXT," + 
        "content TEXT" + 
        ");" + 
        "" + 
        "CREATE INDEX IF NOT EXISTS noteannotation_id_IDX ON note (annotation_id);" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS note;"
    const val INSERT_STATEMENT = "INSERT INTO note (annotation_id,title,content) VALUES (?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE note SET annotation_id=?, title=?, content=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_ANNOTATION_ID,
        C_TITLE,
        C_CONTENT)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_ANNOTATION_ID,
        FULL_C_TITLE,
        FULL_C_CONTENT)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getAnnotationId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ANNOTATION_ID))
    }

    fun getTitle(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_TITLE))) cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE)) else null
    }

    fun getContent(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_CONTENT))) cursor.getString(cursor.getColumnIndexOrThrow(C_CONTENT)) else null
    }


}