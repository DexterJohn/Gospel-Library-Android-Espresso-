package org.lds.ldssa.model.webview.content.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DtoImageSource : Serializable {
    @SerializedName("data-width")
    var width = ""
    @SerializedName("data-height")
    var height = ""
    var src = ""
}
