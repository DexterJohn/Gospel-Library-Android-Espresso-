/*
 * RoleBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.role

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class RoleBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var name: String = ""
    open var position: Int = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return RoleConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return RoleConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return RoleConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(RoleConst.C_NAME, name)
        values.put(RoleConst.C_POSITION, position.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            name,
            position.toLong())
    }

    open fun copy() : Role {
        val copy = Role()
        copy.id = id
        copy.name = name
        copy.position = position
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, name)
        statement.bindLong(2, position.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, name)
        statement.bindLong(2, position.toLong())
        statement.bindLong(3, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        name = values.getAsString(RoleConst.C_NAME)
        position = values.getAsInteger(RoleConst.C_POSITION)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(RoleConst.C_ID))
        name = cursor.getString(cursor.getColumnIndexOrThrow(RoleConst.C_NAME))
        position = cursor.getInt(cursor.getColumnIndexOrThrow(RoleConst.C_POSITION))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}