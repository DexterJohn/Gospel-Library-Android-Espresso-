/*
 * LibrarySectionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.librarysection

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object LibrarySectionConst  {

    const val DATABASE = "catalog"
    const val TABLE = "library_section"
    const val FULL_TABLE = "catalog.library_section"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "library_section._id"
    const val C_EXTERNAL_ID = "external_id"
    const val FULL_C_EXTERNAL_ID = "library_section.external_id"
    const val C_LIBRARY_COLLECTION_ID = "library_collection_id"
    const val FULL_C_LIBRARY_COLLECTION_ID = "library_section.library_collection_id"
    const val C_LIBRARY_SECTION_EXTERNAL_ID = "library_section_external_id"
    const val FULL_C_LIBRARY_SECTION_EXTERNAL_ID = "library_section.library_section_external_id"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "library_section.position"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "library_section.title"
    const val C_INDEX_TITLE = "index_title"
    const val FULL_C_INDEX_TITLE = "library_section.index_title"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS library_section (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "external_id TEXT NOT NULL," + 
        "library_collection_id INTEGER NOT NULL," + 
        "library_section_external_id TEXT NOT NULL," + 
        "position INTEGER NOT NULL," + 
        "title TEXT," + 
        "index_title TEXT," + 
        "UNIQUE(external_id)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS library_section;"
    const val INSERT_STATEMENT = "INSERT INTO library_section (external_id,library_collection_id,library_section_external_id,position,title,index_title) VALUES (?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE library_section SET external_id=?, library_collection_id=?, library_section_external_id=?, position=?, title=?, index_title=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_EXTERNAL_ID,
        C_LIBRARY_COLLECTION_ID,
        C_LIBRARY_SECTION_EXTERNAL_ID,
        C_POSITION,
        C_TITLE,
        C_INDEX_TITLE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_EXTERNAL_ID,
        FULL_C_LIBRARY_COLLECTION_ID,
        FULL_C_LIBRARY_SECTION_EXTERNAL_ID,
        FULL_C_POSITION,
        FULL_C_TITLE,
        FULL_C_INDEX_TITLE)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getExternalId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_EXTERNAL_ID))
    }

    fun getLibraryCollectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_LIBRARY_COLLECTION_ID))
    }

    fun getLibrarySectionExternalId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_LIBRARY_SECTION_EXTERNAL_ID))
    }

    fun getPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_POSITION))
    }

    fun getTitle(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_TITLE))) cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE)) else null
    }

    fun getIndexTitle(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_INDEX_TITLE))) cursor.getString(cursor.getColumnIndexOrThrow(C_INDEX_TITLE)) else null
    }


}