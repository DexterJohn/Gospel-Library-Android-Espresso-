/*
 * SearchCountCollectionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchcountcollection

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object SearchCountCollectionConst  {

    const val DATABASE = "search"
    const val TABLE = "search_count_collection"
    const val FULL_TABLE = "search.search_count_collection"
    const val C_SCREEN_ID = "screen_id"
    const val FULL_C_SCREEN_ID = "search_count_collection.screen_id"
    const val C_COLLECTION_ID = "collection_id"
    const val FULL_C_COLLECTION_ID = "search_count_collection.collection_id"
    const val C_COLLECTION_TITLE = "collection_title"
    const val FULL_C_COLLECTION_TITLE = "search_count_collection.collection_title"
    const val C_PARENT_COLLECTION_ID = "parent_collection_id"
    const val FULL_C_PARENT_COLLECTION_ID = "search_count_collection.parent_collection_id"
    const val C_PARENT_COLLECTION_TITLE = "parent_collection_title"
    const val FULL_C_PARENT_COLLECTION_TITLE = "search_count_collection.parent_collection_title"
    const val C_CONTENT_ITEM_COUNT = "content_item_count"
    const val FULL_C_CONTENT_ITEM_COUNT = "search_count_collection.content_item_count"
    const val C_PHRASE_COUNT = "phrase_count"
    const val FULL_C_PHRASE_COUNT = "search_count_collection.phrase_count"
    const val C_KEYWORD_COUNT = "keyword_count"
    const val FULL_C_KEYWORD_COUNT = "search_count_collection.keyword_count"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "search_count_collection.position"
    val ALL_COLUMNS = arrayOf(
        C_SCREEN_ID,
        C_COLLECTION_ID,
        C_COLLECTION_TITLE,
        C_PARENT_COLLECTION_ID,
        C_PARENT_COLLECTION_TITLE,
        C_CONTENT_ITEM_COUNT,
        C_PHRASE_COUNT,
        C_KEYWORD_COUNT,
        C_POSITION)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_SCREEN_ID,
        FULL_C_COLLECTION_ID,
        FULL_C_COLLECTION_TITLE,
        FULL_C_PARENT_COLLECTION_ID,
        FULL_C_PARENT_COLLECTION_TITLE,
        FULL_C_CONTENT_ITEM_COUNT,
        FULL_C_PHRASE_COUNT,
        FULL_C_KEYWORD_COUNT,
        FULL_C_POSITION)

    fun getScreenId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SCREEN_ID))
    }

    fun getCollectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_COLLECTION_ID))
    }

    fun getCollectionTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_COLLECTION_TITLE))
    }

    fun getParentCollectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_PARENT_COLLECTION_ID))
    }

    fun getParentCollectionTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_PARENT_COLLECTION_TITLE))
    }

    fun getContentItemCount(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_CONTENT_ITEM_COUNT))
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