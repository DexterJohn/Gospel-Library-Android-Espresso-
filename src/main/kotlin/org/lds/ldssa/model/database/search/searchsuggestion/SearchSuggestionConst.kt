/*
 * SearchSuggestionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchsuggestion

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object SearchSuggestionConst  {

    const val DATABASE = "search"
    const val C_ID = "_id"
    const val FULL_C_ID = "search_suggestion._id"
    const val C_CONTENT_ITEM_ID = "content_item_id"
    const val FULL_C_CONTENT_ITEM_ID = "search_suggestion.content_item_id"
    const val C_CHAPTER_NUMBER = "chapter_number"
    const val FULL_C_CHAPTER_NUMBER = "search_suggestion.chapter_number"
    const val C_VERSE_NUMBER = "verse_number"
    const val FULL_C_VERSE_NUMBER = "search_suggestion.verse_number"
    const val C_TYPE = "type"
    const val FULL_C_TYPE = "search_suggestion.type"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "search_suggestion.title"
    const val C_SUB_TITLE = "sub_title"
    const val FULL_C_SUB_TITLE = "search_suggestion.sub_title"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_CONTENT_ITEM_ID,
        C_CHAPTER_NUMBER,
        C_VERSE_NUMBER,
        C_TYPE,
        C_TITLE,
        C_SUB_TITLE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_CONTENT_ITEM_ID,
        FULL_C_CHAPTER_NUMBER,
        FULL_C_VERSE_NUMBER,
        FULL_C_TYPE,
        FULL_C_TITLE,
        FULL_C_SUB_TITLE)

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


}