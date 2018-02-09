/*
 * CatalogItemQueryBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.catalog.catalogitemquery

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class CatalogItemQueryBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<CatalogItemQuery>(databaseManager) {

    override val allColumns: Array<String> = CatalogItemQueryConst.ALL_COLUMNS
    override val primaryKey = "<NO_PRIMARY_KEY_ON_QUERIES>"
    override val dropSql = ""
    override val createSql = ""
    override val insertSql = ""
    override val updateSql = ""

    override fun getDatabaseName() : String {
        return CatalogItemQueryConst.DATABASE
    }

    override fun newRecord() : CatalogItemQuery {
        return CatalogItemQuery()
    }

    abstract fun getQuery() : String

    override fun getTableName() : String {
        return getQuery()
    }


}