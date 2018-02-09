/*
 * LibraryItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.libraryitem

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class LibraryItemBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var externalId: String = ""
    open var librarySectionId: Long? = null
    open var librarySectionExternalId: String? = null
    open var position: Int = 0
    open var titleHtml: String = ""
    open var obsolete: Boolean = false
    open var itemId: Long = 0
    open var itemExternalId: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return LibraryItemConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return LibraryItemConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return LibraryItemConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(LibraryItemConst.C_EXTERNAL_ID, externalId)
        values.put(LibraryItemConst.C_LIBRARY_SECTION_ID, librarySectionId)
        values.put(LibraryItemConst.C_LIBRARY_SECTION_EXTERNAL_ID, librarySectionExternalId)
        values.put(LibraryItemConst.C_POSITION, position.toLong())
        values.put(LibraryItemConst.C_TITLE_HTML, titleHtml)
        values.put(LibraryItemConst.C_OBSOLETE, if (obsolete) 1L else 0L)
        values.put(LibraryItemConst.C_ITEM_ID, itemId)
        values.put(LibraryItemConst.C_ITEM_EXTERNAL_ID, itemExternalId)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            externalId,
            librarySectionId,
            librarySectionExternalId,
            position.toLong(),
            titleHtml,
            if (obsolete) 1L else 0L,
            itemId,
            itemExternalId)
    }

    open fun copy() : LibraryItem {
        val copy = LibraryItem()
        copy.id = id
        copy.externalId = externalId
        copy.librarySectionId = librarySectionId
        copy.librarySectionExternalId = librarySectionExternalId
        copy.position = position
        copy.titleHtml = titleHtml
        copy.obsolete = obsolete
        copy.itemId = itemId
        copy.itemExternalId = itemExternalId
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
        statement.bindLong(6, if (obsolete) 1L else 0L)
        statement.bindLong(7, itemId)
        statement.bindString(8, itemExternalId)
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
        statement.bindLong(6, if (obsolete) 1L else 0L)
        statement.bindLong(7, itemId)
        statement.bindString(8, itemExternalId)
        statement.bindLong(9, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        externalId = values.getAsString(LibraryItemConst.C_EXTERNAL_ID)
        librarySectionId = values.getAsLong(LibraryItemConst.C_LIBRARY_SECTION_ID)
        librarySectionExternalId = values.getAsString(LibraryItemConst.C_LIBRARY_SECTION_EXTERNAL_ID)
        position = values.getAsInteger(LibraryItemConst.C_POSITION)
        titleHtml = values.getAsString(LibraryItemConst.C_TITLE_HTML)
        obsolete = values.getAsBoolean(LibraryItemConst.C_OBSOLETE)
        itemId = values.getAsLong(LibraryItemConst.C_ITEM_ID)
        itemExternalId = values.getAsString(LibraryItemConst.C_ITEM_EXTERNAL_ID)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(LibraryItemConst.C_ID))
        externalId = cursor.getString(cursor.getColumnIndexOrThrow(LibraryItemConst.C_EXTERNAL_ID))
        librarySectionId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(LibraryItemConst.C_LIBRARY_SECTION_ID))) cursor.getLong(cursor.getColumnIndexOrThrow(LibraryItemConst.C_LIBRARY_SECTION_ID)) else null
        librarySectionExternalId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(LibraryItemConst.C_LIBRARY_SECTION_EXTERNAL_ID))) cursor.getString(cursor.getColumnIndexOrThrow(LibraryItemConst.C_LIBRARY_SECTION_EXTERNAL_ID)) else null
        position = cursor.getInt(cursor.getColumnIndexOrThrow(LibraryItemConst.C_POSITION))
        titleHtml = cursor.getString(cursor.getColumnIndexOrThrow(LibraryItemConst.C_TITLE_HTML))
        obsolete = cursor.getInt(cursor.getColumnIndexOrThrow(LibraryItemConst.C_OBSOLETE)) != 0
        itemId = cursor.getLong(cursor.getColumnIndexOrThrow(LibraryItemConst.C_ITEM_ID))
        itemExternalId = cursor.getString(cursor.getColumnIndexOrThrow(LibraryItemConst.C_ITEM_EXTERNAL_ID))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}