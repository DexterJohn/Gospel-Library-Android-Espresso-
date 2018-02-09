/*
 * AvailableRelatedTypeQueryBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.availablerelatedtypequery

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class AvailableRelatedTypeQueryBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<AvailableRelatedTypeQuery>(databaseManager) {

    override val allColumns: Array<String> = AvailableRelatedTypeQueryConst.ALL_COLUMNS
    override val primaryKey = "<NO_PRIMARY_KEY_ON_QUERIES>"
    override val dropSql = ""
    override val createSql = ""
    override val insertSql = ""
    override val updateSql = ""

    override fun getDatabaseName() : String {
        return AvailableRelatedTypeQueryConst.DATABASE
    }

    override fun newRecord() : AvailableRelatedTypeQuery {
        return AvailableRelatedTypeQuery()
    }

    abstract fun getQuery() : String

    override fun getTableName() : String {
        return getQuery()
    }


}