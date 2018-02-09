/*
 * SearchContentCollectionMapManager.kt
 *
 * Generated on: 03/09/2017 11:46:28
 *
 */



package org.lds.ldssa.model.database.search.searchcontentcollectionmap

import org.lds.ldssa.model.database.DatabaseManager
import javax.inject.Inject


@javax.inject.Singleton
class SearchContentCollectionMapManager @Inject constructor(databaseManager: DatabaseManager) : SearchContentCollectionMapBaseManager(databaseManager) {
    fun deleteAllByTabId(screenId: Long) {
        delete("${SearchContentCollectionMapConst.C_SCREEN_ID} = $screenId")
    }
}