/*
 * CustomCollectionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.userdata.customcollection

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class CustomCollectionBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var uniqueId: String = ""
    open var title: String = ""
    open var orderPosition: Int = 0
    open var created: org.threeten.bp.LocalDateTime = org.threeten.bp.LocalDateTime.now()
    open var lastModified: org.threeten.bp.LocalDateTime = org.threeten.bp.LocalDateTime.now()

    constructor() {
    }

    override fun getIdColumnName() : String {
        return CustomCollectionConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return CustomCollectionConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return CustomCollectionConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(CustomCollectionConst.C_UNIQUE_ID, uniqueId)
        values.put(CustomCollectionConst.C_TITLE, title)
        values.put(CustomCollectionConst.C_ORDER_POSITION, orderPosition.toLong())
        values.put(CustomCollectionConst.C_CREATED, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(created)!!)
        values.put(CustomCollectionConst.C_LAST_MODIFIED, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            uniqueId,
            title,
            orderPosition.toLong(),
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(created)!!,
            org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
    }

    open fun copy() : CustomCollection {
        val copy = CustomCollection()
        copy.id = id
        copy.uniqueId = uniqueId
        copy.title = title
        copy.orderPosition = orderPosition
        copy.created = created
        copy.lastModified = lastModified
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindString(1, uniqueId)
        statement.bindString(2, title)
        statement.bindLong(3, orderPosition.toLong())
        statement.bindLong(4, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(created)!!)
        statement.bindLong(5, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindString(1, uniqueId)
        statement.bindString(2, title)
        statement.bindLong(3, orderPosition.toLong())
        statement.bindLong(4, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(created)!!)
        statement.bindLong(5, org.dbtools.android.domain.date.DBToolsThreeTenFormatter.localDateTimeToLong(lastModified)!!)
        statement.bindLong(6, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        uniqueId = values.getAsString(CustomCollectionConst.C_UNIQUE_ID)
        title = values.getAsString(CustomCollectionConst.C_TITLE)
        orderPosition = values.getAsInteger(CustomCollectionConst.C_ORDER_POSITION)
        created = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(values.getAsLong(CustomCollectionConst.C_CREATED))!!
        lastModified = org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(values.getAsLong(CustomCollectionConst.C_LAST_MODIFIED))!!
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(CustomCollectionConst.C_ID))
        uniqueId = cursor.getString(cursor.getColumnIndexOrThrow(CustomCollectionConst.C_UNIQUE_ID))
        title = cursor.getString(cursor.getColumnIndexOrThrow(CustomCollectionConst.C_TITLE))
        orderPosition = cursor.getInt(cursor.getColumnIndexOrThrow(CustomCollectionConst.C_ORDER_POSITION))
        created = if (!cursor.isNull(cursor.getColumnIndexOrThrow(CustomCollectionConst.C_CREATED))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(CustomCollectionConst.C_CREATED)))!! else null!!
        lastModified = if (!cursor.isNull(cursor.getColumnIndexOrThrow(CustomCollectionConst.C_LAST_MODIFIED))) org.dbtools.android.domain.date.DBToolsThreeTenFormatter.longToLocalDateTime(cursor.getLong(cursor.getColumnIndexOrThrow(CustomCollectionConst.C_LAST_MODIFIED)))!! else null!!
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}