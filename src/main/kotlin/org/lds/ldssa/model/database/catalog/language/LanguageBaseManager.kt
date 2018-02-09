/*
 * LanguageBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.catalog.language

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class LanguageBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<Language>(databaseManager) {

    override val allColumns: Array<String> = LanguageConst.ALL_COLUMNS
    override val primaryKey = LanguageConst.PRIMARY_KEY_COLUMN
    override val dropSql = LanguageConst.DROP_TABLE
    override val createSql = LanguageConst.CREATE_TABLE
    override val insertSql = LanguageConst.INSERT_STATEMENT
    override val updateSql = LanguageConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return LanguageConst.DATABASE
    }

    override fun newRecord() : Language {
        return Language()
    }

    override fun getTableName() : String {
        return LanguageConst.TABLE
    }


}