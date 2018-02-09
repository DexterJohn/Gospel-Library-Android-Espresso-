package org.lds.ldssa.model.webservice.annotation.dto.annotation

import com.google.gson.annotations.SerializedName

import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotation

import java.util.ArrayList

class DtoAnnotationFolders {
    @SerializedName("folder")
    private var items: MutableList<DtoAnnotationFolder>? = null

    constructor()

    constructor(notebookAnnotations: List<NotebookAnnotation>?, notebookManager: NotebookManager) {

        if (notebookAnnotations == null || notebookAnnotations.isEmpty()) {
            items = null
            return
        }

        items = ArrayList()
        for (folderAnnotation in notebookAnnotations) {
            val folderUniqueId = notebookManager.findNotebookUniqueIdById(folderAnnotation.notebookId)
            items!!.add(DtoAnnotationFolder(folderUniqueId))
        }
    }

    fun getItems(): List<DtoAnnotationFolder>? {
        return items
    }

    fun setItems(items: MutableList<DtoAnnotationFolder>) {
        this.items = items
    }
}
