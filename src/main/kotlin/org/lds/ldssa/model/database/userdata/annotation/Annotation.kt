/*
 * Annotation.kt
 *
 * Created: 02/09/2017 11:39:55
 */



package org.lds.ldssa.model.database.userdata.annotation

import org.lds.ldssa.model.database.types.AnnotationDisplayType
import org.lds.ldssa.model.database.types.AnnotationStatusType
import org.lds.ldssa.model.database.types.AnnotationType
import org.lds.ldssa.model.database.types.HighlightAnnotationStyle
import org.lds.ldssa.model.database.userdata.bookmark.Bookmark
import org.lds.ldssa.model.database.userdata.highlight.Highlight
import org.lds.ldssa.model.database.userdata.link.Link
import org.lds.ldssa.model.database.userdata.link.LinkManager
import org.lds.ldssa.model.database.userdata.note.Note
import org.lds.ldssa.model.database.userdata.notebook.Notebook
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager
import org.lds.ldssa.model.database.userdata.tag.Tag
import org.lds.ldssa.model.webservice.annotation.dto.annotation.DtoAnnotation
import org.lds.ldssa.model.webservice.annotation.dto.annotation.DtoAnnotationFolders
import org.lds.ldssa.model.webservice.annotation.dto.annotation.DtoBookmark
import org.lds.ldssa.model.webservice.annotation.dto.annotation.DtoHighlights
import org.lds.ldssa.model.webservice.annotation.dto.annotation.DtoNote
import org.lds.ldssa.model.webservice.annotation.dto.annotation.DtoRefs
import org.lds.ldssa.model.webservice.annotation.dto.annotation.DtoTags
import org.lds.ldssa.util.HighlightColor
import java.util.ArrayList
import java.util.UUID


class Annotation : AnnotationBaseRecord() {
    var highlights: MutableList<Highlight> = ArrayList()
    var tags: MutableList<Tag> = ArrayList()
    var links: MutableList<Link> = ArrayList()
    var bookmark: Bookmark? = null
    var note: Note? = null

    // NOTE: notebooks should NOT be used for sync... this field is created for use with displaying annotation data
    var notebooks: List<Notebook> = ArrayList()

    fun initUniqueId() {
        if (uniqueId.isEmpty()) {
            uniqueId = UUID.randomUUID().toString() // id should be unique across users
        }
    }

    fun shouldDisplay(): Boolean {
        return when (status) {
            AnnotationStatusType.TRASHED, AnnotationStatusType.DELETED -> false
            else -> true
        }
    }

    fun hasContent(): Boolean {
        return determineDisplayType() != AnnotationDisplayType.OTHER
    }

    fun removeAllHighlights() {
        this.highlights.clear()
    }

    fun applyAnnotationId() {
        val annotationId = id

        if (bookmark != null) {
            bookmark!!.annotationId = annotationId
        }
        if (note != null) {
            note!!.annotationId = annotationId
        }

        for (highlight in highlights) {
            highlight.annotationId = annotationId
        }

        for (tag in tags) {
            tag.annotationId = annotationId
        }

        for (link in links) {
            link.annotationId = annotationId
        }
    }

    fun getFirstHighlight(): Highlight? {
        return if (!highlights.isEmpty()) highlights[0] else null
    }

    fun getFirstHighlightParagraphAid(): String? {
        val firstHighlight = getFirstHighlight()
        if (firstHighlight != null) {
            return firstHighlight.paragraphAid
        } else {
            return null
        }
    }

    fun setAllHighlightColors(color: HighlightColor, style: HighlightAnnotationStyle) {
        for (highlight in highlights) {
            highlight.color = color.htmlName
            highlight.style = style.stringValue
        }
    }

    fun isBookmark(): Boolean {
        return bookmark != null
    }

    /**
     * Because refs can be easily added and removed from an annotation, the type must be updated (for the sync service) to reflect the proper type.  Failure
     * to do so will cause the annotation to not sync properly
     */
    fun determineType(): AnnotationType {
        return when {
            !links.isEmpty() -> AnnotationType.REFERENCE
            bookmark != null -> AnnotationType.BOOKMARK
            note != null && highlights.isEmpty() && bookmark == null -> AnnotationType.JOURNAL
            else -> AnnotationType.HIGHLIGHT
        }
    }

    /**
     * For use when displaying titles and icons in content, sidebar, etc
     */
    fun determineDisplayType(): AnnotationDisplayType {
        val hasLinks = !links.isEmpty()
        val hasTags = !tags.isEmpty()
        val hasNote = note != null
        val hasNotebooks = !notebooks.isEmpty()
        val hasMultiple = trueCount(hasLinks, hasTags, hasNote, hasNotebooks) > 1

        return when {
            hasMultiple -> AnnotationDisplayType.NOTE
            hasLinks -> AnnotationDisplayType.LINK
            hasTags -> AnnotationDisplayType.TAG
            hasNotebooks -> AnnotationDisplayType.NOTEBOOK
            hasNote -> AnnotationDisplayType.NOTE
            else -> AnnotationDisplayType.OTHER
        }
    }

    private fun trueCount(vararg vars: Boolean): Int {
        return vars.sumBy { if (it) 1 else 0 }
    }

    fun compare(otherAnnotation: Annotation?): Boolean {
        return otherAnnotation != null && uniqueId == otherAnnotation.uniqueId
    }

    fun toDtoAnnotation(linkManager: LinkManager,
                        notebookManager: NotebookManager,
                        notebookAnnotationManager: NotebookAnnotationManager): DtoAnnotation {
        // HIGHLIGHTS
        var dtoHighlights: DtoHighlights? = null
        if (!highlights.isEmpty()) {
            dtoHighlights = DtoHighlights(highlights)
        }

        // TAGS
        var dtoTags: DtoTags? = null
        if (!tags.isEmpty()) {
            dtoTags = DtoTags(tags)
        }

        // REFS
        var dtoRefs: DtoRefs? = null
        if (!links.isEmpty()) {
            dtoRefs = DtoRefs(linkManager.findAllByAnnotationId(id))
        }

        // BOOKMARKS
        var dtoBookmark: DtoBookmark? = null
        if (bookmark != null) {
            dtoBookmark = DtoBookmark(bookmark!!)
        }

        // NOTES
        var dtoNote: DtoNote? = null
        if (note != null) {
            dtoNote = DtoNote(note!!)
        }

        // ANNOTATION FOLDERS
        var dtoAnnotationFolders: DtoAnnotationFolders? = null
        val annotationFolders = notebookAnnotationManager.findAllByAnnotationId(id)
        if (!annotationFolders.isEmpty()) {
            dtoAnnotationFolders = DtoAnnotationFolders(annotationFolders, notebookManager)
        }

        return DtoAnnotation(this, dtoHighlights, dtoTags, dtoRefs, dtoBookmark, dtoNote, dtoAnnotationFolders)
    }

    companion object {
        const val INVERSE_UNIQUE_ID_PREFIX = "-"

        @JvmStatic
        fun getInverseAnnotationId(annotationId: Long): Long {
            return annotationId * -1
        }

        @JvmStatic
        fun isInverseLinkAnnotation(annotationId: Long): Boolean {
            return annotationId < 0
        }

        @JvmStatic
        fun isInverseLinkAnnotation(uniqueId: String): Boolean {
            return uniqueId.startsWith(INVERSE_UNIQUE_ID_PREFIX)
        }

        @JvmStatic
        fun getInverseUniqueId(uniqueId: String): String {
            return when {
                isInverseLinkAnnotation(uniqueId) -> uniqueId.substring(INVERSE_UNIQUE_ID_PREFIX.length, uniqueId.length)
                else -> INVERSE_UNIQUE_ID_PREFIX + uniqueId
            }
        }
    }
}