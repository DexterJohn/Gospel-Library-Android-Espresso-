package org.lds.ldssa.model.webview.content.dto

import java.io.Serializable

class DtoImage : Serializable {
    var id = ""
    var title = ""
    var index = 0
    var sources: List<DtoImageSource> = emptyList()
}