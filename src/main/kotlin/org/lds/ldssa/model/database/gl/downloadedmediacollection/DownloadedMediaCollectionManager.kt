/*
 * DownloadedMediaCollectionManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.gl.downloadedmediacollection

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaConst
import javax.inject.Inject


@javax.inject.Singleton
class DownloadedMediaCollectionManager @Inject constructor(databaseManager: DatabaseManager) : DownloadedMediaCollectionBaseManager(databaseManager) {

    companion object {
        val DROP_VIEW: String = "DROP VIEW IF EXISTS " + DownloadedMediaCollectionConst.TABLE + ";"
        val CREATE_VIEW: String = "CREATE VIEW IF NOT EXISTS " + DownloadedMediaCollectionConst.TABLE + " AS " +
                SQLQueryBuilder()
                        .field(DownloadedMediaConst.FULL_C_CONTENT_ITEM_ID, DownloadedMediaCollectionConst.C_ID)
                        .field("COUNT(*)", DownloadedMediaCollectionConst.C_ITEM_COUNT)
                        .field("SUM(" + DownloadedMediaConst.FULL_C_SIZE + ")", DownloadedMediaCollectionConst.C_TOTAL_SIZE)
                        .table(DownloadedMediaConst.TABLE)
                        .groupBy(DownloadedMediaConst.FULL_C_CONTENT_ITEM_ID)

                        .buildQuery()
    }

}