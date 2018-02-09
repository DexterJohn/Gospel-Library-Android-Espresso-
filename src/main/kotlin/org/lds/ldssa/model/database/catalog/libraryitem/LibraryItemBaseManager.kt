/*
 * LibraryItemBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.catalog.libraryitem

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class LibraryItemBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<LibraryItem>(databaseManager) {

    override val allColumns: Array<String> = LibraryItemConst.ALL_COLUMNS
    override val primaryKey = LibraryItemConst.PRIMARY_KEY_COLUMN
    override val dropSql = LibraryItemConst.DROP_TABLE
    override val createSql = LibraryItemConst.CREATE_TABLE
    override val insertSql = LibraryItemConst.INSERT_STATEMENT
    override val updateSql = LibraryItemConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return LibraryItemConst.DATABASE
    }

    override fun newRecord() : LibraryItem {
        return LibraryItem()
    }

    override fun getTableName() : String {
        return LibraryItemConst.TABLE
    }


}