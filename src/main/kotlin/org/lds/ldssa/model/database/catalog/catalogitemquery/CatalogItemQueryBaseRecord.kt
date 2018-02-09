/*
 * CatalogItemQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.catalogitemquery

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class CatalogItemQueryBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var type: org.lds.ldssa.model.database.types.CatalogItemQueryType = org.lds.ldssa.model.database.types.CatalogItemQueryType.UNKNOWN
    open var parentId: Long = 0
    open var languageId: Long = 0
    open var title: String = ""
    open var uri: String? = null
    open var librarySectionId: Long = 0
    open var sectionId: Long = 0
    open var sectionTitle: String? = null
    open var sectionPosition: Int = 0
    open var collectionType: org.lds.ldssa.model.database.types.LibraryCollectionType = org.lds.ldssa.model.database.types.LibraryCollectionType.UNKNOWN
    open var externalId: String? = null
    open var itemCoverRenditions: String? = null
    open var itemPosition: Int = 0
    open var installed: Boolean = false

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
        return CatalogItemQueryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return CatalogItemQueryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(CatalogItemQueryConst.C_ID, id)
        values.put(CatalogItemQueryConst.C_TYPE, type.ordinal.toLong())
        values.put(CatalogItemQueryConst.C_PARENT_ID, parentId)
        values.put(CatalogItemQueryConst.C_LANGUAGE_ID, languageId)
        values.put(CatalogItemQueryConst.C_TITLE, title)
        values.put(CatalogItemQueryConst.C_URI, uri)
        values.put(CatalogItemQueryConst.C_LIBRARY_SECTION_ID, librarySectionId)
        values.put(CatalogItemQueryConst.C_SECTION_ID, sectionId)
        values.put(CatalogItemQueryConst.C_SECTION_TITLE, sectionTitle)
        values.put(CatalogItemQueryConst.C_SECTION_POSITION, sectionPosition.toLong())
        values.put(CatalogItemQueryConst.C_COLLECTION_TYPE, collectionType.ordinal.toLong())
        values.put(CatalogItemQueryConst.C_EXTERNAL_ID, externalId)
        values.put(CatalogItemQueryConst.C_ITEM_COVER_RENDITIONS, itemCoverRenditions)
        values.put(CatalogItemQueryConst.C_ITEM_POSITION, itemPosition.toLong())
        values.put(CatalogItemQueryConst.C_INSTALLED, if (installed) 1L else 0L)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            type.ordinal.toLong(),
            parentId,
            languageId,
            title,
            uri,
            librarySectionId,
            sectionId,
            sectionTitle,
            sectionPosition.toLong(),
            collectionType.ordinal.toLong(),
            externalId,
            itemCoverRenditions,
            itemPosition.toLong(),
            if (installed) 1L else 0L)
    }

    open fun copy() : CatalogItemQuery {
        val copy = CatalogItemQuery()
        copy.id = id
        copy.type = type
        copy.parentId = parentId
        copy.languageId = languageId
        copy.title = title
        copy.uri = uri
        copy.librarySectionId = librarySectionId
        copy.sectionId = sectionId
        copy.sectionTitle = sectionTitle
        copy.sectionPosition = sectionPosition
        copy.collectionType = collectionType
        copy.externalId = externalId
        copy.itemCoverRenditions = itemCoverRenditions
        copy.itemPosition = itemPosition
        copy.installed = installed
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, type.ordinal.toLong())
        statement.bindLong(3, parentId)
        statement.bindLong(4, languageId)
        statement.bindString(5, title)
        if (uri != null) {
            statement.bindString(6, uri!!)
        } else {
            statement.bindNull(6)
        }
        statement.bindLong(7, librarySectionId)
        statement.bindLong(8, sectionId)
        if (sectionTitle != null) {
            statement.bindString(9, sectionTitle!!)
        } else {
            statement.bindNull(9)
        }
        statement.bindLong(10, sectionPosition.toLong())
        statement.bindLong(11, collectionType.ordinal.toLong())
        if (externalId != null) {
            statement.bindString(12, externalId!!)
        } else {
            statement.bindNull(12)
        }
        if (itemCoverRenditions != null) {
            statement.bindString(13, itemCoverRenditions!!)
        } else {
            statement.bindNull(13)
        }
        statement.bindLong(14, itemPosition.toLong())
        statement.bindLong(15, if (installed) 1L else 0L)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, type.ordinal.toLong())
        statement.bindLong(3, parentId)
        statement.bindLong(4, languageId)
        statement.bindString(5, title)
        if (uri != null) {
            statement.bindString(6, uri!!)
        } else {
            statement.bindNull(6)
        }
        statement.bindLong(7, librarySectionId)
        statement.bindLong(8, sectionId)
        if (sectionTitle != null) {
            statement.bindString(9, sectionTitle!!)
        } else {
            statement.bindNull(9)
        }
        statement.bindLong(10, sectionPosition.toLong())
        statement.bindLong(11, collectionType.ordinal.toLong())
        if (externalId != null) {
            statement.bindString(12, externalId!!)
        } else {
            statement.bindNull(12)
        }
        if (itemCoverRenditions != null) {
            statement.bindString(13, itemCoverRenditions!!)
        } else {
            statement.bindNull(13)
        }
        statement.bindLong(14, itemPosition.toLong())
        statement.bindLong(15, if (installed) 1L else 0L)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        id = values.getAsLong(CatalogItemQueryConst.C_ID)
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.CatalogItemQueryType::class.java, values.getAsInteger(CatalogItemQueryConst.C_TYPE), org.lds.ldssa.model.database.types.CatalogItemQueryType.UNKNOWN)
        parentId = values.getAsLong(CatalogItemQueryConst.C_PARENT_ID)
        languageId = values.getAsLong(CatalogItemQueryConst.C_LANGUAGE_ID)
        title = values.getAsString(CatalogItemQueryConst.C_TITLE)
        uri = values.getAsString(CatalogItemQueryConst.C_URI)
        librarySectionId = values.getAsLong(CatalogItemQueryConst.C_LIBRARY_SECTION_ID)
        sectionId = values.getAsLong(CatalogItemQueryConst.C_SECTION_ID)
        sectionTitle = values.getAsString(CatalogItemQueryConst.C_SECTION_TITLE)
        sectionPosition = values.getAsInteger(CatalogItemQueryConst.C_SECTION_POSITION)
        collectionType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.LibraryCollectionType::class.java, values.getAsInteger(CatalogItemQueryConst.C_COLLECTION_TYPE), org.lds.ldssa.model.database.types.LibraryCollectionType.UNKNOWN)
        externalId = values.getAsString(CatalogItemQueryConst.C_EXTERNAL_ID)
        itemCoverRenditions = values.getAsString(CatalogItemQueryConst.C_ITEM_COVER_RENDITIONS)
        itemPosition = values.getAsInteger(CatalogItemQueryConst.C_ITEM_POSITION)
        installed = values.getAsBoolean(CatalogItemQueryConst.C_INSTALLED)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_ID))
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.CatalogItemQueryType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_TYPE)), org.lds.ldssa.model.database.types.CatalogItemQueryType.UNKNOWN)
        parentId = cursor.getLong(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_PARENT_ID))
        languageId = cursor.getLong(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_LANGUAGE_ID))
        title = cursor.getString(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_TITLE))
        uri = if (!cursor.isNull(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_URI))) cursor.getString(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_URI)) else null
        librarySectionId = cursor.getLong(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_LIBRARY_SECTION_ID))
        sectionId = cursor.getLong(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_SECTION_ID))
        sectionTitle = if (!cursor.isNull(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_SECTION_TITLE))) cursor.getString(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_SECTION_TITLE)) else null
        sectionPosition = cursor.getInt(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_SECTION_POSITION))
        collectionType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.LibraryCollectionType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_COLLECTION_TYPE)), org.lds.ldssa.model.database.types.LibraryCollectionType.UNKNOWN)
        externalId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_EXTERNAL_ID))) cursor.getString(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_EXTERNAL_ID)) else null
        itemCoverRenditions = if (!cursor.isNull(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_ITEM_COVER_RENDITIONS))) cursor.getString(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_ITEM_COVER_RENDITIONS)) else null
        itemPosition = cursor.getInt(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_ITEM_POSITION))
        installed = cursor.getInt(cursor.getColumnIndexOrThrow(CatalogItemQueryConst.C_INSTALLED)) != 0
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}