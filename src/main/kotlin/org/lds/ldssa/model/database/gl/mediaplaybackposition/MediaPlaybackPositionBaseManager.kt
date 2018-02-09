/*
 * MediaPlaybackPositionBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.gl.mediaplaybackposition

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class MediaPlaybackPositionBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<MediaPlaybackPosition>(databaseManager) {

    override val allColumns: Array<String> = MediaPlaybackPositionConst.ALL_COLUMNS
    override val primaryKey = MediaPlaybackPositionConst.PRIMARY_KEY_COLUMN
    override val dropSql = MediaPlaybackPositionConst.DROP_TABLE
    override val createSql = MediaPlaybackPositionConst.CREATE_TABLE
    override val insertSql = MediaPlaybackPositionConst.INSERT_STATEMENT
    override val updateSql = MediaPlaybackPositionConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return MediaPlaybackPositionConst.DATABASE
    }

    override fun newRecord() : MediaPlaybackPosition {
        return MediaPlaybackPosition()
    }

    override fun getTableName() : String {
        return MediaPlaybackPositionConst.TABLE
    }


}