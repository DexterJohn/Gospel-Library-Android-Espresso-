/*
 * LibraryCollectionQueryManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.catalog.librarycollectionquery

import org.dbtools.query.shared.JoinType
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.catalog.language.LanguageConst
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionConst
import org.lds.ldssa.model.database.catalog.librarysection.LibrarySectionConst
import javax.inject.Inject


@javax.inject.Singleton
class LibraryCollectionQueryManager @Inject constructor(databaseManager: DatabaseManager) : LibraryCollectionQueryBaseManager(databaseManager) {

    companion object {
        private val COLLECTION_ALIAS = "ALIAS"
        private val QUERY_BUILDER = SQLQueryBuilder()
                .table(LibraryCollectionConst.TABLE)
                .field(LibraryCollectionConst.FULL_C_ID, LibraryCollectionQueryConst.C_ID)
                .field(LibraryCollectionConst.FULL_C_TITLE_HTML, LibraryCollectionQueryConst.C_TITLE)
                .field(LibraryCollectionConst.FULL_C_ID, LibraryCollectionQueryConst.C_ID)
                .field("${LanguageConst.FULL_C_ID} IS NOT NULL AS ${LibraryCollectionQueryConst.C_ROOT}")
                .field("$COLLECTION_ALIAS.${LibraryCollectionConst.C_TITLE_HTML}", LibraryCollectionQueryConst.C_PARENT_TITLE)
                .join(LibrarySectionConst.TABLE, LibrarySectionConst.FULL_C_ID, LibraryCollectionConst.FULL_C_LIBRARY_SECTION_ID)
                .join("${LibraryCollectionConst.TABLE} $COLLECTION_ALIAS", "$COLLECTION_ALIAS.${LibraryCollectionConst.C_ID}", LibrarySectionConst.FULL_C_LIBRARY_COLLECTION_ID)
                .join(JoinType.LEFT_JOIN, LanguageConst.TABLE, LanguageConst.FULL_C_ROOT_LIBRARY_COLLECTION_ID, LibrarySectionConst.FULL_C_LIBRARY_COLLECTION_ID)
                .orderBy(LibrarySectionConst.FULL_C_POSITION, LibraryCollectionConst.FULL_C_POSITION)
    }

    override fun getQuery() : String {
        return QUERY_BUILDER.buildQuery()
    }
}