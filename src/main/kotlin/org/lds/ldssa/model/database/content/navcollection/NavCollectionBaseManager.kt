/*
 * NavCollectionBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.navcollection

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class NavCollectionBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<NavCollection>(databaseManager) {

    override val allColumns: Array<String> = NavCollectionConst.ALL_COLUMNS
    override val primaryKey = NavCollectionConst.PRIMARY_KEY_COLUMN
    override val dropSql = NavCollectionConst.DROP_TABLE
    override val createSql = NavCollectionConst.CREATE_TABLE
    override val insertSql = NavCollectionConst.INSERT_STATEMENT
    override val updateSql = NavCollectionConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return NavCollectionConst.DATABASE
    }

    override fun newRecord() : NavCollection {
        return NavCollection()
    }

    override fun getTableName() : String {
        return NavCollectionConst.TABLE
    }


}