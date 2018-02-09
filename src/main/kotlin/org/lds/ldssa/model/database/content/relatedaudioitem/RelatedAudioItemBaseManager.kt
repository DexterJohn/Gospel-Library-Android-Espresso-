/*
 * RelatedAudioItemBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.relatedaudioitem

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class RelatedAudioItemBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<RelatedAudioItem>(databaseManager) {

    override val allColumns: Array<String> = RelatedAudioItemConst.ALL_COLUMNS
    override val primaryKey = RelatedAudioItemConst.PRIMARY_KEY_COLUMN
    override val dropSql = RelatedAudioItemConst.DROP_TABLE
    override val createSql = RelatedAudioItemConst.CREATE_TABLE
    override val insertSql = RelatedAudioItemConst.INSERT_STATEMENT
    override val updateSql = RelatedAudioItemConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return RelatedAudioItemConst.DATABASE
    }

    override fun newRecord() : RelatedAudioItem {
        return RelatedAudioItem()
    }

    override fun getTableName() : String {
        return RelatedAudioItemConst.TABLE
    }


}