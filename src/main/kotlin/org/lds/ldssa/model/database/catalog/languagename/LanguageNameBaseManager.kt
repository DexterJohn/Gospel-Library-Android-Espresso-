/*
 * LanguageNameBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.catalog.languagename

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class LanguageNameBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<LanguageName>(databaseManager) {

    override val allColumns: Array<String> = LanguageNameConst.ALL_COLUMNS
    override val primaryKey = LanguageNameConst.PRIMARY_KEY_COLUMN
    override val dropSql = LanguageNameConst.DROP_TABLE
    override val createSql = LanguageNameConst.CREATE_TABLE
    override val insertSql = LanguageNameConst.INSERT_STATEMENT
    override val updateSql = LanguageNameConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return LanguageNameConst.DATABASE
    }

    override fun newRecord() : LanguageName {
        return LanguageName()
    }

    override fun getTableName() : String {
        return LanguageNameConst.TABLE
    }


}