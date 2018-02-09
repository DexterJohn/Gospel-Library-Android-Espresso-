/*
 * SubItemMetadataBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.subitemmetadata

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SubItemMetadataBaseRecord  : AndroidBaseRecord {

    open var itemId: Long = 0
    open var subitemId: Long = 0
    open var docId: String = ""
    open var docVersion: Int = 0

    constructor() {
    }

    override fun getAllColumns() : Array<String> {
        return SubItemMetadataConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SubItemMetadataConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SubItemMetadataConst.C_ITEM_ID, itemId)
        values.put(SubItemMetadataConst.C_SUBITEM_ID, subitemId)
        values.put(SubItemMetadataConst.C_DOC_ID, docId)
        values.put(SubItemMetadataConst.C_DOC_VERSION, docVersion.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            itemId,
            subitemId,
            docId,
            docVersion.toLong())
    }

    open fun copy() : SubItemMetadata {
        val copy = SubItemMetadata()
        copy.itemId = itemId
        copy.subitemId = subitemId
        copy.docId = docId
        copy.docVersion = docVersion
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, itemId)
        statement.bindLong(2, subitemId)
        statement.bindString(3, docId)
        statement.bindLong(4, docVersion.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, itemId)
        statement.bindLong(2, subitemId)
        statement.bindString(3, docId)
        statement.bindLong(4, docVersion.toLong())
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        itemId = values.getAsLong(SubItemMetadataConst.C_ITEM_ID)
        subitemId = values.getAsLong(SubItemMetadataConst.C_SUBITEM_ID)
        docId = values.getAsString(SubItemMetadataConst.C_DOC_ID)
        docVersion = values.getAsInteger(SubItemMetadataConst.C_DOC_VERSION)
    }

    override fun setContent(cursor: Cursor) {
        itemId = cursor.getLong(cursor.getColumnIndexOrThrow(SubItemMetadataConst.C_ITEM_ID))
        subitemId = cursor.getLong(cursor.getColumnIndexOrThrow(SubItemMetadataConst.C_SUBITEM_ID))
        docId = cursor.getString(cursor.getColumnIndexOrThrow(SubItemMetadataConst.C_DOC_ID))
        docVersion = cursor.getInt(cursor.getColumnIndexOrThrow(SubItemMetadataConst.C_DOC_VERSION))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }

    override fun getIdColumnName() : String {
        return "NO_PRIMARY_KEY"
    }

    override fun getPrimaryKeyId() : Long {
        return 0
    }

    override fun setPrimaryKeyId(id: Long) {
        // NO_PRIMARY_KEY
    }


}