package org.lds.ldssa.model.webview.content.dto

import com.google.gson.annotations.SerializedName

class DtoUriOffset {
    @SerializedName("top")
    var position = 0.0f
    @SerializedName("id")
    var uri = ""
}