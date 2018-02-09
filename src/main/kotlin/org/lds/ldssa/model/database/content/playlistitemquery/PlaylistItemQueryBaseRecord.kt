/*
 * PlaylistItemQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.playlistitemquery

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class PlaylistItemQueryBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var subitemId: Int = 0
    open var contentItemId: Long = 0
    open var mediaUrl: String = ""
    open var downloadedMediaUrl: String = ""
    open var title: String = ""
    open var artworkRenditions: String = ""
    open var voice: String? = null

    constructor() {
    }

    override fun getIdColumnName() : String {
        return ""
    }

    override fun getPrimaryKeyId() : Long {
        return 0
    }

    override fun setPrimaryKeyId(id: Long) {
    }

    override fun getAllColumns() : Array<String> {
        return PlaylistItemQueryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return PlaylistItemQueryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(PlaylistItemQueryConst.C_ID, id)
        values.put(PlaylistItemQueryConst.C_SUBITEM_ID, subitemId.toLong())
        values.put(PlaylistItemQueryConst.C_CONTENT_ITEM_ID, contentItemId)
        values.put(PlaylistItemQueryConst.C_MEDIA_URL, mediaUrl)
        values.put(PlaylistItemQueryConst.C_DOWNLOADED_MEDIA_URL, downloadedMediaUrl)
        values.put(PlaylistItemQueryConst.C_TITLE, title)
        values.put(PlaylistItemQueryConst.C_ARTWORK_RENDITIONS, artworkRenditions)
        values.put(PlaylistItemQueryConst.C_VOICE, voice)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            subitemId.toLong(),
            contentItemId,
            mediaUrl,
            downloadedMediaUrl,
            title,
            artworkRenditions,
            voice)
    }

    open fun copy() : PlaylistItemQuery {
        val copy = PlaylistItemQuery()
        copy.id = id
        copy.subitemId = subitemId
        copy.contentItemId = contentItemId
        copy.mediaUrl = mediaUrl
        copy.downloadedMediaUrl = downloadedMediaUrl
        copy.title = title
        copy.artworkRenditions = artworkRenditions
        copy.voice = voice
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, subitemId.toLong())
        statement.bindLong(3, contentItemId)
        statement.bindString(4, mediaUrl)
        statement.bindString(5, downloadedMediaUrl)
        statement.bindString(6, title)
        statement.bindString(7, artworkRenditions)
        if (voice != null) {
            statement.bindString(8, voice!!)
        } else {
            statement.bindNull(8)
        }
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, subitemId.toLong())
        statement.bindLong(3, contentItemId)
        statement.bindString(4, mediaUrl)
        statement.bindString(5, downloadedMediaUrl)
        statement.bindString(6, title)
        statement.bindString(7, artworkRenditions)
        if (voice != null) {
            statement.bindString(8, voice!!)
        } else {
            statement.bindNull(8)
        }
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        id = values.getAsLong(PlaylistItemQueryConst.C_ID)
        subitemId = values.getAsInteger(PlaylistItemQueryConst.C_SUBITEM_ID)
        contentItemId = values.getAsLong(PlaylistItemQueryConst.C_CONTENT_ITEM_ID)
        mediaUrl = values.getAsString(PlaylistItemQueryConst.C_MEDIA_URL)
        downloadedMediaUrl = values.getAsString(PlaylistItemQueryConst.C_DOWNLOADED_MEDIA_URL)
        title = values.getAsString(PlaylistItemQueryConst.C_TITLE)
        artworkRenditions = values.getAsString(PlaylistItemQueryConst.C_ARTWORK_RENDITIONS)
        voice = values.getAsString(PlaylistItemQueryConst.C_VOICE)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(PlaylistItemQueryConst.C_ID))
        subitemId = cursor.getInt(cursor.getColumnIndexOrThrow(PlaylistItemQueryConst.C_SUBITEM_ID))
        contentItemId = cursor.getLong(cursor.getColumnIndexOrThrow(PlaylistItemQueryConst.C_CONTENT_ITEM_ID))
        mediaUrl = cursor.getString(cursor.getColumnIndexOrThrow(PlaylistItemQueryConst.C_MEDIA_URL))
        downloadedMediaUrl = cursor.getString(cursor.getColumnIndexOrThrow(PlaylistItemQueryConst.C_DOWNLOADED_MEDIA_URL))
        title = cursor.getString(cursor.getColumnIndexOrThrow(PlaylistItemQueryConst.C_TITLE))
        artworkRenditions = cursor.getString(cursor.getColumnIndexOrThrow(PlaylistItemQueryConst.C_ARTWORK_RENDITIONS))
        voice = if (!cursor.isNull(cursor.getColumnIndexOrThrow(PlaylistItemQueryConst.C_VOICE))) cursor.getString(cursor.getColumnIndexOrThrow(PlaylistItemQueryConst.C_VOICE)) else null
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}