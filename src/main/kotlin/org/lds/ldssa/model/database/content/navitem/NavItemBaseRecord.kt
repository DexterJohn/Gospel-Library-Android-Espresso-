/*
 * NavItemBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.navitem

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class NavItemBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var navSectionId: Long = 0
    open var position: Int = 0
    open var imageRenditions: String? = null
    open var titleHtml: String = ""
    open var subtitle: String? = null
    open var preview: String? = null
    open var uri: String = ""
    open var subitemId: Long = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return NavItemConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return NavItemConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return NavItemConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(NavItemConst.C_NAV_SECTION_ID, navSectionId)
        values.put(NavItemConst.C_POSITION, position.toLong())
        values.put(NavItemConst.C_IMAGE_RENDITIONS, imageRenditions)
        values.put(NavItemConst.C_TITLE_HTML, titleHtml)
        values.put(NavItemConst.C_SUBTITLE, subtitle)
        values.put(NavItemConst.C_PREVIEW, preview)
        values.put(NavItemConst.C_URI, uri)
        values.put(NavItemConst.C_SUBITEM_ID, subitemId)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            navSectionId,
            position.toLong(),
            imageRenditions,
            titleHtml,
            subtitle,
            preview,
            uri,
            subitemId)
    }

    open fun copy() : NavItem {
        val copy = NavItem()
        copy.id = id
        copy.navSectionId = navSectionId
        copy.position = position
        copy.imageRenditions = imageRenditions
        copy.titleHtml = titleHtml
        copy.subtitle = subtitle
        copy.preview = preview
        copy.uri = uri
        copy.subitemId = subitemId
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, navSectionId)
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
        if (preview != null) {
            statement.bindString(6, preview!!)
        } else {
            statement.bindNull(6)
        }
        statement.bindString(7, uri)
        statement.bindLong(8, subitemId)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, navSectionId)
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
        if (preview != null) {
            statement.bindString(6, preview!!)
        } else {
            statement.bindNull(6)
        }
        statement.bindString(7, uri)
        statement.bindLong(8, subitemId)
        statement.bindLong(9, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        navSectionId = values.getAsLong(NavItemConst.C_NAV_SECTION_ID)
        position = values.getAsInteger(NavItemConst.C_POSITION)
        imageRenditions = values.getAsString(NavItemConst.C_IMAGE_RENDITIONS)
        titleHtml = values.getAsString(NavItemConst.C_TITLE_HTML)
        subtitle = values.getAsString(NavItemConst.C_SUBTITLE)
        preview = values.getAsString(NavItemConst.C_PREVIEW)
        uri = values.getAsString(NavItemConst.C_URI)
        subitemId = values.getAsLong(NavItemConst.C_SUBITEM_ID)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(NavItemConst.C_ID))
        navSectionId = cursor.getLong(cursor.getColumnIndexOrThrow(NavItemConst.C_NAV_SECTION_ID))
        position = cursor.getInt(cursor.getColumnIndexOrThrow(NavItemConst.C_POSITION))
        imageRenditions = if (!cursor.isNull(cursor.getColumnIndexOrThrow(NavItemConst.C_IMAGE_RENDITIONS))) cursor.getString(cursor.getColumnIndexOrThrow(NavItemConst.C_IMAGE_RENDITIONS)) else null
        titleHtml = cursor.getString(cursor.getColumnIndexOrThrow(NavItemConst.C_TITLE_HTML))
        subtitle = if (!cursor.isNull(cursor.getColumnIndexOrThrow(NavItemConst.C_SUBTITLE))) cursor.getString(cursor.getColumnIndexOrThrow(NavItemConst.C_SUBTITLE)) else null
        preview = if (!cursor.isNull(cursor.getColumnIndexOrThrow(NavItemConst.C_PREVIEW))) cursor.getString(cursor.getColumnIndexOrThrow(NavItemConst.C_PREVIEW)) else null
        uri = cursor.getString(cursor.getColumnIndexOrThrow(NavItemConst.C_URI))
        subitemId = cursor.getLong(cursor.getColumnIndexOrThrow(NavItemConst.C_SUBITEM_ID))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}