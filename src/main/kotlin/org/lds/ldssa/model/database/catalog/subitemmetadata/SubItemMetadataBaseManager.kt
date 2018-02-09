/*
 * SubItemMetadataBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.catalog.subitemmetadata

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class SubItemMetadataBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<SubItemMetadata>(databaseManager) {

    override val allColumns: Array<String> = SubItemMetadataConst.ALL_COLUMNS
    override val primaryKey = "NO_PRIMARY_KEY"
    override val dropSql = SubItemMetadataConst.DROP_TABLE
    override val createSql = SubItemMetadataConst.CREATE_TABLE
    override val insertSql = SubItemMetadataConst.INSERT_STATEMENT
    override val updateSql = SubItemMetadataConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return SubItemMetadataConst.DATABASE
    }

    override fun newRecord() : SubItemMetadata {
        return SubItemMetadata()
    }

    override fun getTableName() : String {
        return SubItemMetadataConst.TABLE
    }


}