/*
 * SubItemBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.subitem

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class SubItemBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<SubItem>(databaseManager) {

    override val allColumns: Array<String> = SubItemConst.ALL_COLUMNS
    override val primaryKey = SubItemConst.PRIMARY_KEY_COLUMN
    override val dropSql = SubItemConst.DROP_TABLE
    override val createSql = SubItemConst.CREATE_TABLE
    override val insertSql = SubItemConst.INSERT_STATEMENT
    override val updateSql = SubItemConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return SubItemConst.DATABASE
    }

    override fun newRecord() : SubItem {
        return SubItem()
    }

    override fun getTableName() : String {
        return SubItemConst.TABLE
    }


}