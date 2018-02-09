/*
 * RelatedAudioItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.relatedaudioitem

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object RelatedAudioItemConst  {

    const val DATABASE = "content"
    const val TABLE = "related_audio_item"
    const val FULL_TABLE = "content.related_audio_item"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "related_audio_item._id"
    const val C_SUBITEM_ID = "subitem_id"
    const val FULL_C_SUBITEM_ID = "related_audio_item.subitem_id"
    const val C_MEDIA_URL = "media_url"
    const val FULL_C_MEDIA_URL = "related_audio_item.media_url"
    const val C_FILE_SIZE = "file_size"
    const val FULL_C_FILE_SIZE = "related_audio_item.file_size"
    const val C_DURATION = "duration"
    const val FULL_C_DURATION = "related_audio_item.duration"
    const val C_RELATED_AUDIO_VOICE_ID = "related_audio_voice_id"
    const val FULL_C_RELATED_AUDIO_VOICE_ID = "related_audio_item.related_audio_voice_id"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS related_audio_item (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "subitem_id INTEGER NOT NULL," + 
        "media_url TEXT NOT NULL," + 
        "file_size INTEGER NOT NULL," + 
        "duration INTEGER NOT NULL," + 
        "related_audio_voice_id INTEGER" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS related_audio_item;"
    const val INSERT_STATEMENT = "INSERT INTO related_audio_item (subitem_id,media_url,file_size,duration,related_audio_voice_id) VALUES (?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE related_audio_item SET subitem_id=?, media_url=?, file_size=?, duration=?, related_audio_voice_id=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_SUBITEM_ID,
        C_MEDIA_URL,
        C_FILE_SIZE,
        C_DURATION,
        C_RELATED_AUDIO_VOICE_ID)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_SUBITEM_ID,
        FULL_C_MEDIA_URL,
        FULL_C_FILE_SIZE,
        FULL_C_DURATION,
        FULL_C_RELATED_AUDIO_VOICE_ID)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getSubitemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SUBITEM_ID))
    }

    fun getMediaUrl(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_MEDIA_URL))
    }

    fun getFileSize(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_FILE_SIZE))
    }

    fun getDuration(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_DURATION))
    }

    fun getRelatedAudioVoiceId(cursor: Cursor) : Int? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_RELATED_AUDIO_VOICE_ID))) cursor.getInt(cursor.getColumnIndexOrThrow(C_RELATED_AUDIO_VOICE_ID)) else null
    }


}