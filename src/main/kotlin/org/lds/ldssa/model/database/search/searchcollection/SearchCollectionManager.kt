/*
 * SearchCollectionManager.kt
 *
 * Generated on: 03/09/2017 11:46:28
 *
 */



package org.lds.ldssa.model.database.search.searchcollection

import org.lds.ldssa.model.database.DatabaseManager
import javax.inject.Inject


@javax.inject.Singleton
class SearchCollectionManager @Inject constructor(databaseManager: DatabaseManager) : SearchCollectionBaseManager(databaseManager) {
    fun findCollectionTitle(screenId: Long, collectionId: Long): String {
        return findValueBySelection(
                valueType = String::class.java,
                column = SearchCollectionConst.C_TITLE,
                selection = "${SearchCollectionConst.C_SCREEN_ID} = $screenId AND ${SearchCollectionConst.C_COLLECTION_ID} = $collectionId",
                defaultValue = "")
    }

    fun deleteAllByTabId(screenId: Long) {
        delete("${SearchCollectionConst.C_SCREEN_ID} = $screenId")
    }
}