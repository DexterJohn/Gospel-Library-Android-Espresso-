/*
 * NavSectionBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.navsection

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class NavSectionBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<NavSection>(databaseManager) {

    override val allColumns: Array<String> = NavSectionConst.ALL_COLUMNS
    override val primaryKey = NavSectionConst.PRIMARY_KEY_COLUMN
    override val dropSql = NavSectionConst.DROP_TABLE
    override val createSql = NavSectionConst.CREATE_TABLE
    override val insertSql = NavSectionConst.INSERT_STATEMENT
    override val updateSql = NavSectionConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return NavSectionConst.DATABASE
    }

    override fun newRecord() : NavSection {
        return NavSection()
    }

    override fun getTableName() : String {
        return NavSectionConst.TABLE
    }


}