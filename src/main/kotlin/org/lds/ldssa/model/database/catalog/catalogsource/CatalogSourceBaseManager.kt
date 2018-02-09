/*
 * CatalogSourceBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.catalog.catalogsource

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class CatalogSourceBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<CatalogSource>(databaseManager) {

    override val allColumns: Array<String> = CatalogSourceConst.ALL_COLUMNS
    override val primaryKey = CatalogSourceConst.PRIMARY_KEY_COLUMN
    override val dropSql = CatalogSourceConst.DROP_TABLE
    override val createSql = CatalogSourceConst.CREATE_TABLE
    override val insertSql = CatalogSourceConst.INSERT_STATEMENT
    override val updateSql = CatalogSourceConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return CatalogSourceConst.DATABASE
    }

    override fun newRecord() : CatalogSource {
        return CatalogSource()
    }

    override fun getTableName() : String {
        return CatalogSourceConst.TABLE
    }


}