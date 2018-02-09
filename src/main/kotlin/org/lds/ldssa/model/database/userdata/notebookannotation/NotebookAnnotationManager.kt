/*
 * NotebookAnnotationManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.userdata.notebookannotation

import org.dbtools.query.shared.CompareType
import org.dbtools.query.shared.JoinType
import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.userdata.annotation.AnnotationConst
import org.lds.ldssa.util.UserdataDbUtil
import timber.log.Timber
import javax.inject.Inject


@javax.inject.Singleton
class NotebookAnnotationManager @Inject constructor(databaseManager: DatabaseManager, val userdataDbUtil: UserdataDbUtil) : NotebookAnnotationBaseManager(databaseManager) {
    private val NOTEBOOK_ORDER_QUERY: String = SQLQueryBuilder()
            .field(AnnotationConst.C_UNIQUE_ID)
            .table(NotebookAnnotationConst.TABLE)
            .join(AnnotationConst.TABLE, NotebookAnnotationConst.FULL_C_ANNOTATION_ID, AnnotationConst.FULL_C_ID)
            .filter(NotebookAnnotationConst.C_NOTEBOOK_ID, "?")
            .orderBy(NotebookAnnotationConst.C_DISPLAY_ORDER)
            .buildQuery()

    private val RE_ASSOCIATE_ANNOTATIONS: String = "UPDATE " + NotebookAnnotationConst.TABLE + " SET " + NotebookAnnotationConst.C_ANNOTATION_ID + " = " +
            "(SELECT " + AnnotationConst.FULL_C_ID + " FROM " + AnnotationConst.TABLE + " WHERE " + NotebookAnnotationConst.FULL_C_UNIQUE_ANNOTATION_ID + " = " + AnnotationConst.FULL_C_UNIQUE_ID + ") " +
            " WHERE " + NotebookAnnotationConst.FULL_C_ID + " = " +
            "(SELECT " + NotebookAnnotationConst.FULL_C_ID + " FROM " + NotebookAnnotationConst.TABLE +
            " WHERE " + NotebookAnnotationConst.FULL_C_ANNOTATION_ID + " NOT IN (SELECT " + AnnotationConst.FULL_C_ID + " FROM " + AnnotationConst.TABLE + "));"

    private val UPDATE_ASSOCIATIONS_QUERY: String = "UPDATE " + NotebookAnnotationConst.TABLE + " SET " + NotebookAnnotationConst.C_ANNOTATION_ID + " = ifnull( " +
            "(select " + AnnotationConst.FULL_C_ID + " FROM " + AnnotationConst.TABLE +
            " WHERE " + NotebookAnnotationConst.FULL_C_UNIQUE_ANNOTATION_ID + " = " + AnnotationConst.FULL_C_UNIQUE_ID + ") " + // end of subquery
            ", 0)" + // end of ifnull
            " WHERE " + NotebookAnnotationConst.FULL_C_ANNOTATION_ID + " = 0"

    // only update associations when the annotation_id is 0
    private val NO_ANNOTATION_QUERY: String = SQLQueryBuilder()
            .field(NotebookAnnotationConst.FULL_C_ID)
            .table(NotebookAnnotationConst.TABLE)
            .join(JoinType.LEFT_JOIN, AnnotationConst.TABLE, NotebookAnnotationConst.FULL_C_UNIQUE_ANNOTATION_ID, AnnotationConst.FULL_C_UNIQUE_ID)
            .filter(AnnotationConst.FULL_C_ID, CompareType.IS_NULL)
            .buildQuery()

    override fun getDatabaseName(): String {
        return userdataDbUtil.currentOpenedDatabaseName
    }

    fun updateAnnotationOrder(order: Int, annotationId: Long) {
        val values = createNewDBToolsContentValues()
        values.put(NotebookAnnotationConst.C_DISPLAY_ORDER, order)
        update(values, NotebookAnnotationConst.C_ANNOTATION_ID + " = ?", SQLQueryBuilder.toSelectionArgs(annotationId))
    }

    fun findAnnotationIdDisplayOrder(id: Long): List<String> {
        return findAllValuesByRawQuery(String::class.java, NOTEBOOK_ORDER_QUERY, SQLQueryBuilder.toSelectionArgs(id))
    }

    fun findAllByAnnotationId(annotationId: Long): List<NotebookAnnotation> {
        return findAllBySelection("${NotebookAnnotationConst.C_ANNOTATION_ID} = ?", SQLQueryBuilder.toSelectionArgs(annotationId))
    }

    fun findAllNotebookIdsByAnnotationId(annotationId: Long): List<Long> {
        return findAllValuesBySelection(Long::class.java,
                NotebookAnnotationConst.C_NOTEBOOK_ID,
                NotebookAnnotationConst.C_ANNOTATION_ID + " = ?",
                SQLQueryBuilder.toSelectionArgs(annotationId))
    }

    fun findAllByNotebookId(notebookId: Long): List<NotebookAnnotation> {
        return findAllBySelection(NotebookAnnotationConst.C_NOTEBOOK_ID + " = ?", SQLQueryBuilder.toSelectionArgs(notebookId))
    }

    fun moveNotebookAnnotations(originNotebookId: Long, destinationNotebookId: Long) {
        val values = createNewDBToolsContentValues()
        values.put(NotebookAnnotationConst.C_NOTEBOOK_ID, destinationNotebookId)
        update(values, NotebookAnnotationConst.C_NOTEBOOK_ID + "=?", SQLQueryBuilder.toSelectionArgs(originNotebookId))
    }

    fun deleteByNotebookIdAndAnnotationId(notebookId: Long, annotationId: Long): Int {
        return delete(NotebookAnnotationConst.C_NOTEBOOK_ID + " = ? AND " + NotebookAnnotationConst.C_ANNOTATION_ID + " = ?",
                SQLQueryBuilder.toSelectionArgs(notebookId, annotationId))
    }

    fun findAllAnnotationIdsByNotebookId(notebookId: Long): List<Long> {
        return findAllValuesBySelection(Long::class.java, NotebookAnnotationConst.C_ANNOTATION_ID, NotebookAnnotationConst.C_NOTEBOOK_ID + " = ?", SQLQueryBuilder.toSelectionArgs(notebookId))
    }

    fun deleteAllByNotebookId(notebookId: Long): Int {
        return delete(NotebookAnnotationConst.C_NOTEBOOK_ID + " = ?", SQLQueryBuilder.toSelectionArgs(notebookId))
    }

    fun deleteAllByAnnotationId(annotationId: Long): Int {
        return delete(NotebookAnnotationConst.C_ANNOTATION_ID + " = ?", SQLQueryBuilder.toSelectionArgs(annotationId))
    }

    fun deleteAllByNotebookIdAndAnnotationId(notebookId: Long, annotationId: Long): Int {
        return delete("${NotebookAnnotationConst.C_NOTEBOOK_ID} = ? AND ${NotebookAnnotationConst.C_ANNOTATION_ID} = ?", SQLQueryBuilder.toSelectionArgs(notebookId, annotationId))
    }

    /**
     * After a sync, annotation pk's need to be associated to notebook pk's...
     */
    fun updateNotebookAnnotationAssociations() {
        // 1. Remove any folder annotations that do NOT have an associated annotation (by unique id).  MUST BE DONE BEFORE RE_ASSOCIATE_ANNOTATIONS
        deleteAllWithoutMatchingAnnotation()

        // 2. When annotations get update, they get removed, then re-added... the following will find all rows that have annotationIds that no longer exist
        //    and find the new annotationId (based on annotation unique Id) and set it on the appropriate notebookAnnotations (updating the id on NotebookAnnotation.annotation_id)
        executeSql(RE_ASSOCIATE_ANNOTATIONS)

        // 3. resolve the annotation unique id's to pk's (place in annotation_id column)
        executeSql(UPDATE_ASSOCIATIONS_QUERY)

        // 4. remove any associations that could not be resolved (there was no annotation, in the annotations table, for the given unique id)
        delete(NotebookAnnotationConst.C_ANNOTATION_ID + " = ?", SQLQueryBuilder.toSelectionArgs(0))
    }

    fun shiftDisplayOrder(notebookId: Long) {
        executeSql("UPDATE " + NotebookAnnotationConst.TABLE + " SET " + NotebookAnnotationConst.C_DISPLAY_ORDER + " =  (" + NotebookAnnotationConst.C_DISPLAY_ORDER + " + 1)" +
                " WHERE " + NotebookAnnotationConst.C_NOTEBOOK_ID + " = " + notebookId)
    }

    fun findCountByNotebookIdAndAnnotationUniqueId(notebookId: Long, annotationUniqueId: String): Long {
        return findCountBySelection(NotebookAnnotationConst.C_NOTEBOOK_ID + " = ? AND " + NotebookAnnotationConst.C_UNIQUE_ANNOTATION_ID + " = ?",
                SQLQueryBuilder.toSelectionArgs(notebookId, annotationUniqueId))
    }

    /**
     * It is possible that the service will pass an annotation guid on a folder, but never pass an annotation for that guid in the sync annotation call...
     */
    fun deleteAllWithoutMatchingAnnotation() {
        val ids = findAllWithoutMatchingAnnotation()

        if (!ids.isEmpty()) {
            val message = "Found [" + ids.size + "] notebook annotations with out a matching annotation"

            Timber.e(message)

            // delete items
            for (id in ids) {
                delete(id)
            }
        }
    }

    /**
     * It is possible that the service will pass an annotation guid on a folder, but never pass an annotation for that guid in the sync annotation call...

     * @return List of NotebookAnnotation ids for records that don't have an associated annotation (by unique id)
     */
    fun findAllWithoutMatchingAnnotation(): List<Long> {
        return findAllValuesByRawQuery(Long::class.java, NO_ANNOTATION_QUERY)
    }
}