/*
 * SearchAllCountBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchallcount

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object SearchAllCountConst  {

    const val DATABASE = "search"
    const val TABLE = "search_all_count"
    const val FULL_TABLE = "search.search_all_count"
    const val C_ID = "id"
    const val FULL_C_ID = "search_all_count.id"
    const val C_SCREEN_ID = "screen_id"
    const val FULL_C_SCREEN_ID = "search_all_count.screen_id"
    const val C_TYPE = "type"
    const val FULL_C_TYPE = "search_all_count.type"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "search_all_count.title"
    const val C_SUBTITLE = "subtitle"
    const val FULL_C_SUBTITLE = "search_all_count.subtitle"
    const val C_ITEM_COUNT = "item_count"
    const val FULL_C_ITEM_COUNT = "search_all_count.item_count"
    const val C_PHRASE_COUNT = "phrase_count"
    const val FULL_C_PHRASE_COUNT = "search_all_count.phrase_count"
    const val C_KEYWORD_COUNT = "keyword_count"
    const val FULL_C_KEYWORD_COUNT = "search_all_count.keyword_count"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "search_all_count.position"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_SCREEN_ID,
        C_TYPE,
        C_TITLE,
        C_SUBTITLE,
        C_ITEM_COUNT,
        C_PHRASE_COUNT,
        C_KEYWORD_COUNT,
        C_POSITION)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_SCREEN_ID,
        FULL_C_TYPE,
        FULL_C_TITLE,
        FULL_C_SUBTITLE,
        FULL_C_ITEM_COUNT,
        FULL_C_PHRASE_COUNT,
        FULL_C_KEYWORD_COUNT,
        FULL_C_POSITION)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getScreenId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SCREEN_ID))
    }

    fun getType(cursor: Cursor) : org.lds.ldssa.search.SearchAllType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.search.SearchAllType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_TYPE)), org.lds.ldssa.search.SearchAllType.COLLECTION)
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getSubtitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_SUBTITLE))
    }

    fun getItemCount(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ITEM_COUNT))
    }

    fun getPhraseCount(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_PHRASE_COUNT))
    }

    fun getKeywordCount(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_KEYWORD_COUNT))
    }

    fun getPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_POSITION))
    }


}