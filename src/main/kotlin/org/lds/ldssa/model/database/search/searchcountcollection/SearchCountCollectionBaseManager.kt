/*
 * SearchCountCollectionBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.search.searchcountcollection

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class SearchCountCollectionBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<SearchCountCollection>(databaseManager) {

    override val allColumns: Array<String> = SearchCountCollectionConst.ALL_COLUMNS
    override val primaryKey = "<NO_PRIMARY_KEY_ON_VIEWS>"
    override val dropSql = SearchCountCollectionManager.DROP_VIEW
    override val createSql = SearchCountCollectionManager.CREATE_VIEW
    override val insertSql = ""
    override val updateSql = ""

    override fun getDatabaseName() : String {
        return SearchCountCollectionConst.DATABASE
    }

    override fun newRecord() : SearchCountCollection {
        return SearchCountCollection()
    }

    override fun getTableName() : String {
        return SearchCountCollectionConst.TABLE
    }


}