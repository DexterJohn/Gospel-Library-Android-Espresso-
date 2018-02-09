/*
 * SearchCountContentBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchcountcontent

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SearchCountContentBaseRecord  : AndroidBaseRecord {

    open var screenId: Long = 0
    open var contentItemId: Long = 0
    open var position: Int = 0
    open var title: String = ""
    open var phraseCount: Long = 0
    open var keywordCount: Long = 0

    constructor() {
    }

    override fun getAllColumns() : Array<String> {
        return SearchCountContentConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SearchCountContentConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SearchCountContentConst.C_SCREEN_ID, screenId)
        values.put(SearchCountContentConst.C_CONTENT_ITEM_ID, contentItemId)
        values.put(SearchCountContentConst.C_POSITION, position.toLong())
        values.put(SearchCountContentConst.C_TITLE, title)
        values.put(SearchCountContentConst.C_PHRASE_COUNT, phraseCount)
        values.put(SearchCountContentConst.C_KEYWORD_COUNT, keywordCount)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            screenId,
            contentItemId,
            position.toLong(),
            title,
            phraseCount,
            keywordCount)
    }

    open fun copy() : SearchCountContent {
        val copy = SearchCountContent()
        copy.screenId = screenId
        copy.contentItemId = contentItemId
        copy.position = position
        copy.title = title
        copy.phraseCount = phraseCount
        copy.keywordCount = keywordCount
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, screenId)
        statement.bindLong(2, contentItemId)
        statement.bindLong(3, position.toLong())
        statement.bindString(4, title)
        statement.bindLong(5, phraseCount)
        statement.bindLong(6, keywordCount)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, screenId)
        statement.bindLong(2, contentItemId)
        statement.bindLong(3, position.toLong())
        statement.bindString(4, title)
        statement.bindLong(5, phraseCount)
        statement.bindLong(6, keywordCount)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        screenId = values.getAsLong(SearchCountContentConst.C_SCREEN_ID)
        contentItemId = values.getAsLong(SearchCountContentConst.C_CONTENT_ITEM_ID)
        position = values.getAsInteger(SearchCountContentConst.C_POSITION)
        title = values.getAsString(SearchCountContentConst.C_TITLE)
        phraseCount = values.getAsLong(SearchCountContentConst.C_PHRASE_COUNT)
        keywordCount = values.getAsLong(SearchCountContentConst.C_KEYWORD_COUNT)
    }

    override fun setContent(cursor: Cursor) {
        screenId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchCountContentConst.C_SCREEN_ID))
        contentItemId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchCountContentConst.C_CONTENT_ITEM_ID))
        position = cursor.getInt(cursor.getColumnIndexOrThrow(SearchCountContentConst.C_POSITION))
        title = cursor.getString(cursor.getColumnIndexOrThrow(SearchCountContentConst.C_TITLE))
        phraseCount = cursor.getLong(cursor.getColumnIndexOrThrow(SearchCountContentConst.C_PHRASE_COUNT))
        keywordCount = cursor.getLong(cursor.getColumnIndexOrThrow(SearchCountContentConst.C_KEYWORD_COUNT))
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