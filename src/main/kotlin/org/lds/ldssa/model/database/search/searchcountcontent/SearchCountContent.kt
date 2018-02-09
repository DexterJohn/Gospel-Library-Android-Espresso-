/*
 * SearchCountContent.kt
 *
 * Created: 03/09/2017 11:46:28
 */



package org.lds.ldssa.model.database.search.searchcountcontent


class SearchCountContent : SearchCountContentBaseRecord() {
    fun getFullCount(): Long {
        return keywordCount + phraseCount
    }
}