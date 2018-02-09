/*
 * LibraryCollectionQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.librarycollectionquery

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class LibraryCollectionQueryBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var title: String = ""
    open var root: Boolean = false
    open var parentTitle: String = ""

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
        return LibraryCollectionQueryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return LibraryCollectionQueryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(LibraryCollectionQueryConst.C_ID, id)
        values.put(LibraryCollectionQueryConst.C_TITLE, title)
        values.put(LibraryCollectionQueryConst.C_ROOT, if (root) 1L else 0L)
        values.put(LibraryCollectionQueryConst.C_PARENT_TITLE, parentTitle)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            title,
            if (root) 1L else 0L,
            parentTitle)
    }

    open fun copy() : LibraryCollectionQuery {
        val copy = LibraryCollectionQuery()
        copy.id = id
        copy.title = title
        copy.root = root
        copy.parentTitle = parentTitle
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindString(2, title)
        statement.bindLong(3, if (root) 1L else 0L)
        statement.bindString(4, parentTitle)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindString(2, title)
        statement.bindLong(3, if (root) 1L else 0L)
        statement.bindString(4, parentTitle)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        id = values.getAsLong(LibraryCollectionQueryConst.C_ID)
        title = values.getAsString(LibraryCollectionQueryConst.C_TITLE)
        root = values.getAsBoolean(LibraryCollectionQueryConst.C_ROOT)
        parentTitle = values.getAsString(LibraryCollectionQueryConst.C_PARENT_TITLE)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(LibraryCollectionQueryConst.C_ID))
        title = cursor.getString(cursor.getColumnIndexOrThrow(LibraryCollectionQueryConst.C_TITLE))
        root = cursor.getInt(cursor.getColumnIndexOrThrow(LibraryCollectionQueryConst.C_ROOT)) != 0
        parentTitle = cursor.getString(cursor.getColumnIndexOrThrow(LibraryCollectionQueryConst.C_PARENT_TITLE))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}