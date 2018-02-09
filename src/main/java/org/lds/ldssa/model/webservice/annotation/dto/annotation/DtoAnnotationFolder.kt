package org.lds.ldssa.model.webservice.annotation.dto.annotation

import com.google.gson.annotations.SerializedName

class DtoAnnotationFolder {

    @SerializedName("@uri")
    var uri: String? = null

    constructor()

    constructor(folderUniqueId: String) {
        uri = String.format(FOLDER_URI_TEMPLATE, "0", folderUniqueId) // "0" is the personId that will be set on the server side (set to 0 by default)
    }

    companion object {
        const val FOLDER_URI_TEMPLATE = "/study-tools/folders/%1\$s/%2\$s"
    }
}
