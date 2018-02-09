package org.lds.ldssa.model.webservice.annotation.dto.annotation

import com.google.gson.annotations.SerializedName

import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.tag.Tag

import java.util.ArrayList

class DtoTags {
    @SerializedName("tag")
    var items = ArrayList<String>()

    constructor() {}

    constructor(tags: List<Tag>) {
        if (tags.isEmpty()) {
            return
        }

        items = ArrayList()
        for (tag in tags) {
            items.add(tag.name)
        }
    }

    fun createTagFromDto(annotation: Annotation, tagName: String): Tag {
        val tag = Tag()
        tag.annotationId = annotation.id
        tag.name = tagName

        return tag
    }
}
