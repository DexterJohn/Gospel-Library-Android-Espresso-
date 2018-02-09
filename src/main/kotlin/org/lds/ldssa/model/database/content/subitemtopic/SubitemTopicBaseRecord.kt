/*
 * SubitemTopicBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.subitemtopic

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class SubitemTopicBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var subitemId: Int = 0
    open var topicId: Int = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return SubitemTopicConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return SubitemTopicConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return SubitemTopicConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(SubitemTopicConst.C_SUBITEM_ID, subitemId.toLong())
        values.put(SubitemTopicConst.C_TOPIC_ID, topicId.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            subitemId.toLong(),
            topicId.toLong())
    }

    open fun copy() : SubitemTopic {
        val copy = SubitemTopic()
        copy.id = id
        copy.subitemId = subitemId
        copy.topicId = topicId
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, subitemId.toLong())
        statement.bindLong(2, topicId.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, subitemId.toLong())
        statement.bindLong(2, topicId.toLong())
        statement.bindLong(3, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        subitemId = values.getAsInteger(SubitemTopicConst.C_SUBITEM_ID)
        topicId = values.getAsInteger(SubitemTopicConst.C_TOPIC_ID)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(SubitemTopicConst.C_ID))
        subitemId = cursor.getInt(cursor.getColumnIndexOrThrow(SubitemTopicConst.C_SUBITEM_ID))
        topicId = cursor.getInt(cursor.getColumnIndexOrThrow(SubitemTopicConst.C_TOPIC_ID))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}