/*
 * AllSubItemsInNavCollectionQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.allsubitemsinnavcollectionquery

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object AllSubItemsInNavCollectionQueryConst  {

    const val DATABASE = "content"
    const val C_SUB_ITEM_ID = "sub_item_id"
    const val FULL_C_SUB_ITEM_ID = "all_sub_items_in_nav_collection_query.sub_item_id"
    val ALL_COLUMNS = arrayOf(
        C_SUB_ITEM_ID)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_SUB_ITEM_ID)

    fun getSubItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SUB_ITEM_ID))
    }


}