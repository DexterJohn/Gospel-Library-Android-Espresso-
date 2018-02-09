/*
 * NavParentCollectionQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.navparentcollectionquery

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class NavParentCollectionQueryBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var navSectionId: Long = 0

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
        return NavParentCollectionQueryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return NavParentCollectionQueryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(NavParentCollectionQueryConst.C_ID, id)
        values.put(NavParentCollectionQueryConst.C_NAV_SECTION_ID, navSectionId)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            navSectionId)
    }

    open fun copy() : NavParentCollectionQuery {
        val copy = NavParentCollectionQuery()
        copy.id = id
        copy.navSectionId = navSectionId
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, navSectionId)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, navSectionId)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        id = values.getAsLong(NavParentCollectionQueryConst.C_ID)
        navSectionId = values.getAsLong(NavParentCollectionQueryConst.C_NAV_SECTION_ID)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(NavParentCollectionQueryConst.C_ID))
        navSectionId = cursor.getLong(cursor.getColumnIndexOrThrow(NavParentCollectionQueryConst.C_NAV_SECTION_ID))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}