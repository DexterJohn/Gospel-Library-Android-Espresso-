package org.lds.ldssa.util.annotations

import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.note.Note
import org.lds.ldssa.model.database.userdata.note.NoteManager
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotation
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteUtil @Inject
constructor(private val noteManager: NoteManager, 
            private val annotationManager: AnnotationManager, 
            private val notebookManager: NotebookManager, 
            private val notebookAnnotationManager: NotebookAnnotationManager) {

    fun add(annotationId: Long, title: String, content: String): Boolean {
        val note = Note()
        note.annotationId = annotationId
        note.title = title
        note.content = content

        return save(note)
    }

    fun update(note: Note, title: String, content: String): Boolean {
        note.title = title
        note.content = content
        return save(note)
    }

    private fun save(note: Note): Boolean {
        var saved = false

        // save
        annotationManager.beginTransaction()

        if (noteManager.save(note)) {
            annotationManager.updateLastModified(note.annotationId, true)
            saved = true
        }

        annotationManager.endTransaction(true)

        return saved
    }

    /**
     * Annotations only have 1 note... so only the annotationId is needed
     * @param annotationId id that contains the note to be deleted
     */
    fun deleteByAnnotationId(annotationId: Long) {
        annotationManager.beginTransaction()

        val noteDeleted = noteManager.deleteAllByAnnotationId(annotationId) > 0

        if (noteDeleted) {
            // If the annotation does not contain any savable content then we should delete the annotation
            val trashed = annotationManager.trashAnnotationIfNoSavableContent(annotationId)
            if (!trashed) {
                annotationManager.updateLastModified(annotationId, true)
            }
        }

        annotationManager.endTransaction(true)
    }

    fun createJournalAnnotation(notebookId: Long): Annotation {
        val annotation = Annotation()
        annotationManager.save(annotation)

        //Associate the annotation with the specified notebook
        val notebookAnnotation = NotebookAnnotation()
        notebookAnnotation.annotationId = annotation.id
        notebookAnnotation.uniqueAnnotationId = annotation.uniqueId
        notebookAnnotation.notebookId = notebookId
        notebookAnnotationManager.save(notebookAnnotation)
        notebookManager.updateLastModified(notebookId)

        notebookManager.updateAnnotationOrdering(notebookId, annotationManager.findAllByNotebook(notebookId))

        return annotation
    }

    @Deprecated("") // This method shouldn't be needed after we get market penetration with the new notes system
    fun convertLegacyNote(note: Note?): Note? {
        if (note == null) {
            return null
        }

        // If the note is empty, or if it contains a markdown newline sequence, then return
        val noteContent = note.content
        if (noteContent.isNullOrEmpty() || noteContent?.contains(MARKDOWN_NEWLINE) == true) {
            return note
        }

        if (noteContent != null)  {
            note.content = convertLegacyNoteContent(noteContent)
        }

        return note
    }

    @Deprecated("") // This method shouldn't be needed after we get market penetration with the new notes system
    fun convertLegacyNoteContent(content: String?): String? {
        content ?: return null

        // If the note is empty, or if it contains a markdown newline sequence, then return
        if (content.isEmpty()|| content.contains(MARKDOWN_NEWLINE)) {
            return content
        }

        // Update the legacy new lines to match what the markdown parser is expecting
        var formattedContent = content.replace("\n\n", PLACEHOLDER)
        formattedContent = formattedContent.replace("\n", MARKDOWN_NEWLINE)
        formattedContent = formattedContent.replace(PLACEHOLDER, "\n\n" + MARKDOWN_NEWLINE)

        return formattedContent
    }

    companion object {
        private const val NBSP = "&nbsp;"
        private const val MARKDOWN_NEWLINE = NBSP + "  \n"
        private const val PLACEHOLDER = "\uFFFF" // Unicode non-character
    }
}
