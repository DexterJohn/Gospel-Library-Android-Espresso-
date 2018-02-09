package org.lds.ldssa.model.webservice.annotation.dto.annotation

import org.lds.ldssa.model.database.types.AnnotationChangeType
import org.lds.ldssa.model.database.types.AnnotationStatusType
import org.threeten.bp.LocalDateTime

class DtoAnnotationChange {
    var annotationId: String? = null
    var changeType: AnnotationChangeType? = null
    var timestamp: LocalDateTime? = null
    var annotation: DtoAnnotation? = null

    constructor()

    constructor(status: AnnotationStatusType, dtoAnnotation: DtoAnnotation) {
        annotationId = dtoAnnotation.id
        timestamp = dtoAnnotation.timestamp

        // change type
        when (status) {
            AnnotationStatusType.ACTIVE -> {
                changeType = AnnotationChangeType.NEW
                annotation = dtoAnnotation
            }
            AnnotationStatusType.TRASHED, AnnotationStatusType.DELETED -> {
                changeType = AnnotationChangeType.TRASH
                annotation = null
            }
        }
    }
}
