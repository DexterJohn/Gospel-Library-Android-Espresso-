/*
 * CustomCollectionBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.userdata.customcollection

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class CustomCollectionBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<CustomCollection>(databaseManager) {

    override val allColumns: Array<String> = CustomCollectionConst.ALL_COLUMNS
    override val primaryKey = CustomCollectionConst.PRIMARY_KEY_COLUMN
    override val dropSql = CustomCollectionConst.DROP_TABLE
    override val createSql = CustomCollectionConst.CREATE_TABLE
    override val insertSql = CustomCollectionConst.INSERT_STATEMENT
    override val updateSql = CustomCollectionConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return CustomCollectionConst.DATABASE
    }

    override fun newRecord() : CustomCollection {
        return CustomCollection()
    }

    override fun getTableName() : String {
        return CustomCollectionConst.TABLE
    }


}