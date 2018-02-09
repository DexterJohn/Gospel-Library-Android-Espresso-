/*
 * TipsAppVersionBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.tips.tipsappversion

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class TipsAppVersionBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<TipsAppVersion>(databaseManager) {

    override val allColumns: Array<String> = TipsAppVersionConst.ALL_COLUMNS
    override val primaryKey = TipsAppVersionConst.PRIMARY_KEY_COLUMN
    override val dropSql = TipsAppVersionConst.DROP_TABLE
    override val createSql = TipsAppVersionConst.CREATE_TABLE
    override val insertSql = TipsAppVersionConst.INSERT_STATEMENT
    override val updateSql = TipsAppVersionConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return TipsAppVersionConst.DATABASE
    }

    override fun newRecord() : TipsAppVersion {
        return TipsAppVersion()
    }

    override fun getTableName() : String {
        return TipsAppVersionConst.TABLE
    }


}