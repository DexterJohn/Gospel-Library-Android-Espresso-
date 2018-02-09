/*
 * SearchPreviewSubitemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchpreviewsubitem

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SearchPreviewSubitemBaseRecord  : AndroidBaseRecord {

    open var screenId: Long = 0
    open var contentItemId: Long = 0
    open var subItemId: Long = 0
    open var title: String = ""
    open var text: String = ""
    open var searchResultCountType: org.lds.ldssa.search.SearchResultCountType = org.lds.ldssa.search.SearchResultCountType.KEYWORD
    open var count: Long = 0
    open var position: Int = 0
    open var visited: Boolean = false

    constructor() {
    }

    override fun getAllColumns() : Array<String> {
        return SearchPreviewSubitemConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SearchPreviewSubitemConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SearchPreviewSubitemConst.C_SCREEN_ID, screenId)
        values.put(SearchPreviewSubitemConst.C_CONTENT_ITEM_ID, contentItemId)
        values.put(SearchPreviewSubitemConst.C_SUB_ITEM_ID, subItemId)
        values.put(SearchPreviewSubitemConst.C_TITLE, title)
        values.put(SearchPreviewSubitemConst.C_TEXT, text)
        values.put(SearchPreviewSubitemConst.C_SEARCH_RESULT_COUNT_TYPE, searchResultCountType.ordinal.toLong())
        values.put(SearchPreviewSubitemConst.C_COUNT, count)
        values.put(SearchPreviewSubitemConst.C_POSITION, position.toLong())
        values.put(SearchPreviewSubitemConst.C_VISITED, if (visited) 1L else 0L)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            screenId,
            contentItemId,
            subItemId,
            title,
            text,
            searchResultCountType.ordinal.toLong(),
            count,
            position.toLong(),
            if (visited) 1L else 0L)
    }

    open fun copy() : SearchPreviewSubitem {
        val copy = SearchPreviewSubitem()
        copy.screenId = screenId
        copy.contentItemId = contentItemId
        copy.subItemId = subItemId
        copy.title = title
        copy.text = text
        copy.searchResultCountType = searchResultCountType
        copy.count = count
        copy.position = position
        copy.visited = visited
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, screenId)
        statement.bindLong(2, contentItemId)
        statement.bindLong(3, subItemId)
        statement.bindString(4, title)
        statement.bindString(5, text)
        statement.bindLong(6, searchResultCountType.ordinal.toLong())
        statement.bindLong(7, count)
        statement.bindLong(8, position.toLong())
        statement.bindLong(9, if (visited) 1L else 0L)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, screenId)
        statement.bindLong(2, contentItemId)
        statement.bindLong(3, subItemId)
        statement.bindString(4, title)
        statement.bindString(5, text)
        statement.bindLong(6, searchResultCountType.ordinal.toLong())
        statement.bindLong(7, count)
        statement.bindLong(8, position.toLong())
        statement.bindLong(9, if (visited) 1L else 0L)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        screenId = values.getAsLong(SearchPreviewSubitemConst.C_SCREEN_ID)
        contentItemId = values.getAsLong(SearchPreviewSubitemConst.C_CONTENT_ITEM_ID)
        subItemId = values.getAsLong(SearchPreviewSubitemConst.C_SUB_ITEM_ID)
        title = values.getAsString(SearchPreviewSubitemConst.C_TITLE)
        text = values.getAsString(SearchPreviewSubitemConst.C_TEXT)
        searchResultCountType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.search.SearchResultCountType::class.java, values.getAsInteger(SearchPreviewSubitemConst.C_SEARCH_RESULT_COUNT_TYPE), org.lds.ldssa.search.SearchResultCountType.KEYWORD)
        count = values.getAsLong(SearchPreviewSubitemConst.C_COUNT)
        position = values.getAsInteger(SearchPreviewSubitemConst.C_POSITION)
        visited = values.getAsBoolean(SearchPreviewSubitemConst.C_VISITED)
    }

    override fun setContent(cursor: Cursor) {
        screenId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchPreviewSubitemConst.C_SCREEN_ID))
        contentItemId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchPreviewSubitemConst.C_CONTENT_ITEM_ID))
        subItemId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchPreviewSubitemConst.C_SUB_ITEM_ID))
        title = cursor.getString(cursor.getColumnIndexOrThrow(SearchPreviewSubitemConst.C_TITLE))
        text = cursor.getString(cursor.getColumnIndexOrThrow(SearchPreviewSubitemConst.C_TEXT))
        searchResultCountType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.search.SearchResultCountType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(SearchPreviewSubitemConst.C_SEARCH_RESULT_COUNT_TYPE)), org.lds.ldssa.search.SearchResultCountType.KEYWORD)
        count = cursor.getLong(cursor.getColumnIndexOrThrow(SearchPreviewSubitemConst.C_COUNT))
        position = cursor.getInt(cursor.getColumnIndexOrThrow(SearchPreviewSubitemConst.C_POSITION))
        visited = cursor.getInt(cursor.getColumnIndexOrThrow(SearchPreviewSubitemConst.C_VISITED)) != 0
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