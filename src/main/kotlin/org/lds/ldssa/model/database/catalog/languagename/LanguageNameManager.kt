/*
 * LanguageNameManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.catalog.languagename

import org.dbtools.query.shared.CompareType
import org.dbtools.query.shared.filter.CompareFilter
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.prefs.PrefsConst
import javax.inject.Inject


@javax.inject.Singleton
class LanguageNameManager @Inject constructor(databaseManager: DatabaseManager) : LanguageNameBaseManager(databaseManager) {
    private val LOCALIZED_LANGUAGES_QUERY: String

    init {
        val languageId = SQLQueryBuilder()
                .table(LanguageNameConst.TABLE)
                .field(LanguageNameConst.C_LANGUAGE_ID)
                .filter(LanguageNameConst.C_LOCALIZATION_LANGUAGE_ID, "?")

        val filter = CompareFilter.create(LanguageNameConst.C_LOCALIZATION_LANGUAGE_ID, "?")
                .and(LanguageNameConst.C_LANGUAGE_ID, CompareType.NOT_IN, languageId.buildQuery())
                .or(LanguageNameConst.C_LOCALIZATION_LANGUAGE_ID, "?")

        val localizedLanguages = SQLQueryBuilder()
                .table(LanguageNameConst.TABLE)
                .filter(filter)
                .orderBy(LanguageNameConst.C_NAME)

        LOCALIZED_LANGUAGES_QUERY = localizedLanguages.buildQuery()
    }

    /**
     * Finds the English name for the language with the specified Id

     * @param languageId The id to find the language with
     * *
     * @return The English name for the language with the specified id
     */
    fun findLanguageName(languageId: Long): String? {
        return findValueBySelection(String::class.java, LanguageNameConst.C_NAME,
                selection = LanguageNameConst.C_LANGUAGE_ID + "= ? AND " + LanguageNameConst.C_LOCALIZATION_LANGUAGE_ID + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(languageId, PrefsConst.DEFAULT_LANGUAGE_ID),
                defaultValue = null)
    }

    fun findLocalizedLanguagesById(languageId: Long): List<LanguageName> {
        return findAllByRawQuery(LOCALIZED_LANGUAGES_QUERY, SQLQueryBuilder.toSelectionArgs(PrefsConst.DEFAULT_LANGUAGE_ID, languageId, languageId))
    }
}