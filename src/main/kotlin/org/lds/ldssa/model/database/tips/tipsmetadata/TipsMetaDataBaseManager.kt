/*
 * TipsMetaDataBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.tips.tipsmetadata

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class TipsMetaDataBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<TipsMetaData>(databaseManager) {

    override val allColumns: Array<String> = TipsMetaDataConst.ALL_COLUMNS
    override val primaryKey = TipsMetaDataConst.PRIMARY_KEY_COLUMN
    override val dropSql = TipsMetaDataConst.DROP_TABLE
    override val createSql = TipsMetaDataConst.CREATE_TABLE
    override val insertSql = TipsMetaDataConst.INSERT_STATEMENT
    override val updateSql = TipsMetaDataConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return TipsMetaDataConst.DATABASE
    }

    override fun newRecord() : TipsMetaData {
        return TipsMetaData()
    }

    override fun getTableName() : String {
        return TipsMetaDataConst.TABLE
    }


}