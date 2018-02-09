/*
 * TagViewBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.userdata.tagview

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class TagViewBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<TagView>(databaseManager) {

    override val allColumns: Array<String> = TagViewConst.ALL_COLUMNS
    override val primaryKey = "<NO_PRIMARY_KEY_ON_VIEWS>"
    override val dropSql = TagViewManager.DROP_VIEW
    override val createSql = TagViewManager.CREATE_VIEW
    override val insertSql = ""
    override val updateSql = ""

    override fun getDatabaseName() : String {
        return TagViewConst.DATABASE
    }

    override fun newRecord() : TagView {
        return TagView()
    }

    override fun getTableName() : String {
        return TagViewConst.TABLE
    }


}