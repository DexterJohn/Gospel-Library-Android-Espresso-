package org.lds.ldssa.model.webservice.annotation.dto.annotation

import com.google.gson.annotations.SerializedName

import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.link.Link

class DtoRef {
    @SerializedName("@docId")
    var docId: String? = null
    @SerializedName("@pid")
    var aid: String? = null
    @SerializedName("$")
    var name: String? = null
    @SerializedName("@contentVersion")
    var contentVersion: Int? = null

    constructor() {}

    constructor(link: Link) {
        docId = link.docId
        aid = link.paragraphAid
        name = link.name
        contentVersion = link.contentVersion
    }

    fun createLinkFromDto(annotation: Annotation): Link {
        val link = Link()
        link.name = name ?: ""
        link.docId = docId
        link.paragraphAid = aid
        link.contentVersion = contentVersion ?: 0

        link.annotationId = annotation.id

        return link
    }
}
