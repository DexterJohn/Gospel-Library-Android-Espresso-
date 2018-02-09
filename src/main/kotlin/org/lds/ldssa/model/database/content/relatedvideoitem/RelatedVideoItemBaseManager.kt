/*
 * RelatedVideoItemBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.relatedvideoitem

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class RelatedVideoItemBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<RelatedVideoItem>(databaseManager) {

    override val allColumns: Array<String> = RelatedVideoItemConst.ALL_COLUMNS
    override val primaryKey = RelatedVideoItemConst.PRIMARY_KEY_COLUMN
    override val dropSql = RelatedVideoItemConst.DROP_TABLE
    override val createSql = RelatedVideoItemConst.CREATE_TABLE
    override val insertSql = RelatedVideoItemConst.INSERT_STATEMENT
    override val updateSql = RelatedVideoItemConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return RelatedVideoItemConst.DATABASE
    }

    override fun newRecord() : RelatedVideoItem {
        return RelatedVideoItem()
    }

    override fun getTableName() : String {
        return RelatedVideoItemConst.TABLE
    }


}