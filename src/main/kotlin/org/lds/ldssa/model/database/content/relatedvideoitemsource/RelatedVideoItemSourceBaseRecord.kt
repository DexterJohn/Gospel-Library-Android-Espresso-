/*
 * RelatedVideoItemSourceBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.relatedvideoitemsource

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class RelatedVideoItemSourceBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var mediaUrl: String = ""
    open var type: String = ""
    open var width: Int? = null
    open var height: Int? = null
    open var fileSize: Long? = null
    open var relatedVideoItemId: Long? = null

    constructor() {
    }

    override fun getIdColumnName() : String {
        return RelatedVideoItemSourceConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return RelatedVideoItemSourceConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return RelatedVideoItemSourceConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(RelatedVideoItemSourceConst.C_MEDIA_URL, mediaUrl)
        values.put(RelatedVideoItemSourceConst.C_TYPE, type)
        values.put(RelatedVideoItemSourceConst.C_WIDTH, width?.toLong())
        values.put(RelatedVideoItemSourceConst.C_HEIGHT, height?.toLong())
        values.put(RelatedVideoItemSourceConst.C_FILE_SIZE, fileSize)
        values.put(RelatedVideoItemSourceConst.C_RELATED_VIDEO_ITEM_ID, relatedVideoItemId)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            mediaUrl,
            type,
            width?.toLong(),
            height?.toLong(),
            fileSize,
            relatedVideoItemId)
    }

    open fun copy() : RelatedVideoItemSource {
        val copy = RelatedVideoItemSource()
        copy.id = id
        copy.mediaUrl = mediaUrl
        copy.type = type
        copy.width = width
        copy.height = height
        copy.fileSize = fileSize
        copy.relatedVideoItemId = relatedVideoItemId
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, mediaUrl)
        statement.bindString(2, type)
        if (width != null) {
            statement.bindLong(3, width?.toLong()!!)
        } else {
            statement.bindNull(3)
        }
        if (height != null) {
            statement.bindLong(4, height?.toLong()!!)
        } else {
            statement.bindNull(4)
        }
        if (fileSize != null) {
            statement.bindLong(5, fileSize!!)
        } else {
            statement.bindNull(5)
        }
        if (relatedVideoItemId != null) {
            statement.bindLong(6, relatedVideoItemId!!)
        } else {
            statement.bindNull(6)
        }
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, mediaUrl)
        statement.bindString(2, type)
        if (width != null) {
            statement.bindLong(3, width?.toLong()!!)
        } else {
            statement.bindNull(3)
        }
        if (height != null) {
            statement.bindLong(4, height?.toLong()!!)
        } else {
            statement.bindNull(4)
        }
        if (fileSize != null) {
            statement.bindLong(5, fileSize!!)
        } else {
            statement.bindNull(5)
        }
        if (relatedVideoItemId != null) {
            statement.bindLong(6, relatedVideoItemId!!)
        } else {
            statement.bindNull(6)
        }
        statement.bindLong(7, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        mediaUrl = values.getAsString(RelatedVideoItemSourceConst.C_MEDIA_URL)
        type = values.getAsString(RelatedVideoItemSourceConst.C_TYPE)
        width = values.getAsInteger(RelatedVideoItemSourceConst.C_WIDTH)
        height = values.getAsInteger(RelatedVideoItemSourceConst.C_HEIGHT)
        fileSize = values.getAsLong(RelatedVideoItemSourceConst.C_FILE_SIZE)
        relatedVideoItemId = values.getAsLong(RelatedVideoItemSourceConst.C_RELATED_VIDEO_ITEM_ID)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(RelatedVideoItemSourceConst.C_ID))
        mediaUrl = cursor.getString(cursor.getColumnIndexOrThrow(RelatedVideoItemSourceConst.C_MEDIA_URL))
        type = cursor.getString(cursor.getColumnIndexOrThrow(RelatedVideoItemSourceConst.C_TYPE))
        width = if (!cursor.isNull(cursor.getColumnIndexOrThrow(RelatedVideoItemSourceConst.C_WIDTH))) cursor.getInt(cursor.getColumnIndexOrThrow(RelatedVideoItemSourceConst.C_WIDTH)) else null
        height = if (!cursor.isNull(cursor.getColumnIndexOrThrow(RelatedVideoItemSourceConst.C_HEIGHT))) cursor.getInt(cursor.getColumnIndexOrThrow(RelatedVideoItemSourceConst.C_HEIGHT)) else null
        fileSize = if (!cursor.isNull(cursor.getColumnIndexOrThrow(RelatedVideoItemSourceConst.C_FILE_SIZE))) cursor.getLong(cursor.getColumnIndexOrThrow(RelatedVideoItemSourceConst.C_FILE_SIZE)) else null
        relatedVideoItemId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(RelatedVideoItemSourceConst.C_RELATED_VIDEO_ITEM_ID))) cursor.getLong(cursor.getColumnIndexOrThrow(RelatedVideoItemSourceConst.C_RELATED_VIDEO_ITEM_ID)) else null
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}