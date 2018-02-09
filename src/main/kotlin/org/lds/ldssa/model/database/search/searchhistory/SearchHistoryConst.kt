/*
 * SearchHistoryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchhistory

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object SearchHistoryConst  {

    const val DATABASE = "search"
    const val TABLE = "search_history"
    const val FULL_TABLE = "search.search_history"
    const val C_ID = "_id"
    const val FULL_C_ID = "search_history._id"
    const val C_CONTENT_ITEM_ID = "content_item_id"
    const val FULL_C_CONTENT_ITEM_ID = "search_history.content_item_id"
    const val C_CHAPTER_NUMBER = "chapter_number"
    const val FULL_C_CHAPTER_NUMBER = "search_history.chapter_number"
    const val C_VERSE_NUMBER = "verse_number"
    const val FULL_C_VERSE_NUMBER = "search_history.verse_number"
    const val C_TYPE = "type"
    const val FULL_C_TYPE = "search_history.type"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "search_history.title"
    const val C_SUB_TITLE = "sub_title"
    const val FULL_C_SUB_TITLE = "search_history.sub_title"
    const val C_LANGUAGE_ID = "language_id"
    const val FULL_C_LANGUAGE_ID = "search_history.language_id"
    const val C_LAST_UPDATE = "last_update"
    const val FULL_C_LAST_UPDATE = "search_history.last_update"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS search_history (" + 
        "_id INTEGER NOT NULL," + 
        "content_item_id INTEGER NOT NULL," + 
        "chapter_number TEXT NOT NULL," + 
        "verse_number TEXT NOT NULL," + 
        "type INTEGER NOT NULL," + 
        "title TEXT NOT NULL," + 
        "sub_title TEXT NOT NULL," + 
        "language_id INTEGER NOT NULL," + 
        "last_update INTEGER NOT NULL," + 
        "UNIQUE(title, sub_title) ON CONFLICT REPLACE" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS search_history;"
    const val INSERT_STATEMENT = "INSERT INTO search_history (_id,content_item_id,chapter_number,verse_number,type,title,sub_title,language_id,last_update) VALUES (?,?,?,?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE search_history SET _id=?, content_item_id=?, chapter_number=?, verse_number=?, type=?, title=?, sub_title=?, language_id=?, last_update=? WHERE  = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_CONTENT_ITEM_ID,
        C_CHAPTER_NUMBER,
        C_VERSE_NUMBER,
        C_TYPE,
        C_TITLE,
        C_SUB_TITLE,
        C_LANGUAGE_ID,
        C_LAST_UPDATE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_CONTENT_ITEM_ID,
        FULL_C_CHAPTER_NUMBER,
        FULL_C_VERSE_NUMBER,
        FULL_C_TYPE,
        FULL_C_TITLE,
        FULL_C_SUB_TITLE,
        FULL_C_LANGUAGE_ID,
        FULL_C_LAST_UPDATE)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getContentItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_CONTENT_ITEM_ID))
    }

    fun getChapterNumber(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_CHAPTER_NUMBER))
    }

    fun getVerseNumber(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_VERSE_NUMBER))
    }

    fun getType(cursor: Cursor) : org.lds.ldssa.model.database.types.SearchSuggestionType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.SearchSuggestionType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_TYPE)), org.lds.ldssa.model.database.types.SearchSuggestionType.UNKNOWN)
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getSubTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_SUB_TITLE))
    }

    fun getLanguageId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_LANGUAGE_ID))
    }

    fun getLastUpdate(cursor: Cursor) : org.threeten.bp.LocalDateTime {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_LAST_UPDATE))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(C_LAST_UPDATE)))!! else null!!
    }


}