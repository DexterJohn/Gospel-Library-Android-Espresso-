package org.lds.ldssa.model.webservice.annotation.dto.folder

class DtoFolderOrder {
    var id: List<String> = ArrayList()

    constructor()

    constructor(order: List<String>) {
        this.id = order
    }
}
