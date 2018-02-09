/*
 * RoleCatalogBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.gl.rolecatalog

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class RoleCatalogBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<RoleCatalog>(databaseManager) {

    override val allColumns: Array<String> = RoleCatalogConst.ALL_COLUMNS
    override val primaryKey = RoleCatalogConst.PRIMARY_KEY_COLUMN
    override val dropSql = RoleCatalogConst.DROP_TABLE
    override val createSql = RoleCatalogConst.CREATE_TABLE
    override val insertSql = RoleCatalogConst.INSERT_STATEMENT
    override val updateSql = RoleCatalogConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return RoleCatalogConst.DATABASE
    }

    override fun newRecord() : RoleCatalog {
        return RoleCatalog()
    }

    override fun getTableName() : String {
        return RoleCatalogConst.TABLE
    }


}