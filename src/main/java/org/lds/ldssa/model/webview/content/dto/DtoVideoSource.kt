package org.lds.ldssa.model.webview.content.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class DtoVideoSource : Serializable {
    @SerializedName("src")
    var url = ""
    @SerializedName("type")
    var type = ""
    @SerializedName("data-container")
    var dataContainer = ""
    @SerializedName("data-width")
    var width = ""
    @SerializedName("data-height")
    var height = ""
    @SerializedName("data-encodingbitspersec")
    var bps = ""
    @SerializedName("data-sizeinbytes")
    var size = ""
    @SerializedName("data-durationms")
    var duration = ""
}