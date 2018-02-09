/*
 * NoteSearchQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.notesearchquery

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class NoteSearchQueryBaseRecord  : AndroidBaseRecord {

    open var noteId: Long = 0
    open var annotationId: Long = 0
    open var title: String = ""
    open var content: String = ""
    open var titleSnippet: String? = null
    open var contentSnippet: String? = null
    open var matchInfo: ByteArray = byteArrayOf()
    open var searchResultCountType: org.lds.ldssa.search.SearchResultCountType = org.lds.ldssa.search.SearchResultCountType.KEYWORD

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
        return NoteSearchQueryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return NoteSearchQueryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(NoteSearchQueryConst.C_NOTE_ID, noteId)
        values.put(NoteSearchQueryConst.C_ANNOTATION_ID, annotationId)
        values.put(NoteSearchQueryConst.C_TITLE, title)
        values.put(NoteSearchQueryConst.C_CONTENT, content)
        values.put(NoteSearchQueryConst.C_TITLE_SNIPPET, titleSnippet)
        values.put(NoteSearchQueryConst.C_CONTENT_SNIPPET, contentSnippet)
        values.put(NoteSearchQueryConst.C_MATCH_INFO, matchInfo)
        values.put(NoteSearchQueryConst.C_SEARCH_RESULT_COUNT_TYPE, searchResultCountType.ordinal.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            noteId,
            annotationId,
            title,
            content,
            titleSnippet,
            contentSnippet,
            matchInfo,
            searchResultCountType.ordinal.toLong())
    }

    open fun copy() : NoteSearchQuery {
        val copy = NoteSearchQuery()
        copy.noteId = noteId
        copy.annotationId = annotationId
        copy.title = title
        copy.content = content
        copy.titleSnippet = titleSnippet
        copy.contentSnippet = contentSnippet
        copy.matchInfo = matchInfo
        copy.searchResultCountType = searchResultCountType
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, noteId)
        statement.bindLong(2, annotationId)
        statement.bindString(3, title)
        statement.bindString(4, content)
        if (titleSnippet != null) {
            statement.bindString(5, titleSnippet!!)
        } else {
            statement.bindNull(5)
        }
        if (contentSnippet != null) {
            statement.bindString(6, contentSnippet!!)
        } else {
            statement.bindNull(6)
        }
        statement.bindBlob(7, matchInfo)
        statement.bindLong(8, searchResultCountType.ordinal.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, noteId)
        statement.bindLong(2, annotationId)
        statement.bindString(3, title)
        statement.bindString(4, content)
        if (titleSnippet != null) {
            statement.bindString(5, titleSnippet!!)
        } else {
            statement.bindNull(5)
        }
        if (contentSnippet != null) {
            statement.bindString(6, contentSnippet!!)
        } else {
            statement.bindNull(6)
        }
        statement.bindBlob(7, matchInfo)
        statement.bindLong(8, searchResultCountType.ordinal.toLong())
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        noteId = values.getAsLong(NoteSearchQueryConst.C_NOTE_ID)
        annotationId = values.getAsLong(NoteSearchQueryConst.C_ANNOTATION_ID)
        title = values.getAsString(NoteSearchQueryConst.C_TITLE)
        content = values.getAsString(NoteSearchQueryConst.C_CONTENT)
        titleSnippet = values.getAsString(NoteSearchQueryConst.C_TITLE_SNIPPET)
        contentSnippet = values.getAsString(NoteSearchQueryConst.C_CONTENT_SNIPPET)
        matchInfo = values.getAsByteArray(NoteSearchQueryConst.C_MATCH_INFO)
        searchResultCountType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.search.SearchResultCountType::class.java, values.getAsInteger(NoteSearchQueryConst.C_SEARCH_RESULT_COUNT_TYPE), org.lds.ldssa.search.SearchResultCountType.KEYWORD)
    }

    override fun setContent(cursor: Cursor) {
        noteId = cursor.getLong(cursor.getColumnIndexOrThrow(NoteSearchQueryConst.C_NOTE_ID))
        annotationId = cursor.getLong(cursor.getColumnIndexOrThrow(NoteSearchQueryConst.C_ANNOTATION_ID))
        title = cursor.getString(cursor.getColumnIndexOrThrow(NoteSearchQueryConst.C_TITLE))
        content = cursor.getString(cursor.getColumnIndexOrThrow(NoteSearchQueryConst.C_CONTENT))
        titleSnippet = if (!cursor.isNull(cursor.getColumnIndexOrThrow(NoteSearchQueryConst.C_TITLE_SNIPPET))) cursor.getString(cursor.getColumnIndexOrThrow(NoteSearchQueryConst.C_TITLE_SNIPPET)) else null
        contentSnippet = if (!cursor.isNull(cursor.getColumnIndexOrThrow(NoteSearchQueryConst.C_CONTENT_SNIPPET))) cursor.getString(cursor.getColumnIndexOrThrow(NoteSearchQueryConst.C_CONTENT_SNIPPET)) else null
        matchInfo = cursor.getBlob(cursor.getColumnIndexOrThrow(NoteSearchQueryConst.C_MATCH_INFO))
        searchResultCountType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.search.SearchResultCountType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(NoteSearchQueryConst.C_SEARCH_RESULT_COUNT_TYPE)), org.lds.ldssa.search.SearchResultCountType.KEYWORD)
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}