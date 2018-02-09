/*
 * RelatedVideoItemSourceBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.relatedvideoitemsource

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object RelatedVideoItemSourceConst  {

    const val DATABASE = "content"
    const val TABLE = "related_video_item_source"
    const val FULL_TABLE = "content.related_video_item_source"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "related_video_item_source._id"
    const val C_MEDIA_URL = "media_url"
    const val FULL_C_MEDIA_URL = "related_video_item_source.media_url"
    const val C_TYPE = "type"
    const val FULL_C_TYPE = "related_video_item_source.type"
    const val C_WIDTH = "width"
    const val FULL_C_WIDTH = "related_video_item_source.width"
    const val C_HEIGHT = "height"
    const val FULL_C_HEIGHT = "related_video_item_source.height"
    const val C_FILE_SIZE = "file_size"
    const val FULL_C_FILE_SIZE = "related_video_item_source.file_size"
    const val C_RELATED_VIDEO_ITEM_ID = "related_video_item_id"
    const val FULL_C_RELATED_VIDEO_ITEM_ID = "related_video_item_source.related_video_item_id"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS related_video_item_source (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "media_url TEXT NOT NULL," + 
        "type TEXT NOT NULL," + 
        "width INTEGER," + 
        "height INTEGER," + 
        "file_size INTEGER," + 
        "related_video_item_id INTEGER" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS related_video_item_source;"
    const val INSERT_STATEMENT = "INSERT INTO related_video_item_source (media_url,type,width,height,file_size,related_video_item_id) VALUES (?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE related_video_item_source SET media_url=?, type=?, width=?, height=?, file_size=?, related_video_item_id=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_MEDIA_URL,
        C_TYPE,
        C_WIDTH,
        C_HEIGHT,
        C_FILE_SIZE,
        C_RELATED_VIDEO_ITEM_ID)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_MEDIA_URL,
        FULL_C_TYPE,
        FULL_C_WIDTH,
        FULL_C_HEIGHT,
        FULL_C_FILE_SIZE,
        FULL_C_RELATED_VIDEO_ITEM_ID)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getMediaUrl(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_MEDIA_URL))
    }

    fun getType(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TYPE))
    }

    fun getWidth(cursor: Cursor) : Int? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_WIDTH))) cursor.getInt(cursor.getColumnIndexOrThrow(C_WIDTH)) else null
    }

    fun getHeight(cursor: Cursor) : Int? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_HEIGHT))) cursor.getInt(cursor.getColumnIndexOrThrow(C_HEIGHT)) else null
    }

    fun getFileSize(cursor: Cursor) : Long? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_FILE_SIZE))) cursor.getLong(cursor.getColumnIndexOrThrow(C_FILE_SIZE)) else null
    }

    fun getRelatedVideoItemId(cursor: Cursor) : Long? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_RELATED_VIDEO_ITEM_ID))) cursor.getLong(cursor.getColumnIndexOrThrow(C_RELATED_VIDEO_ITEM_ID)) else null
    }


}