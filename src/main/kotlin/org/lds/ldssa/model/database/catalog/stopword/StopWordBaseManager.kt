/*
 * StopWordBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.catalog.stopword

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class StopWordBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<StopWord>(databaseManager) {

    override val allColumns: Array<String> = StopWordConst.ALL_COLUMNS
    override val primaryKey = StopWordConst.PRIMARY_KEY_COLUMN
    override val dropSql = StopWordConst.DROP_TABLE
    override val createSql = StopWordConst.CREATE_TABLE
    override val insertSql = StopWordConst.INSERT_STATEMENT
    override val updateSql = StopWordConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return StopWordConst.DATABASE
    }

    override fun newRecord() : StopWord {
        return StopWord()
    }

    override fun getTableName() : String {
        return StopWordConst.TABLE
    }


}