/*
 * BookmarkBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.userdata.bookmark

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class BookmarkBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<Bookmark>(databaseManager) {

    override val allColumns: Array<String> = BookmarkConst.ALL_COLUMNS
    override val primaryKey = BookmarkConst.PRIMARY_KEY_COLUMN
    override val dropSql = BookmarkConst.DROP_TABLE
    override val createSql = BookmarkConst.CREATE_TABLE
    override val insertSql = BookmarkConst.INSERT_STATEMENT
    override val updateSql = BookmarkConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return BookmarkConst.DATABASE
    }

    override fun newRecord() : Bookmark {
        return Bookmark()
    }

    override fun getTableName() : String {
        return BookmarkConst.TABLE
    }


}