/*
 * CustomCollectionItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.customcollectionitem

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class CustomCollectionItemBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var customCollectionId: Long = 0
    open var orderPosition: Int = 0
    open var catalogItemExternalId: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return CustomCollectionItemConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return CustomCollectionItemConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return CustomCollectionItemConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(CustomCollectionItemConst.C_CUSTOM_COLLECTION_ID, customCollectionId)
        values.put(CustomCollectionItemConst.C_ORDER_POSITION, orderPosition.toLong())
        values.put(CustomCollectionItemConst.C_CATALOG_ITEM_EXTERNAL_ID, catalogItemExternalId)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            customCollectionId,
            orderPosition.toLong(),
            catalogItemExternalId)
    }

    open fun copy() : CustomCollectionItem {
        val copy = CustomCollectionItem()
        copy.id = id
        copy.customCollectionId = customCollectionId
        copy.orderPosition = orderPosition
        copy.catalogItemExternalId = catalogItemExternalId
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, customCollectionId)
        statement.bindLong(2, orderPosition.toLong())
        statement.bindString(3, catalogItemExternalId)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, customCollectionId)
        statement.bindLong(2, orderPosition.toLong())
        statement.bindString(3, catalogItemExternalId)
        statement.bindLong(4, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        customCollectionId = values.getAsLong(CustomCollectionItemConst.C_CUSTOM_COLLECTION_ID)
        orderPosition = values.getAsInteger(CustomCollectionItemConst.C_ORDER_POSITION)
        catalogItemExternalId = values.getAsString(CustomCollectionItemConst.C_CATALOG_ITEM_EXTERNAL_ID)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(CustomCollectionItemConst.C_ID))
        customCollectionId = cursor.getLong(cursor.getColumnIndexOrThrow(CustomCollectionItemConst.C_CUSTOM_COLLECTION_ID))
        orderPosition = cursor.getInt(cursor.getColumnIndexOrThrow(CustomCollectionItemConst.C_ORDER_POSITION))
        catalogItemExternalId = cursor.getString(cursor.getColumnIndexOrThrow(CustomCollectionItemConst.C_CATALOG_ITEM_EXTERNAL_ID))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}