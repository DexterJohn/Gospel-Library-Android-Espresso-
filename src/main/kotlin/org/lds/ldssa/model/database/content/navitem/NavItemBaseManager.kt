/*
 * NavItemBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.navitem

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class NavItemBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<NavItem>(databaseManager) {

    override val allColumns: Array<String> = NavItemConst.ALL_COLUMNS
    override val primaryKey = NavItemConst.PRIMARY_KEY_COLUMN
    override val dropSql = NavItemConst.DROP_TABLE
    override val createSql = NavItemConst.CREATE_TABLE
    override val insertSql = NavItemConst.INSERT_STATEMENT
    override val updateSql = NavItemConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return NavItemConst.DATABASE
    }

    override fun newRecord() : NavItem {
        return NavItem()
    }

    override fun getTableName() : String {
        return NavItemConst.TABLE
    }


}