/*
 * TipsAppVersionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.tips.tipsappversion

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class TipsAppVersionBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var title: String = ""
    open var position: Int = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return TipsAppVersionConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return TipsAppVersionConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return TipsAppVersionConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(TipsAppVersionConst.C_TITLE, title)
        values.put(TipsAppVersionConst.C_POSITION, position.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            title,
            position.toLong())
    }

    open fun copy() : TipsAppVersion {
        val copy = TipsAppVersion()
        copy.id = id
        copy.title = title
        copy.position = position
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, title)
        statement.bindLong(2, position.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, title)
        statement.bindLong(2, position.toLong())
        statement.bindLong(3, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        title = values.getAsString(TipsAppVersionConst.C_TITLE)
        position = values.getAsInteger(TipsAppVersionConst.C_POSITION)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(TipsAppVersionConst.C_ID))
        title = cursor.getString(cursor.getColumnIndexOrThrow(TipsAppVersionConst.C_TITLE))
        position = cursor.getInt(cursor.getColumnIndexOrThrow(TipsAppVersionConst.C_POSITION))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}