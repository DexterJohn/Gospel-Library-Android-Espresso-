/*
 * StopWordManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.catalog.stopword

import org.dbtools.query.shared.filter.CompareFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import javax.inject.Inject


@javax.inject.Singleton
class StopWordManager @Inject constructor(databaseManager: DatabaseManager) : StopWordBaseManager(databaseManager) {
    private val BASE_QUERY: SQLQueryBuilder = SQLQueryBuilder()
            .table(StopWordConst.TABLE)
            .field(StopWordConst.C_WORD)
            .filter(StopWordConst.C_LANGUAGE_ID, "?")

    fun filterListOfWordsByLanguageId(languageId: Long, words: List<String>): List<String> {
        if (words.isEmpty()) {
            return ArrayList()
        }

        val rawQuery = BASE_QUERY.clone().filter(getCompareFilterForWords(words.size)).buildQuery()
        return findAllValuesByRawQuery(String::class.java, rawQuery, SQLQueryBuilder.toSelectionArgs(languageId, words))
    }

    private fun getCompareFilterForWords(length: Int): CompareFilter? {
        var filter: CompareFilter? = null
        for (index in 0..length - 1) {
            if (filter == null) {
                filter = CompareFilter.create(StopWordConst.C_WORD, "?")
            } else {
                filter.or(StopWordConst.C_WORD, "?")
            }
        }
        return filter
    }
}