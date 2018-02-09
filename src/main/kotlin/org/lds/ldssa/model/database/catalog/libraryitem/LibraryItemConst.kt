/*
 * LibraryItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.libraryitem

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object LibraryItemConst  {

    const val DATABASE = "catalog"
    const val TABLE = "library_item"
    const val FULL_TABLE = "catalog.library_item"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "library_item._id"
    const val C_EXTERNAL_ID = "external_id"
    const val FULL_C_EXTERNAL_ID = "library_item.external_id"
    const val C_LIBRARY_SECTION_ID = "library_section_id"
    const val FULL_C_LIBRARY_SECTION_ID = "library_item.library_section_id"
    const val C_LIBRARY_SECTION_EXTERNAL_ID = "library_section_external_id"
    const val FULL_C_LIBRARY_SECTION_EXTERNAL_ID = "library_item.library_section_external_id"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "library_item.position"
    const val C_TITLE_HTML = "title_html"
    const val FULL_C_TITLE_HTML = "library_item.title_html"
    const val C_OBSOLETE = "is_obsolete"
    const val FULL_C_OBSOLETE = "library_item.is_obsolete"
    const val C_ITEM_ID = "item_id"
    const val FULL_C_ITEM_ID = "library_item.item_id"
    const val C_ITEM_EXTERNAL_ID = "item_external_id"
    const val FULL_C_ITEM_EXTERNAL_ID = "library_item.item_external_id"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS library_item (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "external_id TEXT NOT NULL," + 
        "library_section_id INTEGER," + 
        "library_section_external_id TEXT," + 
        "position INTEGER NOT NULL," + 
        "title_html TEXT NOT NULL," + 
        "is_obsolete INTEGER DEFAULT 0 NOT NULL," + 
        "item_id INTEGER NOT NULL," + 
        "item_external_id TEXT NOT NULL," + 
        "UNIQUE(external_id)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS library_item;"
    const val INSERT_STATEMENT = "INSERT INTO library_item (external_id,library_section_id,library_section_external_id,position,title_html,is_obsolete,item_id,item_external_id) VALUES (?,?,?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE library_item SET external_id=?, library_section_id=?, library_section_external_id=?, position=?, title_html=?, is_obsolete=?, item_id=?, item_external_id=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_EXTERNAL_ID,
        C_LIBRARY_SECTION_ID,
        C_LIBRARY_SECTION_EXTERNAL_ID,
        C_POSITION,
        C_TITLE_HTML,
        C_OBSOLETE,
        C_ITEM_ID,
        C_ITEM_EXTERNAL_ID)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_EXTERNAL_ID,
        FULL_C_LIBRARY_SECTION_ID,
        FULL_C_LIBRARY_SECTION_EXTERNAL_ID,
        FULL_C_POSITION,
        FULL_C_TITLE_HTML,
        FULL_C_OBSOLETE,
        FULL_C_ITEM_ID,
        FULL_C_ITEM_EXTERNAL_ID)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getExternalId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_EXTERNAL_ID))
    }

    fun getLibrarySectionId(cursor: Cursor) : Long? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_LIBRARY_SECTION_ID))) cursor.getLong(cursor.getColumnIndexOrThrow(C_LIBRARY_SECTION_ID)) else null
    }

    fun getLibrarySectionExternalId(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_LIBRARY_SECTION_EXTERNAL_ID))) cursor.getString(cursor.getColumnIndexOrThrow(C_LIBRARY_SECTION_EXTERNAL_ID)) else null
    }

    fun getPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_POSITION))
    }

    fun getTitleHtml(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE_HTML))
    }

    fun isObsolete(cursor: Cursor) : Boolean {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_OBSOLETE)) != 0
    }

    fun getItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ITEM_ID))
    }

    fun getItemExternalId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_ITEM_EXTERNAL_ID))
    }


}