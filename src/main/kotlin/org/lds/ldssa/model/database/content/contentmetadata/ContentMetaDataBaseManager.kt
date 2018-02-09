/*
 * ContentMetaDataBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.contentmetadata

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class ContentMetaDataBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<ContentMetaData>(databaseManager) {

    override val allColumns: Array<String> = ContentMetaDataConst.ALL_COLUMNS
    override val primaryKey = ContentMetaDataConst.PRIMARY_KEY_COLUMN
    override val dropSql = ContentMetaDataConst.DROP_TABLE
    override val createSql = ContentMetaDataConst.CREATE_TABLE
    override val insertSql = ContentMetaDataConst.INSERT_STATEMENT
    override val updateSql = ContentMetaDataConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return ContentMetaDataConst.DATABASE
    }

    override fun newRecord() : ContentMetaData {
        return ContentMetaData()
    }

    override fun getTableName() : String {
        return ContentMetaDataConst.TABLE
    }


}