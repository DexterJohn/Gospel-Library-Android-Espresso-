/*
 * DownloadedItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.downloadeditem

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object DownloadedItemConst  {

    const val DATABASE = "gl"
    const val TABLE = "downloaded_item"
    const val FULL_TABLE = "gl.downloaded_item"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "downloaded_item._id"
    const val C_CONTENT_ITEM_ID = "content_item_id"
    const val FULL_C_CONTENT_ITEM_ID = "downloaded_item.content_item_id"
    const val C_CATALOG_ITEM_SOURCE_TYPE = "catalog_item_source_type"
    const val FULL_C_CATALOG_ITEM_SOURCE_TYPE = "downloaded_item.catalog_item_source_type"
    const val C_EXTERNAL_ID = "external_id"
    const val FULL_C_EXTERNAL_ID = "downloaded_item.external_id"
    const val C_INSTALLED_VERSION = "installed_version"
    const val FULL_C_INSTALLED_VERSION = "downloaded_item.installed_version"
    const val C_LIBRARY_SECTION_ID = "library_section_id"
    const val FULL_C_LIBRARY_SECTION_ID = "downloaded_item.library_section_id"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS downloaded_item (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "content_item_id INTEGER NOT NULL," + 
        "catalog_item_source_type INTEGER NOT NULL," + 
        "external_id TEXT," + 
        "installed_version INTEGER DEFAULT 0 NOT NULL," + 
        "library_section_id INTEGER NOT NULL," + 
        "UNIQUE(content_item_id, external_id) ON CONFLICT REPLACE" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS downloaded_item;"
    const val INSERT_STATEMENT = "INSERT INTO downloaded_item (content_item_id,catalog_item_source_type,external_id,installed_version,library_section_id) VALUES (?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE downloaded_item SET content_item_id=?, catalog_item_source_type=?, external_id=?, installed_version=?, library_section_id=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_CONTENT_ITEM_ID,
        C_CATALOG_ITEM_SOURCE_TYPE,
        C_EXTERNAL_ID,
        C_INSTALLED_VERSION,
        C_LIBRARY_SECTION_ID)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_CONTENT_ITEM_ID,
        FULL_C_CATALOG_ITEM_SOURCE_TYPE,
        FULL_C_EXTERNAL_ID,
        FULL_C_INSTALLED_VERSION,
        FULL_C_LIBRARY_SECTION_ID)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getContentItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_CONTENT_ITEM_ID))
    }

    fun getCatalogItemSourceType(cursor: Cursor) : org.lds.ldssa.model.database.types.CatalogItemSourceType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.CatalogItemSourceType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_CATALOG_ITEM_SOURCE_TYPE)), org.lds.ldssa.model.database.types.CatalogItemSourceType.DEFAULT)
    }

    fun getExternalId(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_EXTERNAL_ID))) cursor.getString(cursor.getColumnIndexOrThrow(C_EXTERNAL_ID)) else null
    }

    fun getInstalledVersion(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_INSTALLED_VERSION))
    }

    fun getLibrarySectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_LIBRARY_SECTION_ID))
    }


}