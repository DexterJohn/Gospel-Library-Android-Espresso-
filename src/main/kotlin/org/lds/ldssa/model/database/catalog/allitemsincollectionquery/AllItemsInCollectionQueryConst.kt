/*
 * AllItemsInCollectionQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.allitemsincollectionquery

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object AllItemsInCollectionQueryConst  {

    const val DATABASE = "catalog"
    const val C_CONTENT_ITEM_ID = "content_item_id"
    const val FULL_C_CONTENT_ITEM_ID = "all_items_in_collection_query.content_item_id"
    val ALL_COLUMNS = arrayOf(
        C_CONTENT_ITEM_ID)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_CONTENT_ITEM_ID)

    fun getContentItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_CONTENT_ITEM_ID))
    }


}