/*
 * NotebookManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.userdata.notebook

import org.dbtools.query.sql.SQLQueryBuilder
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.search.searchsuggestion.SearchSuggestion
import org.lds.ldssa.model.database.types.AnnotationStatusType
import org.lds.ldssa.model.database.types.SearchSuggestionType
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationConst
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager
import org.lds.ldssa.sync.AnnotationSyncScheduler
import org.lds.ldssa.util.ThreeTenUtil
import org.lds.ldssa.util.UserdataDbUtil
import org.threeten.bp.LocalDateTime
import java.util.ArrayList
import javax.inject.Inject


@javax.inject.Singleton
class NotebookManager @Inject constructor(databaseManager: DatabaseManager,
                                          val userdataDbUtil: UserdataDbUtil,
                                          val notebookAnnotationManager: NotebookAnnotationManager,
                                          val syncScheduler: AnnotationSyncScheduler) : NotebookBaseManager(databaseManager) {
    private val MODIFIED_SINCE = NotebookConst.C_LAST_MODIFIED + " > ?"
    private val NOTEBOOKS_BY_ANNOTATION_ID_QUERY = SQLQueryBuilder()
            .field(NotebookConst.TABLE + ".*") // only take the columns from the notebook table
            .table(NotebookConst.TABLE)
            .join(NotebookAnnotationConst.TABLE, NotebookConst.FULL_C_ID, NotebookAnnotationConst.FULL_C_NOTEBOOK_ID)
            .filter(NotebookAnnotationConst.FULL_C_ANNOTATION_ID, "?")
            .buildQuery()


    override fun getDatabaseName(): String {
        return userdataDbUtil.currentOpenedDatabaseName
    }

    override fun save(record: Notebook?, databaseName: String): Boolean {
        if (record == null) {
            return false
        }

        record.lastModified = LocalDateTime.now()
        record.initUniqueId()

        if (super.save(record, databaseName)) {
            // sync when possible
            syncScheduler.scheduleSync()
            return true
        } else {
            return false
        }
    }

    /**
     * ONLY USE WHEN SYNCING INCOMING NOTEBOOK/FOLDER CHANGES
     * Don't post SyncRequestEvent
     */
    fun syncSave(notebook: Notebook?): Boolean {
        if (notebook == null) {
            return false
        }

        notebook.initUniqueId()
        return super.save(notebook, getDatabaseName())
    }

    /**
     * ONLY USE WHEN SYNCING INCOMING ANNOTATION CHANGES
     * Don't post SyncRequestEvent
     */
    fun syncUpdateLastModified(uniqueId: String) {
        val values = createNewDBToolsContentValues()
        values.put(NotebookConst.C_LAST_MODIFIED, System.currentTimeMillis())
        update(values, NotebookConst.C_UNIQUE_ID + " = ? ", SQLQueryBuilder.toSelectionArgs(uniqueId))
    }

    fun findByUniqueId(uniqueId: String): Notebook? {
        return findBySelection("${NotebookConst.C_UNIQUE_ID} = ?", SQLQueryBuilder.toSelectionArgs(uniqueId))
    }

    fun markTrashed(id: Long) {
        val values = createNewDBToolsContentValues()
        values.put(NotebookConst.C_STATUS, AnnotationStatusType.TRASHED.ordinal)
        values.put(NotebookConst.C_LAST_MODIFIED, System.currentTimeMillis())
        update(values, id)
    }

    /**
     * Delete any remaining data that may have not been cleaned up by sync
     */
    fun purgeOrphanDeletedTrashed() {
        val folderIdsToDelete = findAllDeletedTrashedIds()
        for (folderId in folderIdsToDelete) {
            purgeNotebook(folderId)
        }
    }

    /**
     * When SYNC identifies an folder should be removed, then delete JUST that folder (annotation deletes happen separately)
     */
    fun purgeNotebook(folderUniqueId: String): Boolean {
        val notebookId = findIdByUniqueId(folderUniqueId)
        return notebookId != 0L && purgeNotebook(notebookId)
    }

    /**
     * When SYNC identifies an folder should be removed, then delete JUST that folder (annotation deletes happen separately)
     */
    private fun purgeNotebook(notebookId: Long): Boolean {
        notebookAnnotationManager.deleteAllByNotebookId(notebookId)
        return delete(notebookId) > 0
    }

    /**
     * Remove the notebook and associated annotations
     * NOTE: AnnotationManager is passed because of circular dependency
     */
    fun trashById(annotationManager: AnnotationManager, notebookId: Long) {
        trashIndividualNotebook(annotationManager, notebookId)
        syncScheduler.scheduleSync()
    }

    /**
     * Remove the notebooks and associated annotations
     * NOTE: AnnotationManager is passed because of circular dependency
     */
    fun trashByIds(annotationManager: AnnotationManager, notebookIds: List<Long>) {
        for (notebookId in notebookIds) {
            trashIndividualNotebook(annotationManager, notebookId)
        }

        syncScheduler.scheduleSync()
    }

    private fun trashIndividualNotebook(annotationManager: AnnotationManager, notebookId: Long) {
        markTrashed(notebookId)

        // annotations will need to be notified that they need to be updated (annotations have a "folders" section)
        for (annotationId in notebookAnnotationManager.findAllAnnotationIdsByNotebookId(notebookId)) {
            annotationManager.updateLastModified(annotationId, false)
        }

        // remove notebooks
        notebookAnnotationManager.deleteAllByNotebookId(notebookId)
    }

    /**
     * Merge notebooks into a new notebook
     * NOTE: AnnotationManager is passed because of circular dependency
     */
    fun mergeNotebooks(annotationManager: AnnotationManager, notebookName: String, notebookIds: List<Long>) {
        val newNotebook = Notebook()
        newNotebook.name = notebookName
        save(newNotebook)

        for (notebookId in notebookIds) {
            notebookAnnotationManager.moveNotebookAnnotations(notebookId, newNotebook.id)
        }

        trashByIds(annotationManager, notebookIds)
    }

    fun updateLastModified(id: Long) {
        val values = createNewDBToolsContentValues()
        values.put(NotebookConst.C_LAST_MODIFIED, System.currentTimeMillis())
        update(values, id)
    }

    fun findCursorAllChangesSince(sinceTs: LocalDateTime): List<Notebook> {
        return findAllBySelection(MODIFIED_SINCE,
                SQLQueryBuilder.toSelectionArgs(ThreeTenUtil.toMillis(sinceTs)))
    }

    fun findNotebookUniqueIdById(id: Long): String {
        return findValueByRowId(String::class.java,
                column = NotebookConst.C_UNIQUE_ID,
                rowId = id,
                defaultValue = "")
    }

    fun findName(id: Long): String {
        return findValueByRowId(String::class.java,
                column = NotebookConst.C_NAME,
                rowId = id,
                defaultValue = "")
    }

    fun findIdByUniqueId(notebookUniqueId: String): Long {
        return findValueBySelection(Long::class.java,
                column = NotebookConst.C_ID,
                selection = "${NotebookConst.C_UNIQUE_ID} = ?",
                selectionArgs = arrayOf(notebookUniqueId),
                defaultValue = 0L)
    }

    fun findAllDeletedTrashedIds(): List<Long> {
        return findAllValuesBySelection(Long::class.java,
                column = NotebookConst.C_ID,
                selection = "${NotebookConst.C_STATUS} = ? OR ${NotebookConst.C_STATUS} = ?",
                selectionArgs = SQLQueryBuilder.toSelectionArgs(AnnotationStatusType.TRASHED.ordinal, AnnotationStatusType.DELETED.ordinal))
    }

    fun updateAnnotationOrdering(notebookId: Long, annotations: List<Annotation>?) {
        if (annotations == null || annotations.isEmpty()) {
            return
        }

        notebookAnnotationManager.beginTransaction()
        // Save the new annotation ordering
        for (i in annotations.indices) {
            val annotation = annotations[i]
            notebookAnnotationManager.updateAnnotationOrder(i, annotation.id)
        }

        // make sure to sync folder change
        updateLastModified(notebookId)

        notebookAnnotationManager.endTransaction(true)

        // schedule sync
        syncScheduler.scheduleSync()
    }

    fun findAllByAnnotationId(annotationId: Long): List<Notebook> {
        return findAllByRawQuery(NOTEBOOKS_BY_ANNOTATION_ID_QUERY, SQLQueryBuilder.toSelectionArgs(annotationId))
    }

    fun findNotebookNameCount(notebookName: String): Long {
        return findCountBySelection(NotebookConst.C_NAME + " = ?", SQLQueryBuilder.toSelectionArgs(notebookName))
    }

    fun findAllSearchSuggestions(searchText: String, limit: Int): List<SearchSuggestion> {
        val suggestions = ArrayList<SearchSuggestion>()
        val notebooks = findAllByRawQuery("SELECT * FROM ${NotebookConst.TABLE} WHERE ${NotebookConst.C_NAME} LIKE ? LIMIT ?",
                SQLQueryBuilder.toSelectionArgs("%$searchText%", limit))

        for (notebook in notebooks) {
            val searchSuggestion = SearchSuggestion()
            searchSuggestion.type = SearchSuggestionType.NOTEBOOK
            searchSuggestion.id = notebook.id
            searchSuggestion.title = notebook.name

            suggestions.add(searchSuggestion)
        }


        return suggestions
    }

}