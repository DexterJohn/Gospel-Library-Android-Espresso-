/*
 * ScreenBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.userdata.screen

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class ScreenBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<Screen>(databaseManager) {

    override val allColumns: Array<String> = ScreenConst.ALL_COLUMNS
    override val primaryKey = ScreenConst.PRIMARY_KEY_COLUMN
    override val dropSql = ScreenConst.DROP_TABLE
    override val createSql = ScreenConst.CREATE_TABLE
    override val insertSql = ScreenConst.INSERT_STATEMENT
    override val updateSql = ScreenConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return ScreenConst.DATABASE
    }

    override fun newRecord() : Screen {
        return Screen()
    }

    override fun getTableName() : String {
        return ScreenConst.TABLE
    }


}