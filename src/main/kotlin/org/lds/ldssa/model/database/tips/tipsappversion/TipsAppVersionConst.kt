/*
 * TipsAppVersionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.tips.tipsappversion

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object TipsAppVersionConst  {

    const val DATABASE = "tips"
    const val TABLE = "version"
    const val FULL_TABLE = "tips.version"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "version._id"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "version.title"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "version.position"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS version (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "title TEXT NOT NULL," + 
        "position INTEGER NOT NULL" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS version;"
    const val INSERT_STATEMENT = "INSERT INTO version (title,position) VALUES (?,?)"
    const val UPDATE_STATEMENT = "UPDATE version SET title=?, position=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_TITLE,
        C_POSITION)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_TITLE,
        FULL_C_POSITION)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_POSITION))
    }


}