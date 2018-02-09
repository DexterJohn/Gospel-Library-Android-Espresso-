/*
 * LanguageManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.catalog.language

import org.dbtools.query.shared.CompareType
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionConst
import org.lds.ldssa.model.database.catalog.librarysection.LibrarySectionConst
import org.lds.ldssa.model.prefs.PrefsConst
import javax.inject.Inject


@javax.inject.Singleton
class LanguageManager @Inject constructor(databaseManager: DatabaseManager) : LanguageBaseManager(databaseManager) {
    private val ROOT_COLLECTION_ID_QUERY: String = SQLQueryBuilder()
            .field(LanguageConst.C_ROOT_LIBRARY_COLLECTION_ID)
            .table(LanguageConst.TABLE)
            .filter(LanguageConst.C_ID, "?")
            .toString()
    private val LOCALE_BY_LANG_ID_QUERY: String = SQLQueryBuilder()
            .field(LanguageConst.C_ISO6393)
            .table(LanguageConst.TABLE)
            .filter(LanguageConst.C_ID, "?")
            .toString()
    private val LANGUAGE_ID_BY_SECTION_ID: String
    private val LANGUAGE_ID_BY_COLLECTION_ID: String

    private val SECTION_TABLE = "section"
    private val SECTION_ID = "_id"
    private val SECTION_ITEM_ID = "item_id"

    init {

        val fields = SQLQueryBuilder()
                .field(LibraryCollectionConst.FULL_C_ID)
                .field(LibraryCollectionConst.FULL_C_LIBRARY_SECTION_ID)

        val collectionInitial = SQLQueryBuilder()
                .table(LibraryCollectionConst.TABLE)
                .apply(fields)
                .join(LibrarySectionConst.TABLE, LibrarySectionConst.FULL_C_LIBRARY_COLLECTION_ID, LibraryCollectionConst.FULL_C_ID)

        val bySectionId = SQLQueryBuilder()
                .apply(collectionInitial)
                .filter(LibrarySectionConst.FULL_C_ID, "?")

        val byCollectionId = SQLQueryBuilder()
                .apply(collectionInitial)
                .filter(LibraryCollectionConst.FULL_C_ID, "?")

        val collectionRecursive = SQLQueryBuilder()
                .table(LibrarySectionConst.TABLE)
                .apply(fields)
                .join(SECTION_TABLE, getFullSectionField(SECTION_ITEM_ID), LibrarySectionConst.FULL_C_ID)
                .join(LibraryCollectionConst.TABLE, LibrarySectionConst.FULL_C_LIBRARY_COLLECTION_ID, LibraryCollectionConst.FULL_C_ID)

        val items = SQLQueryBuilder()
                .field(LanguageConst.FULL_C_ID)
                .table(SECTION_TABLE)
                .join(LanguageConst.TABLE, getFullSectionField(SECTION_ID), LanguageConst.FULL_C_ROOT_LIBRARY_COLLECTION_ID)
                .filter(SECTION_ITEM_ID, CompareType.IS_NULL)

        val recursiveQuery = "WITH RECURSIVE " +
                SECTION_TABLE + "(" + SECTION_ID + "," + SECTION_ITEM_ID + ") AS "

        LANGUAGE_ID_BY_SECTION_ID = recursiveQuery +
                SQLQueryBuilder.union(bySectionId, collectionRecursive) +
                items.buildQuery()

        LANGUAGE_ID_BY_COLLECTION_ID = recursiveQuery +
                SQLQueryBuilder.union(byCollectionId, collectionRecursive) +
                items.buildQuery()
    }

    fun findIdByLocale(locale: String): Long {
        return findValueBySelection(Long::class.java, LanguageConst.C_ID,
                selection = LanguageConst.C_ISO6393 + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(locale),
                defaultValue = 0L)
    }

    fun findLocaleById(id: Long): String {
        return findValueByRawQuery(String::class.java, rawQuery = LOCALE_BY_LANG_ID_QUERY, selectionArgs = SQLQueryBuilder.toSelectionArgs(id), defaultValue = "eng")
    }

    fun findRootCollectionIdByLanguageId(languageId: Long): Long {
        return findValueByRawQuery(Long::class.java, rawQuery = ROOT_COLLECTION_ID_QUERY, selectionArgs = arrayOf(languageId.toString()), defaultValue = 0L)
    }

    // This being used but isn't anymore. Let's keep it around for a while in case we need it again.
    fun findLanguageIdByLibraryCollection(collectionId: Long): Long {
        return findValueByRawQuery(Long::class.java, rawQuery = LANGUAGE_ID_BY_COLLECTION_ID,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(collectionId), defaultValue = PrefsConst.DEFAULT_LANGUAGE_ID.toLong())
    }

    private fun getFullSectionField(field: String): String {
        return SECTION_TABLE + "." + field
    }

    fun isRootCollection(collectionId: Long): Boolean {
        return findCountBySelection(LanguageConst.C_ROOT_LIBRARY_COLLECTION_ID + " = " + collectionId, null) > 0
    }
}