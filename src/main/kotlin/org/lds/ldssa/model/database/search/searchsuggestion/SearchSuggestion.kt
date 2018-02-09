/*
 * SearchSuggestion.kt
 *
 * Created: 02/09/2017 11:39:55
 */



package org.lds.ldssa.model.database.search.searchsuggestion

class SearchSuggestion : SearchSuggestionBaseRecord() {
    fun getChapterNumberValue(): Int {
        return chapterNumber.toIntOrNull() ?: 0
    }

    fun getVerseNumberValue(): Int {
        return verseNumber.toIntOrNull() ?: 0
    }
}