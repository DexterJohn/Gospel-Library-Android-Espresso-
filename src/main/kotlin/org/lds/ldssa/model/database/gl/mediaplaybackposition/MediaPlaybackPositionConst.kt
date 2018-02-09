/*
 * MediaPlaybackPositionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.mediaplaybackposition

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object MediaPlaybackPositionConst  {

    const val DATABASE = "gl"
    const val TABLE = "media_playback_position"
    const val FULL_TABLE = "gl.media_playback_position"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "media_playback_position._id"
    const val C_CONTENT_ITEM_ID = "content_item_id"
    const val FULL_C_CONTENT_ITEM_ID = "media_playback_position.content_item_id"
    const val C_SUB_ITEM_ID = "sub_item_id"
    const val FULL_C_SUB_ITEM_ID = "media_playback_position.sub_item_id"
    const val C_TYPE = "type"
    const val FULL_C_TYPE = "media_playback_position.type"
    const val C_MEDIA_ID = "media_id"
    const val FULL_C_MEDIA_ID = "media_playback_position.media_id"
    const val C_PLAYBACK_POSITION = "playback_position"
    const val FULL_C_PLAYBACK_POSITION = "media_playback_position.playback_position"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS media_playback_position (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "content_item_id INTEGER NOT NULL," + 
        "sub_item_id INTEGER NOT NULL," + 
        "type INTEGER NOT NULL," + 
        "media_id TEXT NOT NULL," + 
        "playback_position INTEGER DEFAULT 0 NOT NULL," + 
        "UNIQUE(content_item_id, sub_item_id, type, media_id) ON CONFLICT REPLACE" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS media_playback_position;"
    const val INSERT_STATEMENT = "INSERT INTO media_playback_position (content_item_id,sub_item_id,type,media_id,playback_position) VALUES (?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE media_playback_position SET content_item_id=?, sub_item_id=?, type=?, media_id=?, playback_position=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_CONTENT_ITEM_ID,
        C_SUB_ITEM_ID,
        C_TYPE,
        C_MEDIA_ID,
        C_PLAYBACK_POSITION)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_CONTENT_ITEM_ID,
        FULL_C_SUB_ITEM_ID,
        FULL_C_TYPE,
        FULL_C_MEDIA_ID,
        FULL_C_PLAYBACK_POSITION)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getContentItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_CONTENT_ITEM_ID))
    }

    fun getSubItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SUB_ITEM_ID))
    }

    fun getType(cursor: Cursor) : org.lds.ldssa.model.database.types.ItemMediaType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.ItemMediaType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_TYPE)), org.lds.ldssa.model.database.types.ItemMediaType.UNKNOWN)
    }

    fun getMediaId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_MEDIA_ID))
    }

    fun getPlaybackPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_PLAYBACK_POSITION))
    }


}