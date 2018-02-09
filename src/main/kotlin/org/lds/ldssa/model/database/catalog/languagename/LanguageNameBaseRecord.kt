/*
 * LanguageNameBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.languagename

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class LanguageNameBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var languageId: Long = 0
    open var localizationLanguageId: Long = 0
    open var name: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return LanguageNameConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return LanguageNameConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return LanguageNameConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(LanguageNameConst.C_LANGUAGE_ID, languageId)
        values.put(LanguageNameConst.C_LOCALIZATION_LANGUAGE_ID, localizationLanguageId)
        values.put(LanguageNameConst.C_NAME, name)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            languageId,
            localizationLanguageId,
            name)
    }

    open fun copy() : LanguageName {
        val copy = LanguageName()
        copy.id = id
        copy.languageId = languageId
        copy.localizationLanguageId = localizationLanguageId
        copy.name = name
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, languageId)
        statement.bindLong(2, localizationLanguageId)
        statement.bindString(3, name)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, languageId)
        statement.bindLong(2, localizationLanguageId)
        statement.bindString(3, name)
        statement.bindLong(4, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        languageId = values.getAsLong(LanguageNameConst.C_LANGUAGE_ID)
        localizationLanguageId = values.getAsLong(LanguageNameConst.C_LOCALIZATION_LANGUAGE_ID)
        name = values.getAsString(LanguageNameConst.C_NAME)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(LanguageNameConst.C_ID))
        languageId = cursor.getLong(cursor.getColumnIndexOrThrow(LanguageNameConst.C_LANGUAGE_ID))
        localizationLanguageId = cursor.getLong(cursor.getColumnIndexOrThrow(LanguageNameConst.C_LOCALIZATION_LANGUAGE_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(LanguageNameConst.C_NAME))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}