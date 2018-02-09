/*
 * NotebookViewManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.userdata.notebookview

import org.dbtools.query.shared.JoinType
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.types.AnnotationStatusType
import org.lds.ldssa.model.database.userdata.notebook.NotebookConst
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationConst
import org.lds.ldssa.model.prefs.type.NotebookSortType
import org.lds.ldssa.util.UserdataDbUtil
import javax.inject.Inject


@javax.inject.Singleton
class NotebookViewManager @Inject constructor(databaseManager: DatabaseManager, val userdataDbUtil: UserdataDbUtil) : NotebookViewBaseManager(databaseManager) {
    companion object {
        private val ALIAS_TABLE_ITEM_COUNT = "note_item_count"
        private val ALIAS_TABLE_ITEM_COUNT_C_NOTEBOOK_ID = "notebook_id"
        private val ALIAS_TABLE_ITEM_COUNT_C_COUNT = "count"
        private val ALIAS_TABLE_ITEM_COUNT_FULL_C_COUNT = ALIAS_TABLE_ITEM_COUNT + "." + ALIAS_TABLE_ITEM_COUNT_C_COUNT
        private val QUERY_FOR_ANNOTATION: SQLQueryBuilder

        val DROP_VIEW: String = "DROP VIEW IF EXISTS " + NotebookViewConst.TABLE + ";"
        val CREATE_VIEW: String

        init {
            val noteCountSubQuery = SQLQueryBuilder()
                    .field(NotebookAnnotationConst.FULL_C_NOTEBOOK_ID, ALIAS_TABLE_ITEM_COUNT_C_NOTEBOOK_ID)
                    .field("COUNT(" + NotebookAnnotationConst.FULL_C_NOTEBOOK_ID + ")", ALIAS_TABLE_ITEM_COUNT_C_COUNT)
                    .table(NotebookAnnotationConst.TABLE)
                    .groupBy(NotebookAnnotationConst.FULL_C_NOTEBOOK_ID)
                    .buildQuery()

            CREATE_VIEW = "CREATE VIEW IF NOT EXISTS " + NotebookViewConst.TABLE + " AS " +
                    SQLQueryBuilder()
                            .field(NotebookConst.FULL_C_ID, NotebookViewConst.C_ID)
                            .field(NotebookConst.FULL_C_UNIQUE_ID, NotebookViewConst.C_UNIQUE_ID)
                            .field(NotebookConst.FULL_C_NAME, NotebookViewConst.C_NAME)
                            .field(ALIAS_TABLE_ITEM_COUNT_FULL_C_COUNT, NotebookViewConst.C_COUNT)
                            .field(NotebookConst.FULL_C_STATUS, NotebookViewConst.C_STATUS)
                            .field(NotebookConst.FULL_C_LAST_MODIFIED, NotebookViewConst.C_LAST_MODIFIED)
                            .field("0", NotebookViewConst.C_SELECTED) // used for notebook selection
                            .table(NotebookConst.TABLE)
                            .join(JoinType.LEFT_JOIN, "($noteCountSubQuery) AS $ALIAS_TABLE_ITEM_COUNT", NotebookConst.FULL_C_ID, ALIAS_TABLE_ITEM_COUNT + "." + NotebookAnnotationConst.C_NOTEBOOK_ID)
                            .filter(NotebookConst.C_STATUS, AnnotationStatusType.ACTIVE.ordinal)
                            .buildQuery()

            QUERY_FOR_ANNOTATION = SQLQueryBuilder()
                    .field("*")
                    .field("(SELECT count(1) FROM ${NotebookAnnotationConst.TABLE} WHERE ${NotebookAnnotationConst.FULL_C_NOTEBOOK_ID} = ${NotebookViewConst.FULL_C_ID} AND ${NotebookAnnotationConst.FULL_C_ANNOTATION_ID} = ?)", NotebookViewConst.C_SELECTED)
                    .table(NotebookViewConst.TABLE)
        }
    }

    override fun getDatabaseName(): String {
        return userdataDbUtil.currentOpenedDatabaseName
    }

    fun findAllFilter(filterText: String, sortType: NotebookSortType): List<NotebookView> {
        val sortOrder = getSortOrder(sortType)
        if (filterText.isBlank()) {
            return findAllBySelection(orderBy = sortOrder)
        }

        return findAllBySelection(
                selection = "${NotebookViewConst.C_NAME} LIKE ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs("%$filterText%"),
                orderBy = sortOrder)
    }

    fun findAllOrderBy(sortType: NotebookSortType, selectedForAnnotationId: Long): List<NotebookView> {
        val query = QUERY_FOR_ANNOTATION.clone()
                .orderBy(getSortOrder(sortType))
                .buildQuery()
        return findAllByRawQuery(query, SQLQueryBuilder.toSelectionArgs(selectedForAnnotationId))
    }

    private fun getSortOrder(sortType: NotebookSortType): String {
        return when (sortType) {
            NotebookSortType.ALPHABETICAL -> NotebookViewConst.C_NAME + " COLLATE NOCASE ASC"
            NotebookSortType.MOST_RECENT -> NotebookViewConst.C_LAST_MODIFIED + " DESC"
            NotebookSortType.COUNT -> NotebookViewConst.C_COUNT + " DESC"
        }
    }
}