/*
 * NavCollectionIndexEntryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.navcollectionindexentry

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class NavCollectionIndexEntryBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var navCollectionId: Long = 0
    open var position: Int = 0
    open var title: String = ""
    open var listIndex: Long = 0
    open var section: Int = 0
    open var row: Int = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return NavCollectionIndexEntryConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return NavCollectionIndexEntryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return NavCollectionIndexEntryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(NavCollectionIndexEntryConst.C_NAV_COLLECTION_ID, navCollectionId)
        values.put(NavCollectionIndexEntryConst.C_POSITION, position.toLong())
        values.put(NavCollectionIndexEntryConst.C_TITLE, title)
        values.put(NavCollectionIndexEntryConst.C_LIST_INDEX, listIndex)
        values.put(NavCollectionIndexEntryConst.C_SECTION, section.toLong())
        values.put(NavCollectionIndexEntryConst.C_ROW, row.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            navCollectionId,
            position.toLong(),
            title,
            listIndex,
            section.toLong(),
            row.toLong())
    }

    open fun copy() : NavCollectionIndexEntry {
        val copy = NavCollectionIndexEntry()
        copy.id = id
        copy.navCollectionId = navCollectionId
        copy.position = position
        copy.title = title
        copy.listIndex = listIndex
        copy.section = section
        copy.row = row
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, navCollectionId)
        statement.bindLong(2, position.toLong())
        statement.bindString(3, title)
        statement.bindLong(4, listIndex)
        statement.bindLong(5, section.toLong())
        statement.bindLong(6, row.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, navCollectionId)
        statement.bindLong(2, position.toLong())
        statement.bindString(3, title)
        statement.bindLong(4, listIndex)
        statement.bindLong(5, section.toLong())
        statement.bindLong(6, row.toLong())
        statement.bindLong(7, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        navCollectionId = values.getAsLong(NavCollectionIndexEntryConst.C_NAV_COLLECTION_ID)
        position = values.getAsInteger(NavCollectionIndexEntryConst.C_POSITION)
        title = values.getAsString(NavCollectionIndexEntryConst.C_TITLE)
        listIndex = values.getAsLong(NavCollectionIndexEntryConst.C_LIST_INDEX)
        section = values.getAsInteger(NavCollectionIndexEntryConst.C_SECTION)
        row = values.getAsInteger(NavCollectionIndexEntryConst.C_ROW)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(NavCollectionIndexEntryConst.C_ID))
        navCollectionId = cursor.getLong(cursor.getColumnIndexOrThrow(NavCollectionIndexEntryConst.C_NAV_COLLECTION_ID))
        position = cursor.getInt(cursor.getColumnIndexOrThrow(NavCollectionIndexEntryConst.C_POSITION))
        title = cursor.getString(cursor.getColumnIndexOrThrow(NavCollectionIndexEntryConst.C_TITLE))
        listIndex = cursor.getLong(cursor.getColumnIndexOrThrow(NavCollectionIndexEntryConst.C_LIST_INDEX))
        section = cursor.getInt(cursor.getColumnIndexOrThrow(NavCollectionIndexEntryConst.C_SECTION))
        row = cursor.getInt(cursor.getColumnIndexOrThrow(NavCollectionIndexEntryConst.C_ROW))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}