package org.lds.ldssa.model.webview.content.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DtoHighlightInfo : Serializable {
    @SerializedName("annotationId")
    var uniqueId = ""
    var text = ""
    var color = ""
    var style = ""
}
