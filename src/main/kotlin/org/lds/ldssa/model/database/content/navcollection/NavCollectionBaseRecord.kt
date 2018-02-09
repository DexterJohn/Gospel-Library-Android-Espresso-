/*
 * NavCollectionBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.navcollection

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class NavCollectionBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var navSectionId: Long? = null
    open var position: Int = 0
    open var imageRenditions: String? = null
    open var titleHtml: String = ""
    open var subtitle: String? = null
    open var uri: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return NavCollectionConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return NavCollectionConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return NavCollectionConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(NavCollectionConst.C_NAV_SECTION_ID, navSectionId)
        values.put(NavCollectionConst.C_POSITION, position.toLong())
        values.put(NavCollectionConst.C_IMAGE_RENDITIONS, imageRenditions)
        values.put(NavCollectionConst.C_TITLE_HTML, titleHtml)
        values.put(NavCollectionConst.C_SUBTITLE, subtitle)
        values.put(NavCollectionConst.C_URI, uri)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            navSectionId,
            position.toLong(),
            imageRenditions,
            titleHtml,
            subtitle,
            uri)
    }

    open fun copy() : NavCollection {
        val copy = NavCollection()
        copy.id = id
        copy.navSectionId = navSectionId
        copy.position = position
        copy.imageRenditions = imageRenditions
        copy.titleHtml = titleHtml
        copy.subtitle = subtitle
        copy.uri = uri
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        if (navSectionId != null) {
            statement.bindLong(1, navSectionId!!)
        } else {
            statement.bindNull(1)
        }
        statement.bindLong(2, position.toLong())
        if (imageRenditions != null) {
            statement.bindString(3, imageRenditions!!)
        } else {
            statement.bindNull(3)
        }
        statement.bindString(4, titleHtml)
        if (subtitle != null) {
            statement.bindString(5, subtitle!!)
        } else {
            statement.bindNull(5)
        }
        statement.bindString(6, uri)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        if (navSectionId != null) {
            statement.bindLong(1, navSectionId!!)
        } else {
            statement.bindNull(1)
        }
        statement.bindLong(2, position.toLong())
        if (imageRenditions != null) {
            statement.bindString(3, imageRenditions!!)
        } else {
            statement.bindNull(3)
        }
        statement.bindString(4, titleHtml)
        if (subtitle != null) {
            statement.bindString(5, subtitle!!)
        } else {
            statement.bindNull(5)
        }
        statement.bindString(6, uri)
        statement.bindLong(7, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        navSectionId = values.getAsLong(NavCollectionConst.C_NAV_SECTION_ID)
        position = values.getAsInteger(NavCollectionConst.C_POSITION)
        imageRenditions = values.getAsString(NavCollectionConst.C_IMAGE_RENDITIONS)
        titleHtml = values.getAsString(NavCollectionConst.C_TITLE_HTML)
        subtitle = values.getAsString(NavCollectionConst.C_SUBTITLE)
        uri = values.getAsString(NavCollectionConst.C_URI)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(NavCollectionConst.C_ID))
        navSectionId = if (!cursor.isNull(cursor.getColumnIndexOrThrow(NavCollectionConst.C_NAV_SECTION_ID))) cursor.getLong(cursor.getColumnIndexOrThrow(NavCollectionConst.C_NAV_SECTION_ID)) else null
        position = cursor.getInt(cursor.getColumnIndexOrThrow(NavCollectionConst.C_POSITION))
        imageRenditions = if (!cursor.isNull(cursor.getColumnIndexOrThrow(NavCollectionConst.C_IMAGE_RENDITIONS))) cursor.getString(cursor.getColumnIndexOrThrow(NavCollectionConst.C_IMAGE_RENDITIONS)) else null
        titleHtml = cursor.getString(cursor.getColumnIndexOrThrow(NavCollectionConst.C_TITLE_HTML))
        subtitle = if (!cursor.isNull(cursor.getColumnIndexOrThrow(NavCollectionConst.C_SUBTITLE))) cursor.getString(cursor.getColumnIndexOrThrow(NavCollectionConst.C_SUBTITLE)) else null
        uri = cursor.getString(cursor.getColumnIndexOrThrow(NavCollectionConst.C_URI))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}