package org.lds.ldssa.search

class SearchCount(val itemCount: Long,
                  val phraseCount: Long,
                  val keywordCount: Long) {
    fun isNotZero(): Boolean {
        return phraseCount > 0L || keywordCount > 0L
    }
}
