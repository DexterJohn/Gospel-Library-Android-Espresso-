/*
 * AnnotationManager.kt
 *
 * Generated on: 02/09/2017 11:39:55
 *
 */



package org.lds.ldssa.model.database.userdata.annotation

import android.arch.paging.LivePagedListProvider
import android.os.Build
import com.google.gson.Gson
import org.apache.commons.lang3.StringUtils
import org.dbtools.android.domain.DBToolsTiledDataSource
import org.dbtools.query.sql.SQLQueryBuilder
import org.json.JSONObject
import org.lds.ldssa.BuildConfig
import org.lds.ldssa.event.account.AccountSignInPromptEvent
import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.model.database.types.AnnotationStatusType
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkConst
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager
import org.lds.ldssa.model.database.userdata.highlight.HighlightConst
import org.lds.ldssa.model.database.userdata.highlight.HighlightManager
import org.lds.ldssa.model.database.userdata.link.LinkManager
import org.lds.ldssa.model.database.userdata.note.NoteConst
import org.lds.ldssa.model.database.userdata.note.NoteManager
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotation
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationConst
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager
import org.lds.ldssa.model.database.userdata.tag.TagConst
import org.lds.ldssa.model.database.userdata.tag.TagManager
import org.lds.ldssa.sync.AnnotationSyncScheduler
import org.lds.ldssa.util.ThreeTenUtil
import org.lds.ldssa.util.UserdataDbUtil
import org.threeten.bp.LocalDateTime
import pocketbus.Bus
import timber.log.Timber
import java.util.ArrayList
import java.util.LinkedList
import javax.inject.Inject


@javax.inject.Singleton
class AnnotationManager @Inject constructor(databaseManager: DatabaseManager,
                                            private val bus: Bus,
                                            private val gson: Gson,
                                            private val syncScheduler: AnnotationSyncScheduler,
                                            private val highlightManager: HighlightManager,
                                            private val bookmarkManager: BookmarkManager,
                                            private val noteManager: NoteManager,
                                            private val linkManager: LinkManager,
                                            private val tagManager: TagManager,
                                            private val userdataDbUtil: UserdataDbUtil,
                                            private val notebookAnnotationManager: NotebookAnnotationManager,
                                            private val notebookManager: NotebookManager) : AnnotationBaseManager(databaseManager) {

    override fun getDatabaseName(): String {
        return userdataDbUtil.currentOpenedDatabaseName
    }

    fun findFullAnnotationByRowId(id: Long): Annotation? {
        val annotation = findByRowId(id)
        findFullAnnotationData(annotation)
        return annotation
    }

    fun findAllByTag(tagName: String): List<Annotation> {
        val args = SQLQueryBuilder.toSelectionArgs(tagName)

        val annotations = findAllByRawQuery(QUERY_BY_TAG_NAME, args)

        for (annotation in annotations) {
            findFullAnnotationData(annotation)
        }

        return annotations
    }

    fun findAllByNotebook(notebookId: Long): List<Annotation> {
        val args = SQLQueryBuilder.toSelectionArgs(
                notebookId
        )

        val annotations = findAllByRawQuery(QUERY_BY_NOTEBOOK, args)

        for (annotation in annotations) {
            findFullAnnotationData(annotation)
        }

        return annotations
    }

    fun findAllLivePagedListProvider(): LivePagedListProvider<Int, Annotation> {
        return object : LivePagedListProvider<Int, Annotation>() {
            override fun createDataSource() = object : DBToolsTiledDataSource<Annotation>(this@AnnotationManager) {
                override fun loadRange(startPosition: Int, count: Int) = findAllOffset(count, startPosition)
                override fun countItems() = findCount().toInt()
            }
        }
    }

    fun findAllOffset(count: Int, offset: Int): List<Annotation> {
        val args = SQLQueryBuilder.toSelectionArgs(
                count,
                offset
        )

        val cursor = findCursorByRawQuery(QUERY_RECENT, args)
        val annotations = getAllItemsFromCursor(cursor)

        // Retrieves the remaining information associated with the Annotation (Tags, Notes, Highlights, etc.)
        for (annotation in annotations) {
            findFullAnnotationData(annotation)
        }

        return annotations
    }

    fun findFullAnnotationData(annotation: Annotation?) {
        if (annotation == null) {
            return
        }

        val annotationId = annotation.id
        annotation.highlights = highlightManager.findAllByAnnotationId(annotationId).toMutableList()
        annotation.bookmark = bookmarkManager.findByAnnotationId(annotationId)
        annotation.note = noteManager.findByAnnotationId(annotationId)
        annotation.tags = tagManager.findAllByAnnotationId(annotationId).toMutableList()
        annotation.links = linkManager.findAllByAnnotationId(annotationId).toMutableList()
        annotation.notebooks = notebookManager.findAllByAnnotationId(annotationId)
    }

    override fun save(record: Annotation?, databaseName: String): Boolean {
        if (record == null) {
            return false
        }

        var success = false
        val newRecord = record.isNewRecord
        var localTransactionStarted = false
        if (!inTransaction()) {
            beginTransaction()
            localTransactionStarted = true
        }

        record.device = ANNOTATION_DEVICE
        record.source = getAnnotationSource()
        record.lastModified = LocalDateTime.now()
        record.initUniqueId() // make sure this record has a unique id

        if (super.save(record, databaseName)) {
            record.applyAnnotationId()

            // Bookmarks
            bookmarkManager.save(record.bookmark)

            // Notes
            val note = record.note
            if (note != null) {
                if (note.isEmpty()) {
                    noteManager.delete(note)
                    record.note = null
                } else {
                    noteManager.save(note)
                }
            }

            // Tags
            if (!newRecord) {
                // overwrite existing
                val existingTagIdQueue = LinkedList<Long>(tagManager.findAllIdsByAnnotationId(record.id))
                for (tag in record.tags) {
                    if (existingTagIdQueue.peek() != null) {
                        tag.id = existingTagIdQueue.poll()
                    } else {
                        tag.id = 0 // new record..
                    }
                }

                // cleanup orphans
                for (tagId in existingTagIdQueue) {
                    tagManager.delete(tagId)
                }
            }

            val tags = record.tags
            for (tag in tags) {
                tagManager.save(tag)
            }

            // Links
            if (!newRecord) {
                // overwrite existing
                val existingLinkIdQueue = LinkedList<Long>(linkManager.findAllIdsByAnnotationId(record.id))
                for (link in record.links) {
                    if (existingLinkIdQueue.peek() != null) {
                        link.id = existingLinkIdQueue.poll()
                    } else {
                        link.id = 0 // new record..
                    }
                }

                // cleanup orphans
                for (linkId in existingLinkIdQueue) {
                    linkManager.delete(linkId)
                }
            }

            val links = record.links
            for (link in links) {
                linkManager.save(link)
            }

            // Highlights
            if (!newRecord) {
                // overwrite existing
                val existingHighlightIdQueue = LinkedList<Long>(highlightManager.findAllIdsByAnnotationId(record.id))
                for (highlight in record.highlights) {
                    if (existingHighlightIdQueue.peek() != null) {
                        highlight.id = existingHighlightIdQueue.poll()
                    } else {
                        highlight.id = 0 // new record..
                    }
                }

                // cleanup orphans
                for (highlightId in existingHighlightIdQueue) {
                    highlightManager.delete(highlightId)
                }
            }

            highlightManager.saveAll(record.highlights)

            // finalize
            notifyAndScheduleAnnotationSync()

            success = true
        }

        if (localTransactionStarted) {
            endTransaction(success)
        }

        return success
    }

    private fun notifyAndScheduleAnnotationSync() {
        // sync when possible
        syncScheduler.scheduleSync()
        bus.postSticky(AccountSignInPromptEvent())
    }

    /**
     * ONLY USE WHEN SYNCING INCOMING ANNOTATION CHANGES
     * (Will not schedule a sync)
     */
    fun syncSave(annotation: Annotation?): Boolean {
        if (annotation == null) {
            return false
        }

        annotation.initUniqueId() // make sure this record has a unique id

        return super.save(annotation, getDatabaseName())
    }

    /**
     * ONLY USE WHEN SYNCING INCOMING ANNOTATION CHANGES
     * Don't post SyncRequestEvent
     */
    fun syncUpdateLastModified(uniqueId: String) {
        val values = createNewDBToolsContentValues()
        values.put(AnnotationConst.C_LAST_MODIFIED, System.currentTimeMillis())
        update(values, """${AnnotationConst.C_UNIQUE_ID} = ? """, SQLQueryBuilder.toSelectionArgs(uniqueId))
    }

    fun updateLastModified(id: Long, requestSync: Boolean) {
        val values = createNewDBToolsContentValues()
        values.put(AnnotationConst.C_LAST_MODIFIED, System.currentTimeMillis())
        values.put(AnnotationConst.C_DEVICE, ANNOTATION_DEVICE)
        values.put(AnnotationConst.C_SOURCE, getAnnotationSource())
        update(values, id)

        if (requestSync) {
            notifyAndScheduleAnnotationSync()
        }
    }

    /**
     * When CODE needs to remove an annotation... mark it for deletion (trashed).  Also, delete any associated FolderAnnotations
     */
    fun trashById(annotationId: Long) {
        highlightManager.deleteAllByAnnotationId(annotationId)
        tagManager.deleteAllByAnnotationId(annotationId)
        linkManager.deleteAllByAnnotationId(annotationId)
        bookmarkManager.deleteAllByAnnotationId(annotationId)
        noteManager.deleteAllByAnnotationId(annotationId)
        val notebookIds = notebookAnnotationManager.findAllNotebookIdsByAnnotationId(annotationId)
        for (notebookId in notebookIds) {
            notebookAnnotationManager.deleteByNotebookIdAndAnnotationId(notebookId, annotationId)
            notebookManager.updateLastModified(notebookId)
        }

        markTrashed(annotationId)

        // sync when possible
        notifyAndScheduleAnnotationSync()
    }

    /**
     * If the annotation does not contain any savable content (highlight, note, tag, link, or bookmark) then this method will trash the annotation

     * @return true if the annotation was trashed
     */
    fun trashAnnotationIfNoSavableContent(annotationId: Long): Boolean {
        // Check to see if the annotation is tied to a highlight
        if (highlightManager.findCountByAnnotationId(annotationId) > 0) {
            return false
        }

        // Check to see if the annotation has an empty note (no title or content).
        if (!noteManager.isNoteEmpty(annotationId)) {
            return false
        }

        // Check to see if the annotation is tied to a tag
        if (tagManager.findCountByAnnotationId(annotationId) > 0) {
            return false
        }

        // Check to see if the annotation is tied to a link
        // A link cannot exist without a reference (highlight) in the sync service.
        // This check should never return a value greater than zero for now but could change in the future
        if (linkManager.findCountByAnnotationId(annotationId) > 0) {
            return false
        }

        // Check to see if the annotation is tied to a bookmark
        if (bookmarkManager.findCountByAnnotationId(annotationId) > 0) {
            return false
        }

        // No savable content, trash the annotation
        trashById(annotationId)
        return true
    }

    private fun markTrashed(id: Long) {
        val values = createNewDBToolsContentValues()
        values.put(AnnotationConst.C_STATUS, AnnotationStatusType.TRASHED.ordinal)
        values.put(AnnotationConst.C_LAST_MODIFIED, System.currentTimeMillis())
        update(values, id)
    }

    /**
     * Delete any remaining data that may not have been cleaned up by sync
     */
    fun purgeOrphanDeletedTrashed() {
        // annotations
        val annotationIdsToDelete = findAllDeletedTrashedIds()
        for (annotationId in annotationIdsToDelete) {
            purgeAnnotation(annotationId)
        }
    }

    /**
     * When SYNC identifies an annotation should be removed, then delete JUST that annotation (folder deletes happen separately)
     */
    fun purgeAnnotation(annotationUniqueId: String): Boolean {

        val annotationId = findIdByUniqueId(annotationUniqueId)
        return annotationId != 0L && purgeAnnotation(annotationId)
    }

    /**
     * When SYNC identifies an annotation should be removed, then delete JUST that annotation (folder deletes happen separately)
     */
    fun purgeAnnotation(annotationId: Long): Boolean {
        highlightManager.deleteAllByAnnotationId(annotationId)
        tagManager.deleteAllByAnnotationId(annotationId)
        linkManager.deleteAllByAnnotationId(annotationId)
        bookmarkManager.deleteAllByAnnotationId(annotationId)
        noteManager.deleteAllByAnnotationId(annotationId)
        return delete(annotationId) > 0
    }


    fun findByUniqueId(uniqueId: String): Annotation? {
        return findBySelection("${AnnotationConst.C_UNIQUE_ID} = ?", arrayOf(uniqueId))
    }

    fun findIdByUniqueId(annotationUniqueId: String): Long {
        return findValueBySelection(valueType = Long::class.java,
                column = AnnotationConst.C_ID,
                selection = "${AnnotationConst.C_UNIQUE_ID} = ?",
                selectionArgs = arrayOf(annotationUniqueId),
                defaultValue = 0L)
    }

    fun findAllChangesSince(sinceTs: LocalDateTime): List<Annotation> {
        return findAllBySelection(MODIFIED_SINCE, SQLQueryBuilder.toSelectionArgs(ThreeTenUtil.toMillis(sinceTs)))
    }

    fun findUnsyncdCount(lastSyncTs: LocalDateTime): Long {
        return findCountBySelection(MODIFIED_SINCE, SQLQueryBuilder.toSelectionArgs(ThreeTenUtil.toMillis(lastSyncTs)))
    }

    private fun findAllDeletedTrashedIds(): List<Long> {
        val selection = "${AnnotationConst.C_STATUS} = ? OR ${AnnotationConst.C_STATUS} = ?"
        return findAllValuesBySelection(Long::class.java, AnnotationConst.C_ID, selection,
                SQLQueryBuilder.toSelectionArgs(AnnotationStatusType.TRASHED.ordinal, AnnotationStatusType.DELETED.ordinal))
    }

    fun findAllFullActiveByDocId(docId: String): List<Annotation> {
        val annotations = findAllBySelection(ACTIVE_ANNOTATIONS_SELECTION, SQLQueryBuilder.toSelectionArgs(docId))
        for (annotation in annotations) {
            findFullAnnotationData(annotation)
        }

        return annotations
    }

    fun findRelatedAnnotationsFullActiveByDocId(docId: String): List<Annotation> {
        val annotations = findAllByRawQuery(RELATED_CONTENT_ANNOTATIONS, SQLQueryBuilder.toSelectionArgs(docId))
        val relatedAnnotations = ArrayList<Annotation>()
        for (annotation in annotations) {
            findFullAnnotationData(annotation)
            if (annotation.note == null && annotation.tags.isEmpty() && annotation.links.isEmpty()) {
                continue // Only annotations with a note, tag, or link should be returned
            }
            relatedAnnotations.add(annotation)
        }
        return relatedAnnotations
    }

    fun findDuplicateOldAnnotations(): List<Long> {
        return findAllValuesByRawQuery(Long::class.java, DUPLICATE_ANNOTATIONS, null)
    }

    fun findUniqueIdById(annotationId: Long): String {
        return findValueByRowId(String::class.java,
                column = AnnotationConst.C_UNIQUE_ID,
                rowId = annotationId,
                defaultValue = "")
    }

    fun findAllOldUniqueIds(docId: String, docVersion: Int): List<String> {
        return findAllValuesBySelection(String::class.java,
                AnnotationConst.C_UNIQUE_ID,
                """${AnnotationConst.C_DOC_ID} = ?
                    | AND (${AnnotationConst.C_CONTENT_VERSION} != ?
                    | OR ${AnnotationConst.C_CONTENT_VERSION} IS NULL) """.trimMargin(),
                SQLQueryBuilder.toSelectionArgs(docId, docVersion)
        )
    }

    fun findDocIdById(id: Long): String? {
        return findValueByRowId(String::class.java,
                column = AnnotationConst.C_DOC_ID,
                rowId = id,
                defaultValue = null)
    }

    fun findIfDocIdExists(annotationId: Long): Boolean {
        return findCountBySelection("""${AnnotationConst.C_DOC_ID} NOT NULL
            | AND ${AnnotationConst.C_DOC_ID} != ''
            | AND ${AnnotationConst.C_ID} == ?""".trimMargin(), SQLQueryBuilder.toSelectionArgs(annotationId)) > 0
    }

    fun moveToNotebook(annotation: Annotation, notebookId: Long) {
        moveToNotebook(annotation.id, annotation.uniqueId, notebookId)
    }

    fun moveToNotebook(annotationId: Long, uniqueAnnotationId: String, notebookId: Long) {
        // remove the annotation from the source notebook (make sure the annotation is not associated with any other notebook)
        notebookAnnotationManager.deleteAllByAnnotationId(annotationId)

        // Add the annotation to the notebook
        addToNotebook(annotationId, uniqueAnnotationId, notebookId)
    }

    fun addToNotebook(annotationId: Long, notebookId: Long) {
        val annotationUniqueId = findUniqueIdById(annotationId)

        addToNotebook(annotationId, annotationUniqueId, notebookId)
    }

    fun removeFromNotebook(annotationId: Long, notebookId: Long) {
        notebookAnnotationManager.deleteAllByNotebookIdAndAnnotationId(notebookId, annotationId)
        updateLastModified(annotationId, true)
    }

    private fun addToNotebook(annotationId: Long, uniqueAnnotationId: String, notebookId: Long) {
        var localTransactionStarted = false
        if (!inTransaction()) {
            beginTransaction()
            localTransactionStarted = true
        }

        // shift down all items in the notebook (put the new item at the top)
        notebookAnnotationManager.shiftDisplayOrder(notebookId)

        // Add the annotation to the new notebook
        val notebookAnnotation = NotebookAnnotation()
        notebookAnnotation.annotationId = annotationId
        notebookAnnotation.uniqueAnnotationId = uniqueAnnotationId
        notebookAnnotation.notebookId = notebookId
        notebookAnnotation.displayOrder = 0
        notebookAnnotationManager.save(notebookAnnotation)

        // make sure notebook and annotation will sync
        notebookManager.updateLastModified(notebookId)
        updateLastModified(annotationId, true)

        if (localTransactionStarted) {
            endTransaction(true)
        }
    }

    /**
     * NOTES for error logs... note text MUST be scrambled (to protect user content in logs)
     */
    fun findAnnotationLogTextByUniqueId(uniqueId: String): String {
        val annotation = findByUniqueId(uniqueId) ?: return "null"
        findFullAnnotationData(annotation)

        try {
            val note = annotation.note
            if (note != null && StringUtils.isNotBlank(note.content)) {
                val content = note.content
                note.content = content?.replace("""[^-_\*\s\d\.\n&nbsp;]""".toRegex(), "x")
            }

            val jsonText = gson.toJson(annotation.toDtoAnnotation(linkManager, notebookManager, notebookAnnotationManager))

            // make the json pretty
            val jsonObj = JSONObject(jsonText)
            return jsonObj.toString(2)
        } catch (e: Exception) {
            Timber.e(e, "Failed to serialize annotation for log")
            return ""
        }

    }

    fun findAnnotationExists(annotationId: Long): Boolean {
        return findCountBySelection("""${AnnotationConst.C_ID} = $annotationId""", null) > 0
    }

    @Deprecated("remove this when we feel that all legacy journal entries are converted")
    fun findLegacyJournalAnnotationIds(): List<Long> {
        return findAllValuesBySelection(Long::class.java, AnnotationConst.C_ID,
                """${AnnotationConst.FULL_C_ID} IN (SELECT ${NoteConst.FULL_C_ANNOTATION_ID} FROM ${NoteConst.TABLE})
                    | AND ${AnnotationConst.FULL_C_ID} NOT IN (SELECT ${BookmarkConst.FULL_C_ANNOTATION_ID} FROM ${BookmarkConst.TABLE})
                    | AND ${AnnotationConst.FULL_C_ID} NOT IN (SELECT ${HighlightConst.FULL_C_ANNOTATION_ID} FROM ${HighlightConst.TABLE})
                    | AND ${AnnotationConst.FULL_C_ID} NOT IN (SELECT ${NotebookAnnotationConst.FULL_C_ANNOTATION_ID} FROM ${NotebookAnnotationConst.TABLE})""".trimMargin(),
                null)
    }

    fun isUpdatedSince(modifiedTs: Long): Boolean {
        return modifiedTs != getLastTableModifiedTs()
    }

    companion object {
        private const val MODIFIED_SINCE = """${AnnotationConst.C_LAST_MODIFIED} > ?"""
        private const val EXCLUDE_BOOKMARKS: String = "${AnnotationConst.FULL_C_ID} NOT IN (SELECT ${BookmarkConst.FULL_C_ANNOTATION_ID} FROM ${BookmarkConst.TABLE})"
        private val ACTIVE_SELECTION = """${AnnotationConst.C_STATUS} = ${AnnotationStatusType.ACTIVE.ordinal}"""

        const val ANNOTATION_DEVICE = "android"
        private const val ANNOTATION_SOURCE = "Android Gospel Library | App: ${BuildConfig.VERSION_NAME}"// | OS: ${Build.VERSION.SDK_INT}"

        @JvmStatic
        fun getAnnotationSource(): String {
            return "$ANNOTATION_SOURCE | OS: ${Build.VERSION.SDK_INT}"
        }

        val QUERY_RECENT = """SELECT *
            | FROM ${AnnotationConst.TABLE}
            | WHERE $ACTIVE_SELECTION AND $EXCLUDE_BOOKMARKS
            | ORDER BY ${AnnotationConst.C_LAST_MODIFIED} DESC, ${AnnotationConst.C_DOC_ID}
            | LIMIT ? OFFSET ?""".trimMargin()

        val QUERY_BY_TAG_NAME = """SELECT ${AnnotationConst.TABLE}.*
            | FROM ${AnnotationConst.TABLE}
            |   JOIN ${TagConst.TABLE} ON ${TagConst.FULL_C_ANNOTATION_ID} = ${AnnotationConst.FULL_C_ID}
            | WHERE $ACTIVE_SELECTION
            |   AND $EXCLUDE_BOOKMARKS
            |   AND ${TagConst.FULL_C_NAME} = ?
            | ORDER BY ${AnnotationConst.FULL_C_LAST_MODIFIED} DESC""".trimMargin()

        val QUERY_BY_NOTEBOOK = """SELECT ${AnnotationConst.TABLE}.*
            | FROM ${AnnotationConst.TABLE}
            |   JOIN ${NotebookAnnotationConst.TABLE} ON ${NotebookAnnotationConst.FULL_C_ANNOTATION_ID}=${AnnotationConst.FULL_C_ID}
            | WHERE $ACTIVE_SELECTION
            |   AND $EXCLUDE_BOOKMARKS
            |   AND ${NotebookAnnotationConst.FULL_C_NOTEBOOK_ID} = ?
            | ORDER BY ${NotebookAnnotationConst.FULL_C_DISPLAY_ORDER} ASC""".trimMargin()

        val ACTIVE_ANNOTATIONS_SELECTION = "${AnnotationConst.C_DOC_ID} = ? AND ${AnnotationConst.C_STATUS} = ${AnnotationStatusType.ACTIVE.ordinal}"

        val RELATED_CONTENT_ANNOTATIONS = """SELECT ${AnnotationConst.TABLE}.*
            | FROM ${AnnotationConst.TABLE}
            |   JOIN ${HighlightConst.TABLE} ON ${HighlightConst.FULL_C_ANNOTATION_ID} = ${AnnotationConst.FULL_C_ID}
            | WHERE ${AnnotationConst.C_DOC_ID} = ?
            |   AND ${AnnotationConst.C_STATUS} = ${AnnotationStatusType.ACTIVE.ordinal}
            | GROUP BY ${HighlightConst.C_ANNOTATION_ID}
            | ORDER BY ${HighlightConst.FULL_C_OFFSET_START}""".trimMargin()

        val DUPLICATE_ANNOTATIONS = """SELECT ${AnnotationConst.C_ID}
            | FROM ${AnnotationConst.TABLE}
            |   JOIN (
            |     SELECT
            |       min(${AnnotationConst.C_LAST_MODIFIED}) as oldest_last_modified,
            |       ${AnnotationConst.C_UNIQUE_ID}
            |     FROM ${AnnotationConst.TABLE}
            |     GROUP BY ${AnnotationConst.C_UNIQUE_ID}
            |       HAVING count(1) > 1
            |   ) AS duplicate
            | WHERE ${AnnotationConst.FULL_C_UNIQUE_ID} = duplicate.unique_id
            |   AND ${AnnotationConst.FULL_C_LAST_MODIFIED} = duplicate.oldest_last_modified""".trimMargin()
    }
}