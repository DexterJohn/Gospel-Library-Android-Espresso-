/*
 * RelatedVideoItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.relatedvideoitem

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object RelatedVideoItemConst  {

    const val DATABASE = "content"
    const val TABLE = "related_video_item"
    const val FULL_TABLE = "content.related_video_item"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "related_video_item._id"
    const val C_SUBITEM_ID = "subitem_id"
    const val FULL_C_SUBITEM_ID = "related_video_item.subitem_id"
    const val C_POSTER_URL = "poster_url"
    const val FULL_C_POSTER_URL = "related_video_item.poster_url"
    const val C_VIDEO_ID = "video_id"
    const val FULL_C_VIDEO_ID = "related_video_item.video_id"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "related_video_item.title"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS related_video_item (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "subitem_id INTEGER NOT NULL," + 
        "poster_url TEXT," + 
        "video_id TEXT NOT NULL," + 
        "title TEXT NOT NULL" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS related_video_item;"
    const val INSERT_STATEMENT = "INSERT INTO related_video_item (subitem_id,poster_url,video_id,title) VALUES (?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE related_video_item SET subitem_id=?, poster_url=?, video_id=?, title=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_SUBITEM_ID,
        C_POSTER_URL,
        C_VIDEO_ID,
        C_TITLE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_SUBITEM_ID,
        FULL_C_POSTER_URL,
        FULL_C_VIDEO_ID,
        FULL_C_TITLE)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getSubitemId(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SUBITEM_ID))
    }

    fun getPosterUrl(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_POSTER_URL))) cursor.getString(cursor.getColumnIndexOrThrow(C_POSTER_URL)) else null
    }

    fun getVideoId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_VIDEO_ID))
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }


}