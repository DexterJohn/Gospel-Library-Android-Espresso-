/*
 * RoleBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.role

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class RoleBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<Role>(databaseManager) {

    override val allColumns: Array<String> = RoleConst.ALL_COLUMNS
    override val primaryKey = RoleConst.PRIMARY_KEY_COLUMN
    override val dropSql = RoleConst.DROP_TABLE
    override val createSql = RoleConst.CREATE_TABLE
    override val insertSql = RoleConst.INSERT_STATEMENT
    override val updateSql = RoleConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return RoleConst.DATABASE
    }

    override fun newRecord() : Role {
        return Role()
    }

    override fun getTableName() : String {
        return RoleConst.TABLE
    }


}