/*
 * SearchAllCount.kt
 *
 * Created: 03/16/2017 01:30:01
 */



package org.lds.ldssa.model.database.search.searchallcount


class SearchAllCount  : SearchAllCountBaseRecord() {
    fun hasResults(): Boolean {
        return keywordCount > 0 || phraseCount > 0
    }

    fun getFullCount(): Long {
        return keywordCount + phraseCount
    }
}