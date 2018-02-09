/*
 * NavSectionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.navsection

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class NavSectionBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var navCollectionId: Long = 0
    open var position: Int = 0
    open var indentLevel: Int = 0
    open var title: String? = null

    constructor() {
    }

    override fun getIdColumnName() : String {
        return NavSectionConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return NavSectionConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return NavSectionConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(NavSectionConst.C_NAV_COLLECTION_ID, navCollectionId)
        values.put(NavSectionConst.C_POSITION, position.toLong())
        values.put(NavSectionConst.C_INDENT_LEVEL, indentLevel.toLong())
        values.put(NavSectionConst.C_TITLE, title)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            navCollectionId,
            position.toLong(),
            indentLevel.toLong(),
            title)
    }

    open fun copy() : NavSection {
        val copy = NavSection()
        copy.id = id
        copy.navCollectionId = navCollectionId
        copy.position = position
        copy.indentLevel = indentLevel
        copy.title = title
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, navCollectionId)
        statement.bindLong(2, position.toLong())
        statement.bindLong(3, indentLevel.toLong())
        if (title != null) {
            statement.bindString(4, title!!)
        } else {
            statement.bindNull(4)
        }
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, navCollectionId)
        statement.bindLong(2, position.toLong())
        statement.bindLong(3, indentLevel.toLong())
        if (title != null) {
            statement.bindString(4, title!!)
        } else {
            statement.bindNull(4)
        }
        statement.bindLong(5, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        navCollectionId = values.getAsLong(NavSectionConst.C_NAV_COLLECTION_ID)
        position = values.getAsInteger(NavSectionConst.C_POSITION)
        indentLevel = values.getAsInteger(NavSectionConst.C_INDENT_LEVEL)
        title = values.getAsString(NavSectionConst.C_TITLE)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(NavSectionConst.C_ID))
        navCollectionId = cursor.getLong(cursor.getColumnIndexOrThrow(NavSectionConst.C_NAV_COLLECTION_ID))
        position = cursor.getInt(cursor.getColumnIndexOrThrow(NavSectionConst.C_POSITION))
        indentLevel = cursor.getInt(cursor.getColumnIndexOrThrow(NavSectionConst.C_INDENT_LEVEL))
        title = if (!cursor.isNull(cursor.getColumnIndexOrThrow(NavSectionConst.C_TITLE))) cursor.getString(cursor.getColumnIndexOrThrow(NavSectionConst.C_TITLE)) else null
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}