/*
 * SubitemAuthorBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.subitemauthor

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SubitemAuthorBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var subitemId: Int = 0
    open var authorId: Int = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return SubitemAuthorConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return SubitemAuthorConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SubitemAuthorConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SubitemAuthorConst.C_SUBITEM_ID, subitemId.toLong())
        values.put(SubitemAuthorConst.C_AUTHOR_ID, authorId.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            subitemId.toLong(),
            authorId.toLong())
    }

    open fun copy() : SubitemAuthor {
        val copy = SubitemAuthor()
        copy.id = id
        copy.subitemId = subitemId
        copy.authorId = authorId
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, subitemId.toLong())
        statement.bindLong(2, authorId.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, subitemId.toLong())
        statement.bindLong(2, authorId.toLong())
        statement.bindLong(3, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        subitemId = values.getAsInteger(SubitemAuthorConst.C_SUBITEM_ID)
        authorId = values.getAsInteger(SubitemAuthorConst.C_AUTHOR_ID)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(SubitemAuthorConst.C_ID))
        subitemId = cursor.getInt(cursor.getColumnIndexOrThrow(SubitemAuthorConst.C_SUBITEM_ID))
        authorId = cursor.getInt(cursor.getColumnIndexOrThrow(SubitemAuthorConst.C_AUTHOR_ID))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}