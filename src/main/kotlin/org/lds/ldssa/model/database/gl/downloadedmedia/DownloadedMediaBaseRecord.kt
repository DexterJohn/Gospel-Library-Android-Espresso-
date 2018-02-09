/*
 * DownloadedMediaBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.downloadedmedia

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class DownloadedMediaBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var contentItemId: Long = 0
    open var subItemId: Long = 0
    open var tertiaryId: String? = null
    open var title: String = ""
    open var type: org.lds.ldssa.model.database.types.ItemMediaType = org.lds.ldssa.model.database.types.ItemMediaType.UNKNOWN
    open var file: String? = null
    open var size: Int = 0
    open var quality: String? = null

    constructor() {
    }

    override fun getIdColumnName() : String {
        return DownloadedMediaConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return DownloadedMediaConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return DownloadedMediaConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(DownloadedMediaConst.C_CONTENT_ITEM_ID, contentItemId)
        values.put(DownloadedMediaConst.C_SUB_ITEM_ID, subItemId)
        values.put(DownloadedMediaConst.C_TERTIARY_ID, tertiaryId)
        values.put(DownloadedMediaConst.C_TITLE, title)
        values.put(DownloadedMediaConst.C_TYPE, type.ordinal.toLong())
        values.put(DownloadedMediaConst.C_FILE, file)
        values.put(DownloadedMediaConst.C_SIZE, size.toLong())
        values.put(DownloadedMediaConst.C_QUALITY, quality)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            contentItemId,
            subItemId,
            tertiaryId,
            title,
            type.ordinal.toLong(),
            file,
            size.toLong(),
            quality)
    }

    open fun copy() : DownloadedMedia {
        val copy = DownloadedMedia()
        copy.id = id
        copy.contentItemId = contentItemId
        copy.subItemId = subItemId
        copy.tertiaryId = tertiaryId
        copy.title = title
        copy.type = type
        copy.file = file
        copy.size = size
        copy.quality = quality
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, contentItemId)
        statement.bindLong(2, subItemId)
        if (tertiaryId != null) {
            statement.bindString(3, tertiaryId!!)
        } else {
            statement.bindNull(3)
        }
        statement.bindString(4, title)
        statement.bindLong(5, type.ordinal.toLong())
        if (file != null) {
            statement.bindString(6, file!!)
        } else {
            statement.bindNull(6)
        }
        statement.bindLong(7, size.toLong())
        if (quality != null) {
            statement.bindString(8, quality!!)
        } else {
            statement.bindNull(8)
        }
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, contentItemId)
        statement.bindLong(2, subItemId)
        if (tertiaryId != null) {
            statement.bindString(3, tertiaryId!!)
        } else {
            statement.bindNull(3)
        }
        statement.bindString(4, title)
        statement.bindLong(5, type.ordinal.toLong())
        if (file != null) {
            statement.bindString(6, file!!)
        } else {
            statement.bindNull(6)
        }
        statement.bindLong(7, size.toLong())
        if (quality != null) {
            statement.bindString(8, quality!!)
        } else {
            statement.bindNull(8)
        }
        statement.bindLong(9, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        contentItemId = values.getAsLong(DownloadedMediaConst.C_CONTENT_ITEM_ID)
        subItemId = values.getAsLong(DownloadedMediaConst.C_SUB_ITEM_ID)
        tertiaryId = values.getAsString(DownloadedMediaConst.C_TERTIARY_ID)
        title = values.getAsString(DownloadedMediaConst.C_TITLE)
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.ItemMediaType::class.java, values.getAsInteger(DownloadedMediaConst.C_TYPE), org.lds.ldssa.model.database.types.ItemMediaType.UNKNOWN)
        file = values.getAsString(DownloadedMediaConst.C_FILE)
        size = values.getAsInteger(DownloadedMediaConst.C_SIZE)
        quality = values.getAsString(DownloadedMediaConst.C_QUALITY)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadedMediaConst.C_ID))
        contentItemId = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadedMediaConst.C_CONTENT_ITEM_ID))
        subItemId = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadedMediaConst.C_SUB_ITEM_ID))
        tertiaryId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(DownloadedMediaConst.C_TERTIARY_ID))) cursor.getString(cursor.getColumnIndexOrThrow(DownloadedMediaConst.C_TERTIARY_ID)) else null
        title = cursor.getString(cursor.getColumnIndexOrThrow(DownloadedMediaConst.C_TITLE))
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.ItemMediaType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(DownloadedMediaConst.C_TYPE)), org.lds.ldssa.model.database.types.ItemMediaType.UNKNOWN)
        file = if (!cursor.isNull(cursor.getColumnIndexOrThrow(DownloadedMediaConst.C_FILE))) cursor.getString(cursor.getColumnIndexOrThrow(DownloadedMediaConst.C_FILE)) else null
        size = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadedMediaConst.C_SIZE))
        quality = if (!cursor.isNull(cursor.getColumnIndexOrThrow(DownloadedMediaConst.C_QUALITY))) cursor.getString(cursor.getColumnIndexOrThrow(DownloadedMediaConst.C_QUALITY)) else null
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}