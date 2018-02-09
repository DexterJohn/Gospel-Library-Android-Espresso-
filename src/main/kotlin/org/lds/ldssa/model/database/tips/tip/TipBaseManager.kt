/*
 * TipBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.tips.tip

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class TipBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<Tip>(databaseManager) {

    override val allColumns: Array<String> = TipConst.ALL_COLUMNS
    override val primaryKey = TipConst.PRIMARY_KEY_COLUMN
    override val dropSql = TipConst.DROP_TABLE
    override val createSql = TipConst.CREATE_TABLE
    override val insertSql = TipConst.INSERT_STATEMENT
    override val updateSql = TipConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return TipConst.DATABASE
    }

    override fun newRecord() : Tip {
        return Tip()
    }

    override fun getTableName() : String {
        return TipConst.TABLE
    }


}