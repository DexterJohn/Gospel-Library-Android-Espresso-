/*
 * CatalogSourceBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.catalogsource

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object CatalogSourceConst  {

    const val DATABASE = "catalog"
    const val TABLE = "source"
    const val FULL_TABLE = "catalog.source"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "source._id"
    const val C_NAME = "name"
    const val FULL_C_NAME = "source.name"
    const val C_SOURCE_TYPE = "type_id"
    const val FULL_C_SOURCE_TYPE = "source.type_id"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS source (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "name TEXT NOT NULL," + 
        "type_id INTEGER NOT NULL," + 
        "UNIQUE(name)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS source;"
    const val INSERT_STATEMENT = "INSERT INTO source (name,type_id) VALUES (?,?)"
    const val UPDATE_STATEMENT = "UPDATE source SET name=?, type_id=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_NAME,
        C_SOURCE_TYPE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_NAME,
        FULL_C_SOURCE_TYPE)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }

    fun getSourceType(cursor: Cursor) : org.lds.ldssa.model.database.types.CatalogItemSourceType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.CatalogItemSourceType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_SOURCE_TYPE)), org.lds.ldssa.model.database.types.CatalogItemSourceType.DEFAULT)
    }


}