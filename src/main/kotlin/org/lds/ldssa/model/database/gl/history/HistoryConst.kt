/*
 * HistoryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.history

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object HistoryConst  {

    const val DATABASE = "gl"
    const val TABLE = "history"
    const val FULL_TABLE = "gl.history"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "history._id"
    const val C_TIME = "time"
    const val FULL_C_TIME = "history.time"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "history.title"
    const val C_DESCRIPTION = "description"
    const val FULL_C_DESCRIPTION = "history.description"
    const val C_CONTENT_ITEM_ID = "content_item_id"
    const val FULL_C_CONTENT_ITEM_ID = "history.content_item_id"
    const val C_ITEM_URI = "item_uri"
    const val FULL_C_ITEM_URI = "history.item_uri"
    const val C_SCROLL_POSITION = "scroll_position"
    const val FULL_C_SCROLL_POSITION = "history.scroll_position"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS history (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "time INTEGER NOT NULL," + 
        "title TEXT NOT NULL," + 
        "description TEXT NOT NULL," + 
        "content_item_id INTEGER DEFAULT 0 NOT NULL," + 
        "item_uri TEXT," + 
        "scroll_position INTEGER NOT NULL" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS history;"
    const val INSERT_STATEMENT = "INSERT INTO history (time,title,description,content_item_id,item_uri,scroll_position) VALUES (?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE history SET time=?, title=?, description=?, content_item_id=?, item_uri=?, scroll_position=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_TIME,
        C_TITLE,
        C_DESCRIPTION,
        C_CONTENT_ITEM_ID,
        C_ITEM_URI,
        C_SCROLL_POSITION)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_TIME,
        FULL_C_TITLE,
        FULL_C_DESCRIPTION,
        FULL_C_CONTENT_ITEM_ID,
        FULL_C_ITEM_URI,
        FULL_C_SCROLL_POSITION)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getTime(cursor: Cursor) : org.threeten.bp.LocalDateTime {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_TIME))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_TIME)))!! else null!!
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getDescription(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_DESCRIPTION))
    }

    fun getContentItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_CONTENT_ITEM_ID))
    }

    fun getItemUri(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_ITEM_URI))) cursor.getString(cursor.getColumnIndexOrThrow(C_ITEM_URI)) else null
    }

    fun getScrollPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SCROLL_POSITION))
    }


}