/*
 * CustomCollectionItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.customcollectionitem

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object CustomCollectionItemConst  {

    const val DATABASE = "userdata"
    const val TABLE = "custom_collection_item"
    const val FULL_TABLE = "userdata.custom_collection_item"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "custom_collection_item._id"
    const val C_CUSTOM_COLLECTION_ID = "custom_collection_id"
    const val FULL_C_CUSTOM_COLLECTION_ID = "custom_collection_item.custom_collection_id"
    const val C_ORDER_POSITION = "order_position"
    const val FULL_C_ORDER_POSITION = "custom_collection_item.order_position"
    const val C_CATALOG_ITEM_EXTERNAL_ID = "catalog_item_external_id"
    const val FULL_C_CATALOG_ITEM_EXTERNAL_ID = "custom_collection_item.catalog_item_external_id"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS custom_collection_item (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "custom_collection_id INTEGER NOT NULL," + 
        "order_position INTEGER NOT NULL," + 
        "catalog_item_external_id TEXT NOT NULL," + 
        "UNIQUE(custom_collection_id, catalog_item_external_id) ON CONFLICT REPLACE" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS custom_collection_item;"
    const val INSERT_STATEMENT = "INSERT INTO custom_collection_item (custom_collection_id,order_position,catalog_item_external_id) VALUES (?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE custom_collection_item SET custom_collection_id=?, order_position=?, catalog_item_external_id=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_CUSTOM_COLLECTION_ID,
        C_ORDER_POSITION,
        C_CATALOG_ITEM_EXTERNAL_ID)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_CUSTOM_COLLECTION_ID,
        FULL_C_ORDER_POSITION,
        FULL_C_CATALOG_ITEM_EXTERNAL_ID)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getCustomCollectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_CUSTOM_COLLECTION_ID))
    }

    fun getOrderPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_ORDER_POSITION))
    }

    fun getCatalogItemExternalId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_CATALOG_ITEM_EXTERNAL_ID))
    }


}