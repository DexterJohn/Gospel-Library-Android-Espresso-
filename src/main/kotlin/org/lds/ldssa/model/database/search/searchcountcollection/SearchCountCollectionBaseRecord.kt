/*
 * SearchCountCollectionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchcountcollection

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SearchCountCollectionBaseRecord  : AndroidBaseRecord {

    open var screenId: Long = 0
    open var collectionId: Long = 0
    open var collectionTitle: String = ""
    open var parentCollectionId: Long = 0
    open var parentCollectionTitle: String = ""
    open var contentItemCount: Long = 0
    open var phraseCount: Long = 0
    open var keywordCount: Long = 0
    open var position: Int = 0

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
        return SearchCountCollectionConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SearchCountCollectionConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SearchCountCollectionConst.C_SCREEN_ID, screenId)
        values.put(SearchCountCollectionConst.C_COLLECTION_ID, collectionId)
        values.put(SearchCountCollectionConst.C_COLLECTION_TITLE, collectionTitle)
        values.put(SearchCountCollectionConst.C_PARENT_COLLECTION_ID, parentCollectionId)
        values.put(SearchCountCollectionConst.C_PARENT_COLLECTION_TITLE, parentCollectionTitle)
        values.put(SearchCountCollectionConst.C_CONTENT_ITEM_COUNT, contentItemCount)
        values.put(SearchCountCollectionConst.C_PHRASE_COUNT, phraseCount)
        values.put(SearchCountCollectionConst.C_KEYWORD_COUNT, keywordCount)
        values.put(SearchCountCollectionConst.C_POSITION, position.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            screenId,
            collectionId,
            collectionTitle,
            parentCollectionId,
            parentCollectionTitle,
            contentItemCount,
            phraseCount,
            keywordCount,
            position.toLong())
    }

    open fun copy() : SearchCountCollection {
        val copy = SearchCountCollection()
        copy.screenId = screenId
        copy.collectionId = collectionId
        copy.collectionTitle = collectionTitle
        copy.parentCollectionId = parentCollectionId
        copy.parentCollectionTitle = parentCollectionTitle
        copy.contentItemCount = contentItemCount
        copy.phraseCount = phraseCount
        copy.keywordCount = keywordCount
        copy.position = position
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, screenId)
        statement.bindLong(2, collectionId)
        statement.bindString(3, collectionTitle)
        statement.bindLong(4, parentCollectionId)
        statement.bindString(5, parentCollectionTitle)
        statement.bindLong(6, contentItemCount)
        statement.bindLong(7, phraseCount)
        statement.bindLong(8, keywordCount)
        statement.bindLong(9, position.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, screenId)
        statement.bindLong(2, collectionId)
        statement.bindString(3, collectionTitle)
        statement.bindLong(4, parentCollectionId)
        statement.bindString(5, parentCollectionTitle)
        statement.bindLong(6, contentItemCount)
        statement.bindLong(7, phraseCount)
        statement.bindLong(8, keywordCount)
        statement.bindLong(9, position.toLong())
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        screenId = values.getAsLong(SearchCountCollectionConst.C_SCREEN_ID)
        collectionId = values.getAsLong(SearchCountCollectionConst.C_COLLECTION_ID)
        collectionTitle = values.getAsString(SearchCountCollectionConst.C_COLLECTION_TITLE)
        parentCollectionId = values.getAsLong(SearchCountCollectionConst.C_PARENT_COLLECTION_ID)
        parentCollectionTitle = values.getAsString(SearchCountCollectionConst.C_PARENT_COLLECTION_TITLE)
        contentItemCount = values.getAsLong(SearchCountCollectionConst.C_CONTENT_ITEM_COUNT)
        phraseCount = values.getAsLong(SearchCountCollectionConst.C_PHRASE_COUNT)
        keywordCount = values.getAsLong(SearchCountCollectionConst.C_KEYWORD_COUNT)
        position = values.getAsInteger(SearchCountCollectionConst.C_POSITION)
    }

    override fun setContent(cursor: Cursor) {
        screenId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchCountCollectionConst.C_SCREEN_ID))
        collectionId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchCountCollectionConst.C_COLLECTION_ID))
        collectionTitle = cursor.getString(cursor.getColumnIndexOrThrow(SearchCountCollectionConst.C_COLLECTION_TITLE))
        parentCollectionId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchCountCollectionConst.C_PARENT_COLLECTION_ID))
        parentCollectionTitle = cursor.getString(cursor.getColumnIndexOrThrow(SearchCountCollectionConst.C_PARENT_COLLECTION_TITLE))
        contentItemCount = cursor.getLong(cursor.getColumnIndexOrThrow(SearchCountCollectionConst.C_CONTENT_ITEM_COUNT))
        phraseCount = cursor.getLong(cursor.getColumnIndexOrThrow(SearchCountCollectionConst.C_PHRASE_COUNT))
        keywordCount = cursor.getLong(cursor.getColumnIndexOrThrow(SearchCountCollectionConst.C_KEYWORD_COUNT))
        position = cursor.getInt(cursor.getColumnIndexOrThrow(SearchCountCollectionConst.C_POSITION))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}