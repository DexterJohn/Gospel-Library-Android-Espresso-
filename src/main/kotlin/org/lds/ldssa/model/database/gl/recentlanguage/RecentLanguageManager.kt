/*
 * RecentLanguageManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.gl.recentlanguage

import org.lds.ldssa.model.database.DatabaseManager
import org.threeten.bp.LocalDateTime
import javax.inject.Inject


@javax.inject.Singleton
class RecentLanguageManager @Inject constructor(databaseManager: DatabaseManager) : RecentLanguageBaseManager(databaseManager) {

    fun findAllRecent(): List<Long> {
        return findAllValuesBySelection(valueType = Long::class.java,
                column = RecentLanguageConst.C_LANGUAGE_ID,
                orderBy = "${RecentLanguageConst.C_TIMESTAMP} DESC")
    }

    fun saveRecentLanguage(languageId: Long) {
        save(RecentLanguage().apply {
            this.languageId = languageId
            timestamp = LocalDateTime.now() })
    }
}