/*
 * SearchContentCollectionMapBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchcontentcollectionmap

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SearchContentCollectionMapBaseRecord  : AndroidBaseRecord {

    open var screenId: Long = 0
    open var contentItemId: Long = 0
    open var collectionId: Long = 0

    constructor() {
    }

    override fun getAllColumns() : Array<String> {
        return SearchContentCollectionMapConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SearchContentCollectionMapConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SearchContentCollectionMapConst.C_SCREEN_ID, screenId)
        values.put(SearchContentCollectionMapConst.C_CONTENT_ITEM_ID, contentItemId)
        values.put(SearchContentCollectionMapConst.C_COLLECTION_ID, collectionId)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            screenId,
            contentItemId,
            collectionId)
    }

    open fun copy() : SearchContentCollectionMap {
        val copy = SearchContentCollectionMap()
        copy.screenId = screenId
        copy.contentItemId = contentItemId
        copy.collectionId = collectionId
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, screenId)
        statement.bindLong(2, contentItemId)
        statement.bindLong(3, collectionId)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, screenId)
        statement.bindLong(2, contentItemId)
        statement.bindLong(3, collectionId)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        screenId = values.getAsLong(SearchContentCollectionMapConst.C_SCREEN_ID)
        contentItemId = values.getAsLong(SearchContentCollectionMapConst.C_CONTENT_ITEM_ID)
        collectionId = values.getAsLong(SearchContentCollectionMapConst.C_COLLECTION_ID)
    }

    override fun setContent(cursor: Cursor) {
        screenId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchContentCollectionMapConst.C_SCREEN_ID))
        contentItemId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchContentCollectionMapConst.C_CONTENT_ITEM_ID))
        collectionId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchContentCollectionMapConst.C_COLLECTION_ID))
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