/*
 * SubitemAuthorBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.subitemauthor

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object SubitemAuthorConst  {

    const val DATABASE = "content"
    const val TABLE = "subitem_author"
    const val FULL_TABLE = "content.subitem_author"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "subitem_author._id"
    const val C_SUBITEM_ID = "subitem_id"
    const val FULL_C_SUBITEM_ID = "subitem_author.subitem_id"
    const val C_AUTHOR_ID = "author_id"
    const val FULL_C_AUTHOR_ID = "subitem_author.author_id"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS subitem_author (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "subitem_id INTEGER NOT NULL," + 
        "author_id INTEGER NOT NULL," + 
        "UNIQUE(subitem_id, author_id)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS subitem_author;"
    const val INSERT_STATEMENT = "INSERT INTO subitem_author (subitem_id,author_id) VALUES (?,?)"
    const val UPDATE_STATEMENT = "UPDATE subitem_author SET subitem_id=?, author_id=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_SUBITEM_ID,
        C_AUTHOR_ID)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_SUBITEM_ID,
        FULL_C_AUTHOR_ID)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getSubitemId(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SUBITEM_ID))
    }

    fun getAuthorId(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_AUTHOR_ID))
    }


}