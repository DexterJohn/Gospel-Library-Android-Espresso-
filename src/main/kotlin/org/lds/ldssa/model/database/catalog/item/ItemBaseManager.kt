/*
 * ItemBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.catalog.item

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class ItemBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<Item>(databaseManager) {

    override val allColumns: Array<String> = ItemConst.ALL_COLUMNS
    override val primaryKey = ItemConst.PRIMARY_KEY_COLUMN
    override val dropSql = ItemConst.DROP_TABLE
    override val createSql = ItemConst.CREATE_TABLE
    override val insertSql = ItemConst.INSERT_STATEMENT
    override val updateSql = ItemConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return ItemConst.DATABASE
    }

    override fun newRecord() : Item {
        return Item()
    }

    override fun getTableName() : String {
        return ItemConst.TABLE
    }


}