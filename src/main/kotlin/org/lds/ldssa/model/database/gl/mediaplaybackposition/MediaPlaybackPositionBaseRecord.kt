/*
 * MediaPlaybackPositionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.mediaplaybackposition

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class MediaPlaybackPositionBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var contentItemId: Long = 0
    open var subItemId: Long = 0
    open var type: org.lds.ldssa.model.database.types.ItemMediaType = org.lds.ldssa.model.database.types.ItemMediaType.UNKNOWN
    open var mediaId: String = ""
    open var playbackPosition: Int = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return MediaPlaybackPositionConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return MediaPlaybackPositionConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return MediaPlaybackPositionConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(MediaPlaybackPositionConst.C_CONTENT_ITEM_ID, contentItemId)
        values.put(MediaPlaybackPositionConst.C_SUB_ITEM_ID, subItemId)
        values.put(MediaPlaybackPositionConst.C_TYPE, type.ordinal.toLong())
        values.put(MediaPlaybackPositionConst.C_MEDIA_ID, mediaId)
        values.put(MediaPlaybackPositionConst.C_PLAYBACK_POSITION, playbackPosition.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            contentItemId,
            subItemId,
            type.ordinal.toLong(),
            mediaId,
            playbackPosition.toLong())
    }

    open fun copy() : MediaPlaybackPosition {
        val copy = MediaPlaybackPosition()
        copy.id = id
        copy.contentItemId = contentItemId
        copy.subItemId = subItemId
        copy.type = type
        copy.mediaId = mediaId
        copy.playbackPosition = playbackPosition
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, contentItemId)
        statement.bindLong(2, subItemId)
        statement.bindLong(3, type.ordinal.toLong())
        statement.bindString(4, mediaId)
        statement.bindLong(5, playbackPosition.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, contentItemId)
        statement.bindLong(2, subItemId)
        statement.bindLong(3, type.ordinal.toLong())
        statement.bindString(4, mediaId)
        statement.bindLong(5, playbackPosition.toLong())
        statement.bindLong(6, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        contentItemId = values.getAsLong(MediaPlaybackPositionConst.C_CONTENT_ITEM_ID)
        subItemId = values.getAsLong(MediaPlaybackPositionConst.C_SUB_ITEM_ID)
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.ItemMediaType::class.java, values.getAsInteger(MediaPlaybackPositionConst.C_TYPE), org.lds.ldssa.model.database.types.ItemMediaType.UNKNOWN)
        mediaId = values.getAsString(MediaPlaybackPositionConst.C_MEDIA_ID)
        playbackPosition = values.getAsInteger(MediaPlaybackPositionConst.C_PLAYBACK_POSITION)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaPlaybackPositionConst.C_ID))
        contentItemId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaPlaybackPositionConst.C_CONTENT_ITEM_ID))
        subItemId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaPlaybackPositionConst.C_SUB_ITEM_ID))
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.ItemMediaType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(MediaPlaybackPositionConst.C_TYPE)), org.lds.ldssa.model.database.types.ItemMediaType.UNKNOWN)
        mediaId = cursor.getString(cursor.getColumnIndexOrThrow(MediaPlaybackPositionConst.C_MEDIA_ID))
        playbackPosition = cursor.getInt(cursor.getColumnIndexOrThrow(MediaPlaybackPositionConst.C_PLAYBACK_POSITION))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}