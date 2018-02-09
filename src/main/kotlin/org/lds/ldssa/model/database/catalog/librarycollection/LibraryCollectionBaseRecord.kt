/*
 * LibraryCollectionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.librarycollection

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class LibraryCollectionBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var externalId: String = ""
    open var librarySectionId: Long? = null
    open var librarySectionExternalId: String? = null
    open var position: Int = 0
    open var titleHtml: String = ""
    open var coverRenditions: String? = null
    open var type: org.lds.ldssa.model.database.types.LibraryCollectionType = org.lds.ldssa.model.database.types.LibraryCollectionType.DEFAULT

    constructor() {
    }

    override fun getIdColumnName() : String {
        return LibraryCollectionConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return LibraryCollectionConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return LibraryCollectionConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(LibraryCollectionConst.C_EXTERNAL_ID, externalId)
        values.put(LibraryCollectionConst.C_LIBRARY_SECTION_ID, librarySectionId)
        values.put(LibraryCollectionConst.C_LIBRARY_SECTION_EXTERNAL_ID, librarySectionExternalId)
        values.put(LibraryCollectionConst.C_POSITION, position.toLong())
        values.put(LibraryCollectionConst.C_TITLE_HTML, titleHtml)
        values.put(LibraryCollectionConst.C_COVER_RENDITIONS, coverRenditions)
        values.put(LibraryCollectionConst.C_TYPE, type.ordinal.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            externalId,
            librarySectionId,
            librarySectionExternalId,
            position.toLong(),
            titleHtml,
            coverRenditions,
            type.ordinal.toLong())
    }

    open fun copy() : LibraryCollection {
        val copy = LibraryCollection()
        copy.id = id
        copy.externalId = externalId
        copy.librarySectionId = librarySectionId
        copy.librarySectionExternalId = librarySectionExternalId
        copy.position = position
        copy.titleHtml = titleHtml
        copy.coverRenditions = coverRenditions
        copy.type = type
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, externalId)
        if (librarySectionId != null) {
            statement.bindLong(2, librarySectionId!!)
        } else {
            statement.bindNull(2)
        }
        if (librarySectionExternalId != null) {
            statement.bindString(3, librarySectionExternalId!!)
        } else {
            statement.bindNull(3)
        }
        statement.bindLong(4, position.toLong())
        statement.bindString(5, titleHtml)
        if (coverRenditions != null) {
            statement.bindString(6, coverRenditions!!)
        } else {
            statement.bindNull(6)
        }
        statement.bindLong(7, type.ordinal.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, externalId)
        if (librarySectionId != null) {
            statement.bindLong(2, librarySectionId!!)
        } else {
            statement.bindNull(2)
        }
        if (librarySectionExternalId != null) {
            statement.bindString(3, librarySectionExternalId!!)
        } else {
            statement.bindNull(3)
        }
        statement.bindLong(4, position.toLong())
        statement.bindString(5, titleHtml)
        if (coverRenditions != null) {
            statement.bindString(6, coverRenditions!!)
        } else {
            statement.bindNull(6)
        }
        statement.bindLong(7, type.ordinal.toLong())
        statement.bindLong(8, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        externalId = values.getAsString(LibraryCollectionConst.C_EXTERNAL_ID)
        librarySectionId = values.getAsLong(LibraryCollectionConst.C_LIBRARY_SECTION_ID)
        librarySectionExternalId = values.getAsString(LibraryCollectionConst.C_LIBRARY_SECTION_EXTERNAL_ID)
        position = values.getAsInteger(LibraryCollectionConst.C_POSITION)
        titleHtml = values.getAsString(LibraryCollectionConst.C_TITLE_HTML)
        coverRenditions = values.getAsString(LibraryCollectionConst.C_COVER_RENDITIONS)
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.LibraryCollectionType::class.java, values.getAsInteger(LibraryCollectionConst.C_TYPE), org.lds.ldssa.model.database.types.LibraryCollectionType.DEFAULT)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(LibraryCollectionConst.C_ID))
        externalId = cursor.getString(cursor.getColumnIndexOrThrow(LibraryCollectionConst.C_EXTERNAL_ID))
        librarySectionId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(LibraryCollectionConst.C_LIBRARY_SECTION_ID))) cursor.getLong(cursor.getColumnIndexOrThrow(LibraryCollectionConst.C_LIBRARY_SECTION_ID)) else null
        librarySectionExternalId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(LibraryCollectionConst.C_LIBRARY_SECTION_EXTERNAL_ID))) cursor.getString(cursor.getColumnIndexOrThrow(LibraryCollectionConst.C_LIBRARY_SECTION_EXTERNAL_ID)) else null
        position = cursor.getInt(cursor.getColumnIndexOrThrow(LibraryCollectionConst.C_POSITION))
        titleHtml = cursor.getString(cursor.getColumnIndexOrThrow(LibraryCollectionConst.C_TITLE_HTML))
        coverRenditions = if (!cursor.isNull(cursor.getColumnIndexOrThrow(LibraryCollectionConst.C_COVER_RENDITIONS))) cursor.getString(cursor.getColumnIndexOrThrow(LibraryCollectionConst.C_COVER_RENDITIONS)) else null
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.LibraryCollectionType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(LibraryCollectionConst.C_TYPE)), org.lds.ldssa.model.database.types.LibraryCollectionType.DEFAULT)
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}