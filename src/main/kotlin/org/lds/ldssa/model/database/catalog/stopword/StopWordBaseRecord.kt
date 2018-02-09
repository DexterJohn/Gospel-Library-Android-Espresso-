/*
 * StopWordBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.stopword

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class StopWordBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var languageId: Long = 0
    open var word: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return StopWordConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return StopWordConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return StopWordConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(StopWordConst.C_LANGUAGE_ID, languageId)
        values.put(StopWordConst.C_WORD, word)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            languageId,
            word)
    }

    open fun copy() : StopWord {
        val copy = StopWord()
        copy.id = id
        copy.languageId = languageId
        copy.word = word
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, languageId)
        statement.bindString(2, word)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, languageId)
        statement.bindString(2, word)
        statement.bindLong(3, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        languageId = values.getAsLong(StopWordConst.C_LANGUAGE_ID)
        word = values.getAsString(StopWordConst.C_WORD)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(StopWordConst.C_ID))
        languageId = cursor.getLong(cursor.getColumnIndexOrThrow(StopWordConst.C_LANGUAGE_ID))
        word = cursor.getString(cursor.getColumnIndexOrThrow(StopWordConst.C_WORD))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}