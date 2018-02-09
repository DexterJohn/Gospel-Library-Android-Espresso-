/*
 * ItemCategoryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.itemcategory

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object ItemCategoryConst  {

    const val DATABASE = "catalog"
    const val TABLE = "item_category"
    const val FULL_TABLE = "catalog.item_category"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "item_category._id"
    const val C_NAME = "name"
    const val FULL_C_NAME = "item_category.name"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS item_category (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "name TEXT NOT NULL," + 
        "UNIQUE(name)" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS item_category;"
    const val INSERT_STATEMENT = "INSERT INTO item_category (name) VALUES (?)"
    const val UPDATE_STATEMENT = "UPDATE item_category SET name=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_NAME)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_NAME)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_NAME))
    }


}