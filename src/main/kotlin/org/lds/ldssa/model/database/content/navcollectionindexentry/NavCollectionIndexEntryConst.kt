/*
 * NavCollectionIndexEntryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.navcollectionindexentry

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object NavCollectionIndexEntryConst  {

    const val DATABASE = "content"
    const val TABLE = "nav_collection_index_entry"
    const val FULL_TABLE = "content.nav_collection_index_entry"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "nav_collection_index_entry._id"
    const val C_NAV_COLLECTION_ID = "nav_collection_id"
    const val FULL_C_NAV_COLLECTION_ID = "nav_collection_index_entry.nav_collection_id"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "nav_collection_index_entry.position"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "nav_collection_index_entry.title"
    const val C_LIST_INDEX = "list_index"
    const val FULL_C_LIST_INDEX = "nav_collection_index_entry.list_index"
    const val C_SECTION = "section"
    const val FULL_C_SECTION = "nav_collection_index_entry.section"
    const val C_ROW = "row"
    const val FULL_C_ROW = "nav_collection_index_entry.row"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS nav_collection_index_entry (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "nav_collection_id INTEGER NOT NULL," + 
        "position INTEGER NOT NULL," + 
        "title TEXT NOT NULL," + 
        "list_index INTEGER NOT NULL," + 
        "section INTEGER NOT NULL," + 
        "row INTEGER NOT NULL," + 
        "UNIQUE(nav_collection_id, position)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS nav_collection_index_entry;"
    const val INSERT_STATEMENT = "INSERT INTO nav_collection_index_entry (nav_collection_id,position,title,list_index,section,row) VALUES (?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE nav_collection_index_entry SET nav_collection_id=?, position=?, title=?, list_index=?, section=?, row=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_NAV_COLLECTION_ID,
        C_POSITION,
        C_TITLE,
        C_LIST_INDEX,
        C_SECTION,
        C_ROW)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_NAV_COLLECTION_ID,
        FULL_C_POSITION,
        FULL_C_TITLE,
        FULL_C_LIST_INDEX,
        FULL_C_SECTION,
        FULL_C_ROW)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getNavCollectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_NAV_COLLECTION_ID))
    }

    fun getPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_POSITION))
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getListIndex(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_LIST_INDEX))
    }

    fun getSection(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SECTION))
    }

    fun getRow(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_ROW))
    }


}