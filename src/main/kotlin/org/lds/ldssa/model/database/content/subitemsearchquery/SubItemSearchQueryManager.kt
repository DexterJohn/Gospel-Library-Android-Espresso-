/*
 * SubItemSearchQueryManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.content.subitemsearchquery

import android.database.Cursor
import org.dbtools.query.shared.JoinType
import org.dbtools.query.shared.filter.InFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.content.allsubitemsinnavcollectionquery.AllSubItemsInNavCollectionQueryManager
import org.lds.ldssa.model.database.content.subitem.SubItemConst
import org.lds.ldssa.model.database.content.subitemcontent.SubItemContentConst
import org.lds.ldssa.search.SearchCount
import org.lds.ldssa.search.SearchResultCountType
import org.lds.ldssa.search.SearchTextRequest
import org.lds.ldssa.search.SearchUtil
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject


@javax.inject.Singleton
class SubItemSearchQueryManager @Inject constructor(databaseManager: DatabaseManager,
                                                    val contentItemUtil: ContentItemUtil,
                                                    val searchUtil: SearchUtil,
                                                    val allSubItemsInNavCollectionQueryManager: AllSubItemsInNavCollectionQueryManager) : SubItemSearchQueryBaseManager(databaseManager) {
    companion object {
        private const val FTS_TABLE = SubItemContentConst.TABLE + "_fts"
        private const val FTS_C_DOCID = "docid" // subItemId should be in this fts column

        // CONTENT SEARCH RESULTS QUERIES
        private val CONTENT_KEYWORD_QUERY = SQLQueryBuilder()
                .field(SearchResultCountType.KEYWORD.ordinal.toString(), SubItemSearchQueryConst.C_SEARCH_RESULT_COUNT_TYPE)
                .field(SubItemConst.FULL_C_TITLE, SubItemSearchQueryConst.C_TITLE)
                .field(SubItemConst.FULL_C_ID, SubItemSearchQueryConst.C_SUB_ITEM_ID)
                .field(SubItemConst.FULL_C_POSITION, SubItemSearchQueryConst.C_POSITION)
                .field("matchinfo($FTS_TABLE, 'y')", SubItemSearchQueryConst.C_MATCH_INFO)
                .field("snippet($FTS_TABLE, '<b>', '</b>', '${SearchUtil.ELLIPSE_DIVIDER}', -1, ${SearchUtil.SNIPPET_LENGTH})", SubItemSearchQueryConst.C_SNIPPET)
                .table(FTS_TABLE)
                .filter("$FTS_TABLE.${SubItemContentConst.C_CONTENT_HTML} MATCH ?")
                .join(JoinType.JOIN, SubItemConst.TABLE, SubItemConst.FULL_C_ID, "$FTS_TABLE.${SubItemContentConst.C_SUBITEM_ID}")

        private val CONTENT_EXACT_PHRASE_QUERY = SQLQueryBuilder()
                .field(SearchResultCountType.EXACT_PHRASE.ordinal.toString(), SubItemSearchQueryConst.C_SEARCH_RESULT_COUNT_TYPE)
                .field(SubItemConst.FULL_C_TITLE, SubItemSearchQueryConst.C_TITLE)
                .field(SubItemConst.FULL_C_ID, SubItemSearchQueryConst.C_SUB_ITEM_ID)
                .field(SubItemConst.FULL_C_POSITION, SubItemSearchQueryConst.C_POSITION)
                .field("matchinfo($FTS_TABLE, 'y')", SubItemSearchQueryConst.C_MATCH_INFO)
                .field("snippet($FTS_TABLE, '<b>', '</b>', '${SearchUtil.ELLIPSE_DIVIDER}', -1, ${SearchUtil.SNIPPET_LENGTH})", SubItemSearchQueryConst.C_SNIPPET)
                .table(FTS_TABLE)
                .filter("$FTS_TABLE.${SubItemContentConst.C_CONTENT_HTML} MATCH ?")
                .join(JoinType.JOIN, SubItemConst.TABLE, SubItemConst.FULL_C_ID, "$FTS_TABLE.${SubItemContentConst.C_SUBITEM_ID}")

        private val DEFAULT_ORDER_BY = "${SubItemSearchQueryConst.C_SEARCH_RESULT_COUNT_TYPE}, ${SubItemConst.C_POSITION}"

        // COUNT QUERIES
        private val COUNT_KEYWORD_QUERY = SQLQueryBuilder()
                        .field("matchinfo($FTS_TABLE, 'y')", SubItemSearchQueryConst.C_MATCH_INFO)
                        .field(SearchResultCountType.KEYWORD.ordinal.toString(), SubItemSearchQueryConst.C_SEARCH_RESULT_COUNT_TYPE)
                        .table(FTS_TABLE)
                        .filter("$FTS_TABLE.${SubItemContentConst.C_CONTENT_HTML} MATCH ?")

        private val COUNT_EXACT_PHRASE_QUERY = SQLQueryBuilder()
                        .field("matchinfo($FTS_TABLE, 'y')", SubItemSearchQueryConst.C_MATCH_INFO)
                        .field(SearchResultCountType.EXACT_PHRASE.ordinal.toString(), SubItemSearchQueryConst.C_SEARCH_RESULT_COUNT_TYPE)
                        .table(FTS_TABLE)
                        .filter("$FTS_TABLE.${SubItemContentConst.C_CONTENT_HTML} MATCH ?")

        // OFFSET QUERIES
        private val OFFSET_QUERY = SQLQueryBuilder()
                .filter("$FTS_TABLE.${SubItemContentConst.C_CONTENT_HTML} MATCH ?")
                .field("offsets($FTS_TABLE)")
                .filter(SubItemConst.FULL_C_ID, "?")
                .table(FTS_TABLE)
                .join(JoinType.JOIN, SubItemConst.TABLE, SubItemConst.FULL_C_ID, "$FTS_TABLE.${SubItemContentConst.C_SUBITEM_ID}")
                .buildQuery()

        private val OFFSET_QUERY_UNION = "$OFFSET_QUERY UNION ALL $OFFSET_QUERY"  // 1st offset query is Keyword, 2nd offset is exact phrase
    }

    override fun getQuery(): String {
        return "ONLY_USE_RAW_QUERIES"
    }

    private fun cloneAndFilter(defaultQuery: SQLQueryBuilder, contentItemId: Long, navCollectionId: Long): SQLQueryBuilder {
        val query = defaultQuery.clone()

        // add filter
        if (navCollectionId > 1) { // 1 == root collection
            val filterSubItemIds = allSubItemsInNavCollectionQueryManager.findAllSubItemIdsByNavCollectionId(contentItemId, navCollectionId)
            query.filter(InFilter.create(FTS_C_DOCID, filterSubItemIds)) // docid is a fts column
        }

        return query
    }

    fun findPreviewBySearchTextRequest(contentItemId: Long, searchTextRequest: SearchTextRequest, navCollectionId: Long): List<SubItemSearchQuery> {
        if (!contentItemUtil.isItemDownloadedAndOpen(contentItemId)) {
            return emptyList()
        }

        if (searchTextRequest.keywordList.size == 1) {
            val query = cloneAndFilter(CONTENT_KEYWORD_QUERY, contentItemId, navCollectionId)
            query.orderBy(DEFAULT_ORDER_BY)

            return findAllByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                    rawQuery = query.buildQuery(),
                    selectionArgs = SQLQueryBuilder.toSelectionArgs(searchTextRequest.getKeywordsForFtsMatch()))
        } else {
            if (searchTextRequest.exactSearchOnly) {
                val query = cloneAndFilter(CONTENT_EXACT_PHRASE_QUERY, contentItemId, navCollectionId)
                query.orderBy(DEFAULT_ORDER_BY)

                return findAllByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                        rawQuery = query.buildQuery(),
                        selectionArgs = SQLQueryBuilder.toSelectionArgs(searchTextRequest.getExactMatchForFtsMatch()))
            } else {
                val keywordQuery = cloneAndFilter(CONTENT_KEYWORD_QUERY, contentItemId, navCollectionId).buildQuery()
                val phraseQuery = cloneAndFilter(CONTENT_EXACT_PHRASE_QUERY, contentItemId, navCollectionId).buildQuery()

                val contentUnionQuery = "$keywordQuery UNION $phraseQuery ORDER BY $DEFAULT_ORDER_BY"

                return findAllByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                        rawQuery = contentUnionQuery,
                        selectionArgs = SQLQueryBuilder.toSelectionArgs(
                                searchTextRequest.getKeywordsForFtsMatch(), // keyword
                                searchTextRequest.getExactMatchForFtsMatch())) // exact phrase
            }
        }
    }

    fun findCountBySearchTextRequest(contentItemId: Long, searchTextRequest: SearchTextRequest, navCollectionId: Long): SearchCount {
        if (!contentItemUtil.isItemDownloadedAndOpen(contentItemId)) {
            return SearchCount(0, 0, 0)
        }

        val cursor: Cursor?

        if (searchTextRequest.keywordList.size == 1) {
            cursor = findCursorByRawQuery(
                    databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                    rawQuery = cloneAndFilter(COUNT_KEYWORD_QUERY, contentItemId, navCollectionId).buildQuery(),
                    selectionArgs = arrayOf(searchTextRequest.getKeywordsForFtsMatch()))
        } else {
            if (searchTextRequest.exactSearchOnly) {
                cursor = findCursorByRawQuery(
                        databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                        rawQuery = cloneAndFilter(COUNT_EXACT_PHRASE_QUERY, contentItemId, navCollectionId).buildQuery(),
                        selectionArgs = arrayOf(searchTextRequest.getExactMatchForFtsMatch()))
            } else {
                val keywordCountQuery = cloneAndFilter(COUNT_KEYWORD_QUERY, contentItemId, navCollectionId).buildQuery()
                val phraseCountQuery = cloneAndFilter(COUNT_EXACT_PHRASE_QUERY, contentItemId, navCollectionId).buildQuery()

                val contentUnionQuery = "$keywordCountQuery UNION ALL $phraseCountQuery"

                cursor = findCursorByRawQuery(
                        databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                        rawQuery = contentUnionQuery,
                        selectionArgs = arrayOf(
                                searchTextRequest.getKeywordsForFtsMatch(),
                                searchTextRequest.getExactMatchForFtsMatch()))
            }
        }

        var itemCount = 0L
        var totalPhrase = 0L
        var totalKeywords = 0L
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val count = searchUtil.getFtsCount(SubItemSearchQueryConst.getMatchInfo(cursor))

                when (SubItemSearchQueryConst.getSearchResultCountType(cursor)) {
                    SearchResultCountType.KEYWORD -> totalKeywords += count
                    SearchResultCountType.EXACT_PHRASE -> totalPhrase += count
                }

                itemCount++
            } while (cursor.moveToNext())
        }

        cursor?.close()

        // close the content item database
        contentItemUtil.closeItem(contentItemId)

        return SearchCount(itemCount, totalPhrase, totalKeywords)
    }

    fun findTextOffsets(contentItemId: Long, subItemId: Long, searchTextRequest: SearchTextRequest): String {
        if (searchTextRequest.isEmpty() || !contentItemUtil.isItemDownloadedAndOpen(contentItemId)) {
            return ""
        }

        if (searchTextRequest.exactSearchOnly) {
            return findValueByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                    valueType = String::class.java,
                    rawQuery = OFFSET_QUERY,
                    selectionArgs = SQLQueryBuilder.toSelectionArgs(searchTextRequest.getExactMatchForFtsMatch(), subItemId),
                    defaultValue = "")

        } else {
            return findValueByRawQuery(databaseName = contentItemUtil.getOpenedDatabaseName(contentItemId),
                    valueType = String::class.java,
                    rawQuery = OFFSET_QUERY_UNION,
                    selectionArgs = SQLQueryBuilder.toSelectionArgs(searchTextRequest.getKeywordsForFtsMatch(), subItemId, searchTextRequest.getExactMatchForFtsMatch(), subItemId),
                    defaultValue = "")
        }
    }
}