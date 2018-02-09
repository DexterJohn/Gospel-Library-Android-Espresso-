/*
 * LinkBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.userdata.link

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class LinkBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<Link>(databaseManager) {

    override val allColumns: Array<String> = LinkConst.ALL_COLUMNS
    override val primaryKey = LinkConst.PRIMARY_KEY_COLUMN
    override val dropSql = LinkConst.DROP_TABLE
    override val createSql = LinkConst.CREATE_TABLE
    override val insertSql = LinkConst.INSERT_STATEMENT
    override val updateSql = LinkConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return LinkConst.DATABASE
    }

    override fun newRecord() : Link {
        return Link()
    }

    override fun getTableName() : String {
        return LinkConst.TABLE
    }


}