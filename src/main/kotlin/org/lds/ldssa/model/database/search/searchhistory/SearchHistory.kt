/*
 * SearchHistory.kt
 *
 * Created: 02/09/2017 11:39:55
 */



package org.lds.ldssa.model.database.search.searchhistory

import org.lds.ldssa.model.database.search.searchsuggestion.SearchSuggestion
import org.threeten.bp.LocalDateTime


class SearchHistory() : SearchHistoryBaseRecord() {

    constructor(searchSuggestion: SearchSuggestion, languageId: Long) : this() {
        id = searchSuggestion.id
        contentItemId = searchSuggestion.contentItemId
        chapterNumber = searchSuggestion.chapterNumber
        verseNumber = searchSuggestion.verseNumber
        type = searchSuggestion.type
        title = searchSuggestion.title
        subTitle = searchSuggestion.subTitle

        this.languageId = languageId
        lastUpdate = LocalDateTime.now()
    }
}