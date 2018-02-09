package org.lds.ldssa.search

import android.support.annotation.Size

class SearchTextRequest constructor(val searchText: String,
                                    @param:Size(min = 1) val keywordList: List<String>,
                                    val exactSearchOnly: Boolean) {

    fun getExactMatchForFtsMatch(): String  {
        return "\"$searchText\""
    }

    fun getKeywordsForFtsMatch(): String {
        return keywordList.joinToString(separator = " NEAR ")
    }

    fun isEmpty(): Boolean {
        return searchText.isEmpty()// && phrase.isNullOrEmpty()
    }
}
