/*
 * NoteSearchQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.notesearchquery

import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
object NoteSearchQueryConst  {

    const val DATABASE = "userdata"
    const val C_NOTE_ID = "note_id"
    const val FULL_C_NOTE_ID = "note_search_query.note_id"
    const val C_ANNOTATION_ID = "annotation_id"
    const val FULL_C_ANNOTATION_ID = "note_search_query.annotation_id"
    const val C_TITLE = "title"
    const val FULL_C_TITLE = "note_search_query.title"
    const val C_CONTENT = "content"
    const val FULL_C_CONTENT = "note_search_query.content"
    const val C_TITLE_SNIPPET = "title_snippet"
    const val FULL_C_TITLE_SNIPPET = "note_search_query.title_snippet"
    const val C_CONTENT_SNIPPET = "content_snippet"
    const val FULL_C_CONTENT_SNIPPET = "note_search_query.content_snippet"
    const val C_MATCH_INFO = "match_info"
    const val FULL_C_MATCH_INFO = "note_search_query.match_info"
    const val C_SEARCH_RESULT_COUNT_TYPE = "search_result_count_type"
    const val FULL_C_SEARCH_RESULT_COUNT_TYPE = "note_search_query.search_result_count_type"
    val ALL_COLUMNS = arrayOf(
        C_NOTE_ID,
        C_ANNOTATION_ID,
        C_TITLE,
        C_CONTENT,
        C_TITLE_SNIPPET,
        C_CONTENT_SNIPPET,
        C_MATCH_INFO,
        C_SEARCH_RESULT_COUNT_TYPE)
    val ALL_COLUMNS_FULL = arrayOf(
        FULL_C_NOTE_ID,
        FULL_C_ANNOTATION_ID,
        FULL_C_TITLE,
        FULL_C_CONTENT,
        FULL_C_TITLE_SNIPPET,
        FULL_C_CONTENT_SNIPPET,
        FULL_C_MATCH_INFO,
        FULL_C_SEARCH_RESULT_COUNT_TYPE)

    fun getNoteId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_NOTE_ID))
    }

    fun getAnnotationId(cursor: Cursor) : Long {
        return cursor.getLong(cursor.getColumnIndexOrThrow(C_ANNOTATION_ID))
    }

    fun getTitle(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE))
    }

    fun getContent(cursor: Cursor) : String {
        return cursor.getString(cursor.getColumnIndexOrThrow(C_CONTENT))
    }

    fun getTitleSnippet(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_TITLE_SNIPPET))) cursor.getString(cursor.getColumnIndexOrThrow(C_TITLE_SNIPPET)) else null
    }

    fun getContentSnippet(cursor: Cursor) : String? {
        return if (!cursor.isNull(cursor.getColumnIndexOrThrow(C_CONTENT_SNIPPET))) cursor.getString(cursor.getColumnIndexOrThrow(C_CONTENT_SNIPPET)) else null
    }

    fun getMatchInfo(cursor: Cursor) : ByteArray {
        return cursor.getBlob(cursor.getColumnIndexOrThrow(C_MATCH_INFO))
    }

    fun getSearchResultCountType(cursor: Cursor) : org.lds.ldssa.search.SearchResultCountType {
        return org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.search.SearchResultCountType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(C_SEARCH_RESULT_COUNT_TYPE)), org.lds.ldssa.search.SearchResultCountType.KEYWORD)
    }


}