/*
 * DownloadedMediaBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.gl.downloadedmedia

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerWritable


@Suppress("unused")
@SuppressWarnings("all")
abstract class DownloadedMediaBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerWritable<DownloadedMedia>(databaseManager) {

    override val allColumns: Array<String> = DownloadedMediaConst.ALL_COLUMNS
    override val primaryKey = DownloadedMediaConst.PRIMARY_KEY_COLUMN
    override val dropSql = DownloadedMediaConst.DROP_TABLE
    override val createSql = DownloadedMediaConst.CREATE_TABLE
    override val insertSql = DownloadedMediaConst.INSERT_STATEMENT
    override val updateSql = DownloadedMediaConst.UPDATE_STATEMENT

    override fun getDatabaseName() : String {
        return DownloadedMediaConst.DATABASE
    }

    override fun newRecord() : DownloadedMedia {
        return DownloadedMedia()
    }

    override fun getTableName() : String {
        return DownloadedMediaConst.TABLE
    }


}