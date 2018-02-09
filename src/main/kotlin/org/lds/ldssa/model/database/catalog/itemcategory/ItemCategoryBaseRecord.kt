/*
 * ItemCategoryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.catalog.itemcategory

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class ItemCategoryBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var name: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return ItemCategoryConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return ItemCategoryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return ItemCategoryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(ItemCategoryConst.C_NAME, name)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            name)
    }

    open fun copy() : ItemCategory {
        val copy = ItemCategory()
        copy.id = id
        copy.name = name
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, name)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, name)
        statement.bindLong(2, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        name = values.getAsString(ItemCategoryConst.C_NAME)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(ItemCategoryConst.C_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(ItemCategoryConst.C_NAME))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}