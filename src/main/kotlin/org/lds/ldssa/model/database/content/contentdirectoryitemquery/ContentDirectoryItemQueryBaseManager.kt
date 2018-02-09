/*
 * ContentDirectoryItemQueryBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.contentdirectoryitemquery

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class ContentDirectoryItemQueryBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<ContentDirectoryItemQuery>(databaseManager) {

    override val allColumns: Array<String> = ContentDirectoryItemQueryConst.ALL_COLUMNS
    override val primaryKey = "<NO_PRIMARY_KEY_ON_QUERIES>"
    override val dropSql = ""
    override val createSql = ""
    override val insertSql = ""
    override val updateSql = ""

    override fun getDatabaseName() : String {
        return ContentDirectoryItemQueryConst.DATABASE
    }

    override fun newRecord() : ContentDirectoryItemQuery {
        return ContentDirectoryItemQuery()
    }

    abstract fun getQuery() : String

    override fun getTableName() : String {
        return getQuery()
    }


}