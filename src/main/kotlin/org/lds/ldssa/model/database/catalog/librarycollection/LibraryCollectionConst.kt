/*
 * LibraryCollectionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.librarycollection

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object LibraryCollectionConst  {

    const val DATABASE = "catalog"
    const val TABLE = "library_collection"
    const val FULL_TABLE = "catalog.library_collection"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "library_collection._id"
    const val C_EXTERNAL_ID = "external_id"
    const val FULL_C_EXTERNAL_ID = "library_collection.external_id"
    const val C_LIBRARY_SECTION_ID = "library_section_id"
    const val FULL_C_LIBRARY_SECTION_ID = "library_collection.library_section_id"
    const val C_LIBRARY_SECTION_EXTERNAL_ID = "library_section_external_id"
    const val FULL_C_LIBRARY_SECTION_EXTERNAL_ID = "library_collection.library_section_external_id"
    const val C_POSITION = "position"
    const val FULL_C_POSITION = "library_collection.position"
    const val C_TITLE_HTML = "title_html"
    const val FULL_C_TITLE_HTML = "library_collection.title_html"
    const val C_COVER_RENDITIONS = "cover_renditions"
    const val FULL_C_COVER_RENDITIONS = "library_collection.cover_renditions"
    const val C_TYPE = "type_id"
    const val FULL_C_TYPE = "library_collection.type_id"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS library_collection (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "external_id TEXT NOT NULL," + 
        "library_section_id INTEGER," + 
        "library_section_external_id TEXT," + 
        "position INTEGER NOT NULL," + 
        "title_html TEXT NOT NULL," + 
        "cover_renditions TEXT," + 
        "type_id INTEGER NOT NULL," + 
        "UNIQUE(external_id)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS library_collection;"
    const val INSERT_STATEMENT = "INSERT INTO library_collection (external_id,library_section_id,library_section_external_id,position,title_html,cover_renditions,type_id) VALUES (?,?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE library_collection SET external_id=?, library_section_id=?, library_section_external_id=?, position=?, title_html=?, cover_renditions=?, type_id=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_EXTERNAL_ID,
        C_LIBRARY_SECTION_ID,
        C_LIBRARY_SECTION_EXTERNAL_ID,
        C_POSITION,
        C_TITLE_HTML,
        C_COVER_RENDITIONS,
        C_TYPE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_EXTERNAL_ID,
        FULL_C_LIBRARY_SECTION_ID,
        FULL_C_LIBRARY_SECTION_EXTERNAL_ID,
        FULL_C_POSITION,
        FULL_C_TITLE_HTML,
        FULL_C_COVER_RENDITIONS,
        FULL_C_TYPE)

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

    fun getCoverRenditions(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_COVER_RENDITIONS))) cursor.getString(cursor.getColumnIndexOrThrow(C_COVER_RENDITIONS)) else null
    }

    fun getType(cursor: Cursor) : org.lds.ldssa.model.database.types.LibraryCollectionType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.LibraryCollectionType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_TYPE)), org.lds.ldssa.model.database.types.LibraryCollectionType.DEFAULT)
    }


}