/*
 * DownloadQueueItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.downloadqueueitem

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object DownloadQueueItemConst  {

    const val DATABASE = "gl"
    const val TABLE = "download_queue_item"
    const val FULL_TABLE = "gl.download_queue_item"
    const val PRIMARY_KEY_COLUMN = "_id"
    const val C_ID = "_id"
    const val FULL_C_ID = "download_queue_item._id"
    const val C_ANDROID_DOWNLOAD_ID = "android_download_id"
    const val FULL_C_ANDROID_DOWNLOAD_ID = "download_queue_item.android_download_id"
    const val C_TYPE = "type"
    const val FULL_C_TYPE = "download_queue_item.type"
    const val C_PROCESSING_DOWNLOADED_ITEM = "processing_downloaded_item"
    const val FULL_C_PROCESSING_DOWNLOADED_ITEM = "download_queue_item.processing_downloaded_item"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "download_queue_item.title"
    const val C_CONTENT_ITEM_ID = "content_item_id"
    const val FULL_C_CONTENT_ITEM_ID = "download_queue_item.content_item_id"
    const val C_VERSION = "version"
    const val FULL_C_VERSION = "download_queue_item.version"
    const val C_SUB_ITEM_ID = "sub_item_id"
    const val FULL_C_SUB_ITEM_ID = "download_queue_item.sub_item_id"
    const val C_TERTIARY_ID = "tertiary_id"
    const val FULL_C_TERTIARY_ID = "download_queue_item.tertiary_id"
    const val C_SOURCE_URI = "source_uri"
    const val FULL_C_SOURCE_URI = "download_queue_item.source_uri"
    const val C_DESTINATION_URI = "destination_uri"
    const val FULL_C_DESTINATION_URI = "download_queue_item.destination_uri"
    const val C_CATALOG_NAME = "catalog_name"
    const val FULL_C_CATALOG_NAME = "download_queue_item.catalog_name"
    const val C_CATALOG_ITEM_SOURCE_TYPE = "catalog_item_source_type"
    const val FULL_C_CATALOG_ITEM_SOURCE_TYPE = "download_queue_item.catalog_item_source_type"
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS download_queue_item (" + 
        "_id INTEGER PRIMARY KEY  AUTOINCREMENT," + 
        "android_download_id INTEGER NOT NULL," + 
        "type INTEGER NOT NULL," + 
        "processing_downloaded_item INTEGER DEFAULT 0 NOT NULL," + 
        "title TEXT NOT NULL," + 
        "content_item_id INTEGER NOT NULL," + 
        "version INTEGER NOT NULL," + 
        "sub_item_id INTEGER DEFAULT -1 NOT NULL," + 
        "tertiary_id TEXT," + 
        "source_uri TEXT NOT NULL," + 
        "destination_uri TEXT NOT NULL," + 
        "catalog_name TEXT NOT NULL," + 
        "catalog_item_source_type INTEGER NOT NULL" + 
        ");" + 
        "" + 
        "CREATE INDEX IF NOT EXISTS download_queue_itemandroid_download_id_IDX ON download_queue_item (android_download_id);" + 
        "" + 
        "CREATE INDEX IF NOT EXISTS download_queue_itemcontent_item_id_IDX ON download_queue_item (content_item_id);" + 
        "" + 
        "CREATE INDEX IF NOT EXISTS download_queue_itemversion_IDX ON download_queue_item (version);" + 
        "" + 
        ""
    const val DROP_TABLE = "DROP TABLE IF EXISTS download_queue_item;"
    const val INSERT_STATEMENT = "INSERT INTO download_queue_item (android_download_id,type,processing_downloaded_item,title,content_item_id,version,sub_item_id,tertiary_id,source_uri,destination_uri,catalog_name,catalog_item_source_type) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)"
    const val UPDATE_STATEMENT = "UPDATE download_queue_item SET android_download_id=?, type=?, processing_downloaded_item=?, title=?, content_item_id=?, version=?, sub_item_id=?, tertiary_id=?, source_uri=?, destination_uri=?, catalog_name=?, catalog_item_source_type=? WHERE _id = ?"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_ANDROID_DOWNLOAD_ID,
        C_TYPE,
        C_PROCESSING_DOWNLOADED_ITEM,
        C_TITLE,
        C_CONTENT_ITEM_ID,
        C_VERSION,
        C_SUB_ITEM_ID,
        C_TERTIARY_ID,
        C_SOURCE_URI,
        C_DESTINATION_URI,
        C_CATALOG_NAME,
        C_CATALOG_ITEM_SOURCE_TYPE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_ANDROID_DOWNLOAD_ID,
        FULL_C_TYPE,
        FULL_C_PROCESSING_DOWNLOADED_ITEM,
        FULL_C_TITLE,
        FULL_C_CONTENT_ITEM_ID,
        FULL_C_VERSION,
        FULL_C_SUB_ITEM_ID,
        FULL_C_TERTIARY_ID,
        FULL_C_SOURCE_URI,
        FULL_C_DESTINATION_URI,
        FULL_C_CATALOG_NAME,
        FULL_C_CATALOG_ITEM_SOURCE_TYPE)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getAndroidDownloadId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ANDROID_DOWNLOAD_ID))
    }

    fun getType(cursor: Cursor) : org.lds.ldssa.model.database.types.ItemMediaType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.ItemMediaType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_TYPE)), org.lds.ldssa.model.database.types.ItemMediaType.UNKNOWN)
    }

    fun isProcessingDownloadedItem(cursor: Cursor) : Boolean {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_PROCESSING_DOWNLOADED_ITEM)) != 0
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getContentItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_CONTENT_ITEM_ID))
    }

    fun getVersion(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_VERSION))
    }

    fun getSubItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_SUB_ITEM_ID))
    }

    fun getTertiaryId(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_TERTIARY_ID))) cursor.getString(cursor.getColumnIndexOrThrow(C_TERTIARY_ID)) else null
    }

    fun getSourceUri(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_SOURCE_URI))
    }

    fun getDestinationUri(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_DESTINATION_URI))
    }

    fun getCatalogName(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_CATALOG_NAME))
    }

    fun getCatalogItemSourceType(cursor: Cursor) : org.lds.ldssa.model.database.types.CatalogItemSourceType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.CatalogItemSourceType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_CATALOG_ITEM_SOURCE_TYPE)), org.lds.ldssa.model.database.types.CatalogItemSourceType.DEFAULT)
    }


}