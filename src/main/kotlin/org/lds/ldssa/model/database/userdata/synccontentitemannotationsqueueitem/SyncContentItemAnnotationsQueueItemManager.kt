/*
 * SyncContentItemAnnotationsQueueItemManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.userdata.synccontentitemannotationsqueueitem

import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.util.UserdataDbUtil
import javax.inject.Inject


@javax.inject.Singleton
class SyncContentItemAnnotationsQueueItemManager @Inject constructor(databaseManager: DatabaseManager, val userdataDbUtil: UserdataDbUtil) : SyncContentItemAnnotationsQueueItemBaseManager(databaseManager) {
    override fun getDatabaseName(): String {
        return userdataDbUtil.currentOpenedDatabaseName
    }

    fun findAllContentItemIds(): List<Long> {
        return findAllValuesBySelection(Long::class.java, SyncContentItemAnnotationsQueueItemConst.C_CONTENT_ITEM_ID)
    }
}