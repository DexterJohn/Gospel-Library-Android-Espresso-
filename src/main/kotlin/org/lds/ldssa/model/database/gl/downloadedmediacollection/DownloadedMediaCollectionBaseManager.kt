/*
 * DownloadedMediaCollectionBaseManager.kt
 *
 * GENERATED FILE - DO NOT EDIT
 * 
 */



package org.lds.ldssa.model.database.gl.downloadedmediacollection

import org.lds.ldssa.model.database.DatabaseManager
import org.dbtools.android.domain.RxKotlinAndroidBaseManagerReadOnly


@Suppress("unused")
@SuppressWarnings("all")
abstract class DownloadedMediaCollectionBaseManager (databaseManager: DatabaseManager) : RxKotlinAndroidBaseManagerReadOnly<DownloadedMediaCollection>(databaseManager) {

    override val allColumns: Array<String> = DownloadedMediaCollectionConst.ALL_COLUMNS
    override val primaryKey = "<NO_PRIMARY_KEY_ON_VIEWS>"
    override val dropSql = DownloadedMediaCollectionManager.DROP_VIEW
    override val createSql = DownloadedMediaCollectionManager.CREATE_VIEW
    override val insertSql = ""
    override val updateSql = ""

    override fun getDatabaseName() : String {
        return DownloadedMediaCollectionConst.DATABASE
    }

    override fun newRecord() : DownloadedMediaCollection {
        return DownloadedMediaCollection()
    }

    override fun getTableName() : String {
        return DownloadedMediaCollectionConst.TABLE
    }


}