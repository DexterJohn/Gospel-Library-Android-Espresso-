package org.lds.ldssa.model.webservice.annotation.dto.folder

import com.google.gson.annotations.SerializedName
import org.lds.ldssa.model.database.types.AnnotationStatusType
import org.lds.ldssa.model.database.userdata.notebook.Notebook
import org.threeten.bp.LocalDateTime

class DtoFolder {
    var label: String? = null
    @SerializedName("desc")
    var description: String? = null
    var timestamp: LocalDateTime? = null
    @SerializedName("@guid")
    var id: String? = null
    @SerializedName("@status")
    var status: AnnotationStatusType? = null
    var order: DtoFolderOrder? = null

    constructor()

    constructor(notebook: Notebook, orderList: List<String>) {
        label = notebook.name
        description = notebook.description
        id = notebook.uniqueId
        timestamp = notebook.lastModified
        order = DtoFolderOrder(orderList)
    }

    fun createNotebookFromDto(): Notebook {
        val notebook = Notebook()
        notebook.name = label ?: ""
        notebook.description = description ?: ""
        notebook.uniqueId = id ?: ""
        notebook.lastModified = timestamp ?: LocalDateTime.now()

        return notebook
    }
}
