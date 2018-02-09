package org.lds.ldssa.model.webservice.annotation

class AnnotationResults {
    var annotationsSaved: Long = 0
    var annotationsReceived: Long = 0
    var annotationsAdded: Long = 0
    var annotationsUpdated: Long = 0
    var annotationsRemoved: Long = 0

    var notebooksSaved: Long = 0
    var notebooksReceived: Long = 0
    var notebooksAdded: Long = 0
    var notebooksUpdated: Long = 0
    var notebooksRemoved: Long = 0

    var bookmarkCount: Long = 0
    var highlightCount: Long = 0
    var linkCount: Long = 0
    var tagCount: Long = 0
    var noteCount: Long = 0

    var annotationNotebookCount: Long = 0

    fun setAnnotationCount(annotationsSaved: Long) {
        this.annotationsSaved = annotationsSaved
    }

    fun setNotebookAnnotationCount(annotationNotebookCount: Long) {
        this.annotationNotebookCount = annotationNotebookCount
    }
}
