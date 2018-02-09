package org.lds.ldssa.model.webservice.annotation.dto.annotation

import com.google.gson.annotations.SerializedName

import org.lds.ldssa.model.database.userdata.highlight.Highlight

import java.util.ArrayList

class DtoHighlights {
    @SerializedName("highlight")
    var items = ArrayList<DtoHighlight>()

    constructor()

    constructor(highlights: List<Highlight>?) {
        if (highlights == null || highlights.isEmpty()) {
            return
        }

        items = ArrayList()
        for (highlight in highlights) {
            items.add(DtoHighlight(highlight))
        }
    }
}
