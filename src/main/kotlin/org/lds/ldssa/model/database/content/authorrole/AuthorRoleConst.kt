/*
 * AuthorRoleBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.authorrole

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object AuthorRoleConst  {

    const val DATABASE = "content"
    const val TABLE = "author_role"
    const val FULL_TABLE = "content.author_role"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "author_role._id"
    const val C_AUTHOR_ID = "author_id"
    const val FULL_C_AUTHOR_ID = "author_role.author_id"
    const val C_ROLE_ID = "role_id"
    const val FULL_C_ROLE_ID = "author_role.role_id"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "author_role.position"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS author_role (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "author_id INTEGER NOT NULL," + 
        "role_id INTEGER NOT NULL," + 
        "position INTEGER NOT NULL," + 
        "UNIQUE(author_id, role_id)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS author_role;"
    const val INSERT_STATEMENT = "INSERT INTO author_role (author_id,role_id,position) VALUES (?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE author_role SET author_id=?, role_id=?, position=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_AUTHOR_ID,
        C_ROLE_ID,
        C_POSITION)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_AUTHOR_ID,
        FULL_C_ROLE_ID,
        FULL_C_POSITION)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getAuthorId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_AUTHOR_ID))
    }

    fun getRoleId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ROLE_ID))
    }

    fun getPosition(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_POSITION))
    }


}