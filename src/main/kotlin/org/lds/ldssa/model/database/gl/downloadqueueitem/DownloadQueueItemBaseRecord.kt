/*
 * DownloadQueueItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.downloadqueueitem

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class DownloadQueueItemBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var androidDownloadId: Long = 0
    open var type: org.lds.ldssa.model.database.types.ItemMediaType = org.lds.ldssa.model.database.types.ItemMediaType.UNKNOWN
    open var processingDownloadedItem: Boolean = false
    open var title: String = ""
    open var contentItemId: Long = 0
    open var version: Int = 0
    open var subItemId: Long = -1
    open var tertiaryId: String? = null
    open var sourceUri: String = ""
    open var destinationUri: String = ""
    open var catalogName: String = ""
    open var catalogItemSourceType: org.lds.ldssa.model.database.types.CatalogItemSourceType = org.lds.ldssa.model.database.types.CatalogItemSourceType.DEFAULT

    constructor() {
    }

    override fun getIdColumnName() : String {
        return DownloadQueueItemConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return DownloadQueueItemConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return DownloadQueueItemConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(DownloadQueueItemConst.C_ANDROID_DOWNLOAD_ID, androidDownloadId)
        values.put(DownloadQueueItemConst.C_TYPE, type.ordinal.toLong())
        values.put(DownloadQueueItemConst.C_PROCESSING_DOWNLOADED_ITEM, if (processingDownloadedItem) 1L else 0L)
        values.put(DownloadQueueItemConst.C_TITLE, title)
        values.put(DownloadQueueItemConst.C_CONTENT_ITEM_ID, contentItemId)
        values.put(DownloadQueueItemConst.C_VERSION, version.toLong())
        values.put(DownloadQueueItemConst.C_SUB_ITEM_ID, subItemId)
        values.put(DownloadQueueItemConst.C_TERTIARY_ID, tertiaryId)
        values.put(DownloadQueueItemConst.C_SOURCE_URI, sourceUri)
        values.put(DownloadQueueItemConst.C_DESTINATION_URI, destinationUri)
        values.put(DownloadQueueItemConst.C_CATALOG_NAME, catalogName)
        values.put(DownloadQueueItemConst.C_CATALOG_ITEM_SOURCE_TYPE, catalogItemSourceType.ordinal.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            androidDownloadId,
            type.ordinal.toLong(),
            if (processingDownloadedItem) 1L else 0L,
            title,
            contentItemId,
            version.toLong(),
            subItemId,
            tertiaryId,
            sourceUri,
            destinationUri,
            catalogName,
            catalogItemSourceType.ordinal.toLong())
    }

    open fun copy() : DownloadQueueItem {
        val copy = DownloadQueueItem()
        copy.id = id
        copy.androidDownloadId = androidDownloadId
        copy.type = type
        copy.processingDownloadedItem = processingDownloadedItem
        copy.title = title
        copy.contentItemId = contentItemId
        copy.version = version
        copy.subItemId = subItemId
        copy.tertiaryId = tertiaryId
        copy.sourceUri = sourceUri
        copy.destinationUri = destinationUri
        copy.catalogName = catalogName
        copy.catalogItemSourceType = catalogItemSourceType
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, androidDownloadId)
        statement.bindLong(2, type.ordinal.toLong())
        statement.bindLong(3, if (processingDownloadedItem) 1L else 0L)
        statement.bindString(4, title)
        statement.bindLong(5, contentItemId)
        statement.bindLong(6, version.toLong())
        statement.bindLong(7, subItemId)
        if (tertiaryId != null) {
            statement.bindString(8, tertiaryId!!)
        } else {
            statement.bindNull(8)
        }
        statement.bindString(9, sourceUri)
        statement.bindString(10, destinationUri)
        statement.bindString(11, catalogName)
        statement.bindLong(12, catalogItemSourceType.ordinal.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, androidDownloadId)
        statement.bindLong(2, type.ordinal.toLong())
        statement.bindLong(3, if (processingDownloadedItem) 1L else 0L)
        statement.bindString(4, title)
        statement.bindLong(5, contentItemId)
        statement.bindLong(6, version.toLong())
        statement.bindLong(7, subItemId)
        if (tertiaryId != null) {
            statement.bindString(8, tertiaryId!!)
        } else {
            statement.bindNull(8)
        }
        statement.bindString(9, sourceUri)
        statement.bindString(10, destinationUri)
        statement.bindString(11, catalogName)
        statement.bindLong(12, catalogItemSourceType.ordinal.toLong())
        statement.bindLong(13, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        androidDownloadId = values.getAsLong(DownloadQueueItemConst.C_ANDROID_DOWNLOAD_ID)
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.ItemMediaType::class.java, values.getAsInteger(DownloadQueueItemConst.C_TYPE), org.lds.ldssa.model.database.types.ItemMediaType.UNKNOWN)
        processingDownloadedItem = values.getAsBoolean(DownloadQueueItemConst.C_PROCESSING_DOWNLOADED_ITEM)
        title = values.getAsString(DownloadQueueItemConst.C_TITLE)
        contentItemId = values.getAsLong(DownloadQueueItemConst.C_CONTENT_ITEM_ID)
        version = values.getAsInteger(DownloadQueueItemConst.C_VERSION)
        subItemId = values.getAsLong(DownloadQueueItemConst.C_SUB_ITEM_ID)
        tertiaryId = values.getAsString(DownloadQueueItemConst.C_TERTIARY_ID)
        sourceUri = values.getAsString(DownloadQueueItemConst.C_SOURCE_URI)
        destinationUri = values.getAsString(DownloadQueueItemConst.C_DESTINATION_URI)
        catalogName = values.getAsString(DownloadQueueItemConst.C_CATALOG_NAME)
        catalogItemSourceType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.CatalogItemSourceType::class.java, values.getAsInteger(DownloadQueueItemConst.C_CATALOG_ITEM_SOURCE_TYPE), org.lds.ldssa.model.database.types.CatalogItemSourceType.DEFAULT)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadQueueItemConst.C_ID))
        androidDownloadId = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadQueueItemConst.C_ANDROID_DOWNLOAD_ID))
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.ItemMediaType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(DownloadQueueItemConst.C_TYPE)), org.lds.ldssa.model.database.types.ItemMediaType.UNKNOWN)
        processingDownloadedItem = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadQueueItemConst.C_PROCESSING_DOWNLOADED_ITEM)) != 0
        title = cursor.getString(cursor.getColumnIndexOrThrow(DownloadQueueItemConst.C_TITLE))
        contentItemId = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadQueueItemConst.C_CONTENT_ITEM_ID))
        version = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadQueueItemConst.C_VERSION))
        subItemId = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadQueueItemConst.C_SUB_ITEM_ID))
        tertiaryId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(DownloadQueueItemConst.C_TERTIARY_ID))) cursor.getString(cursor.getColumnIndexOrThrow(DownloadQueueItemConst.C_TERTIARY_ID)) else null
        sourceUri = cursor.getString(cursor.getColumnIndexOrThrow(DownloadQueueItemConst.C_SOURCE_URI))
        destinationUri = cursor.getString(cursor.getColumnIndexOrThrow(DownloadQueueItemConst.C_DESTINATION_URI))
        catalogName = cursor.getString(cursor.getColumnIndexOrThrow(DownloadQueueItemConst.C_CATALOG_NAME))
        catalogItemSourceType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.CatalogItemSourceType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(DownloadQueueItemConst.C_CATALOG_ITEM_SOURCE_TYPE)), org.lds.ldssa.model.database.types.CatalogItemSourceType.DEFAULT)
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}