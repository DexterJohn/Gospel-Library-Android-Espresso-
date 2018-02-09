/*
 * ContentDirectoryItemQueryBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.content.contentdirectoryitemquery

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class ContentDirectoryItemQueryBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var type: org.lds.ldssa.model.database.types.QueryItemType = org.lds.ldssa.model.database.types.QueryItemType.UNKNOWN
    open var sectionId: Long = 0
    open var sectionPosition: Int = 0
    open var sectionTitle: String? = null
    open var sectionIndentLevel: Int = 0
    open var position: Int = 0
    open var imageRenditions: String? = null
    open var titleHtml: String = ""
    open var subtitle: String? = null
    open var preview: String? = null
    open var uri: String = ""

    constructor() {
    }

    override fun getIdColumnName() : String {
        return ""
    }

    override fun getPrimaryKeyId() : Long {
        return 0
    }

    override fun setPrimaryKeyId(id: Long) {
    }

    override fun getAllColumns() : Array<String> {
        return ContentDirectoryItemQueryConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return ContentDirectoryItemQueryConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(ContentDirectoryItemQueryConst.C_ID, id)
        values.put(ContentDirectoryItemQueryConst.C_TYPE, type.ordinal.toLong())
        values.put(ContentDirectoryItemQueryConst.C_SECTION_ID, sectionId)
        values.put(ContentDirectoryItemQueryConst.C_SECTION_POSITION, sectionPosition.toLong())
        values.put(ContentDirectoryItemQueryConst.C_SECTION_TITLE, sectionTitle)
        values.put(ContentDirectoryItemQueryConst.C_SECTION_INDENT_LEVEL, sectionIndentLevel.toLong())
        values.put(ContentDirectoryItemQueryConst.C_POSITION, position.toLong())
        values.put(ContentDirectoryItemQueryConst.C_IMAGE_RENDITIONS, imageRenditions)
        values.put(ContentDirectoryItemQueryConst.C_TITLE_HTML, titleHtml)
        values.put(ContentDirectoryItemQueryConst.C_SUBTITLE, subtitle)
        values.put(ContentDirectoryItemQueryConst.C_PREVIEW, preview)
        values.put(ContentDirectoryItemQueryConst.C_URI, uri)
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            type.ordinal.toLong(),
            sectionId,
            sectionPosition.toLong(),
            sectionTitle,
            sectionIndentLevel.toLong(),
            position.toLong(),
            imageRenditions,
            titleHtml,
            subtitle,
            preview,
            uri)
    }

    open fun copy() : ContentDirectoryItemQuery {
        val copy = ContentDirectoryItemQuery()
        copy.id = id
        copy.type = type
        copy.sectionId = sectionId
        copy.sectionPosition = sectionPosition
        copy.sectionTitle = sectionTitle
        copy.sectionIndentLevel = sectionIndentLevel
        copy.position = position
        copy.imageRenditions = imageRenditions
        copy.titleHtml = titleHtml
        copy.subtitle = subtitle
        copy.preview = preview
        copy.uri = uri
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, type.ordinal.toLong())
        statement.bindLong(3, sectionId)
        statement.bindLong(4, sectionPosition.toLong())
        if (sectionTitle != null) {
            statement.bindString(5, sectionTitle!!)
        } else {
            statement.bindNull(5)
        }
        statement.bindLong(6, sectionIndentLevel.toLong())
        statement.bindLong(7, position.toLong())
        if (imageRenditions != null) {
            statement.bindString(8, imageRenditions!!)
        } else {
            statement.bindNull(8)
        }
        statement.bindString(9, titleHtml)
        if (subtitle != null) {
            statement.bindString(10, subtitle!!)
        } else {
            statement.bindNull(10)
        }
        if (preview != null) {
            statement.bindString(11, preview!!)
        } else {
            statement.bindNull(11)
        }
        statement.bindString(12, uri)
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, id)
        statement.bindLong(2, type.ordinal.toLong())
        statement.bindLong(3, sectionId)
        statement.bindLong(4, sectionPosition.toLong())
        if (sectionTitle != null) {
            statement.bindString(5, sectionTitle!!)
        } else {
            statement.bindNull(5)
        }
        statement.bindLong(6, sectionIndentLevel.toLong())
        statement.bindLong(7, position.toLong())
        if (imageRenditions != null) {
            statement.bindString(8, imageRenditions!!)
        } else {
            statement.bindNull(8)
        }
        statement.bindString(9, titleHtml)
        if (subtitle != null) {
            statement.bindString(10, subtitle!!)
        } else {
            statement.bindNull(10)
        }
        if (preview != null) {
            statement.bindString(11, preview!!)
        } else {
            statement.bindNull(11)
        }
        statement.bindString(12, uri)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        id = values.getAsLong(ContentDirectoryItemQueryConst.C_ID)
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.QueryItemType::class.java, values.getAsInteger(ContentDirectoryItemQueryConst.C_TYPE), org.lds.ldssa.model.database.types.QueryItemType.UNKNOWN)
        sectionId = values.getAsLong(ContentDirectoryItemQueryConst.C_SECTION_ID)
        sectionPosition = values.getAsInteger(ContentDirectoryItemQueryConst.C_SECTION_POSITION)
        sectionTitle = values.getAsString(ContentDirectoryItemQueryConst.C_SECTION_TITLE)
        sectionIndentLevel = values.getAsInteger(ContentDirectoryItemQueryConst.C_SECTION_INDENT_LEVEL)
        position = values.getAsInteger(ContentDirectoryItemQueryConst.C_POSITION)
        imageRenditions = values.getAsString(ContentDirectoryItemQueryConst.C_IMAGE_RENDITIONS)
        titleHtml = values.getAsString(ContentDirectoryItemQueryConst.C_TITLE_HTML)
        subtitle = values.getAsString(ContentDirectoryItemQueryConst.C_SUBTITLE)
        preview = values.getAsString(ContentDirectoryItemQueryConst.C_PREVIEW)
        uri = values.getAsString(ContentDirectoryItemQueryConst.C_URI)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(ContentDirectoryItemQueryConst.C_ID))
        type = org.dbtools.android.domain.util.EnumUtil.ordinalToEnum(org.lds.ldssa.model.database.types.QueryItemType::class.java, cursor.getInt(cursor.getColumnIndexOrThrow(ContentDirectoryItemQueryConst.C_TYPE)), org.lds.ldssa.model.database.types.QueryItemType.UNKNOWN)
        sectionId = cursor.getLong(cursor.getColumnIndexOrThrow(ContentDirectoryItemQueryConst.C_SECTION_ID))
        sectionPosition = cursor.getInt(cursor.getColumnIndexOrThrow(ContentDirectoryItemQueryConst.C_SECTION_POSITION))
        sectionTitle = if (!cursor.isNull(cursor.getColumnIndexOrThrow(ContentDirectoryItemQueryConst.C_SECTION_TITLE))) cursor.getString(cursor.getColumnIndexOrThrow(ContentDirectoryItemQueryConst.C_SECTION_TITLE)) else null
        sectionIndentLevel = cursor.getInt(cursor.getColumnIndexOrThrow(ContentDirectoryItemQueryConst.C_SECTION_INDENT_LEVEL))
        position = cursor.getInt(cursor.getColumnIndexOrThrow(ContentDirectoryItemQueryConst.C_POSITION))
        imageRenditions = if (!cursor.isNull(cursor.getColumnIndexOrThrow(ContentDirectoryItemQueryConst.C_IMAGE_RENDITIONS))) cursor.getString(cursor.getColumnIndexOrThrow(ContentDirectoryItemQueryConst.C_IMAGE_RENDITIONS)) else null
        titleHtml = cursor.getString(cursor.getColumnIndexOrThrow(ContentDirectoryItemQueryConst.C_TITLE_HTML))
        subtitle = if (!cursor.isNull(cursor.getColumnIndexOrThrow(ContentDirectoryItemQueryConst.C_SUBTITLE))) cursor.getString(cursor.getColumnIndexOrThrow(ContentDirectoryItemQueryConst.C_SUBTITLE)) else null
        preview = if (!cursor.isNull(cursor.getColumnIndexOrThrow(ContentDirectoryItemQueryConst.C_PREVIEW))) cursor.getString(cursor.getColumnIndexOrThrow(ContentDirectoryItemQueryConst.C_PREVIEW)) else null
        uri = cursor.getString(cursor.getColumnIndexOrThrow(ContentDirectoryItemQueryConst.C_URI))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}