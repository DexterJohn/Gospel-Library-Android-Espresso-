/*
 * TagBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.userdata.tag

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class TagBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<Tag>(databaseManager) {

    override val allColumns: Array<String> = TagConst.ALL_COLUMNS
    override val primaryKey = TagConst.PRIMARY_KEY_COLUMN
    override val dropSql = TagConst.DROP_TABLE
    override val createSql = TagConst.CREATE_TABLE
    override val insertSql = TagConst.INSERT_STATEMENT
    override val updateSql = TagConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return TagConst.DATABASE
    }

    override fun newRecord() : Tag {
        return Tag()
    }

    override fun getTableName() : String {
        return TagConst.TABLE
    }


}