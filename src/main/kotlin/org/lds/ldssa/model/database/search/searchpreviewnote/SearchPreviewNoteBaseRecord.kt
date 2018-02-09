/*
 * SearchPreviewNoteBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchpreviewnote

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SearchPreviewNoteBaseRecord  : AndroidBaseRecord {

    open var screenId: Long = 0
    open var annotationId: Long = 0
    open var title: String = ""
    open var text: String = ""
    open var searchResultCountType: org.lds.ldssa.search.SearchResultCountType = org.lds.ldssa.search.SearchResultCountType.KEYWORD
    open var count: Long = 0
    open var position: Int = 0
    open var visited: Boolean = false

    constructor() {
    }

    override fun getAllColumns() : Array<String> {
        return SearchPreviewNoteConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SearchPreviewNoteConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SearchPreviewNoteConst.C_SCREEN_ID, screenId)
        values.put(SearchPreviewNoteConst.C_ANNOTATION_ID, annotationId)
        values.put(SearchPreviewNoteConst.C_TITLE, title)
        values.put(SearchPreviewNoteConst.C_TEXT, text)
        values.put(SearchPreviewNoteConst.C_SEARCH_RESULT_COUNT_TYPE, searchResultCountType.ordinal.toLong())
        values.put(SearchPreviewNoteConst.C_COUNT, count)
        values.put(SearchPreviewNoteConst.C_POSITION, position.toLong())
        values.put(SearchPreviewNoteConst.C_VISITED, if (visited) 1L else 0L)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            screenId,
            annotationId,
            title,
            text,
            searchResultCountType.ordinal.toLong(),
            count,
            position.toLong(),
            if (visited) 1L else 0L)
    }

    open fun copy() : SearchPreviewNote {
        val copy = SearchPreviewNote()
        copy.screenId = screenId
        copy.annotationId = annotationId
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
        statement.bindLong(2, annotationId)
        statement.bindString(3, title)
        statement.bindString(4, text)
        statement.bindLong(5, searchResultCountType.ordinal.toLong())
        statement.bindLong(6, count)
        statement.bindLong(7, position.toLong())
        statement.bindLong(8, if (visited) 1L else 0L)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, screenId)
        statement.bindLong(2, annotationId)
        statement.bindString(3, title)
        statement.bindString(4, text)
        statement.bindLong(5, searchResultCountType.ordinal.toLong())
        statement.bindLong(6, count)
        statement.bindLong(7, position.toLong())
        statement.bindLong(8, if (visited) 1L else 0L)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        screenId = values.getAsLong(SearchPreviewNoteConst.C_SCREEN_ID)
        annotationId = values.getAsLong(SearchPreviewNoteConst.C_ANNOTATION_ID)
        title = values.getAsString(SearchPreviewNoteConst.C_TITLE)
        text = values.getAsString(SearchPreviewNoteConst.C_TEXT)
        searchResultCountType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.search.SearchResultCountType::class.java, values.getAsInteger(SearchPreviewNoteConst.C_SEARCH_RESULT_COUNT_TYPE), org.lds.ldssa.search.SearchResultCountType.KEYWORD)
        count = values.getAsLong(SearchPreviewNoteConst.C_COUNT)
        position = values.getAsInteger(SearchPreviewNoteConst.C_POSITION)
        visited = values.getAsBoolean(SearchPreviewNoteConst.C_VISITED)
    }

    override fun setContent(cursor: Cursor) {
        screenId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchPreviewNoteConst.C_SCREEN_ID))
        annotationId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchPreviewNoteConst.C_ANNOTATION_ID))
        title = cursor.getString(cursor.getColumnIndexOrThrow(SearchPreviewNoteConst.C_TITLE))
        text = cursor.getString(cursor.getColumnIndexOrThrow(SearchPreviewNoteConst.C_TEXT))
        searchResultCountType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.search.SearchResultCountType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(SearchPreviewNoteConst.C_SEARCH_RESULT_COUNT_TYPE)), org.lds.ldssa.search.SearchResultCountType.KEYWORD)
        count = cursor.getLong(cursor.getColumnIndexOrThrow(SearchPreviewNoteConst.C_COUNT))
        position = cursor.getInt(cursor.getColumnIndexOrThrow(SearchPreviewNoteConst.C_POSITION))
        visited = cursor.getInt(cursor.getColumnIndexOrThrow(SearchPreviewNoteConst.C_VISITED)) != 0
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