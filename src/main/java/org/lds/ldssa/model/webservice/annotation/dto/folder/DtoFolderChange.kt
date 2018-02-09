package org.lds.ldssa.model.webservice.annotation.dto.folder

import org.lds.ldssa.model.database.types.AnnotationChangeType
import org.lds.ldssa.model.database.types.AnnotationStatusType
import org.lds.ldssa.model.database.userdata.notebook.Notebook
import org.threeten.bp.LocalDateTime

class DtoFolderChange {
    var folderId: String? = null
    var changeType: AnnotationChangeType? = null
    var timestamp: LocalDateTime? = null
    var folder: DtoFolder? = null

    constructor()

    constructor(notebook: Notebook, annotationOrder: List<String>) {
        folderId = notebook.uniqueId
        timestamp = notebook.lastModified

        // change type
        when (notebook.status) {
            AnnotationStatusType.ACTIVE -> {
                changeType = AnnotationChangeType.NEW
                folder = DtoFolder(notebook, annotationOrder)
            }
            AnnotationStatusType.TRASHED, AnnotationStatusType.DELETED -> {
                changeType = AnnotationChangeType.TRASH
                folder = null
            }
        }
    }
}
