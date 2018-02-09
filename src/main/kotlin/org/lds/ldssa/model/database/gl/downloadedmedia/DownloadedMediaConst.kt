/*
 * DownloadedMediaBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.downloadedmedia

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object DownloadedMediaConst  {

    const val DATABASE = "gl"
    const val TABLE = "downloaded_media"
    const val FULL_TABLE = "gl.downloaded_media"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "downloaded_media._id"
    const val C_CONTENT_ITEM_ID = "content_item_id"
    const val FULL_C_CONTENT_ITEM_ID = "downloaded_media.content_item_id"
    const val C_SUB_ITEM_ID = "sub_item_id"
    const val FULL_C_SUB_ITEM_ID = "downloaded_media.sub_item_id"
    const val C_TERTIARY_ID = "tertiary_id"
    const val FULL_C_TERTIARY_ID = "downloaded_media.tertiary_id"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "downloaded_media.title"
    const val C_TYPE = "type"
    const val FULL_C_TYPE = "downloaded_media.type"
    const val C_FILE = "file"
    const val FULL_C_FILE = "downloaded_media.file"
    const val C_SIZE = "size"
    const val FULL_C_SIZE = "downloaded_media.size"
    const val C_QUALITY = "quality"
    const val FULL_C_QUALITY = "downloaded_media.quality"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS downloaded_media (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "content_item_id INTEGER NOT NULL," + 
        "sub_item_id INTEGER NOT NULL," + 
        "tertiary_id TEXT," + 
        "title TEXT NOT NULL," + 
        "type INTEGER NOT NULL," + 
        "file TEXT," + 
        "size INTEGER DEFAULT 0 NOT NULL," + 
        "quality TEXT," + 
        "UNIQUE(content_item_id, sub_item_id, tertiary_id, type) ON CONFLICT REPLACE" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS downloaded_media;"
    const val INSERT_STATEMENT = "INSERT INTO downloaded_media (content_item_id,sub_item_id,tertiary_id,title,type,file,size,quality) VALUES (?,?,?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE downloaded_media SET content_item_id=?, sub_item_id=?, tertiary_id=?, title=?, type=?, file=?, size=?, quality=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_CONTENT_ITEM_ID,
        C_SUB_ITEM_ID,
        C_TERTIARY_ID,
        C_TITLE,
        C_TYPE,
        C_FILE,
        C_SIZE,
        C_QUALITY)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_CONTENT_ITEM_ID,
        FULL_C_SUB_ITEM_ID,
        FULL_C_TERTIARY_ID,
        FULL_C_TITLE,
        FULL_C_TYPE,
        FULL_C_FILE,
        FULL_C_SIZE,
        FULL_C_QUALITY)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getContentItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_CONTENT_ITEM_ID))
    }

    fun getSubItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SUB_ITEM_ID))
    }

    fun getTertiaryId(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_TERTIARY_ID))) cursor.getString(cursor.getColumnIndexOrThrow(C_TERTIARY_ID)) else null
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getType(cursor: Cursor) : org.lds.ldssa.model.database.types.ItemMediaType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.ItemMediaType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_TYPE)), org.lds.ldssa.model.database.types.ItemMediaType.UNKNOWN)
    }

    fun getFile(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_FILE))) cursor.getString(cursor.getColumnIndexOrThrow(C_FILE)) else null
    }

    fun getSize(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SIZE))
    }

    fun getQuality(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_QUALITY))) cursor.getString(cursor.getColumnIndexOrThrow(C_QUALITY)) else null
    }


}