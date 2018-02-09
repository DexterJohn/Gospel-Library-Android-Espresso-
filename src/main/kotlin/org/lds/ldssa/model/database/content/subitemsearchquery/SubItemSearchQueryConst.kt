/*
 * SubItemSearchQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.subitemsearchquery

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object SubItemSearchQueryConst  {

    const val DATABASE = "content"
    const val C_SUB_ITEM_ID = "sub_item_id"
    const val FULL_C_SUB_ITEM_ID = "subitem_search_query.sub_item_id"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "subitem_search_query.title"
    const val C_SNIPPET = "snippet"
    const val FULL_C_SNIPPET = "subitem_search_query.snippet"
    const val C_MATCH_INFO = "match_info"
    const val FULL_C_MATCH_INFO = "subitem_search_query.match_info"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "subitem_search_query.position"
    const val C_SEARCH_RESULT_COUNT_TYPE = "search_result_count_type"
    const val FULL_C_SEARCH_RESULT_COUNT_TYPE = "subitem_search_query.search_result_count_type"
    val ALL_COLUMNS = arrayOf(
        C_SUB_ITEM_ID,
        C_TITLE,
        C_SNIPPET,
        C_MATCH_INFO,
        C_POSITION,
        C_SEARCH_RESULT_COUNT_TYPE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_SUB_ITEM_ID,
        FULL_C_TITLE,
        FULL_C_SNIPPET,
        FULL_C_MATCH_INFO,
        FULL_C_POSITION,
        FULL_C_SEARCH_RESULT_COUNT_TYPE)

    fun getSubItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SUB_ITEM_ID))
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getSnippet(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_SNIPPET))
    }

    fun getMatchInfo(cursor: Cursor) : ByteArray {
        return cursor.getBlob(cursor.getColumnIndexOrThrow(C_MATCH_INFO))
    }

    fun getPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_POSITION))
    }

    fun getSearchResultCountType(cursor: Cursor) : org.lds.ldssa.search.SearchResultCountType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.search.SearchResultCountType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_SEARCH_RESULT_COUNT_TYPE)), org.lds.ldssa.search.SearchResultCountType.KEYWORD)
    }


}