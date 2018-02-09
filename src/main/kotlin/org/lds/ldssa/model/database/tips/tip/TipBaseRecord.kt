/*
 * TipBaseRecord.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * CHECKSTYLE:OFF
 * 
 */



package org.lds.ldssa.model.database.tips.tip

import org.dbtools.android.domain.AndroidBaseRecord
import org.dbtools.android.domain.database.statement.StatementWrapper
import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues
import android.database.Cursor


@Suppress("LeakingThis", "unused", "RemoveEmptySecondaryConstructorBody", "ConvertSecondaryConstructorToPrimary")
@SuppressWarnings("all")
abstract class TipBaseRecord  : AndroidBaseRecord {

    open var id: Long = 0
    open var versionId: Long = 0
    open var iso6393: String = ""
    open var title: String = ""
    open var description: String = ""
    open var phoneImageFilename: String? = null
    open var phoneImageRenditions: String = ""
    open var phoneVideoUrl: String? = null
    open var tabletImageFilename: String? = null
    open var tabletImageRenditions: String = ""
    open var tabletVideoUrl: String? = null
    open var showInWelcome: Boolean = false
    open var position: Int = 0

    constructor() {
    }

    override fun getIdColumnName() : String {
        return TipConst.C_ID
    }

    override fun getPrimaryKeyId() : Long {
        return id
    }

    override fun setPrimaryKeyId(id: Long) {
        this.id = id
    }

    override fun getAllColumns() : Array<String> {
        return TipConst.ALL_COLUMNS.clone()
    }

    fun getAllColumnsFull() : Array<String> {
        return TipConst.ALL_COLUMNS_FULL.clone()
    }

    override fun getContentValues(values: DBToolsContentValues<*>) {
        values.put(TipConst.C_VERSION_ID, versionId)
        values.put(TipConst.C_ISO6393, iso6393)
        values.put(TipConst.C_TITLE, title)
        values.put(TipConst.C_DESCRIPTION, description)
        values.put(TipConst.C_PHONE_IMAGE_FILENAME, phoneImageFilename)
        values.put(TipConst.C_PHONE_IMAGE_RENDITIONS, phoneImageRenditions)
        values.put(TipConst.C_PHONE_VIDEO_URL, phoneVideoUrl)
        values.put(TipConst.C_TABLET_IMAGE_FILENAME, tabletImageFilename)
        values.put(TipConst.C_TABLET_IMAGE_RENDITIONS, tabletImageRenditions)
        values.put(TipConst.C_TABLET_VIDEO_URL, tabletVideoUrl)
        values.put(TipConst.C_SHOW_IN_WELCOME, if (showInWelcome) 1L else 0L)
        values.put(TipConst.C_POSITION, position.toLong())
    }

    override fun getValues() : Array<Any?> {
        return arrayOf(
            id,
            versionId,
            iso6393,
            title,
            description,
            phoneImageFilename,
            phoneImageRenditions,
            phoneVideoUrl,
            tabletImageFilename,
            tabletImageRenditions,
            tabletVideoUrl,
            if (showInWelcome) 1L else 0L,
            position.toLong())
    }

    open fun copy() : Tip {
        val copy = Tip()
        copy.id = id
        copy.versionId = versionId
        copy.iso6393 = iso6393
        copy.title = title
        copy.description = description
        copy.phoneImageFilename = phoneImageFilename
        copy.phoneImageRenditions = phoneImageRenditions
        copy.phoneVideoUrl = phoneVideoUrl
        copy.tabletImageFilename = tabletImageFilename
        copy.tabletImageRenditions = tabletImageRenditions
        copy.tabletVideoUrl = tabletVideoUrl
        copy.showInWelcome = showInWelcome
        copy.position = position
        return copy
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindInsertStatement(statement: StatementWrapper) {
        statement.bindLong(1, versionId)
        statement.bindString(2, iso6393)
        statement.bindString(3, title)
        statement.bindString(4, description)
        if (phoneImageFilename != null) {
            statement.bindString(5, phoneImageFilename!!)
        } else {
            statement.bindNull(5)
        }
        statement.bindString(6, phoneImageRenditions)
        if (phoneVideoUrl != null) {
            statement.bindString(7, phoneVideoUrl!!)
        } else {
            statement.bindNull(7)
        }
        if (tabletImageFilename != null) {
            statement.bindString(8, tabletImageFilename!!)
        } else {
            statement.bindNull(8)
        }
        statement.bindString(9, tabletImageRenditions)
        if (tabletVideoUrl != null) {
            statement.bindString(10, tabletVideoUrl!!)
        } else {
            statement.bindNull(10)
        }
        statement.bindLong(11, if (showInWelcome) 1L else 0L)
        statement.bindLong(12, position.toLong())
    }

    @Suppress("UNNECESSARY_NOT_NULL_ASSERTION")
    override fun bindUpdateStatement(statement: StatementWrapper) {
        statement.bindLong(1, versionId)
        statement.bindString(2, iso6393)
        statement.bindString(3, title)
        statement.bindString(4, description)
        if (phoneImageFilename != null) {
            statement.bindString(5, phoneImageFilename!!)
        } else {
            statement.bindNull(5)
        }
        statement.bindString(6, phoneImageRenditions)
        if (phoneVideoUrl != null) {
            statement.bindString(7, phoneVideoUrl!!)
        } else {
            statement.bindNull(7)
        }
        if (tabletImageFilename != null) {
            statement.bindString(8, tabletImageFilename!!)
        } else {
            statement.bindNull(8)
        }
        statement.bindString(9, tabletImageRenditions)
        if (tabletVideoUrl != null) {
            statement.bindString(10, tabletVideoUrl!!)
        } else {
            statement.bindNull(10)
        }
        statement.bindLong(11, if (showInWelcome) 1L else 0L)
        statement.bindLong(12, position.toLong())
        statement.bindLong(13, id)
    }

    override fun setContent(values: DBToolsContentValues<*>) {
        versionId = values.getAsLong(TipConst.C_VERSION_ID)
        iso6393 = values.getAsString(TipConst.C_ISO6393)
        title = values.getAsString(TipConst.C_TITLE)
        description = values.getAsString(TipConst.C_DESCRIPTION)
        phoneImageFilename = values.getAsString(TipConst.C_PHONE_IMAGE_FILENAME)
        phoneImageRenditions = values.getAsString(TipConst.C_PHONE_IMAGE_RENDITIONS)
        phoneVideoUrl = values.getAsString(TipConst.C_PHONE_VIDEO_URL)
        tabletImageFilename = values.getAsString(TipConst.C_TABLET_IMAGE_FILENAME)
        tabletImageRenditions = values.getAsString(TipConst.C_TABLET_IMAGE_RENDITIONS)
        tabletVideoUrl = values.getAsString(TipConst.C_TABLET_VIDEO_URL)
        showInWelcome = values.getAsBoolean(TipConst.C_SHOW_IN_WELCOME)
        position = values.getAsInteger(TipConst.C_POSITION)
    }

    override fun setContent(cursor: Cursor) {
        id = cursor.getLong(cursor.getColumnIndexOrThrow(TipConst.C_ID))
        versionId = cursor.getLong(cursor.getColumnIndexOrThrow(TipConst.C_VERSION_ID))
        iso6393 = cursor.getString(cursor.getColumnIndexOrThrow(TipConst.C_ISO6393))
        title = cursor.getString(cursor.getColumnIndexOrThrow(TipConst.C_TITLE))
        description = cursor.getString(cursor.getColumnIndexOrThrow(TipConst.C_DESCRIPTION))
        phoneImageFilename = if (!cursor.isNull(cursor.getColumnIndexOrThrow(TipConst.C_PHONE_IMAGE_FILENAME))) cursor.getString(cursor.getColumnIndexOrThrow(TipConst.C_PHONE_IMAGE_FILENAME)) else null
        phoneImageRenditions = cursor.getString(cursor.getColumnIndexOrThrow(TipConst.C_PHONE_IMAGE_RENDITIONS))
        phoneVideoUrl = if (!cursor.isNull(cursor.getColumnIndexOrThrow(TipConst.C_PHONE_VIDEO_URL))) cursor.getString(cursor.getColumnIndexOrThrow(TipConst.C_PHONE_VIDEO_URL)) else null
        tabletImageFilename = if (!cursor.isNull(cursor.getColumnIndexOrThrow(TipConst.C_TABLET_IMAGE_FILENAME))) cursor.getString(cursor.getColumnIndexOrThrow(TipConst.C_TABLET_IMAGE_FILENAME)) else null
        tabletImageRenditions = cursor.getString(cursor.getColumnIndexOrThrow(TipConst.C_TABLET_IMAGE_RENDITIONS))
        tabletVideoUrl = if (!cursor.isNull(cursor.getColumnIndexOrThrow(TipConst.C_TABLET_VIDEO_URL))) cursor.getString(cursor.getColumnIndexOrThrow(TipConst.C_TABLET_VIDEO_URL)) else null
        showInWelcome = cursor.getInt(cursor.getColumnIndexOrThrow(TipConst.C_SHOW_IN_WELCOME)) != 0
        position = cursor.getInt(cursor.getColumnIndexOrThrow(TipConst.C_POSITION))
    }

    override fun isNewRecord() : Boolean {
        return primaryKeyId <= 0
    }


}