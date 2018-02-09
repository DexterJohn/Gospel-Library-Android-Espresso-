/*
 * RelatedContentItemBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.relatedcontentitem

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class RelatedContentItemBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<RelatedContentItem>(databaseManager) {

    override val allColumns: Array<String> = RelatedContentItemConst.ALL_COLUMNS
    override val primaryKey = RelatedContentItemConst.PRIMARY_KEY_COLUMN
    override val dropSql = RelatedContentItemConst.DROP_TABLE
    override val createSql = RelatedContentItemConst.CREATE_TABLE
    override val insertSql = RelatedContentItemConst.INSERT_STATEMENT
    override val updateSql = RelatedContentItemConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return RelatedContentItemConst.DATABASE
    }

    override fun newRecord() : RelatedContentItem {
        return RelatedContentItem()
    }

    override fun getTableName() : String {
        return RelatedContentItemConst.TABLE
    }


}