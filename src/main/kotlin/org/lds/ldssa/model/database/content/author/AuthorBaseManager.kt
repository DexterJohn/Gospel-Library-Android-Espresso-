/*
 * AuthorBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.author

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class AuthorBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<Author>(databaseManager) {

    override val allColumns: Array<String> = AuthorConst.ALL_COLUMNS
    override val primaryKey = AuthorConst.PRIMARY_KEY_COLUMN
    override val dropSql = AuthorConst.DROP_TABLE
    override val createSql = AuthorConst.CREATE_TABLE
    override val insertSql = AuthorConst.INSERT_STATEMENT
    override val updateSql = AuthorConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return AuthorConst.DATABASE
    }

    override fun newRecord() : Author {
        return Author()
    }

    override fun getTableName() : String {
        return AuthorConst.TABLE
    }


}