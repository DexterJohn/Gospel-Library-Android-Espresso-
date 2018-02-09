/*
 * RecentLanguageBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.recentlanguage

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class RecentLanguageBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var languageId: Long = 0
    open var timestamp: org.threeten.bp.LocalDateTime = org.threeten.bp.LocalDateTime.now()

    constructor() {
    }

    override fun getIdColumnName() : String {
        return RecentLanguageConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return RecentLanguageConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return RecentLanguageConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(RecentLanguageConst.C_LANGUAGE_ID, languageId)
        values.put(RecentLanguageConst.C_TIMESTAMP, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(timestamp)!!)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            languageId,
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(timestamp)!!)
    }

    open fun copy() : RecentLanguage {
        val copy = RecentLanguage()
        copy.id = id
        copy.languageId = languageId
        copy.timestamp = timestamp
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, languageId)
        statement.bindLong(2, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(timestamp)!!)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, languageId)
        statement.bindLong(2, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(timestamp)!!)
        statement.bindLong(3, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        languageId = values.getAsLong(RecentLanguageConst.C_LANGUAGE_ID)
        timestamp = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(values.getAsLong(RecentLanguageConst.C_TIMESTAMP))!!
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(RecentLanguageConst.C_ID))
        languageId = cursor.getLong(cursor.getColumnIndexOrThrow(RecentLanguageConst.C_LANGUAGE_ID))
        timestamp = if (!cursor.isNull(cursor.getColumnIndexOrThrow(RecentLanguageConst.C_TIMESTAMP))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(RecentLanguageConst.C_TIMESTAMP)))!! else null!!
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}