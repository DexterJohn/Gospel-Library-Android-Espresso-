/*
 * TipViewedBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.gl.tipviewed

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class TipViewedBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var tipId: Long = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return TipViewedConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return TipViewedConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return TipViewedConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(TipViewedConst.C_TIP_ID, tipId)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            tipId)
    }

    open fun copy() : TipViewed {
        val copy = TipViewed()
        copy.id = id
        copy.tipId = tipId
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, tipId)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, tipId)
        statement.bindLong(2, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        tipId = values.getAsLong(TipViewedConst.C_TIP_ID)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(TipViewedConst.C_ID))
        tipId = cursor.getLong(cursor.getColumnIndexOrThrow(TipViewedConst.C_TIP_ID))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}