/*
 * SearchContentCollectionMapBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchcontentcollectionmap

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object SearchContentCollectionMapConst  {

    const val DATABASE = "search"
    const val TABLE = "search_content_collection_map"
    const val FULL_TABLE = "search.search_content_collection_map"
    const val C_SCREEN_ID = "screen_id"
    const val FULL_C_SCREEN_ID = "search_content_collection_map.screen_id"
    const val C_CONTENT_ITEM_ID = "content_item_id"
    const val FULL_C_CONTENT_ITEM_ID = "search_content_collection_map.content_item_id"
    const val C_COLLECTION_ID = "collection_id"
    const val FULL_C_COLLECTION_ID = "search_content_collection_map.collection_id"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS search_content_collection_map (" + 
        "screen_id INTEGER NOT NULL," + 
        "content_item_id INTEGER NOT NULL," + 
        "collection_id INTEGER NOT NULL" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS search_content_collection_map;"
    const val INSERT_STATEMENT = "INSERT INTO search_content_collection_map (screen_id,content_item_id,collection_id) VALUES (?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE search_content_collection_map SET screen_id=?, content_item_id=?, collection_id=? WHERE  = ?"
    val ALL_COLUMNS = arrayOf(
        C_SCREEN_ID,
        C_CONTENT_ITEM_ID,
        C_COLLECTION_ID)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_SCREEN_ID,
        FULL_C_CONTENT_ITEM_ID,
        FULL_C_COLLECTION_ID)

    fun getScreenId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SCREEN_ID))
    }

    fun getContentItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_CONTENT_ITEM_ID))
    }

    fun getCollectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_COLLECTION_ID))
    }


}