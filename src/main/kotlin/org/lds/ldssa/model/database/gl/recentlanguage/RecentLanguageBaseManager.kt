/*
 * RecentLanguageBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.gl.recentlanguage

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class RecentLanguageBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<RecentLanguage>(databaseManager) {

    override val allColumns: Array<String> = RecentLanguageConst.ALL_COLUMNS
    override val primaryKey = RecentLanguageConst.PRIMARY_KEY_COLUMN
    override val dropSql = RecentLanguageConst.DROP_TABLE
    override val createSql = RecentLanguageConst.CREATE_TABLE
    override val insertSql = RecentLanguageConst.INSERT_STATEMENT
    override val updateSql = RecentLanguageConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return RecentLanguageConst.DATABASE
    }

    override fun newRecord() : RecentLanguage {
        return RecentLanguage()
    }

    override fun getTableName() : String {
        return RecentLanguageConst.TABLE
    }


}