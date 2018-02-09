/*
 * SubItemMetadataBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.subitemmetadata

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object SubItemMetadataConst  {

    const val DATABASE = "catalog"
    const val TABLE = "subitem_metadata"
    const val FULL_TABLE = "catalog.subitem_metadata"
    const val C_ITEM_ID = "item_id"
    const val FULL_C_ITEM_ID = "subitem_metadata.item_id"
    const val C_SUBITEM_ID = "subitem_id"
    const val FULL_C_SUBITEM_ID = "subitem_metadata.subitem_id"
    const val C_DOC_ID = "doc_id"
    const val FULL_C_DOC_ID = "subitem_metadata.doc_id"
    const val C_DOC_VERSION = "doc_version"
    const val FULL_C_DOC_VERSION = "subitem_metadata.doc_version"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS subitem_metadata (" + 
        "item_id INTEGER NOT NULL," + 
        "subitem_id INTEGER NOT NULL," + 
        "doc_id TEXT NOT NULL," + 
        "doc_version INTEGER NOT NULL" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS subitem_metadata;"
    const val INSERT_STATEMENT = "INSERT INTO subitem_metadata (item_id,subitem_id,doc_id,doc_version) VALUES (?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE subitem_metadata SET item_id=?, subitem_id=?, doc_id=?, doc_version=? WHERE  = ?"
    val ALL_COLUMNS = arrayOf(
        C_ITEM_ID,
        C_SUBITEM_ID,
        C_DOC_ID,
        C_DOC_VERSION)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ITEM_ID,
        FULL_C_SUBITEM_ID,
        FULL_C_DOC_ID,
        FULL_C_DOC_VERSION)

    fun getItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ITEM_ID))
    }

    fun getSubitemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SUBITEM_ID))
    }

    fun getDocId(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_DOC_ID))
    }

    fun getDocVersion(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_DOC_VERSION))
    }


}