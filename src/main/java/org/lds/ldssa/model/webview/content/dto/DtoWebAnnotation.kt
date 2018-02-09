package org.lds.ldssa.model.webview.content.dto


import com.google.gson.annotations.SerializedName
import org.lds.ldssa.model.database.types.AnnotationDisplayType
import org.lds.ldssa.model.database.types.AnnotationStatusType
import org.lds.ldssa.model.database.types.AnnotationType
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.link.Link
import org.lds.ldssa.model.database.userdata.note.Note
import org.lds.ldssa.model.database.userdata.tag.Tag
import java.util.ArrayList

@Suppress("MemberVisibilityCanPrivate")
class DtoWebAnnotation {
    @SerializedName("id")
    var uniqueId = ""
    var device = "android"
    var languageId: Long = 0
    var source = ""
    var status = AnnotationStatusType.ACTIVE
    var type = AnnotationType.UNKNOWN
    var displayType = AnnotationDisplayType.OTHER
    var docId: String? = null

    var hasContent: Boolean = false
    var colorName: String? = null
    var style: String? = null

    private var highlights: MutableList<DtoWebHighlight> = ArrayList()
    var tags: List<Tag> = ArrayList()
    var links: List<Link> = ArrayList()
    var bookmark: DtoWebBookmark? = null
    var note: Note? = null

    constructor()

    constructor(annotation: Annotation) {
        this.docId = annotation.docId
        this.uniqueId = annotation.uniqueId
        this.type = annotation.determineType()
        this.displayType = annotation.determineDisplayType()
        this.hasContent = annotation.hasContent()

        val firstHighlight = annotation.getFirstHighlight()
        if (firstHighlight != null) {
            style = firstHighlight.style
            colorName = firstHighlight.color
        }

        for (highlight in annotation.highlights) {
            highlights.add(DtoWebHighlight(highlight))
        }

        if (annotation.bookmark != null) {
            bookmark = DtoWebBookmark(annotation.bookmark)
        }
    }

    fun getHighlights(): List<DtoWebHighlight> {
        return highlights
    }

    fun setHighlights(highlights: MutableList<DtoWebHighlight>) {
        this.highlights = highlights
    }
}
