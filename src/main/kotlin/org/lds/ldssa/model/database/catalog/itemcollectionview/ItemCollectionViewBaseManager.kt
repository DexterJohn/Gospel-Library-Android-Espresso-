/*
 * ItemCollectionViewBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.catalog.itemcollectionview

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class ItemCollectionViewBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<ItemCollectionView>(databaseManager) {

    override val allColumns: Array<String> = ItemCollectionViewConst.ALL_COLUMNS
    override val primaryKey = "<NO_PRIMARY_KEY_ON_VIEWS>"
    override val dropSql = ItemCollectionViewManager.DROP_VIEW
    override val createSql = ItemCollectionViewManager.CREATE_VIEW
    override val insertSql = ""
    override val updateSql = ""

    override fun getDatabaseName() : String {
        return ItemCollectionViewConst.DATABASE
    }

    override fun newRecord() : ItemCollectionView {
        return ItemCollectionView()
    }

    override fun getTableName() : String {
        return ItemCollectionViewConst.TABLE
    }


}