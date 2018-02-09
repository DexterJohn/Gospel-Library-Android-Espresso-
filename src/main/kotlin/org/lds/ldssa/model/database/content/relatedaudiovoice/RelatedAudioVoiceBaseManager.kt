/*
 * RelatedAudioVoiceBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.content.relatedaudiovoice

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class RelatedAudioVoiceBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<RelatedAudioVoice>(databaseManager) {

    override val allColumns: Array<String> = RelatedAudioVoiceConst.ALL_COLUMNS
    override val primaryKey = RelatedAudioVoiceConst.PRIMARY_KEY_COLUMN
    override val dropSql = RelatedAudioVoiceConst.DROP_TABLE
    override val createSql = RelatedAudioVoiceConst.CREATE_TABLE
    override val insertSql = RelatedAudioVoiceConst.INSERT_STATEMENT
    override val updateSql = RelatedAudioVoiceConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return RelatedAudioVoiceConst.DATABASE
    }

    override fun newRecord() : RelatedAudioVoice {
        return RelatedAudioVoice()
    }

    override fun getTableName() : String {
        return RelatedAudioVoiceConst.TABLE
    }


}