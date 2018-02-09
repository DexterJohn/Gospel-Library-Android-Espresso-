/*
 * SearchCountContentBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchcountcontent

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object SearchCountContentConst  {

    const val DATABASE = "search"
    const val TABLE = "search_count_content"
    const val FULL_TABLE = "search.search_count_content"
    const val C_SCREEN_ID = "screen_id"
    const val FULL_C_SCREEN_ID = "search_count_content.screen_id"
    const val C_CONTENT_ITEM_ID = "content_item_id"
    const val FULL_C_CONTENT_ITEM_ID = "search_count_content.content_item_id"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "search_count_content.position"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "search_count_content.title"
    const val C_PHRASE_COUNT = "phrase_count"
    const val FULL_C_PHRASE_COUNT = "search_count_content.phrase_count"
    const val C_KEYWORD_COUNT = "keyword_count"
    const val FULL_C_KEYWORD_COUNT = "search_count_content.keyword_count"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS search_count_content (" + 
        "screen_id INTEGER NOT NULL," + 
        "content_item_id INTEGER NOT NULL," + 
        "position INTEGER NOT NULL," + 
        "title TEXT NOT NULL," + 
        "phrase_count INTEGER NOT NULL," + 
        "keyword_count INTEGER NOT NULL" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS search_count_content;"
    const val INSERT_STATEMENT = "INSERT INTO search_count_content (screen_id,content_item_id,position,title,phrase_count,keyword_count) VALUES (?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE search_count_content SET screen_id=?, content_item_id=?, position=?, title=?, phrase_count=?, keyword_count=? WHERE  = ?"
    val ALL_COLUMNS = arrayOf(
        C_SCREEN_ID,
        C_CONTENT_ITEM_ID,
        C_POSITION,
        C_TITLE,
        C_PHRASE_COUNT,
        C_KEYWORD_COUNT)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_SCREEN_ID,
        FULL_C_CONTENT_ITEM_ID,
        FULL_C_POSITION,
        FULL_C_TITLE,
        FULL_C_PHRASE_COUNT,
        FULL_C_KEYWORD_COUNT)

    fun getScreenId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SCREEN_ID))
    }

    fun getContentItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_CONTENT_ITEM_ID))
    }

    fun getPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_POSITION))
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getPhraseCount(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_PHRASE_COUNT))
    }

    fun getKeywordCount(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_KEYWORD_COUNT))
    }


}