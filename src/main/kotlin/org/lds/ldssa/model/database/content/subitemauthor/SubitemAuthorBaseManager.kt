/*
 * SubitemAuthorBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.subitemauthor

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class SubitemAuthorBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<SubitemAuthor>(databaseManager) {

    override val allColumns: Array<String> = SubitemAuthorConst.ALL_COLUMNS
    override val primaryKey = SubitemAuthorConst.PRIMARY_KEY_COLUMN
    override val dropSql = SubitemAuthorConst.DROP_TABLE
    override val createSql = SubitemAuthorConst.CREATE_TABLE
    override val insertSql = SubitemAuthorConst.INSERT_STATEMENT
    override val updateSql = SubitemAuthorConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return SubitemAuthorConst.DATABASE
    }

    override fun newRecord() : SubitemAuthor {
        return SubitemAuthor()
    }

    override fun getTableName() : String {
        return SubitemAuthorConst.TABLE
    }


}