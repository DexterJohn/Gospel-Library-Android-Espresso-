/*
 * TipQueryBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.tips.tipquery

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class TipQueryBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<TipQuery>(databaseManager) {

    override val allColumns: Array<String> = TipQueryConst.ALL_COLUMNS
    override val primaryKey = "<NO_PRIMARY_KEY_ON_QUERIES>"
    override val dropSql = ""
    override val createSql = ""
    override val insertSql = ""
    override val updateSql = ""

    override fun getDatabaseName() : String {
        return TipQueryConst.DATABASE
    }

    override fun newRecord() : TipQuery {
        return TipQuery()
    }

    abstract fun getQuery() : String

    override fun getTableName() : String {
        return getQuery()
    }


}