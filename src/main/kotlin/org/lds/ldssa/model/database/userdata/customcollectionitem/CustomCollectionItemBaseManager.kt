/*
 * CustomCollectionItemBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.userdata.customcollectionitem

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class CustomCollectionItemBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<CustomCollectionItem>(databaseManager) {

    override val allColumns: Array<String> = CustomCollectionItemConst.ALL_COLUMNS
    override val primaryKey = CustomCollectionItemConst.PRIMARY_KEY_COLUMN
    override val dropSql = CustomCollectionItemConst.DROP_TABLE
    override val createSql = CustomCollectionItemConst.CREATE_TABLE
    override val insertSql = CustomCollectionItemConst.INSERT_STATEMENT
    override val updateSql = CustomCollectionItemConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return CustomCollectionItemConst.DATABASE
    }

    override fun newRecord() : CustomCollectionItem {
        return CustomCollectionItem()
    }

    override fun getTableName() : String {
        return CustomCollectionItemConst.TABLE
    }


}