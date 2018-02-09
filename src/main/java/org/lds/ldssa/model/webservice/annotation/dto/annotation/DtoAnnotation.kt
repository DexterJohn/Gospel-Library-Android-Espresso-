package org.lds.ldssa.model.webservice.annotation.dto.annotation

import com.google.gson.annotations.SerializedName
import org.lds.ldssa.model.database.types.AnnotationStatusType
import org.lds.ldssa.model.database.types.AnnotationType
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.threeten.bp.LocalDateTime

@Suppress("MemberVisibilityCanPrivate")
class DtoAnnotation {
    var device: String? = null
    var source: String? = null
    var timestamp: LocalDateTime? = null
    @SerializedName("@id")
    var id: String? = null
    @SerializedName("@docId")
    var docId: String? = null
        set(docId) {
            field = if (docId.isNullOrBlank()) null else docId
        }
    @SerializedName("@contentVersion")
    var contentVersion: Int? = null
    @SerializedName("@type")
    var type: AnnotationType? = null
    @SerializedName("@status")
    var status: AnnotationStatusType? = null
    @SerializedName("highlights")
    var highlights: DtoHighlights? = null
    @SerializedName("tags")
    var tags: DtoTags? = null
    @SerializedName("folders")
    var folders: DtoAnnotationFolders? = null
    @SerializedName("refs")
    var refs: DtoRefs? = null
    var bookmark: DtoBookmark? = null
    var note: DtoNote? = null
    // DO NOT SET
    @SerializedName("@person-id")
    var personId = "0" // WAM id... filled in by service (default to 0... will ALWAYS be overwritten)
    // Following not used in Gospel Library, but must be preserved with sync
    var citation: String? = null
    var scope: String? = null

    constructor()

    constructor(annotation: Annotation,
                dtoHighlights: DtoHighlights?,
                dtoTags: DtoTags?,
                dtoRefs: DtoRefs?,
                dtoBookmark: DtoBookmark?,
                dtoNote: DtoNote?,
                dtoAnnotationFolders: DtoAnnotationFolders?) {
        status = if (annotation.status === AnnotationStatusType.ACTIVE) null else annotation.status
        personId = "0" // don't include the personId

        id = annotation.uniqueId
        device = annotation.device
        source = annotation.source?.take(SOURCE_MAX_LENGTH)
        type = annotation.determineType()
        timestamp = annotation.lastModified
        docId = annotation.docId
        contentVersion = annotation.contentVersion

        highlights = dtoHighlights
        tags = dtoTags
        refs = dtoRefs
        bookmark = dtoBookmark
        note = dtoNote
        folders = dtoAnnotationFolders

        // Following not used in Gospel Library, but must be preserved with sync
        citation = annotation.citation
        scope = annotation.scope
    }

    fun createAnnotationFromDto(): Annotation {
        val annotation = Annotation()
        annotation.uniqueId = id ?: ""
        annotation.device = device
        annotation.source = source
        // type is not set, but determined dynamically
        annotation.docId = docId
        annotation.contentVersion = contentVersion ?: 0
        annotation.lastModified = timestamp ?: LocalDateTime.now()

        // Following not used in Gospel Library, but must be preserved with sync
        annotation.citation = citation
        annotation.scope = scope

        return annotation
    }

    companion object {
        private const val SOURCE_MAX_LENGTH = 100
    }
}
