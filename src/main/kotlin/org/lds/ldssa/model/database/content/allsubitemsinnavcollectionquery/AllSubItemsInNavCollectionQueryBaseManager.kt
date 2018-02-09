/*
 * AllSubItemsInNavCollectionQueryBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.allsubitemsinnavcollectionquery

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class AllSubItemsInNavCollectionQueryBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<AllSubItemsInNavCollectionQuery>(databaseManager) {

    override val allColumns: Array<String> = AllSubItemsInNavCollectionQueryConst.ALL_COLUMNS
    override val primaryKey = "<NO_PRIMARY_KEY_ON_QUERIES>"
    override val dropSql = ""
    override val createSql = ""
    override val insertSql = ""
    override val updateSql = ""

    override fun getDatabaseName() : String {
        return AllSubItemsInNavCollectionQueryConst.DATABASE
    }

    override fun newRecord() : AllSubItemsInNavCollectionQuery {
        return AllSubItemsInNavCollectionQuery()
    }

    abstract fun getQuery() : String

    override fun getTableName() : String {
        return getQuery()
    }


}