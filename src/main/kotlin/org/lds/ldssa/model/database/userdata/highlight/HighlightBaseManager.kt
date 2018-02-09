/*
 * HighlightBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.userdata.highlight

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class HighlightBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<Highlight>(databaseManager) {

    override val allColumns: Array<String> = HighlightConst.ALL_COLUMNS
    override val primaryKey = HighlightConst.PRIMARY_KEY_COLUMN
    override val dropSql = HighlightConst.DROP_TABLE
    override val createSql = HighlightConst.CREATE_TABLE
    override val insertSql = HighlightConst.INSERT_STATEMENT
    override val updateSql = HighlightConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return HighlightConst.DATABASE
    }

    override fun newRecord() : Highlight {
        return Highlight()
    }

    override fun getTableName() : String {
        return HighlightConst.TABLE
    }


}