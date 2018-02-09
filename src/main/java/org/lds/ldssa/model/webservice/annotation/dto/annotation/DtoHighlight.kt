package org.lds.ldssa.model.webservice.annotation.dto.annotation

import com.google.gson.annotations.SerializedName

import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.highlight.Highlight


class DtoHighlight {
    @SerializedName("@pid")
    var aid: String? = null
    @SerializedName("@offset-start")
    var offsetStart: String? = null
    @SerializedName("@offset-end")
    var offsetEnd: String? = null
    @SerializedName("@color")
    var color: String? = null
    @SerializedName("@style")
    var style: String? = null

    constructor()

    constructor(highlight: Highlight) {
        aid = highlight.paragraphAid
        offsetStart = Integer.toString(highlight.offsetStart)
        offsetEnd = Integer.toString(highlight.offsetEnd)
        color = highlight.color
        style = highlight.style
    }

    fun createHighlightFromDto(annotation: Annotation): Highlight {
        val highlight = Highlight()
        highlight.paragraphAid = aid
        highlight.offsetStart = Integer.parseInt(offsetStart)
        highlight.offsetEnd = Integer.parseInt(offsetEnd)
        highlight.style = style
        highlight.color = color
        highlight.annotationId = annotation.id

        return highlight
    }
}
