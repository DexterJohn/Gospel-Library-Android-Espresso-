/*
 * ItemCategoryBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.catalog.itemcategory

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class ItemCategoryBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<ItemCategory>(databaseManager) {

    override val allColumns: Array<String> = ItemCategoryConst.ALL_COLUMNS
    override val primaryKey = ItemCategoryConst.PRIMARY_KEY_COLUMN
    override val dropSql = ItemCategoryConst.DROP_TABLE
    override val createSql = ItemCategoryConst.CREATE_TABLE
    override val insertSql = ItemCategoryConst.INSERT_STATEMENT
    override val updateSql = ItemCategoryConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return ItemCategoryConst.DATABASE
    }

    override fun newRecord() : ItemCategory {
        return ItemCategory()
    }

    override fun getTableName() : String {
        return ItemCategoryConst.TABLE
    }


}