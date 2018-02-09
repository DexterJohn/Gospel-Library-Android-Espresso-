/*
 * SearchCountAllNotesManager.kt
 *
 * Generated on: 03/16/2017 10:44:02
 *
 */



package org.lds.ldssa.model.database.search.searchcountallnotes

import org.lds.ldssa.model.database.DatabaseManager
import javax.inject.Inject


@javax.inject.Singleton
class SearchCountAllNotesManager @Inject constructor(databaseManager: DatabaseManager) : SearchCountAllNotesBaseManager(databaseManager) {
    fun deleteAllByTabId(screenId: Long) {
        delete("${SearchCountAllNotesConst.C_SCREEN_ID} = $screenId")
    }
}