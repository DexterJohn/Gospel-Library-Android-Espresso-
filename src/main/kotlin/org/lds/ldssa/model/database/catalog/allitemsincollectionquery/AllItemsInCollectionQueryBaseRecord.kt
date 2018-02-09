/*
 * AllItemsInCollectionQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.allitemsincollectionquery

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class AllItemsInCollectionQueryBaseRecord  : AndroidBaseRecord {

    open var contentItemId: Long = 0

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
        return AllItemsInCollectionQueryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return AllItemsInCollectionQueryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(AllItemsInCollectionQueryConst.C_CONTENT_ITEM_ID, contentItemId)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            contentItemId)
    }

    open fun copy() : AllItemsInCollectionQuery {
        val copy = AllItemsInCollectionQuery()
        copy.contentItemId = contentItemId
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, contentItemId)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, contentItemId)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        contentItemId = values.getAsLong(AllItemsInCollectionQueryConst.C_CONTENT_ITEM_ID)
    }

    override fun setContent(cursor: Cursor) {
        contentItemId = cursor.getLong(cursor.getColumnIndexOrThrow(AllItemsInCollectionQueryConst.C_CONTENT_ITEM_ID))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}