/*
 * RoleBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.role

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object RoleConst  {

    const val DATABASE = "content"
    const val TABLE = "role"
    const val FULL_TABLE = "content.role"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "role._id"
    const val C_NAME = "name"
    const val FULL_C_NAME = "role.name"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "role.position"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS role (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "name TEXT NOT NULL," + 
        "position INTEGER NOT NULL," + 
        "UNIQUE(name)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS role;"
    const val INSERT_STATEMENT = "INSERT INTO role (name,position) VALUES (?,?)"
    const val UPDATE_STATEMENT = "UPDATE role SET name=?, position=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_NAME,
        C_POSITION)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_NAME,
        FULL_C_POSITION)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }

    fun getPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_POSITION))
    }


}