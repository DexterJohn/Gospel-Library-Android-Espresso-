/*
 * TabBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.tab

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object TabConst  {

    const val DATABASE = "gl"
    const val TABLE = "tab"
    const val FULL_TABLE = "gl.tab"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "tab._id"
    const val C_ANDROID_TASK_ID = "androidTaskId"
    const val FULL_C_ANDROID_TASK_ID = "tab.androidTaskId"
    const val C_LANGUAGE_ID = "language_id"
    const val FULL_C_LANGUAGE_ID = "tab.language_id"
    const val C_NAME = "name"
    const val FULL_C_NAME = "tab.name"
    const val C_DISPLAY_ORDER = "display_order"
    const val FULL_C_DISPLAY_ORDER = "tab.display_order"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS tab (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "androidTaskId INTEGER NOT NULL," + 
        "language_id INTEGER NOT NULL," + 
        "name TEXT NOT NULL," + 
        "display_order INTEGER NOT NULL" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS tab;"
    const val INSERT_STATEMENT = "INSERT INTO tab (androidTaskId,language_id,name,display_order) VALUES (?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE tab SET androidTaskId=?, language_id=?, name=?, display_order=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_ANDROID_TASK_ID,
        C_LANGUAGE_ID,
        C_NAME,
        C_DISPLAY_ORDER)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_ANDROID_TASK_ID,
        FULL_C_LANGUAGE_ID,
        FULL_C_NAME,
        FULL_C_DISPLAY_ORDER)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getAndroidTaskId(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_ANDROID_TASK_ID))
    }

    fun getLanguageId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_LANGUAGE_ID))
    }

    fun getName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }

    fun getDisplayOrder(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_DISPLAY_ORDER))
    }


}