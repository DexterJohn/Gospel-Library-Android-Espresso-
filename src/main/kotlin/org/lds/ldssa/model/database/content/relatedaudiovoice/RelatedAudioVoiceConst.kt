/*
 * RelatedAudioVoiceBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.relatedaudiovoice

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object RelatedAudioVoiceConst  {

    const val DATABASE = "content"
    const val TABLE = "related_audio_voice"
    const val FULL_TABLE = "content.related_audio_voice"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "related_audio_voice._id"
    const val C_NAME = "name"
    const val FULL_C_NAME = "related_audio_voice.name"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS related_audio_voice (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "name TEXT NOT NULL" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS related_audio_voice;"
    const val INSERT_STATEMENT = "INSERT INTO related_audio_voice (name) VALUES (?)"
    const val UPDATE_STATEMENT = "UPDATE related_audio_voice SET name=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_NAME)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_NAME)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }


}