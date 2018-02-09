package org.lds.ldssa.model.webservice.annotation.dto.annotationrequest

class DtoAnnotationRequest {
    var aid = ""
    var ver = 0

    constructor()

    constructor(aid: String) {
        this.aid = aid
    }

    constructor(aid: String, ver: Int) {
        this.aid = aid
        this.ver = ver
    }
}
