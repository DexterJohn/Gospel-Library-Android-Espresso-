/*
 * AuthorRoleBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.authorrole

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class AuthorRoleBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var authorId: Long = 0
    open var roleId: Long = 0
    open var position: Long = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return AuthorRoleConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return AuthorRoleConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return AuthorRoleConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(AuthorRoleConst.C_AUTHOR_ID, authorId)
        values.put(AuthorRoleConst.C_ROLE_ID, roleId)
        values.put(AuthorRoleConst.C_POSITION, position)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            authorId,
            roleId,
            position)
    }

    open fun copy() : AuthorRole {
        val copy = AuthorRole()
        copy.id = id
        copy.authorId = authorId
        copy.roleId = roleId
        copy.position = position
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, authorId)
        statement.bindLong(2, roleId)
        statement.bindLong(3, position)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, authorId)
        statement.bindLong(2, roleId)
        statement.bindLong(3, position)
        statement.bindLong(4, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        authorId = values.getAsLong(AuthorRoleConst.C_AUTHOR_ID)
        roleId = values.getAsLong(AuthorRoleConst.C_ROLE_ID)
        position = values.getAsLong(AuthorRoleConst.C_POSITION)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(AuthorRoleConst.C_ID))
        authorId = cursor.getLong(cursor.getColumnIndexOrThrow(AuthorRoleConst.C_AUTHOR_ID))
        roleId = cursor.getLong(cursor.getColumnIndexOrThrow(AuthorRoleConst.C_ROLE_ID))
        position = cursor.getLong(cursor.getColumnIndexOrThrow(AuthorRoleConst.C_POSITION))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}