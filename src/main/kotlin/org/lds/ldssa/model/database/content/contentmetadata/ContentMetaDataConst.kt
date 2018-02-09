/*
 * ContentMetaDataBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.contentmetadata

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object ContentMetaDataConst  {

    const val DATABASE = "content"
    const val TABLE = "metadata"
    const val FULL_TABLE = "content.metadata"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "metadata._id"
    const val C_KEY = "key"
    const val FULL_C_KEY = "metadata.key"
    const val C_VALUE = "value"
    const val FULL_C_VALUE = "metadata.value"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS metadata (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "key TEXT NOT NULL," + 
        "value TEXT NOT NULL," + 
        "UNIQUE(KEY)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS metadata;"
    const val INSERT_STATEMENT = "INSERT INTO metadata (key,value) VALUES (?,?)"
    const val UPDATE_STATEMENT = "UPDATE metadata SET key=?, value=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_KEY,
        C_VALUE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_KEY,
        FULL_C_VALUE)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getKey(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_KEY))
    }

    fun getValue(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_VALUE))
    }


}