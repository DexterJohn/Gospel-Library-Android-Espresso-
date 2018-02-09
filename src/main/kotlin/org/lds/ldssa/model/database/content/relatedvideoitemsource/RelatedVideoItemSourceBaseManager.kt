/*
 * RelatedVideoItemSourceBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.relatedvideoitemsource

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class RelatedVideoItemSourceBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<RelatedVideoItemSource>(databaseManager) {

    override val allColumns: Array<String> = RelatedVideoItemSourceConst.ALL_COLUMNS
    override val primaryKey = RelatedVideoItemSourceConst.PRIMARY_KEY_COLUMN
    override val dropSql = RelatedVideoItemSourceConst.DROP_TABLE
    override val createSql = RelatedVideoItemSourceConst.CREATE_TABLE
    override val insertSql = RelatedVideoItemSourceConst.INSERT_STATEMENT
    override val updateSql = RelatedVideoItemSourceConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return RelatedVideoItemSourceConst.DATABASE
    }

    override fun newRecord() : RelatedVideoItemSource {
        return RelatedVideoItemSource()
    }

    override fun getTableName() : String {
        return RelatedVideoItemSourceConst.TABLE
    }


}