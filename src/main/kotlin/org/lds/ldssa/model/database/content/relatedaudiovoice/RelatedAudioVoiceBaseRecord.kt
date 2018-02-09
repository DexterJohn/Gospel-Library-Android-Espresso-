/*
 * RelatedAudioVoiceBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.relatedaudiovoice

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class RelatedAudioVoiceBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var name: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return RelatedAudioVoiceConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return RelatedAudioVoiceConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return RelatedAudioVoiceConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(RelatedAudioVoiceConst.C_NAME, name)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            name)
    }

    open fun copy() : RelatedAudioVoice {
        val copy = RelatedAudioVoice()
        copy.id = id
        copy.name = name
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, name)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, name)
        statement.bindLong(2, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        name = values.getAsString(RelatedAudioVoiceConst.C_NAME)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(RelatedAudioVoiceConst.C_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(RelatedAudioVoiceConst.C_NAME))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}