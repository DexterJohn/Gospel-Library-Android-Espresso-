package org.lds.ldssa.model.webview.content.dto

import com.google.gson.annotations.SerializedName


class DtoWebTouch {
    enum class WebTouchSource {
        @SerializedName("link")
        LINK,
        @SerializedName("ref")
        REF,
        @SerializedName("sticky")
        NOTE,
        @SerializedName("image")
        IMAGE,
        @SerializedName("video")
        VIDEO,
        @SerializedName("video-download")
        VIDEO_DOWNLOAD,
        @SerializedName("other")
        OTHER
    }

    var touchSource: WebTouchSource? = null
    var x = 0.0f
    var y = 0.0f
    var ids = ArrayList<String>()
    var uri = ""
    var text = ""

    fun toText(): String {
        return "|| src: $touchSource  x: $x  y: $y  ids: [$ids ] uri: $uri text: $text ||"
    }
}