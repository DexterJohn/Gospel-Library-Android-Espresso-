package org.lds.ldssa.model.webview.content.dto

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class DtoInlineVideo : Serializable {
    var title = ""
    @SerializedName("id")
    var videoId: String? = null
    var index: String? = null
    var sources = ArrayList<DtoVideoSource>()
}
