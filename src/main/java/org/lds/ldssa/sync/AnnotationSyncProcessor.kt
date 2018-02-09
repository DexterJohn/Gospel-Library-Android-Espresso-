package org.lds.ldssa.sync

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import org.apache.commons.lang3.StringUtils
import org.lds.ldssa.R
import org.lds.ldssa.model.database.types.AnnotationChangeType
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager
import org.lds.ldssa.model.database.userdata.highlight.HighlightManager
import org.lds.ldssa.model.database.userdata.link.LinkManager
import org.lds.ldssa.model.database.userdata.note.NoteManager
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotation
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager
import org.lds.ldssa.model.database.userdata.tag.TagManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.webservice.ServiceModule
import org.lds.ldssa.model.webservice.annotation.dto.DtoError
import org.lds.ldssa.model.webservice.annotation.dto.annotation.DtoAnnotation
import org.lds.ldssa.model.webservice.annotation.dto.annotation.DtoAnnotationChange
import org.lds.ldssa.model.webservice.annotation.dto.annotation.DtoAnnotationFolders
import org.lds.ldssa.ui.notification.AnnotationSyncNotification
import org.lds.ldssa.util.CitationUtil
import org.lds.ldssa.util.ThreeTenUtil
import org.lds.mobile.util.LdsTimeUtil
import org.threeten.bp.LocalDateTime
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.ArrayList
import javax.inject.Inject

class AnnotationSyncProcessor @Inject
constructor(private val annotationManager: AnnotationManager,
            private val bookmarkManager: BookmarkManager,
            private val highlightManager: HighlightManager,
            private val tagManager: TagManager,
            private val notebookManager: NotebookManager,
            private val notebookAnnotationManager: NotebookAnnotationManager,
            private val linkManager: LinkManager,
            private val noteManager: NoteManager,
            private val timeUtil: LdsTimeUtil,
            private val citationUtil: CitationUtil,
            private val annotationSyncNotification: AnnotationSyncNotification,
            private val gson: Gson) {

    // number of annotations reported by service (for progress)
    private var annotationChangeTotalCount = 0

    var annotationsSent = 0
        private set
    var annotationsReceived = 0
        private set
    var annotationsAdded = 0
        private set
    var annotationsUpdated = 0
        private set
    var annotationsRemoved = 0
        private set
    // value to be used in preferences, and to be used to determined changed annotations
    var newLastSyncTs: LocalDateTime = LocalDateTime.now()
        private set

    var errors = ArrayList<DtoError>()
        private set

    fun reset() {
        annotationsSent = 0
        annotationsReceived = 0
        annotationsAdded = 0
        annotationsUpdated = 0
        annotationsRemoved = 0
        errors = ArrayList()
    }

    @Throws(IOException::class)
    fun processAnnotationField(reader: JsonReader) {
        val currentField = reader.nextName()
        when (currentField) {
            "count" -> annotationChangeTotalCount = reader.nextInt()
            "changes" -> annotationChangesToDb(reader)
            "since" // "before" should be used... so "since" is ignored
                , "timeDiff" // not used
            -> reader.skipValue()
            "errors" -> processErrors(reader)
            else -> {
                Timber.w("Skipped value for [%s]", currentField)
                reader.skipValue()
            }
        }
    }

    @Throws(IOException::class)
    private fun processErrors(reader: JsonReader) {
        // start array
        reader.beginArray()

        // parse error array
        val token = reader.peek()
        while (token == JsonToken.BEGIN_OBJECT) {
            val error = gson.fromJson<DtoError>(reader, DtoError::class.java)
            errors.add(error)

            // mark annotation (that has an error) last modified timestamp updated so that it will try to sync again on next sync
            val errorId = error.id
            if (errorId != null) {
                annotationManager.syncUpdateLastModified(errorId)
            }
        }

        reader.endArray()
    }

    @Throws(IOException::class)
    private fun annotationChangesToDb(reader: JsonReader) {
        reset()

        reader.beginArray()

        while (reader.peek() == JsonToken.BEGIN_OBJECT) {
            val dtoAnnotationChange = gson.fromJson<DtoAnnotationChange>(reader, DtoAnnotationChange::class.java)
            annotationChangesToDb(dtoAnnotationChange)
        }

        reader.endArray()

        cleanupUpdatedAnnotations()
    }

    private fun annotationChangesToDb(dtoAnnotationChange: DtoAnnotationChange) {
        if (annotationsReceived != 0 && annotationsReceived % ANNOTATION_LOG_INTERVAL == 0) {
            Timber.d("Saving annotations progress [%s]", annotationsReceived)
            annotationSyncNotification.updateNotification(R.string.sync_notification_processing_changes, annotationChangeTotalCount, annotationsReceived)
        }

        annotationsReceived++
        val uniqueId = dtoAnnotationChange.annotationId
        val changeType = dtoAnnotationChange.changeType

        if (changeType != null) {
            when (changeType) {
                AnnotationChangeType.NEW -> {
                    saveNewAnnotation(dtoAnnotationChange.annotation)
                    annotationsAdded++
                }
                AnnotationChangeType.TRASH, AnnotationChangeType.DELETE -> if (uniqueId != null && annotationManager.purgeAnnotation(uniqueId)) {
                    annotationsRemoved++ // log
                }
                else -> throw IllegalStateException("Unknown change type for annotation id: " + uniqueId!!)
            }
        }
    }

    /**
     * Remove duplicate annotations that got added (which are just updates)
     */
    private fun cleanupUpdatedAnnotations() {
        Timber.d("Cleaning up updated annotations...")
        Timber.d("Annotation count BEFORE: %d", annotationManager.findCount())
        val startMs = System.currentTimeMillis()

        val oldAnnotationIds = annotationManager.findDuplicateOldAnnotations()
        for (annotationId in oldAnnotationIds) {
            annotationManager.purgeAnnotation(annotationId)

            // update stats (change from added to updated)
            annotationsUpdated++
            annotationsAdded--
        }

        timeUtil.logTimeElapsedFromNow("Cleanup Annotations", "Cleaning up updated annotations FINISHED", startMs)
        Timber.d("Annotation count AFTER: %d", annotationManager.findCount())
    }

    private fun saveNewAnnotation(dtoAnnotation: DtoAnnotation?) {
        val annotation = dtoAnnotation!!.createAnnotationFromDto()
        annotationManager.syncSave(annotation)

        // BOOKMARKS
        val dtoBookmark = dtoAnnotation.bookmark
        if (dtoBookmark != null) {
            val bookmark = dtoBookmark.createBookmarkFromDto(annotation)
            bookmark.citation = citationUtil.createCitationText(annotation.docId, bookmark.paragraphAid)
            bookmarkManager.save(bookmark)
        }

        // HIGHLIGHTS
        val dtoHighlights = dtoAnnotation.highlights
        if (dtoHighlights != null) {
            for (dtoHighlight in dtoHighlights.items) {
                highlightManager.save(dtoHighlight.createHighlightFromDto(annotation))
            }
        }

        // LINKS
        val dtoRefs = dtoAnnotation.refs
        if (dtoRefs != null && !dtoRefs.items.isEmpty()) {
            for (dtoRef in dtoRefs.items) {
                linkManager.save(dtoRef.createLinkFromDto(annotation))
            }
        }

        // TAGS
        saveTags(dtoAnnotation, annotation)

        // NOTES
        val dtoNote = dtoAnnotation.note
        if (dtoNote != null) {
            noteManager.save(dtoNote.createNoteFromDto(annotation))
        }

        // FOLDERS
        // folders should be handled later with notebookAnnotationManager.updateNotebookAnnotationAssociations(); in AnnotationSync.java
        // BUT.. folders may NOT contain the annotation (rule that can't be enforced on server)... so we need to add any missing associations here
        val dtoAnnotationFolders = dtoAnnotation.folders
        if (dtoAnnotationFolders != null) {
            processAnnotationFolders(annotation.id, annotation.uniqueId, dtoAnnotationFolders)
        }
    }

    private fun processAnnotationFolders(annotationId: Long, annotationUniqueId: String, dtoAnnotationFolders: DtoAnnotationFolders) {
        val dtoAnnotationFoldersItems = dtoAnnotationFolders.getItems() ?: return

        for (dtoAnnotationFoldersItem in dtoAnnotationFoldersItems) {
            val folderUniqueId = StringUtils.substringAfterLast(dtoAnnotationFoldersItem.uri, "/")
            val notebookId = notebookManager.findIdByUniqueId(folderUniqueId)

            if (notebookId <= 0) {
                Timber.e("WARNING! Could NOT associate annotation [%s] with Notebook because Notebook/Folder NOT found for folder unique ID: [%s]", annotationUniqueId, folderUniqueId)
                continue // skip this, and move to next folder
            }

            // check to see if there is already an association... if not, create one
            if (notebookAnnotationManager.findCountByNotebookIdAndAnnotationUniqueId(notebookId, annotationUniqueId) == 0L) {
                val newNotebookAnnotation = NotebookAnnotation()
                newNotebookAnnotation.annotationId = annotationId
                newNotebookAnnotation.uniqueAnnotationId = annotationUniqueId
                newNotebookAnnotation.notebookId = notebookId
                newNotebookAnnotation.displayOrder = 0 // because there was association... let's just set the display order to 0

                notebookAnnotationManager.save(newNotebookAnnotation)
            }
        }
    }

    private fun saveTags(dtoAnnotation: DtoAnnotation, annotation: Annotation) {
        val dtoTags = dtoAnnotation.tags
        if (dtoTags == null || dtoTags.items.isEmpty()) {
            return
        }

        dtoTags.items
                .map { dtoTags.createTagFromDto(annotation, it) }
                .forEach { tagManager.save(it) }
    }

    @Throws(IOException::class)
    fun annotationChangesToJson(outputFile: File, serverSince: String, deviceChangesSince: LocalDateTime) {
        val startMs = System.currentTimeMillis()

        Timber.d("Gathering annotation updates since (serverTs: [%s])...", serverSince)

        val writer = JsonWriter(FileWriter(outputFile))
        ServiceModule.setupJsonWriter(writer)

        // Header
        writer.beginObject()

        writer.name("syncAnnotations")
        writer.beginObject()
        //        writer.name("@xmlns).value(http://lds.org/schema/folder"); // NOSONAR tebbs "not needed"
        //        writer.name("schemaDate).value(2011-04-13T13:00:00.000-06:00"); // NOSONAR tebbs "not needed"
        writer.name("since").value(serverSince)
        writer.name("clientTime").value(ThreeTenUtil.formatIso(LocalDateTime.now()))

        // if this is a first time sync, then don't bother to get the deleted annotations
        if (serverSince == Prefs.DAWN_OF_TIME_TEXT) {
            writer.name("syncStatus").value("normal")
        }

        // Content
        writeAnnotations(writer, deviceChangesSince)

        // Footer
        writer.endObject()
        writer.endObject()
        writer.close()
        timeUtil.logTimeElapsedFromNow("AnnotationChangesToJson", "Finished gathering annotation updates", startMs)
    }

    @Throws(IOException::class)
    private fun writeAnnotations(writer: JsonWriter, deviceChangesSince: LocalDateTime) {
        // Save the current timestamp to make sure that we have overlap from findAllChangesSince (just in case the user is making changes during the sync)
        newLastSyncTs = LocalDateTime.now()

        val modifiedAnnotations = annotationManager.findAllChangesSince(deviceChangesSince)

        Timber.i("Annotation change count: [%d] since: [%s]", modifiedAnnotations.size, deviceChangesSince)
        if (modifiedAnnotations.isEmpty()) {
            return
        }

        annotationsSent = 0
        writer.name("changes")
        writer.beginArray()

        for (annotation in modifiedAnnotations) {
            // inflate the annotation
            annotationManager.findFullAnnotationData(annotation)
            val dtoAnnotation = annotation.toDtoAnnotation(linkManager, notebookManager, notebookAnnotationManager)
            val dtoAnnotationChange = DtoAnnotationChange(annotation.status, dtoAnnotation)

            // write to file
            gson.toJson(dtoAnnotationChange, DtoAnnotationChange::class.java, writer)

            annotationsSent++
        }

        writer.endArray()
    }

    fun hasChanges() = annotationsAdded > 0 || annotationsUpdated > 0 || annotationsRemoved > 0

    fun hasErrors() = !errors.isEmpty()

    companion object {
        private val ANNOTATION_LOG_INTERVAL = 100
    }

}
