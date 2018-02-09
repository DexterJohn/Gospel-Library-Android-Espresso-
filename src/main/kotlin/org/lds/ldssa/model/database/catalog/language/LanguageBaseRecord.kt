/*
 * LanguageBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.language

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class LanguageBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var ldsLanguageCode: String = ""
    open var iso6393: String = ""
    open var bcp47: String? = null
    open var rootLibraryCollectionId: Long = 0
    open var rootLibraryCollectionExternalId: Long = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return LanguageConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return LanguageConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return LanguageConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(LanguageConst.C_LDS_LANGUAGE_CODE, ldsLanguageCode)
        values.put(LanguageConst.C_ISO6393, iso6393)
        values.put(LanguageConst.C_BCP47, bcp47)
        values.put(LanguageConst.C_ROOT_LIBRARY_COLLECTION_ID, rootLibraryCollectionId)
        values.put(LanguageConst.C_ROOT_LIBRARY_COLLECTION_EXTERNAL_ID, rootLibraryCollectionExternalId)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            ldsLanguageCode,
            iso6393,
            bcp47,
            rootLibraryCollectionId,
            rootLibraryCollectionExternalId)
    }

    open fun copy() : Language {
        val copy = Language()
        copy.id = id
        copy.ldsLanguageCode = ldsLanguageCode
        copy.iso6393 = iso6393
        copy.bcp47 = bcp47
        copy.rootLibraryCollectionId = rootLibraryCollectionId
        copy.rootLibraryCollectionExternalId = rootLibraryCollectionExternalId
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, ldsLanguageCode)
        statement.bindString(2, iso6393)
        if (bcp47 != null) {
            statement.bindString(3, bcp47!!)
        } else {
            statement.bindNull(3)
        }
        statement.bindLong(4, rootLibraryCollectionId)
        statement.bindLong(5, rootLibraryCollectionExternalId)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, ldsLanguageCode)
        statement.bindString(2, iso6393)
        if (bcp47 != null) {
            statement.bindString(3, bcp47!!)
        } else {
            statement.bindNull(3)
        }
        statement.bindLong(4, rootLibraryCollectionId)
        statement.bindLong(5, rootLibraryCollectionExternalId)
        statement.bindLong(6, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        ldsLanguageCode = values.getAsString(LanguageConst.C_LDS_LANGUAGE_CODE)
        iso6393 = values.getAsString(LanguageConst.C_ISO6393)
        bcp47 = values.getAsString(LanguageConst.C_BCP47)
        rootLibraryCollectionId = values.getAsLong(LanguageConst.C_ROOT_LIBRARY_COLLECTION_ID)
        rootLibraryCollectionExternalId = values.getAsLong(LanguageConst.C_ROOT_LIBRARY_COLLECTION_EXTERNAL_ID)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(LanguageConst.C_ID))
        ldsLanguageCode = cursor.getString(cursor.getColumnIndexOrThrow(LanguageConst.C_LDS_LANGUAGE_CODE))
        iso6393 = cursor.getString(cursor.getColumnIndexOrThrow(LanguageConst.C_ISO6393))
        bcp47 = if (!cursor.isNull(cursor.getColumnIndexOrThrow(LanguageConst.C_BCP47))) cursor.getString(cursor.getColumnIndexOrThrow(LanguageConst.C_BCP47)) else null
        rootLibraryCollectionId = cursor.getLong(cursor.getColumnIndexOrThrow(LanguageConst.C_ROOT_LIBRARY_COLLECTION_ID))
        rootLibraryCollectionExternalId = cursor.getLong(cursor.getColumnIndexOrThrow(LanguageConst.C_ROOT_LIBRARY_COLLECTION_EXTERNAL_ID))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}