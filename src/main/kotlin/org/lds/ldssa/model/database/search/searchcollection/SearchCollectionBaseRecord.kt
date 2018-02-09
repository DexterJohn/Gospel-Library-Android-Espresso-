/*
 * SearchCollectionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchcollection

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SearchCollectionBaseRecord  : AndroidBaseRecord {

    open var screenId: Long = 0
    open var collectionId: Long = 0
    open var position: Int = 0
    open var title: String = ""
    open var parentCollectionIsRoot: Boolean = false
    open var parentCollectionId: Long = 0
    open var parentCollectionTitle: String = ""

    constructor() {
    }

    override fun getAllColumns() : Array<String> {
        return SearchCollectionConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SearchCollectionConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SearchCollectionConst.C_SCREEN_ID, screenId)
        values.put(SearchCollectionConst.C_COLLECTION_ID, collectionId)
        values.put(SearchCollectionConst.C_POSITION, position.toLong())
        values.put(SearchCollectionConst.C_TITLE, title)
        values.put(SearchCollectionConst.C_PARENT_COLLECTION_IS_ROOT, if (parentCollectionIsRoot) 1L else 0L)
        values.put(SearchCollectionConst.C_PARENT_COLLECTION_ID, parentCollectionId)
        values.put(SearchCollectionConst.C_PARENT_COLLECTION_TITLE, parentCollectionTitle)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            screenId,
            collectionId,
            position.toLong(),
            title,
            if (parentCollectionIsRoot) 1L else 0L,
            parentCollectionId,
            parentCollectionTitle)
    }

    open fun copy() : SearchCollection {
        val copy = SearchCollection()
        copy.screenId = screenId
        copy.collectionId = collectionId
        copy.position = position
        copy.title = title
        copy.parentCollectionIsRoot = parentCollectionIsRoot
        copy.parentCollectionId = parentCollectionId
        copy.parentCollectionTitle = parentCollectionTitle
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, screenId)
        statement.bindLong(2, collectionId)
        statement.bindLong(3, position.toLong())
        statement.bindString(4, title)
        statement.bindLong(5, if (parentCollectionIsRoot) 1L else 0L)
        statement.bindLong(6, parentCollectionId)
        statement.bindString(7, parentCollectionTitle)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, screenId)
        statement.bindLong(2, collectionId)
        statement.bindLong(3, position.toLong())
        statement.bindString(4, title)
        statement.bindLong(5, if (parentCollectionIsRoot) 1L else 0L)
        statement.bindLong(6, parentCollectionId)
        statement.bindString(7, parentCollectionTitle)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        screenId = values.getAsLong(SearchCollectionConst.C_SCREEN_ID)
        collectionId = values.getAsLong(SearchCollectionConst.C_COLLECTION_ID)
        position = values.getAsInteger(SearchCollectionConst.C_POSITION)
        title = values.getAsString(SearchCollectionConst.C_TITLE)
        parentCollectionIsRoot = values.getAsBoolean(SearchCollectionConst.C_PARENT_COLLECTION_IS_ROOT)
        parentCollectionId = values.getAsLong(SearchCollectionConst.C_PARENT_COLLECTION_ID)
        parentCollectionTitle = values.getAsString(SearchCollectionConst.C_PARENT_COLLECTION_TITLE)
    }

    override fun setContent(cursor: Cursor) {
        screenId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchCollectionConst.C_SCREEN_ID))
        collectionId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchCollectionConst.C_COLLECTION_ID))
        position = cursor.getInt(cursor.getColumnIndexOrThrow(SearchCollectionConst.C_POSITION))
        title = cursor.getString(cursor.getColumnIndexOrThrow(SearchCollectionConst.C_TITLE))
        parentCollectionIsRoot = cursor.getInt(cursor.getColumnIndexOrThrow(SearchCollectionConst.C_PARENT_COLLECTION_IS_ROOT)) != 0
        parentCollectionId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchCollectionConst.C_PARENT_COLLECTION_ID))
        parentCollectionTitle = cursor.getString(cursor.getColumnIndexOrThrow(SearchCollectionConst.C_PARENT_COLLECTION_TITLE))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }

    override fun getIdColumnName() : String {
        return "NO_PRIMARY_KEY"
    }

    override fun getPrimaryKeyId() : Long {
        return 0
    }

    override fun setPrimaryKeyId(id: Long) {
        // NO_PRIMARY_KEY
    }


}