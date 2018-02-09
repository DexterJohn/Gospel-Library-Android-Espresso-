/*
 * NoteSearchQueryManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.userdata.notesearchquery

import android.database.Cursor
import org.dbtools.query.shared.JoinType
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.userdata.annotation.AnnotationConst
import org.lds.ldssa.model.database.userdata.note.NoteConst
import org.lds.ldssa.model.database.userdata.note.NoteManager
import org.lds.ldssa.search.SearchCount
import org.lds.ldssa.search.SearchResultCountType
import org.lds.ldssa.search.SearchTextRequest
import org.lds.ldssa.search.SearchUtil
import org.lds.ldssa.util.UserdataDbUtil
import javax.inject.Inject


@javax.inject.Singleton
class NoteSearchQueryManager @Inject constructor(databaseManager: DatabaseManager, val userdataDbUtil: UserdataDbUtil, val searchUtil: SearchUtil) : NoteSearchQueryBaseManager(databaseManager) {
    companion object {
        // NOTE CONTENT QUERIES
        private const val outerAlias = "aliasOuter"
        private const val innerAlias = "aliasInner"
        private val TITLE_SNIPPET = "SELECT snippet(${NoteManager.NOTE_FTS_TABLE}) " +
                "FROM ${NoteManager.NOTE_FTS_TABLE} AS $innerAlias " +
                "WHERE $innerAlias.${NoteSearchQueryConst.C_TITLE} MATCH ? " +
                "AND $innerAlias.${NoteManager.NOTE_ROW_ID} = $outerAlias.${NoteManager.NOTE_ROW_ID}"
        private val CONTENT_SNIPPET = "SELECT snippet(${NoteManager.NOTE_FTS_TABLE}) " +
                "FROM ${NoteManager.NOTE_FTS_TABLE} AS $innerAlias " +
                "WHERE $innerAlias.${NoteSearchQueryConst.C_CONTENT} MATCH ? " +
                "AND $innerAlias.${NoteManager.NOTE_ROW_ID} = $outerAlias.${NoteManager.NOTE_ROW_ID}"

        private val NOTE_KEYWORD_QUERY = SQLQueryBuilder()
                .field(SearchResultCountType.KEYWORD.ordinal.toString(), NoteSearchQueryConst.C_SEARCH_RESULT_COUNT_TYPE)
                .field(NoteConst.FULL_C_ID, NoteSearchQueryConst.C_NOTE_ID)
                .field(NoteConst.FULL_C_ANNOTATION_ID, NoteSearchQueryConst.C_ANNOTATION_ID)
                .field(NoteConst.FULL_C_TITLE, NoteSearchQueryConst.C_TITLE)
                .field(NoteConst.FULL_C_CONTENT, NoteSearchQueryConst.C_CONTENT)
                .field("($TITLE_SNIPPET)", NoteSearchQueryConst.C_TITLE_SNIPPET)
                .field("($CONTENT_SNIPPET)", NoteSearchQueryConst.C_CONTENT_SNIPPET)
                .field("matchinfo(${NoteManager.NOTE_FTS_TABLE}, 'y')", NoteSearchQueryConst.C_MATCH_INFO)
                .table(NoteManager.NOTE_FTS_TABLE, outerAlias)
                .join(NoteConst.TABLE, NoteConst.FULL_C_ID, "$outerAlias.${NoteManager.NOTE_ROW_ID}")
                .join(JoinType.LEFT_JOIN, AnnotationConst.TABLE, AnnotationConst.FULL_C_ID, NoteConst.FULL_C_ANNOTATION_ID)
                .filter("${NoteManager.NOTE_FTS_TABLE} MATCH ?")
                .buildQuery()

        private val NOTE_EXACT_PHRASE_QUERY = SQLQueryBuilder()
                .field(SearchResultCountType.EXACT_PHRASE.ordinal.toString(), NoteSearchQueryConst.C_SEARCH_RESULT_COUNT_TYPE)
                .field(NoteConst.FULL_C_ID, NoteSearchQueryConst.C_NOTE_ID)
                .field(NoteConst.FULL_C_ANNOTATION_ID, NoteSearchQueryConst.C_ANNOTATION_ID)
                .field(NoteConst.FULL_C_TITLE, NoteSearchQueryConst.C_TITLE)
                .field(NoteConst.FULL_C_CONTENT, NoteSearchQueryConst.C_CONTENT)
                .field("($TITLE_SNIPPET)", NoteSearchQueryConst.C_TITLE_SNIPPET)
                .field("($CONTENT_SNIPPET)", NoteSearchQueryConst.C_CONTENT_SNIPPET)
                .field("matchinfo(${NoteManager.NOTE_FTS_TABLE}, 'y')", NoteSearchQueryConst.C_MATCH_INFO)
                .table(NoteManager.NOTE_FTS_TABLE, outerAlias)
                .join(NoteConst.TABLE, NoteConst.FULL_C_ID, "$outerAlias.${NoteManager.NOTE_ROW_ID}")
                .join(JoinType.LEFT_JOIN, AnnotationConst.TABLE, AnnotationConst.FULL_C_ID, NoteConst.FULL_C_ANNOTATION_ID)
                .filter("${NoteManager.NOTE_FTS_TABLE} MATCH ?")
                .buildQuery()

        private val ALL_NOTES_QUERY = "$NOTE_KEYWORD_QUERY UNION $NOTE_EXACT_PHRASE_QUERY"


        // COUNT QUERIES
        private val COUNT_KEYWORD_QUERY = SQLQueryBuilder()
                .field(SearchResultCountType.KEYWORD.ordinal.toString(), NoteSearchQueryConst.C_SEARCH_RESULT_COUNT_TYPE)
                .field("matchinfo(${NoteManager.NOTE_FTS_TABLE}, 'y')", NoteSearchQueryConst.C_MATCH_INFO)
                .table(NoteManager.NOTE_FTS_TABLE)
                .filter("${NoteManager.NOTE_FTS_TABLE} MATCH ?")
                .buildQuery()

        private val COUNT_EXACT_PHRASE_QUERY = SQLQueryBuilder()
                .field(SearchResultCountType.EXACT_PHRASE.ordinal.toString(), NoteSearchQueryConst.C_SEARCH_RESULT_COUNT_TYPE)
                .field("matchinfo(${NoteManager.NOTE_FTS_TABLE}, 'y')", NoteSearchQueryConst.C_MATCH_INFO)
                .table(NoteManager.NOTE_FTS_TABLE)
                .filter("${NoteManager.NOTE_FTS_TABLE} MATCH ?")
                .buildQuery()

        private val ALL_COUNT_QUERY = "$COUNT_KEYWORD_QUERY UNION ALL $COUNT_EXACT_PHRASE_QUERY"
    }

    override fun getDatabaseName(): String {
        return userdataDbUtil.currentOpenedDatabaseName
    }

    override fun getQuery(): String {
        return "ONLY_USE_RAW_QUERIES"
    }

    fun findAllBySearchTextRequest(searchTextRequest: SearchTextRequest): List<NoteSearchQuery> {
        if (searchTextRequest.keywordList.size == 1) {
            return findAllByRawQuery(NOTE_KEYWORD_QUERY,
                    SQLQueryBuilder.toSelectionArgs(
                            searchTextRequest.getKeywordsForFtsMatch(),
                            searchTextRequest.getKeywordsForFtsMatch(),
                            searchTextRequest.getKeywordsForFtsMatch()
                    ))
        } else {
            if (searchTextRequest.exactSearchOnly) {
                return findAllByRawQuery(NOTE_EXACT_PHRASE_QUERY,
                        SQLQueryBuilder.toSelectionArgs(
                                searchTextRequest.getExactMatchForFtsMatch(),
                                searchTextRequest.getExactMatchForFtsMatch(),
                                searchTextRequest.getExactMatchForFtsMatch()))
            } else {
                return findAllByRawQuery(ALL_NOTES_QUERY,
                        SQLQueryBuilder.toSelectionArgs(
                                searchTextRequest.getKeywordsForFtsMatch(),
                                searchTextRequest.getKeywordsForFtsMatch(),
                                searchTextRequest.getKeywordsForFtsMatch(),
                                searchTextRequest.getExactMatchForFtsMatch(),
                                searchTextRequest.getExactMatchForFtsMatch(),
                                searchTextRequest.getExactMatchForFtsMatch()))
            }
        }
    }

    fun findCountsBySearchTextRequest(searchTextRequest: SearchTextRequest): SearchCount {
        val cursor: Cursor?
        if (searchTextRequest.keywordList.size == 1) {
            cursor = findCursorByRawQuery(
                    rawQuery = COUNT_KEYWORD_QUERY,
                    selectionArgs = arrayOf(searchTextRequest.getKeywordsForFtsMatch()))

        } else {
            if (searchTextRequest.exactSearchOnly) {
                cursor = findCursorByRawQuery(
                        rawQuery = COUNT_EXACT_PHRASE_QUERY,
                        selectionArgs = arrayOf(searchTextRequest.getExactMatchForFtsMatch()))
            } else {
                cursor = findCursorByRawQuery(
                        rawQuery = ALL_COUNT_QUERY,
                        selectionArgs = arrayOf(searchTextRequest.getKeywordsForFtsMatch(), searchTextRequest.getExactMatchForFtsMatch()))
            }
        }

        var itemCount = 0L
        var totalPhrase = 0L
        var totalKeywords = 0L
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val count = searchUtil.getFtsCount(NoteSearchQueryConst.getMatchInfo(cursor))
                when (NoteSearchQueryConst.getSearchResultCountType(cursor)) {
                    SearchResultCountType.KEYWORD -> totalKeywords += count
                    SearchResultCountType.EXACT_PHRASE -> totalPhrase += count
                }

                itemCount++
            } while (cursor.moveToNext())
        }

        cursor?.close()

        return SearchCount(itemCount, totalPhrase, totalKeywords)
    }
}