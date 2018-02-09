/*
 * SubitemTopicBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.subitemtopic

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class SubitemTopicBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<SubitemTopic>(databaseManager) {

    override val allColumns: Array<String> = SubitemTopicConst.ALL_COLUMNS
    override val primaryKey = SubitemTopicConst.PRIMARY_KEY_COLUMN
    override val dropSql = SubitemTopicConst.DROP_TABLE
    override val createSql = SubitemTopicConst.CREATE_TABLE
    override val insertSql = SubitemTopicConst.INSERT_STATEMENT
    override val updateSql = SubitemTopicConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return SubitemTopicConst.DATABASE
    }

    override fun newRecord() : SubitemTopic {
        return SubitemTopic()
    }

    override fun getTableName() : String {
        return SubitemTopicConst.TABLE
    }


}