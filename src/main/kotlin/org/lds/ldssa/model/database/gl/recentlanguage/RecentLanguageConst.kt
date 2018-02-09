/*
 * RecentLanguageBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.recentlanguage

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object RecentLanguageConst  {

    const val DATABASE = "gl"
    const val TABLE = "recent_language"
    const val FULL_TABLE = "gl.recent_language"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "recent_language._id"
    const val C_LANGUAGE_ID = "language_id"
    const val FULL_C_LANGUAGE_ID = "recent_language.language_id"
    const val C_TIMESTAMP = "timestamp"
    const val FULL_C_TIMESTAMP = "recent_language.timestamp"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS recent_language (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "language_id INTEGER NOT NULL," + 
        "timestamp INTEGER NOT NULL," + 
        "UNIQUE(language_id) ON CONFLICT REPLACE" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS recent_language;"
    const val INSERT_STATEMENT = "INSERT INTO recent_language (language_id,timestamp) VALUES (?,?)"
    const val UPDATE_STATEMENT = "UPDATE recent_language SET language_id=?, timestamp=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_LANGUAGE_ID,
        C_TIMESTAMP)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_LANGUAGE_ID,
        FULL_C_TIMESTAMP)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getLanguageId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_LANGUAGE_ID))
    }

    fun getTimestamp(cursor: Cursor) : org.threeten.bp.LocalDateTime {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_TIMESTAMP))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_TIMESTAMP)))!! else null!!
    }


}