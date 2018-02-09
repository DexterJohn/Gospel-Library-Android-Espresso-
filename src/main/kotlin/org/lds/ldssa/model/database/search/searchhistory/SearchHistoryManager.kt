/*
 * SearchHistoryManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.search.searchhistory

import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.search.searchsuggestion.SearchSuggestion
import org.lds.ldssa.model.database.types.SearchSuggestionType
import org.threeten.bp.LocalDateTime
import javax.inject.Inject


@javax.inject.Singleton
class SearchHistoryManager @Inject constructor(databaseManager: DatabaseManager) : SearchHistoryBaseManager(databaseManager) {

    val MAX_HISTORY_COUNT = 6 // per language

    val CLEANUP_WHERE = "${SearchHistoryConst.C_LAST_UPDATE} <= (SELECT ${SearchHistoryConst.C_LAST_UPDATE} FROM ${SearchHistoryConst.TABLE} ORDER BY ${SearchHistoryConst.C_LAST_UPDATE} desc LIMIT 1 OFFSET $MAX_HISTORY_COUNT)"

    fun addItemToHistory(searchText: String) {
        val searchHistory = SearchHistory().apply {
            title = searchText
            type = SearchSuggestionType.SEARCH_FOR
            lastUpdate = LocalDateTime.now()
        }

        save(searchHistory)
        cleanupHistory()
    }

    fun findAllByMostRecent(): List<SearchHistory> {
        return findAllBySelection(orderBy = "${SearchHistoryConst.C_LAST_UPDATE} DESC")
    }

    /**
     * Maintain a list of max MAX_HISTORY_COUNT per language
     */
    fun cleanupHistory() {
        delete(CLEANUP_WHERE)
    }
}