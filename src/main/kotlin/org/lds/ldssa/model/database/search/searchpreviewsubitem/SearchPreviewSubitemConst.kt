/*
 * SearchPreviewSubitemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchpreviewsubitem

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object SearchPreviewSubitemConst  {

    const val DATABASE = "search"
    const val TABLE = "search_preview_subitem"
    const val FULL_TABLE = "search.search_preview_subitem"
    const val C_SCREEN_ID = "screen_id"
    const val FULL_C_SCREEN_ID = "search_preview_subitem.screen_id"
    const val C_CONTENT_ITEM_ID = "content_item_id"
    const val FULL_C_CONTENT_ITEM_ID = "search_preview_subitem.content_item_id"
    const val C_SUB_ITEM_ID = "sub_item_id"
    const val FULL_C_SUB_ITEM_ID = "search_preview_subitem.sub_item_id"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "search_preview_subitem.title"
    const val C_TEXT = "text"
    const val FULL_C_TEXT = "search_preview_subitem.text"
    const val C_SEARCH_RESULT_COUNT_TYPE = "search_result_count_type"
    const val FULL_C_SEARCH_RESULT_COUNT_TYPE = "search_preview_subitem.search_result_count_type"
    const val C_COUNT = "count"
    const val FULL_C_COUNT = "search_preview_subitem.count"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "search_preview_subitem.position"
    const val C_VISITED = "visited"
    const val FULL_C_VISITED = "search_preview_subitem.visited"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS search_preview_subitem (" + 
        "screen_id INTEGER NOT NULL," + 
        "content_item_id INTEGER NOT NULL," + 
        "sub_item_id INTEGER NOT NULL," + 
        "title TEXT NOT NULL," + 
        "text TEXT NOT NULL," + 
        "search_result_count_type INTEGER NOT NULL," + 
        "count INTEGER NOT NULL," + 
        "position INTEGER NOT NULL," + 
        "visited INTEGER DEFAULT 0 NOT NULL" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS search_preview_subitem;"
    const val INSERT_STATEMENT = "INSERT INTO search_preview_subitem (screen_id,content_item_id,sub_item_id,title,text,search_result_count_type,count,position,visited) VALUES (?,?,?,?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE search_preview_subitem SET screen_id=?, content_item_id=?, sub_item_id=?, title=?, text=?, search_result_count_type=?, count=?, position=?, visited=? WHERE  = ?"
    val ALL_COLUMNS = arrayOf(
        C_SCREEN_ID,
        C_CONTENT_ITEM_ID,
        C_SUB_ITEM_ID,
        C_TITLE,
        C_TEXT,
        C_SEARCH_RESULT_COUNT_TYPE,
        C_COUNT,
        C_POSITION,
        C_VISITED)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_SCREEN_ID,
        FULL_C_CONTENT_ITEM_ID,
        FULL_C_SUB_ITEM_ID,
        FULL_C_TITLE,
        FULL_C_TEXT,
        FULL_C_SEARCH_RESULT_COUNT_TYPE,
        FULL_C_COUNT,
        FULL_C_POSITION,
        FULL_C_VISITED)

    fun getScreenId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SCREEN_ID))
    }

    fun getContentItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_CONTENT_ITEM_ID))
    }

    fun getSubItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SUB_ITEM_ID))
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getText(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TEXT))
    }

    fun getSearchResultCountType(cursor: Cursor) : org.lds.ldssa.search.SearchResultCountType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.search.SearchResultCountType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_SEARCH_RESULT_COUNT_TYPE)), org.lds.ldssa.search.SearchResultCountType.KEYWORD)
    }

    fun getCount(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_COUNT))
    }

    fun getPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_POSITION))
    }

    fun isVisited(cursor: Cursor) : Boolean {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_VISITED)) != 0
    }


}