/*
 * LibrarySectionBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.catalog.librarysection

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class LibrarySectionBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<LibrarySection>(databaseManager) {

    override val allColumns: Array<String> = LibrarySectionConst.ALL_COLUMNS
    override val primaryKey = LibrarySectionConst.PRIMARY_KEY_COLUMN
    override val dropSql = LibrarySectionConst.DROP_TABLE
    override val createSql = LibrarySectionConst.CREATE_TABLE
    override val insertSql = LibrarySectionConst.INSERT_STATEMENT
    override val updateSql = LibrarySectionConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return LibrarySectionConst.DATABASE
    }

    override fun newRecord() : LibrarySection {
        return LibrarySection()
    }

    override fun getTableName() : String {
        return LibrarySectionConst.TABLE
    }


}