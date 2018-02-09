/*
 * SearchCollectionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchcollection

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object SearchCollectionConst  {

    const val DATABASE = "search"
    const val TABLE = "search_collection"
    const val FULL_TABLE = "search.search_collection"
    const val C_SCREEN_ID = "screen_id"
    const val FULL_C_SCREEN_ID = "search_collection.screen_id"
    const val C_COLLECTION_ID = "collection_id"
    const val FULL_C_COLLECTION_ID = "search_collection.collection_id"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "search_collection.position"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "search_collection.title"
    const val C_PARENT_COLLECTION_IS_ROOT = "parent_collection_is_root"
    const val FULL_C_PARENT_COLLECTION_IS_ROOT = "search_collection.parent_collection_is_root"
    const val C_PARENT_COLLECTION_ID = "parent_collection_id"
    const val FULL_C_PARENT_COLLECTION_ID = "search_collection.parent_collection_id"
    const val C_PARENT_COLLECTION_TITLE = "parent_collection_title"
    const val FULL_C_PARENT_COLLECTION_TITLE = "search_collection.parent_collection_title"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS search_collection (" + 
        "screen_id INTEGER NOT NULL," + 
        "collection_id INTEGER NOT NULL," + 
        "position INTEGER NOT NULL," + 
        "title TEXT NOT NULL," + 
        "parent_collection_is_root INTEGER DEFAULT 0 NOT NULL," + 
        "parent_collection_id INTEGER NOT NULL," + 
        "parent_collection_title TEXT NOT NULL" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS search_collection;"
    const val INSERT_STATEMENT = "INSERT INTO search_collection (screen_id,collection_id,position,title,parent_collection_is_root,parent_collection_id,parent_collection_title) VALUES (?,?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE search_collection SET screen_id=?, collection_id=?, position=?, title=?, parent_collection_is_root=?, parent_collection_id=?, parent_collection_title=? WHERE  = ?"
    val ALL_COLUMNS = arrayOf(
        C_SCREEN_ID,
        C_COLLECTION_ID,
        C_POSITION,
        C_TITLE,
        C_PARENT_COLLECTION_IS_ROOT,
        C_PARENT_COLLECTION_ID,
        C_PARENT_COLLECTION_TITLE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_SCREEN_ID,
        FULL_C_COLLECTION_ID,
        FULL_C_POSITION,
        FULL_C_TITLE,
        FULL_C_PARENT_COLLECTION_IS_ROOT,
        FULL_C_PARENT_COLLECTION_ID,
        FULL_C_PARENT_COLLECTION_TITLE)

    fun getScreenId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SCREEN_ID))
    }

    fun getCollectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_COLLECTION_ID))
    }

    fun getPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_POSITION))
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun isParentCollectionIsRoot(cursor: Cursor) : Boolean {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_PARENT_COLLECTION_IS_ROOT)) != 0
    }

    fun getParentCollectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_PARENT_COLLECTION_ID))
    }

    fun getParentCollectionTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_PARENT_COLLECTION_TITLE))
    }


}