/*
 * TipViewedManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.gl.tipviewed

import org.lds.ldssa.model.database.DatabaseManager
import javax.inject.Inject


@javax.inject.Singleton
class TipViewedManager @Inject constructor(databaseManager: DatabaseManager) : TipViewedBaseManager(databaseManager) {

    fun findAllTipIds(): List<Long> {
        return findAllValuesBySelection(Long::class.java, TipViewedConst.C_TIP_ID, null, null)
    }

    fun saveTipViewed(tipId: Long) {
        save(TipViewed().apply { this.tipId = tipId })
    }
}