/*
 * SubItemContentBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.subitemcontent

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class SubItemContentBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<SubItemContent>(databaseManager) {

    override val allColumns: Array<String> = SubItemContentConst.ALL_COLUMNS
    override val primaryKey = SubItemContentConst.PRIMARY_KEY_COLUMN
    override val dropSql = SubItemContentConst.DROP_TABLE
    override val createSql = SubItemContentConst.CREATE_TABLE
    override val insertSql = SubItemContentConst.INSERT_STATEMENT
    override val updateSql = SubItemContentConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return SubItemContentConst.DATABASE
    }

    override fun newRecord() : SubItemContent {
        return SubItemContent()
    }

    override fun getTableName() : String {
        return SubItemContentConst.TABLE
    }


}