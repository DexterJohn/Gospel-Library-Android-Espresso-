/*
 * ItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.item

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class ItemBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var externalId: String = ""
    open var languageId: Long = 0
    open var sourceId: Long = 0
    open var platform: org.lds.ldssa.model.database.types.PlatformType = org.lds.ldssa.model.database.types.PlatformType.ALL
    open var uri: String = ""
    open var title: String = ""
    open var itemCoverRenditions: String? = null
    open var itemCategoryId: Long = 0
    open var version: Int = 0
    open var obsolete: Boolean = false

    constructor() {
    }

    override fun getIdColumnName() : String {
        return ItemConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return ItemConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return ItemConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(ItemConst.C_EXTERNAL_ID, externalId)
        values.put(ItemConst.C_LANGUAGE_ID, languageId)
        values.put(ItemConst.C_SOURCE_ID, sourceId)
        values.put(ItemConst.C_PLATFORM, platform.ordinal.toLong())
        values.put(ItemConst.C_URI, uri)
        values.put(ItemConst.C_TITLE, title)
        values.put(ItemConst.C_ITEM_COVER_RENDITIONS, itemCoverRenditions)
        values.put(ItemConst.C_ITEM_CATEGORY_ID, itemCategoryId)
        values.put(ItemConst.C_VERSION, version.toLong())
        values.put(ItemConst.C_OBSOLETE, if (obsolete) 1L else 0L)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            externalId,
            languageId,
            sourceId,
            platform.ordinal.toLong(),
            uri,
            title,
            itemCoverRenditions,
            itemCategoryId,
            version.toLong(),
            if (obsolete) 1L else 0L)
    }

    open fun copy() : Item {
        val copy = Item()
        copy.id = id
        copy.externalId = externalId
        copy.languageId = languageId
        copy.sourceId = sourceId
        copy.platform = platform
        copy.uri = uri
        copy.title = title
        copy.itemCoverRenditions = itemCoverRenditions
        copy.itemCategoryId = itemCategoryId
        copy.version = version
        copy.obsolete = obsolete
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, externalId)
        statement.bindLong(2, languageId)
        statement.bindLong(3, sourceId)
        statement.bindLong(4, platform.ordinal.toLong())
        statement.bindString(5, uri)
        statement.bindString(6, title)
        if (itemCoverRenditions != null) {
            statement.bindString(7, itemCoverRenditions!!)
        } else {
            statement.bindNull(7)
        }
        statement.bindLong(8, itemCategoryId)
        statement.bindLong(9, version.toLong())
        statement.bindLong(10, if (obsolete) 1L else 0L)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, externalId)
        statement.bindLong(2, languageId)
        statement.bindLong(3, sourceId)
        statement.bindLong(4, platform.ordinal.toLong())
        statement.bindString(5, uri)
        statement.bindString(6, title)
        if (itemCoverRenditions != null) {
            statement.bindString(7, itemCoverRenditions!!)
        } else {
            statement.bindNull(7)
        }
        statement.bindLong(8, itemCategoryId)
        statement.bindLong(9, version.toLong())
        statement.bindLong(10, if (obsolete) 1L else 0L)
        statement.bindLong(11, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        externalId = values.getAsString(ItemConst.C_EXTERNAL_ID)
        languageId = values.getAsLong(ItemConst.C_LANGUAGE_ID)
        sourceId = values.getAsLong(ItemConst.C_SOURCE_ID)
        platform = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.PlatformType::class.java, values.getAsInteger(ItemConst.C_PLATFORM), org.lds.ldssa.model.database.types.PlatformType.ALL)
        uri = values.getAsString(ItemConst.C_URI)
        title = values.getAsString(ItemConst.C_TITLE)
        itemCoverRenditions = values.getAsString(ItemConst.C_ITEM_COVER_RENDITIONS)
        itemCategoryId = values.getAsLong(ItemConst.C_ITEM_CATEGORY_ID)
        version = values.getAsInteger(ItemConst.C_VERSION)
        obsolete = values.getAsBoolean(ItemConst.C_OBSOLETE)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(ItemConst.C_ID))
        externalId = cursor.getString(cursor.getColumnIndexOrThrow(ItemConst.C_EXTERNAL_ID))
        languageId = cursor.getLong(cursor.getColumnIndexOrThrow(ItemConst.C_LANGUAGE_ID))
        sourceId = cursor.getLong(cursor.getColumnIndexOrThrow(ItemConst.C_SOURCE_ID))
        platform = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.PlatformType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(ItemConst.C_PLATFORM)), org.lds.ldssa.model.database.types.PlatformType.ALL)
        uri = cursor.getString(cursor.getColumnIndexOrThrow(ItemConst.C_URI))
        title = cursor.getString(cursor.getColumnIndexOrThrow(ItemConst.C_TITLE))
        itemCoverRenditions = if (!cursor.isNull(cursor.getColumnIndexOrThrow(ItemConst.C_ITEM_COVER_RENDITIONS))) cursor.getString(cursor.getColumnIndexOrThrow(ItemConst.C_ITEM_COVER_RENDITIONS)) else null
        itemCategoryId = cursor.getLong(cursor.getColumnIndexOrThrow(ItemConst.C_ITEM_CATEGORY_ID))
        version = cursor.getInt(cursor.getColumnIndexOrThrow(ItemConst.C_VERSION))
        obsolete = cursor.getInt(cursor.getColumnIndexOrThrow(ItemConst.C_OBSOLETE)) != 0
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}