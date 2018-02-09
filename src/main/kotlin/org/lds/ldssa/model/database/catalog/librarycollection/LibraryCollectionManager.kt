/*
 * LibraryCollectionManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.catalog.librarycollection

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.language.LanguageManager
import org.lds.ldssa.model.database.catalog.libraryitem.LibraryItemConst
import org.lds.ldssa.model.database.catalog.librarysection.LibrarySectionConst
import javax.inject.Inject


@javax.inject.Singleton
class LibraryCollectionManager @Inject constructor(databaseManager: DatabaseManager, val languageManager: LanguageManager) : LibraryCollectionBaseManager(databaseManager) {
    private val DEFAULT_ID_BY_CONTENT_ITEM_ID_QUERY = SQLQueryBuilder()
            .table(LibraryCollectionConst.TABLE)
            .join(LibrarySectionConst.TABLE, LibraryCollectionConst.FULL_C_ID, LibrarySectionConst.FULL_C_LIBRARY_COLLECTION_ID)
            .join(LibraryItemConst.TABLE, LibrarySectionConst.FULL_C_ID, LibraryItemConst.FULL_C_LIBRARY_SECTION_ID)
            .filter(LibraryItemConst.FULL_C_ITEM_ID, "?")
            .buildQuery()

    private val PARENT_COLLECTION_QUERY = "SELECT * FROM ${LibraryCollectionConst.TABLE} WHERE " +
            "${LibraryCollectionConst.C_ID} = (" +
            "SELECT ${LibrarySectionConst.FULL_C_LIBRARY_COLLECTION_ID} FROM ${LibrarySectionConst.TABLE} WHERE ${LibrarySectionConst.FULL_C_ID} = (" +
            "SELECT ${LibraryCollectionConst.C_LIBRARY_SECTION_ID} FROM ${LibraryCollectionConst.TABLE} WHERE ${LibraryCollectionConst.FULL_C_ID} = ?))"

    fun findTitleById(id: Long): String {
        return findValueByRowId(
                valueType = String::class.java,
                column = LibraryCollectionConst.C_TITLE_HTML,
                rowId = id,
                defaultValue = "")
    }

    fun findRootCollection(languageId: Long): LibraryCollection? {
        return findByRowId(languageManager.findRootCollectionIdByLanguageId(languageId))
    }

    fun findIdByExternalId(collectionExternalId: String): Long {
        return findValueBySelection(Long::class.java,
                column = LibraryCollectionConst.C_ID,
                selection = LibraryCollectionConst.C_EXTERNAL_ID + " = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(collectionExternalId),
                defaultValue = 0L)
    }

    fun findDefaultIdByContentItemId(contentItemId: Long): Long {
        return findValueByRawQuery(Long::class.java, rawQuery = DEFAULT_ID_BY_CONTENT_ITEM_ID_QUERY, selectionArgs = SQLQueryBuilder.toSelectionArgs(contentItemId), defaultValue = 0L)
    }

    fun collectionExists(collectionId: Long): Boolean {
        return findCountBySelection(selection = "${LibraryCollectionConst.C_ID} = $collectionId") > 0
    }

    fun findParentCollection(collectionId: Long): LibraryCollection? {
        return findByRawQuery(rawQuery = PARENT_COLLECTION_QUERY, selectionArgs = SQLQueryBuilder.toSelectionArgs(collectionId))
    }


}