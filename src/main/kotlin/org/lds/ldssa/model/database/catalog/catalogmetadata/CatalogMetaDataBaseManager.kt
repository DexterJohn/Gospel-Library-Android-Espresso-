/*
 * CatalogMetaDataBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.catalog.catalogmetadata

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class CatalogMetaDataBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<CatalogMetaData>(databaseManager) {

    override val allColumns: Array<String> = CatalogMetaDataConst.ALL_COLUMNS
    override val primaryKey = CatalogMetaDataConst.PRIMARY_KEY_COLUMN
    override val dropSql = CatalogMetaDataConst.DROP_TABLE
    override val createSql = CatalogMetaDataConst.CREATE_TABLE
    override val insertSql = CatalogMetaDataConst.INSERT_STATEMENT
    override val updateSql = CatalogMetaDataConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return CatalogMetaDataConst.DATABASE
    }

    override fun newRecord() : CatalogMetaData {
        return CatalogMetaData()
    }

    override fun getTableName() : String {
        return CatalogMetaDataConst.TABLE
    }


}