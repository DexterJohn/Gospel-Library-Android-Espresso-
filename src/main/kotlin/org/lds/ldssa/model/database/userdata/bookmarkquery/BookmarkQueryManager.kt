/*
 * BookmarkQueryManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.userdata.bookmarkquery

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.types.AnnotationStatusType
import org.lds.ldssa.model.database.userdata.annotation.AnnotationConst
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkConst
import org.lds.ldssa.util.UserdataDbUtil
import javax.inject.Inject


@javax.inject.Singleton
class BookmarkQueryManager @Inject constructor(databaseManager: DatabaseManager, val userdataDbUtil: UserdataDbUtil) : BookmarkQueryBaseManager(databaseManager) {
    companion object {
        private val ALL_QUERY: String
        private val SINGLE_QUERY: String

        init {
            val allQuery = SQLQueryBuilder()
                    .field(BookmarkConst.FULL_C_ID, BookmarkQueryConst.C_ID)
                    .field(BookmarkConst.FULL_C_ANNOTATION_ID, BookmarkQueryConst.C_ANNOTATION_ID)
                    .field(AnnotationConst.FULL_C_DOC_ID, BookmarkQueryConst.C_DOC_ID)
                    .field(BookmarkConst.FULL_C_PARAGRAPH_AID, BookmarkQueryConst.C_PARAGRAPH_AID)
                    .field(BookmarkConst.FULL_C_NAME, BookmarkQueryConst.C_NAME)
                    .field(BookmarkConst.FULL_C_CITATION, BookmarkQueryConst.C_CITATION)
                    .field(BookmarkConst.FULL_C_DISPLAY_ORDER, BookmarkQueryConst.C_DISPLAY_ORDER)
                    .field(AnnotationConst.FULL_C_LAST_MODIFIED, BookmarkQueryConst.C_LAST_MODIFIED)
                    .table(BookmarkConst.TABLE)
                    .join(AnnotationConst.TABLE, AnnotationConst.FULL_C_ID, BookmarkConst.FULL_C_ANNOTATION_ID)
                    .filter(AnnotationConst.C_STATUS, AnnotationStatusType.ACTIVE.ordinal)
                    .orderBy(BookmarkConst.C_DISPLAY_ORDER)
                    .orderBy(AnnotationConst.C_LAST_MODIFIED, false)

            ALL_QUERY = allQuery.buildQuery()

            // add the bookmark filter to the all query to make the single query
            allQuery.filter(BookmarkConst.FULL_C_ID, "?")
            SINGLE_QUERY = allQuery.buildQuery()
        }
    }

    override fun getDatabaseName(): String {
        return userdataDbUtil.currentOpenedDatabaseName
    }

    override fun getQuery(): String {
        return ALL_QUERY
    }

    fun findAllOrderByDisplayOrderAndLastModified(): List<BookmarkQuery> {
        return findAllByRawQuery(ALL_QUERY, null)
    }

    fun findByBookmarkId(bookmarkId: Long): BookmarkQuery? {
        return findByRawQuery(rawQuery = SINGLE_QUERY,
                selectionArgs = SQLQueryBuilder.toSelectionArgs(bookmarkId))
    }

}