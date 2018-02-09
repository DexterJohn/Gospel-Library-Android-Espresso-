/*
 * RoleCatalogBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.rolecatalog

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class RoleCatalogBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var name: String = ""
    open var baseUrl: String = ""
    open var catalogItemSourceType: org.lds.ldssa.model.database.types.CatalogItemSourceType = org.lds.ldssa.model.database.types.CatalogItemSourceType.DEFAULT
    open var version: Int = 0
    open var installed: Boolean = false

    constructor() {
    }

    override fun getIdColumnName() : String {
        return RoleCatalogConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return RoleCatalogConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return RoleCatalogConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(RoleCatalogConst.C_NAME, name)
        values.put(RoleCatalogConst.C_BASE_URL, baseUrl)
        values.put(RoleCatalogConst.C_CATALOG_ITEM_SOURCE_TYPE, catalogItemSourceType.ordinal.toLong())
        values.put(RoleCatalogConst.C_VERSION, version.toLong())
        values.put(RoleCatalogConst.C_INSTALLED, if (installed) 1L else 0L)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            name,
            baseUrl,
            catalogItemSourceType.ordinal.toLong(),
            version.toLong(),
            if (installed) 1L else 0L)
    }

    open fun copy() : RoleCatalog {
        val copy = RoleCatalog()
        copy.id = id
        copy.name = name
        copy.baseUrl = baseUrl
        copy.catalogItemSourceType = catalogItemSourceType
        copy.version = version
        copy.installed = installed
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, name)
        statement.bindString(2, baseUrl)
        statement.bindLong(3, catalogItemSourceType.ordinal.toLong())
        statement.bindLong(4, version.toLong())
        statement.bindLong(5, if (installed) 1L else 0L)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, name)
        statement.bindString(2, baseUrl)
        statement.bindLong(3, catalogItemSourceType.ordinal.toLong())
        statement.bindLong(4, version.toLong())
        statement.bindLong(5, if (installed) 1L else 0L)
        statement.bindLong(6, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        name = values.getAsString(RoleCatalogConst.C_NAME)
        baseUrl = values.getAsString(RoleCatalogConst.C_BASE_URL)
        catalogItemSourceType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.CatalogItemSourceType::class.java, values.getAsInteger(RoleCatalogConst.C_CATALOG_ITEM_SOURCE_TYPE), org.lds.ldssa.model.database.types.CatalogItemSourceType.DEFAULT)
        version = values.getAsInteger(RoleCatalogConst.C_VERSION)
        installed = values.getAsBoolean(RoleCatalogConst.C_INSTALLED)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(RoleCatalogConst.C_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(RoleCatalogConst.C_NAME))
        baseUrl = cursor.getString(cursor.getColumnIndexOrThrow(RoleCatalogConst.C_BASE_URL))
        catalogItemSourceType = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.CatalogItemSourceType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(RoleCatalogConst.C_CATALOG_ITEM_SOURCE_TYPE)), org.lds.ldssa.model.database.types.CatalogItemSourceType.DEFAULT)
        version = cursor.getInt(cursor.getColumnIndexOrThrow(RoleCatalogConst.C_VERSION))
        installed = cursor.getInt(cursor.getColumnIndexOrThrow(RoleCatalogConst.C_INSTALLED)) != 0
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}