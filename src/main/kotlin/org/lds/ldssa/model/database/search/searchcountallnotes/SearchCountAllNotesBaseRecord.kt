/*
 * SearchCountAllNotesBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.search.searchcountallnotes

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SearchCountAllNotesBaseRecord  : AndroidBaseRecord {

    open var screenId: Long = 0
    open var noteCount: Long = 0
    open var phraseCount: Long = 0
    open var keywordCount: Long = 0

    constructor() {
    }

    override fun getAllColumns() : Array<String> {
        return SearchCountAllNotesConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SearchCountAllNotesConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SearchCountAllNotesConst.C_SCREEN_ID, screenId)
        values.put(SearchCountAllNotesConst.C_NOTE_COUNT, noteCount)
        values.put(SearchCountAllNotesConst.C_PHRASE_COUNT, phraseCount)
        values.put(SearchCountAllNotesConst.C_KEYWORD_COUNT, keywordCount)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            screenId,
            noteCount,
            phraseCount,
            keywordCount)
    }

    open fun copy() : SearchCountAllNotes {
        val copy = SearchCountAllNotes()
        copy.screenId = screenId
        copy.noteCount = noteCount
        copy.phraseCount = phraseCount
        copy.keywordCount = keywordCount
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, screenId)
        statement.bindLong(2, noteCount)
        statement.bindLong(3, phraseCount)
        statement.bindLong(4, keywordCount)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, screenId)
        statement.bindLong(2, noteCount)
        statement.bindLong(3, phraseCount)
        statement.bindLong(4, keywordCount)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        screenId = values.getAsLong(SearchCountAllNotesConst.C_SCREEN_ID)
        noteCount = values.getAsLong(SearchCountAllNotesConst.C_NOTE_COUNT)
        phraseCount = values.getAsLong(SearchCountAllNotesConst.C_PHRASE_COUNT)
        keywordCount = values.getAsLong(SearchCountAllNotesConst.C_KEYWORD_COUNT)
    }

    override fun setContent(cursor: Cursor) {
        screenId = cursor.getLong(cursor.getColumnIndexOrThrow(SearchCountAllNotesConst.C_SCREEN_ID))
        noteCount = cursor.getLong(cursor.getColumnIndexOrThrow(SearchCountAllNotesConst.C_NOTE_COUNT))
        phraseCount = cursor.getLong(cursor.getColumnIndexOrThrow(SearchCountAllNotesConst.C_PHRASE_COUNT))
        keywordCount = cursor.getLong(cursor.getColumnIndexOrThrow(SearchCountAllNotesConst.C_KEYWORD_COUNT))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }

    override fun getIdColumnName() : String {
        return "NO_PRIMARY_KEY"
    }

    override fun getPrimaryKeyId() : Long {
        return 0
    }

    override fun setPrimaryKeyId(id: Long) {
        // NO_PRIMARY_KEY
    }


}