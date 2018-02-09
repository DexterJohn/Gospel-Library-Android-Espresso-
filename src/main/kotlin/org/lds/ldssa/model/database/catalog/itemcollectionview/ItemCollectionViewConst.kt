/*
 * ItemCollectionViewBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.itemcollectionview

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object ItemCollectionViewConst  {

    const val DATABASE = "catalog"
    const val TABLE = "item_collection_view"
    const val FULL_TABLE = "catalog.item_collection_view"
    const val C_ITEM_ID = "item_id"
    const val FULL_C_ITEM_ID = "item_collection_view.item_id"
    const val C_ITEM_TITLE = "item_title"
    const val FULL_C_ITEM_TITLE = "item_collection_view.item_title"
    const val C_ITEM_POSITION = "item_position"
    const val FULL_C_ITEM_POSITION = "item_collection_view.item_position"
    const val C_COLLECTION_ID = "collection_id"
    const val FULL_C_COLLECTION_ID = "item_collection_view.collection_id"
    const val C_COLLECTION_TITLE = "collection_title"
    const val FULL_C_COLLECTION_TITLE = "item_collection_view.collection_title"
    const val C_COLLECTION_POSITION = "collection_position"
    const val FULL_C_COLLECTION_POSITION = "item_collection_view.collection_position"
    const val C_SECTION_ID = "section_id"
    const val FULL_C_SECTION_ID = "item_collection_view.section_id"
    const val C_SECTION_POSITION = "section_position"
    const val FULL_C_SECTION_POSITION = "item_collection_view.section_position"
    val ALL_COLUMNS = arrayOf(
        C_ITEM_ID,
        C_ITEM_TITLE,
        C_ITEM_POSITION,
        C_COLLECTION_ID,
        C_COLLECTION_TITLE,
        C_COLLECTION_POSITION,
        C_SECTION_ID,
        C_SECTION_POSITION)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ITEM_ID,
        FULL_C_ITEM_TITLE,
        FULL_C_ITEM_POSITION,
        FULL_C_COLLECTION_ID,
        FULL_C_COLLECTION_TITLE,
        FULL_C_COLLECTION_POSITION,
        FULL_C_SECTION_ID,
        FULL_C_SECTION_POSITION)

    fun getItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ITEM_ID))
    }

    fun getItemTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_ITEM_TITLE))
    }

    fun getItemPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_ITEM_POSITION))
    }

    fun getCollectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_COLLECTION_ID))
    }

    fun getCollectionTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_COLLECTION_TITLE))
    }

    fun getCollectionPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_COLLECTION_POSITION))
    }

    fun getSectionId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SECTION_ID))
    }

    fun getSectionPosition(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SECTION_POSITION))
    }


}