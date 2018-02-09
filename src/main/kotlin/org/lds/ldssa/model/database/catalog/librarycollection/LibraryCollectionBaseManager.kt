/*
 * LibraryCollectionBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.catalog.librarycollection

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class LibraryCollectionBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<LibraryCollection>(databaseManager) {

    override val allColumns: Array<String> = LibraryCollectionConst.ALL_COLUMNS
    override val primaryKey = LibraryCollectionConst.PRIMARY_KEY_COLUMN
    override val dropSql = LibraryCollectionConst.DROP_TABLE
    override val createSql = LibraryCollectionConst.CREATE_TABLE
    override val insertSql = LibraryCollectionConst.INSERT_STATEMENT
    override val updateSql = LibraryCollectionConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return LibraryCollectionConst.DATABASE
    }

    override fun newRecord() : LibraryCollection {
        return LibraryCollection()
    }

    override fun getTableName() : String {
        return LibraryCollectionConst.TABLE
    }


}