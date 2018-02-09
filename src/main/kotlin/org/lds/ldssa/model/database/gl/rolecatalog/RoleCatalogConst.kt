/*
 * RoleCatalogBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.rolecatalog

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object RoleCatalogConst  {

    const val DATABASE = "gl"
    const val TABLE = "role_catalog"
    const val FULL_TABLE = "gl.role_catalog"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "role_catalog._id"
    const val C_NAME = "name"
    const val FULL_C_NAME = "role_catalog.name"
    const val C_BASE_URL = "base_url"
    const val FULL_C_BASE_URL = "role_catalog.base_url"
    const val C_CATALOG_ITEM_SOURCE_TYPE = "catalog_item_source_type"
    const val FULL_C_CATALOG_ITEM_SOURCE_TYPE = "role_catalog.catalog_item_source_type"
    const val C_VERSION = "version"
    const val FULL_C_VERSION = "role_catalog.version"
    const val C_INSTALLED = "installed"
    const val FULL_C_INSTALLED = "role_catalog.installed"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS role_catalog (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "name TEXT NOT NULL," + 
        "base_url TEXT NOT NULL," + 
        "catalog_item_source_type INTEGER NOT NULL," + 
        "version INTEGER NOT NULL," + 
        "installed INTEGER NOT NULL" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS role_catalog;"
    const val INSERT_STATEMENT = "INSERT INTO role_catalog (name,base_url,catalog_item_source_type,version,installed) VALUES (?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE role_catalog SET name=?, base_url=?, catalog_item_source_type=?, version=?, installed=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_NAME,
        C_BASE_URL,
        C_CATALOG_ITEM_SOURCE_TYPE,
        C_VERSION,
        C_INSTALLED)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_NAME,
        FULL_C_BASE_URL,
        FULL_C_CATALOG_ITEM_SOURCE_TYPE,
        FULL_C_VERSION,
        FULL_C_INSTALLED)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }

    fun getBaseUrl(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_BASE_URL))
    }

    fun getCatalogItemSourceType(cursor: Cursor) : org.lds.ldssa.model.database.types.CatalogItemSourceType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.CatalogItemSourceType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_CATALOG_ITEM_SOURCE_TYPE)), org.lds.ldssa.model.database.types.CatalogItemSourceType.DEFAULT)
    }

    fun getVersion(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_VERSION))
    }

    fun isInstalled(cursor: Cursor) : Boolean {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_INSTALLED)) != 0
    }


}