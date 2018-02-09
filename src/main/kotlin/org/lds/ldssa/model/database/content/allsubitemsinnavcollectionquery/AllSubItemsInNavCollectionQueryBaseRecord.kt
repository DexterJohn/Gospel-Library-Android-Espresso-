/*
 * AllSubItemsInNavCollectionQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.allsubitemsinnavcollectionquery

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class AllSubItemsInNavCollectionQueryBaseRecord  : AndroidBaseRecord {

    open var subItemId: Long = 0

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
        return AllSubItemsInNavCollectionQueryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return AllSubItemsInNavCollectionQueryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(AllSubItemsInNavCollectionQueryConst.C_SUB_ITEM_ID, subItemId)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            subItemId)
    }

    open fun copy() : AllSubItemsInNavCollectionQuery {
        val copy = AllSubItemsInNavCollectionQuery()
        copy.subItemId = subItemId
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, subItemId)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, subItemId)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        subItemId = values.getAsLong(AllSubItemsInNavCollectionQueryConst.C_SUB_ITEM_ID)
    }

    override fun setContent(cursor: Cursor) {
        subItemId = cursor.getLong(cursor.getColumnIndexOrThrow(AllSubItemsInNavCollectionQueryConst.C_SUB_ITEM_ID))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}