/*
 * RelatedAudioItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.relatedaudioitem

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class RelatedAudioItemBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var subitemId: Long = 0
    open var mediaUrl: String = ""
    open var fileSize: Int = 0
    open var duration: Int = 0
    open var relatedAudioVoiceId: Int? = null

    constructor() {
    }

    override fun getIdColumnName() : String {
        return RelatedAudioItemConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return RelatedAudioItemConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return RelatedAudioItemConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(RelatedAudioItemConst.C_SUBITEM_ID, subitemId)
        values.put(RelatedAudioItemConst.C_MEDIA_URL, mediaUrl)
        values.put(RelatedAudioItemConst.C_FILE_SIZE, fileSize.toLong())
        values.put(RelatedAudioItemConst.C_DURATION, duration.toLong())
        values.put(RelatedAudioItemConst.C_RELATED_AUDIO_VOICE_ID, relatedAudioVoiceId?.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            subitemId,
            mediaUrl,
            fileSize.toLong(),
            duration.toLong(),
            relatedAudioVoiceId?.toLong())
    }

    open fun copy() : RelatedAudioItem {
        val copy = RelatedAudioItem()
        copy.id = id
        copy.subitemId = subitemId
        copy.mediaUrl = mediaUrl
        copy.fileSize = fileSize
        copy.duration = duration
        copy.relatedAudioVoiceId = relatedAudioVoiceId
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, subitemId)
        statement.bindString(2, mediaUrl)
        statement.bindLong(3, fileSize.toLong())
        statement.bindLong(4, duration.toLong())
        if (relatedAudioVoiceId != null) {
            statement.bindLong(5, relatedAudioVoiceId?.toLong()!!)
        } else {
            statement.bindNull(5)
        }
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, subitemId)
        statement.bindString(2, mediaUrl)
        statement.bindLong(3, fileSize.toLong())
        statement.bindLong(4, duration.toLong())
        if (relatedAudioVoiceId != null) {
            statement.bindLong(5, relatedAudioVoiceId?.toLong()!!)
        } else {
            statement.bindNull(5)
        }
        statement.bindLong(6, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        subitemId = values.getAsLong(RelatedAudioItemConst.C_SUBITEM_ID)
        mediaUrl = values.getAsString(RelatedAudioItemConst.C_MEDIA_URL)
        fileSize = values.getAsInteger(RelatedAudioItemConst.C_FILE_SIZE)
        duration = values.getAsInteger(RelatedAudioItemConst.C_DURATION)
        relatedAudioVoiceId = values.getAsInteger(RelatedAudioItemConst.C_RELATED_AUDIO_VOICE_ID)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(RelatedAudioItemConst.C_ID))
        subitemId = cursor.getLong(cursor.getColumnIndexOrThrow(RelatedAudioItemConst.C_SUBITEM_ID))
        mediaUrl = cursor.getString(cursor.getColumnIndexOrThrow(RelatedAudioItemConst.C_MEDIA_URL))
        fileSize = cursor.getInt(cursor.getColumnIndexOrThrow(RelatedAudioItemConst.C_FILE_SIZE))
        duration = cursor.getInt(cursor.getColumnIndexOrThrow(RelatedAudioItemConst.C_DURATION))
        relatedAudioVoiceId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(RelatedAudioItemConst.C_RELATED_AUDIO_VOICE_ID))) cursor.getInt(cursor.getColumnIndexOrThrow(RelatedAudioItemConst.C_RELATED_AUDIO_VOICE_ID)) else null
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}