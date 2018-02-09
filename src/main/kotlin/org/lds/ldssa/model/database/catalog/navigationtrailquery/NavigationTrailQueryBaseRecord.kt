/*
 * NavigationTrailQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.navigationtrailquery

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class NavigationTrailQueryBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var contentItemId: Long = 0
    open var title: String = ""
    open var type: org.lds.ldssa.model.database.types.ScreenSourceType = org.lds.ldssa.model.database.types.ScreenSourceType.CATALOG_DIRECTORY

    constructor() {
    }

    override fun getIdColumnName() : String {
        return ""
    }

    override fun getPrimaryKeyId() : Long {
        return 0
    }

    override fun setPrimaryKeyId(id: Long) {
    }

    override fun getAllColumns() : Array<String> {
        return NavigationTrailQueryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return NavigationTrailQueryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(NavigationTrailQueryConst.C_ID, id)
        values.put(NavigationTrailQueryConst.C_CONTENT_ITEM_ID, contentItemId)
        values.put(NavigationTrailQueryConst.C_TITLE, title)
        values.put(NavigationTrailQueryConst.C_TYPE, type.ordinal.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            contentItemId,
            title,
            type.ordinal.toLong())
    }

    open fun copy() : NavigationTrailQuery {
        val copy = NavigationTrailQuery()
        copy.id = id
        copy.contentItemId = contentItemId
        copy.title = title
        copy.type = type
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, contentItemId)
        statement.bindString(3, title)
        statement.bindLong(4, type.ordinal.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, contentItemId)
        statement.bindString(3, title)
        statement.bindLong(4, type.ordinal.toLong())
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        id = values.getAsLong(NavigationTrailQueryConst.C_ID)
        contentItemId = values.getAsLong(NavigationTrailQueryConst.C_CONTENT_ITEM_ID)
        title = values.getAsString(NavigationTrailQueryConst.C_TITLE)
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.ScreenSourceType::class.java, values.getAsInteger(NavigationTrailQueryConst.C_TYPE), org.lds.ldssa.model.database.types.ScreenSourceType.CATALOG_DIRECTORY)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(NavigationTrailQueryConst.C_ID))
        contentItemId = cursor.getLong(cursor.getColumnIndexOrThrow(NavigationTrailQueryConst.C_CONTENT_ITEM_ID))
        title = cursor.getString(cursor.getColumnIndexOrThrow(NavigationTrailQueryConst.C_TITLE))
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.ScreenSourceType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(NavigationTrailQueryConst.C_TYPE)), org.lds.ldssa.model.database.types.ScreenSourceType.CATALOG_DIRECTORY)
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}