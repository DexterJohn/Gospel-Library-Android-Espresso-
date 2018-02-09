/*
 * NavSectionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.navsection

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object NavSectionConst  {

    const val DATABASE = "content"
    const val TABLE = "nav_section"
    const val FULL_TABLE = "content.nav_section"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "nav_section._id"
    const val C_NAV_COLLECTION_ID = "nav_collection_id"
    const val FULL_C_NAV_COLLECTION_ID = "nav_section.nav_collection_id"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "nav_section.position"
    const val C_INDENT_LEVEL = "indent_level"
    const val FULL_C_INDENT_LEVEL = "nav_section.indent_level"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "nav_section.title"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS nav_section (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "nav_collection_id INTEGER NOT NULL," + 
        "position INTEGER NOT NULL," + 
        "indent_level INTEGER NOT NULL," + 
        "title TEXT," + 
        "UNIQUE(nav_collection_id, position)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS nav_section;"
    const val INSERT_STATEMENT = "INSERT INTO nav_section (nav_collection_id,position,indent_level,title) VALUES (?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE nav_section SET nav_collection_id=?, position=?, indent_level=?, title=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_NAV_COLLECTION_ID,
        C_POSITION,
        C_INDENT_LEVEL,
        C_TITLE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_NAV_COLLECTION_ID,
        FULL_C_POSITION,
        FULL_C_INDENT_LEVEL,
        FULL_C_TITLE)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getNavCollectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_NAV_COLLECTION_ID))
    }

    fun getPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_POSITION))
    }

    fun getIndentLevel(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_INDENT_LEVEL))
    }

    fun getTitle(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_TITLE))) cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE)) else null
    }


}