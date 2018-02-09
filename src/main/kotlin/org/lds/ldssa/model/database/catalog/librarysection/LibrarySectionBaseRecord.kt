/*
 * LibrarySectionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.librarysection

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class LibrarySectionBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var externalId: String = ""
    open var libraryCollectionId: Long = 0
    open var librarySectionExternalId: String = ""
    open var position: Int = 0
    open var title: String? = null
    open var indexTitle: String? = null

    constructor() {
    }

    override fun getIdColumnName() : String {
        return LibrarySectionConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return LibrarySectionConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return LibrarySectionConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(LibrarySectionConst.C_EXTERNAL_ID, externalId)
        values.put(LibrarySectionConst.C_LIBRARY_COLLECTION_ID, libraryCollectionId)
        values.put(LibrarySectionConst.C_LIBRARY_SECTION_EXTERNAL_ID, librarySectionExternalId)
        values.put(LibrarySectionConst.C_POSITION, position.toLong())
        values.put(LibrarySectionConst.C_TITLE, title)
        values.put(LibrarySectionConst.C_INDEX_TITLE, indexTitle)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            externalId,
            libraryCollectionId,
            librarySectionExternalId,
            position.toLong(),
            title,
            indexTitle)
    }

    open fun copy() : LibrarySection {
        val copy = LibrarySection()
        copy.id = id
        copy.externalId = externalId
        copy.libraryCollectionId = libraryCollectionId
        copy.librarySectionExternalId = librarySectionExternalId
        copy.position = position
        copy.title = title
        copy.indexTitle = indexTitle
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, externalId)
        statement.bindLong(2, libraryCollectionId)
        statement.bindString(3, librarySectionExternalId)
        statement.bindLong(4, position.toLong())
        if (title != null) {
            statement.bindString(5, title!!)
        } else {
            statement.bindNull(5)
        }
        if (indexTitle != null) {
            statement.bindString(6, indexTitle!!)
        } else {
            statement.bindNull(6)
        }
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, externalId)
        statement.bindLong(2, libraryCollectionId)
        statement.bindString(3, librarySectionExternalId)
        statement.bindLong(4, position.toLong())
        if (title != null) {
            statement.bindString(5, title!!)
        } else {
            statement.bindNull(5)
        }
        if (indexTitle != null) {
            statement.bindString(6, indexTitle!!)
        } else {
            statement.bindNull(6)
        }
        statement.bindLong(7, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        externalId = values.getAsString(LibrarySectionConst.C_EXTERNAL_ID)
        libraryCollectionId = values.getAsLong(LibrarySectionConst.C_LIBRARY_COLLECTION_ID)
        librarySectionExternalId = values.getAsString(LibrarySectionConst.C_LIBRARY_SECTION_EXTERNAL_ID)
        position = values.getAsInteger(LibrarySectionConst.C_POSITION)
        title = values.getAsString(LibrarySectionConst.C_TITLE)
        indexTitle = values.getAsString(LibrarySectionConst.C_INDEX_TITLE)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(LibrarySectionConst.C_ID))
        externalId = cursor.getString(cursor.getColumnIndexOrThrow(LibrarySectionConst.C_EXTERNAL_ID))
        libraryCollectionId = cursor.getLong(cursor.getColumnIndexOrThrow(LibrarySectionConst.C_LIBRARY_COLLECTION_ID))
        librarySectionExternalId = cursor.getString(cursor.getColumnIndexOrThrow(LibrarySectionConst.C_LIBRARY_SECTION_EXTERNAL_ID))
        position = cursor.getInt(cursor.getColumnIndexOrThrow(LibrarySectionConst.C_POSITION))
        title = if (!cursor.isNull(cursor.getColumnIndexOrThrow(LibrarySectionConst.C_TITLE))) cursor.getString(cursor.getColumnIndexOrThrow(LibrarySectionConst.C_TITLE)) else null
        indexTitle = if (!cursor.isNull(cursor.getColumnIndexOrThrow(LibrarySectionConst.C_INDEX_TITLE))) cursor.getString(cursor.getColumnIndexOrThrow(LibrarySectionConst.C_INDEX_TITLE)) else null
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}