/*
 * DownloadedItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.downloadeditem

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class DownloadedItemBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var contentItemId: Long = 0
    open var catalogItemSourceType: org.lds.ldssa.model.database.types.CatalogItemSourceType = org.lds.ldssa.model.database.types.CatalogItemSourceType.DEFAULT
    open var externalId: String? = null
    open var installedVersion: Long = 0
    open var librarySectionId: Long = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return DownloadedItemConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return DownloadedItemConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return DownloadedItemConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(DownloadedItemConst.C_CONTENT_ITEM_ID, contentItemId)
        values.put(DownloadedItemConst.C_CATALOG_ITEM_SOURCE_TYPE, catalogItemSourceType.ordinal.toLong())
        values.put(DownloadedItemConst.C_EXTERNAL_ID, externalId)
        values.put(DownloadedItemConst.C_INSTALLED_VERSION, installedVersion)
        values.put(DownloadedItemConst.C_LIBRARY_SECTION_ID, librarySectionId)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            contentItemId,
            catalogItemSourceType.ordinal.toLong(),
            externalId,
            installedVersion,
            librarySectionId)
    }

    open fun copy() : DownloadedItem {
        val copy = DownloadedItem()
        copy.id = id
        copy.contentItemId = contentItemId
        copy.catalogItemSourceType = catalogItemSourceType
        copy.externalId = externalId
        copy.installedVersion = installedVersion
        copy.librarySectionId = librarySectionId
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, contentItemId)
        statement.bindLong(2, catalogItemSourceType.ordinal.toLong())
        if (externalId != null) {
            statement.bindString(3, externalId!!)
        } else {
            statement.bindNull(3)
        }
        statement.bindLong(4, installedVersion)
        statement.bindLong(5, librarySectionId)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, contentItemId)
        statement.bindLong(2, catalogItemSourceType.ordinal.toLong())
        if (externalId != null) {
            statement.bindString(3, externalId!!)
        } else {
            statement.bindNull(3)
        }
        statement.bindLong(4, installedVersion)
        statement.bindLong(5, librarySectionId)
        statement.bindLong(6, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        contentItemId = values.getAsLong(DownloadedItemConst.C_CONTENT_ITEM_ID)
        catalogItemSourceType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.CatalogItemSourceType::class.java, values.getAsInteger(DownloadedItemConst.C_CATALOG_ITEM_SOURCE_TYPE), org.lds.ldssa.model.database.types.CatalogItemSourceType.DEFAULT)
        externalId = values.getAsString(DownloadedItemConst.C_EXTERNAL_ID)
        installedVersion = values.getAsLong(DownloadedItemConst.C_INSTALLED_VERSION)
        librarySectionId = values.getAsLong(DownloadedItemConst.C_LIBRARY_SECTION_ID)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadedItemConst.C_ID))
        contentItemId = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadedItemConst.C_CONTENT_ITEM_ID))
        catalogItemSourceType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.CatalogItemSourceType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(DownloadedItemConst.C_CATALOG_ITEM_SOURCE_TYPE)), org.lds.ldssa.model.database.types.CatalogItemSourceType.DEFAULT)
        externalId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(DownloadedItemConst.C_EXTERNAL_ID))) cursor.getString(cursor.getColumnIndexOrThrow(DownloadedItemConst.C_EXTERNAL_ID)) else null
        installedVersion = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadedItemConst.C_INSTALLED_VERSION))
        librarySectionId = cursor.getLong(cursor.getColumnIndexOrThrow(DownloadedItemConst.C_LIBRARY_SECTION_ID))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}