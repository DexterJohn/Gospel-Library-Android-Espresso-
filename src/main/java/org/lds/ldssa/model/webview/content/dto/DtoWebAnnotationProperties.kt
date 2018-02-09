package org.lds.ldssa.model.webview.content.dto


import com.google.gson.annotations.SerializedName

class DtoWebAnnotationProperties {
    @SerializedName("annotationId")
    var uniqueId = ""
    private var yScrollOffset: Long = 0
    var highlights = ArrayList<DtoWebHighlight>()
    var rects = ArrayList<DtoWebRect>()

    fun getyScrollOffset(): Long {
        return yScrollOffset
    }

    fun setyScrollOffset(yScrollOffset: Long) {
        this.yScrollOffset = yScrollOffset
    }
}