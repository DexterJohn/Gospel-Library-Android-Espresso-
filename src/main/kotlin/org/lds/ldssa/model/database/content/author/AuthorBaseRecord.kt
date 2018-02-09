/*
 * AuthorBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.author

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class AuthorBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var givenName: String = ""
    open var familyName: String = ""
    open var imageRenditions: String? = null

    constructor() {
    }

    override fun getIdColumnName() : String {
        return AuthorConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return AuthorConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return AuthorConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(AuthorConst.C_GIVEN_NAME, givenName)
        values.put(AuthorConst.C_FAMILY_NAME, familyName)
        values.put(AuthorConst.C_IMAGE_RENDITIONS, imageRenditions)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            givenName,
            familyName,
            imageRenditions)
    }

    open fun copy() : Author {
        val copy = Author()
        copy.id = id
        copy.givenName = givenName
        copy.familyName = familyName
        copy.imageRenditions = imageRenditions
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, givenName)
        statement.bindString(2, familyName)
        if (imageRenditions != null) {
            statement.bindString(3, imageRenditions!!)
        } else {
            statement.bindNull(3)
        }
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, givenName)
        statement.bindString(2, familyName)
        if (imageRenditions != null) {
            statement.bindString(3, imageRenditions!!)
        } else {
            statement.bindNull(3)
        }
        statement.bindLong(4, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        givenName = values.getAsString(AuthorConst.C_GIVEN_NAME)
        familyName = values.getAsString(AuthorConst.C_FAMILY_NAME)
        imageRenditions = values.getAsString(AuthorConst.C_IMAGE_RENDITIONS)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(AuthorConst.C_ID))
        givenName = cursor.getString(cursor.getColumnIndexOrThrow(AuthorConst.C_GIVEN_NAME))
        familyName = cursor.getString(cursor.getColumnIndexOrThrow(AuthorConst.C_FAMILY_NAME))
        imageRenditions = if (!cursor.isNull(cursor.getColumnIndexOrThrow(AuthorConst.C_IMAGE_RENDITIONS))) cursor.getString(cursor.getColumnIndexOrThrow(AuthorConst.C_IMAGE_RENDITIONS)) else null
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}