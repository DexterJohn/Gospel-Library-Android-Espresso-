/*
 * CatalogSourceBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.catalogsource

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class CatalogSourceBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var name: String = ""
    open var sourceType: org.lds.ldssa.model.database.types.CatalogItemSourceType = org.lds.ldssa.model.database.types.CatalogItemSourceType.DEFAULT

    constructor() {
    }

    override fun getIdColumnName() : String {
        return CatalogSourceConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return CatalogSourceConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return CatalogSourceConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(CatalogSourceConst.C_NAME, name)
        values.put(CatalogSourceConst.C_SOURCE_TYPE, sourceType.ordinal.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            name,
            sourceType.ordinal.toLong())
    }

    open fun copy() : CatalogSource {
        val copy = CatalogSource()
        copy.id = id
        copy.name = name
        copy.sourceType = sourceType
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, name)
        statement.bindLong(2, sourceType.ordinal.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, name)
        statement.bindLong(2, sourceType.ordinal.toLong())
        statement.bindLong(3, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        name = values.getAsString(CatalogSourceConst.C_NAME)
        sourceType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.CatalogItemSourceType::class.java, values.getAsInteger(CatalogSourceConst.C_SOURCE_TYPE), org.lds.ldssa.model.database.types.CatalogItemSourceType.DEFAULT)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(CatalogSourceConst.C_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(CatalogSourceConst.C_NAME))
        sourceType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.CatalogItemSourceType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(CatalogSourceConst.C_SOURCE_TYPE)), org.lds.ldssa.model.database.types.CatalogItemSourceType.DEFAULT)
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}