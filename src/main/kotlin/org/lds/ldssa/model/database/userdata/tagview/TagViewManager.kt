/*
 * TagViewManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.userdata.tagview

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.userdata.annotation.AnnotationConst
import org.lds.ldssa.model.database.userdata.tag.TagConst
import org.lds.ldssa.model.prefs.type.TagSortType
import org.lds.ldssa.util.UserdataDbUtil
import javax.inject.Inject


@javax.inject.Singleton
class TagViewManager @Inject constructor(databaseManager: DatabaseManager, val userdataDbUtil: UserdataDbUtil) : TagViewBaseManager(databaseManager) {
    companion object {
        val DROP_VIEW = "DROP VIEW IF EXISTS " + TagViewConst.TABLE + ";"
        val CREATE_VIEW = "CREATE VIEW IF NOT EXISTS " + TagViewConst.TABLE + " AS " +
                SQLQueryBuilder()
                        .field(TagConst.C_NAME, TagViewConst.C_NAME)
                        .field("COUNT(" + TagConst.C_NAME + ")", TagViewConst.C_COUNT)
                        .field("MAX(" + AnnotationConst.C_LAST_MODIFIED + ")", TagViewConst.C_LAST_MODIFIED)
                        .field("0", TagViewConst.C_SELECTED) // used for tag selection
                        .table(TagConst.TABLE)
                        .join(AnnotationConst.TABLE, TagConst.C_ANNOTATION_ID, AnnotationConst.FULL_C_ID)
                        .groupBy(TagConst.C_NAME)
                        .buildQuery()

        private val QUERY_FOR_ANNOTATION = SQLQueryBuilder()
                .field("*")
                .field("(SELECT count(1) FROM ${TagConst.TABLE} WHERE ${TagConst.FULL_C_NAME} = ${TagViewConst.FULL_C_NAME} AND ${TagConst.FULL_C_ANNOTATION_ID} = ?)", TagViewConst.C_SELECTED)
                .table(TagViewConst.TABLE)

    }

    override fun getDatabaseName(): String {
        return userdataDbUtil.currentOpenedDatabaseName
    }

    fun findAllFilter(filter: String, sortType: TagSortType): List<TagView> {
        return findAllBySelection(selection = "${TagConst.C_NAME} LIKE ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs("%$filter%"),
                orderBy = getSortOrder(sortType))
    }

    fun findAllOrderBy(sortType: TagSortType): List<TagView> {
        return findAll(orderBy = getSortOrder(sortType))
    }

    fun findAllFilter(filter: String, sortType: TagSortType, selectedForAnnotationId: Long): List<TagView> {
        val query = QUERY_FOR_ANNOTATION.clone()
                .filter("${TagViewConst.C_NAME} LIKE ?")
                .orderBy(getSortOrder(sortType))
                .buildQuery()
        return findAllByRawQuery(query, SQLQueryBuilder.toSelectionArgs(selectedForAnnotationId, "%$filter%"))
    }

    fun findAllOrderBy(sortType: TagSortType, selectedForAnnotationId: Long): List<TagView> {
        val query = QUERY_FOR_ANNOTATION.clone()
                .orderBy(getSortOrder(sortType))
                .buildQuery()
        return findAllByRawQuery(query, SQLQueryBuilder.toSelectionArgs(selectedForAnnotationId))
    }

    fun findTagNameExists(tagName: String): Boolean {
        return findCountBySelection("${TagViewConst.C_NAME} = ?", SQLQueryBuilder.toSelectionArgs(tagName)) > 0
    }

    private fun getSortOrder(sortType: TagSortType): String {
        return when (sortType) {
            TagSortType.ALPHABETICAL -> "${TagViewConst.C_NAME} COLLATE NOCASE ASC"
            TagSortType.MOST_RECENT -> "${TagViewConst.C_LAST_MODIFIED} DESC"
            TagSortType.COUNT -> "${TagViewConst.C_COUNT} DESC, ${TagViewConst.C_NAME} COLLATE NOCASE ASC"
        }
    }
}