/*
 * PlaylistItemQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.playlistitemquery

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object PlaylistItemQueryConst  {

    const val DATABASE = "content"
    const val C_ID = "_id"
    const val FULL_C_ID = "playlist_item_query._id"
    const val C_SUBITEM_ID = "subitem_id"
    const val FULL_C_SUBITEM_ID = "playlist_item_query.subitem_id"
    const val C_CONTENT_ITEM_ID = "content_item_id"
    const val FULL_C_CONTENT_ITEM_ID = "playlist_item_query.content_item_id"
    const val C_MEDIA_URL = "media_url"
    const val FULL_C_MEDIA_URL = "playlist_item_query.media_url"
    const val C_DOWNLOADED_MEDIA_URL = "downloaded_media_url"
    const val FULL_C_DOWNLOADED_MEDIA_URL = "playlist_item_query.downloaded_media_url"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "playlist_item_query.title"
    const val C_ARTWORK_RENDITIONS = "artwork_renditions"
    const val FULL_C_ARTWORK_RENDITIONS = "playlist_item_query.artwork_renditions"
    const val C_VOICE = "voice"
    const val FULL_C_VOICE = "playlist_item_query.voice"
    val ALL_COLUMNS = arrayOf(
        C_ID,
        C_SUBITEM_ID,
        C_CONTENT_ITEM_ID,
        C_MEDIA_URL,
        C_DOWNLOADED_MEDIA_URL,
        C_TITLE,
        C_ARTWORK_RENDITIONS,
        C_VOICE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_ID,
        FULL_C_SUBITEM_ID,
        FULL_C_CONTENT_ITEM_ID,
        FULL_C_MEDIA_URL,
        FULL_C_DOWNLOADED_MEDIA_URL,
        FULL_C_TITLE,
        FULL_C_ARTWORK_RENDITIONS,
        FULL_C_VOICE)

    fun getId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ID))
    }

    fun getSubitemId(cursor: Cursor) : Int {
        return cursor.getInt(cursor.getColumnIndexOrThrow(C_SUBITEM_ID))
    }

    fun getContentItemId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_CONTENT_ITEM_ID))
    }

    fun getMediaUrl(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_MEDIA_URL))
    }

    fun getDownloadedMediaUrl(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_DOWNLOADED_MEDIA_URL))
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getArtworkRenditions(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_ARTWORK_RENDITIONS))
    }

    fun getVoice(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_VOICE))) cursor.getString(cursor.getColumnIndexOrThrow(C_VOICE)) else null
    }


}