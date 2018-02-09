/*
 * AuthorRoleBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.authorrole

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class AuthorRoleBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<AuthorRole>(databaseManager) {

    override val allColumns: Array<String> = AuthorRoleConst.ALL_COLUMNS
    override val primaryKey = AuthorRoleConst.PRIMARY_KEY_COLUMN
    override val dropSql = AuthorRoleConst.DROP_TABLE
    override val createSql = AuthorRoleConst.CREATE_TABLE
    override val insertSql = AuthorRoleConst.INSERT_STATEMENT
    override val updateSql = AuthorRoleConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return AuthorRoleConst.DATABASE
    }

    override fun newRecord() : AuthorRole {
        return AuthorRole()
    }

    override fun getTableName() : String {
        return AuthorRoleConst.TABLE
    }


}