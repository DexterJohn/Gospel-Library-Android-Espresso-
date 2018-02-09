/*
 * SyncContentItemAnnotationsQueueItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.synccontentitemannotationsqueueitem

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object SyncContentItemAnnotationsQueueItemConst  {

    const val DATABASE = "userdata"
    const val TABLE = "sync_content_item_annotations_queue_item"
    const val FULL_TABLE = "userdata.sync_content_item_annotations_queue_item"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "sync_content_item_annotations_queue_item._id"
    const val C_CONTENT_ITEM_ID = "content_item_id"
    const val FULL_C_CONTENT_ITEM_ID = "sync_content_item_annotations_queue_item.content_item_id"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS sync_content_item_annotations_queue_item (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "content_item_id INTEGER NOT NULL" + 
        ");" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS sync_content_item_annotations_queue_item;"
    const val INSERT_STATEMENT = "INSERT INTO sync_content_item_annotations_queue_item (content_item_id) VALUES (?)"
    const val UPDATE_STATEMENT = "UPDATE sync_content_item_annotations_queue_item SET content_item_id=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_CONTENT_ITEM_ID)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_CONTENT_ITEM_ID)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getContentItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_CONTENT_ITEM_ID))
    }


}