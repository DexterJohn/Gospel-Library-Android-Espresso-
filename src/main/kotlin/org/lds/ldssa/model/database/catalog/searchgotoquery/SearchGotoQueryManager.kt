/*
 * SearchGotoQueryManager.kt
 *
 * Generated on: 08/31/2017 12:57:06
 *
 */



package org.lds.ldssa.model.database.catalog.searchgotoquery

import org.dbtools.query.shared.CompareType
import org.dbtools.query.shared.filter.CompareFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.item.ItemConst
import org.lds.ldssa.model.database.catalog.searchgoto.SearchGotoConst
import org.lds.ldssa.search.goto.GotoParts
import javax.inject.Inject

@javax.inject.Singleton
class SearchGotoQueryManager @Inject constructor(databaseManager: DatabaseManager) : SearchGotoQueryBaseManager(databaseManager) {
    private val GOTO_QUERY_STARTS_WITH: String

    init {
        val SEARCH_TITLE = "search_title"
        val startsWithQueryByText = SQLQueryBuilder()
                .field(SearchGotoConst.FULL_C_LANGUAGE_ID, SearchGotoQueryConst.C_LANGUAGE_ID)
                .field(SearchGotoConst.FULL_C_CONTENT_ITEM_ID, SearchGotoQueryConst.C_CONTENT_ITEM_ID)
                .field(SearchGotoConst.FULL_C_ITEM_POSITION, SearchGotoQueryConst.C_ITEM_POSITION)
                .field(SearchGotoConst.FULL_C_NAV_COLLECTION_ID, SearchGotoQueryConst.C_NAV_COLLECTION_ID)
                .field(SearchGotoConst.FULL_C_NAV_SECTION_ID, SearchGotoQueryConst.C_NAV_SECTION_ID)
                .field(SearchGotoConst.FULL_C_NAV_POSITION, SearchGotoQueryConst.C_NAV_POSITION)
                .field(SearchGotoConst.FULL_C_SUBITEM_ID, SearchGotoQueryConst.C_SUBITEM_ID)
                .field(SearchGotoConst.FULL_C_TITLE, SearchGotoQueryConst.C_TITLE)
                .field(SearchGotoConst.FULL_C_SHORT_TITLE, SearchGotoQueryConst.C_SHORT_TITLE)
                .field(ItemConst.FULL_C_TITLE, SearchGotoQueryConst.C_SUB_TITLE)
                .field(SearchGotoConst.FULL_C_CHAPTER_COUNT, SearchGotoQueryConst.C_CHAPTER_COUNT)
                .field(SearchGotoConst.FULL_C_HAS_VERSES, SearchGotoQueryConst.C_HAS_VERSES)
                .field("ltrim(${SearchGotoConst.FULL_C_TITLE}, \"1234567890. \")", SEARCH_TITLE)
                .table(SearchGotoConst.TABLE)
                .join(ItemConst.TABLE, ItemConst.FULL_C_ID, SearchGotoConst.FULL_C_CONTENT_ITEM_ID)
                .filter(SearchGotoConst.FULL_C_LANGUAGE_ID, "?")

                .filter(CompareFilter.create(SEARCH_TITLE, CompareType.LIKE, "?")
                        .or(SearchGotoConst.FULL_C_TITLE, CompareType.LIKE, "?")
                        .or(SearchGotoConst.FULL_C_SHORT_TITLE, CompareType.LIKE, "?")
                        .or(SearchGotoConst.FULL_C_SHORT_TITLE, CompareType.LIKE, "?")) // trimmed query param... so music can be searched (example "20. God of Power, God of Right" should search with "20" (not "20 ")

                .orderBy("${SearchGotoConst.C_ITEM_POSITION}, ${SearchGotoConst.C_NAV_POSITION}")

        GOTO_QUERY_STARTS_WITH = startsWithQueryByText.buildQuery()
    }

    fun findAllByGoto(languageId: Long, gotoParts: GotoParts, limit: Int): List<SearchGotoQuery> {
        val query = GOTO_QUERY_STARTS_WITH + if (limit > 0) " LIMIT $limit" else ""

        val startsWithParam = gotoParts.bookSearchText + "%"
        val startsWithParamTrimmed = gotoParts.bookSearchText.trim() + "%"

        return findAllByRawQuery(query, SQLQueryBuilder.toSelectionArgs(languageId,
                startsWithParam, // title without leading numbers/spaces
                startsWithParam, // title
                startsWithParam, // short title
                startsWithParamTrimmed)) // short title trimmed (support music titles)
    }

    override fun getQuery(): String {
        return ""
    }
}