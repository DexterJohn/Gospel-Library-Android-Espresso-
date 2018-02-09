/*
 * TagBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.tag

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object TagConst  {

    const val DATABASE = "userdata"
    const val TABLE = "tag"
    const val FULL_TABLE = "userdata.tag"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "tag._id"
    const val C_ANNOTATION_ID = "annotation_id"
    const val FULL_C_ANNOTATION_ID = "tag.annotation_id"
    const val C_NAME = "name"
    const val FULL_C_NAME = "tag.name"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS tag (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "annotation_id INTEGER NOT NULL," + 
        "name TEXT NOT NULL" + 
        ");" + 
        "" + 
        "CREATE INDEX IF NOT EXISTS tagannotation_id_IDX ON tag (annotation_id);" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS tag;"
    const val INSERT_STATEMENT = "INSERT INTO tag (annotation_id,name) VALUES (?,?)"
    const val UPDATE_STATEMENT = "UPDATE tag SET annotation_id=?, name=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_ANNOTATION_ID,
        C_NAME)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_ANNOTATION_ID,
        FULL_C_NAME)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getAnnotationId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ANNOTATION_ID))
    }

    fun getName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }


}