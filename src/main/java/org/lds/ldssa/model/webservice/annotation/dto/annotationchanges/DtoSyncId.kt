package org.lds.ldssa.model.webservice.annotation.dto.annotationchanges

class DtoSyncId {
    var aid = ""
    var docId = ""

    fun hasDocId(): Boolean {
        return docId.isNotEmpty()
    }
}
