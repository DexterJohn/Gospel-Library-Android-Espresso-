/*
 * DownloadedMediaCollectionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.downloadedmediacollection

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object DownloadedMediaCollectionConst  {

    const val DATABASE = "gl"
    const val TABLE = "downloaded_media_collection"
    const val FULL_TABLE = "gl.downloaded_media_collection"
    const val C_ID = "_id"
    const val FULL_C_ID = "downloaded_media_collection._id"
    const val C_ITEM_COUNT = "item_count"
    const val FULL_C_ITEM_COUNT = "downloaded_media_collection.item_count"
    const val C_TOTAL_SIZE = "total_size"
    const val FULL_C_TOTAL_SIZE = "downloaded_media_collection.total_size"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_ITEM_COUNT,
        C_TOTAL_SIZE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_ITEM_COUNT,
        FULL_C_TOTAL_SIZE)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getItemCount(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ITEM_COUNT))
    }

    fun getTotalSize(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_TOTAL_SIZE))
    }


}