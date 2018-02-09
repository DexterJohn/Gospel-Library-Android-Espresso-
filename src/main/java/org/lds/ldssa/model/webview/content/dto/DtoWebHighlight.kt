package org.lds.ldssa.model.webview.content.dto

import org.lds.ldssa.model.database.userdata.highlight.Highlight
import java.io.Serializable

@Suppress("MemberVisibilityCanPrivate")
class DtoWebHighlight : Serializable {
    var aid: String? = null
    var startOffset = ""
    var endOffset = ""
    var colorName: String? = null
    var style: String? = null

    constructor()

    constructor(highlight: Highlight) {
        this.aid = highlight.paragraphAid
        this.startOffset = highlight.offsetStart.toString()
        this.endOffset = highlight.offsetEnd.toString()
        this.colorName = highlight.color

        val highlightStyle = highlight.style
        if (!highlightStyle.isNullOrBlank()) {
            this.style = highlightStyle
        }
    }

    fun applyToHighlight(highlight: Highlight) {
        highlight.paragraphAid = aid
        highlight.offsetStart = Integer.parseInt(startOffset)
        highlight.offsetEnd = Integer.parseInt(endOffset)
        highlight.color = colorName
        highlight.style = style
    }
}
