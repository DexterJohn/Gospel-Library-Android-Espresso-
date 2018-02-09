/*
 * SearchSuggestionBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.search.searchsuggestion

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class SearchSuggestionBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<SearchSuggestion>(databaseManager) {

    override val allColumns: Array<String> = SearchSuggestionConst.ALL_COLUMNS
    override val primaryKey = "<NO_PRIMARY_KEY_ON_QUERIES>"
    override val dropSql = ""
    override val createSql = ""
    override val insertSql = ""
    override val updateSql = ""

    override fun getDatabaseName() : String {
        return SearchSuggestionConst.DATABASE
    }

    override fun newRecord() : SearchSuggestion {
        return SearchSuggestion()
    }

    abstract fun getQuery() : String

    override fun getTableName() : String {
        return getQuery()
    }


}