/*
 * NavCollectionIndexEntryBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.navcollectionindexentry

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class NavCollectionIndexEntryBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<NavCollectionIndexEntry>(databaseManager) {

    override val allColumns: Array<String> = NavCollectionIndexEntryConst.ALL_COLUMNS
    override val primaryKey = NavCollectionIndexEntryConst.PRIMARY_KEY_COLUMN
    override val dropSql = NavCollectionIndexEntryConst.DROP_TABLE
    override val createSql = NavCollectionIndexEntryConst.CREATE_TABLE
    override val insertSql = NavCollectionIndexEntryConst.INSERT_STATEMENT
    override val updateSql = NavCollectionIndexEntryConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return NavCollectionIndexEntryConst.DATABASE
    }

    override fun newRecord() : NavCollectionIndexEntry {
        return NavCollectionIndexEntry()
    }

    override fun getTableName() : String {
        return NavCollectionIndexEntryConst.TABLE
    }


}