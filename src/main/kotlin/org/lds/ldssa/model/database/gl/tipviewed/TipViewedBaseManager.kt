/*
 * TipViewedBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.gl.tipviewed

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class TipViewedBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<TipViewed>(databaseManager) {

    override val allColumns: Array<String> = TipViewedConst.ALL_COLUMNS
    override val primaryKey = TipViewedConst.PRIMARY_KEY_COLUMN
    override val dropSql = TipViewedConst.DROP_TABLE
    override val createSql = TipViewedConst.CREATE_TABLE
    override val insertSql = TipViewedConst.INSERT_STATEMENT
    override val updateSql = TipViewedConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return TipViewedConst.DATABASE
    }

    override fun newRecord() : TipViewed {
        return TipViewed()
    }

    override fun getTableName() : String {
        return TipViewedConst.TABLE
    }


}