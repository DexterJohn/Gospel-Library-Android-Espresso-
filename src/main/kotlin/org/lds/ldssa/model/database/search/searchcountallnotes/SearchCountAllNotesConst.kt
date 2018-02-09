/*
 * SearchCountAllNotesBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchcountallnotes

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object SearchCountAllNotesConst  {

    const val DATABASE = "search"
    const val TABLE = "search_count_all_notes"
    const val FULL_TABLE = "search.search_count_all_notes"
    const val C_SCREEN_ID = "screen_id"
    const val FULL_C_SCREEN_ID = "search_count_all_notes.screen_id"
    const val C_NOTE_COUNT = "note_count"
    const val FULL_C_NOTE_COUNT = "search_count_all_notes.note_count"
    const val C_PHRASE_COUNT = "phrase_count"
    const val FULL_C_PHRASE_COUNT = "search_count_all_notes.phrase_count"
    const val C_KEYWORD_COUNT = "keyword_count"
    const val FULL_C_KEYWORD_COUNT = "search_count_all_notes.keyword_count"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS search_count_all_notes (" + 
        "screen_id INTEGER NOT NULL," + 
        "note_count INTEGER NOT NULL," + 
        "phrase_count INTEGER NOT NULL," + 
        "keyword_count INTEGER NOT NULL" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS search_count_all_notes;"
    const val INSERT_STATEMENT = "INSERT INTO search_count_all_notes (screen_id,note_count,phrase_count,keyword_count) VALUES (?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE search_count_all_notes SET screen_id=?, note_count=?, phrase_count=?, keyword_count=? WHERE  = ?"
    val ALL_COLUMNS = arrayOf(
        C_SCREEN_ID,
        C_NOTE_COUNT,
        C_PHRASE_COUNT,
        C_KEYWORD_COUNT)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_SCREEN_ID,
        FULL_C_NOTE_COUNT,
        FULL_C_PHRASE_COUNT,
        FULL_C_KEYWORD_COUNT)

    fun getScreenId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SCREEN_ID))
    }

    fun getNoteCount(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_NOTE_COUNT))
    }

    fun getPhraseCount(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_PHRASE_COUNT))
    }

    fun getKeywordCount(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_KEYWORD_COUNT))
    }


}