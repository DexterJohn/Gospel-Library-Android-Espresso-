package org.lds.ldssa.model.webservice.annotation.dto.annotation

import com.google.gson.annotations.SerializedName

import org.lds.ldssa.model.database.userdata.link.Link

import java.util.ArrayList

class DtoRefs {
    @SerializedName("ref")
    var items = ArrayList<DtoRef>()

    constructor() {}

    constructor(links: List<Link>) {
        if (links.isEmpty()) {
            return
        }

        items = ArrayList()
        for (link in links) {
            items.add(DtoRef(link))
        }
    }
}
