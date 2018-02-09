package org.lds.ldssa.model.webservice.annotation.dto.annotation

import com.google.gson.annotations.SerializedName

import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.bookmark.Bookmark

class DtoBookmark {

    @SerializedName("name")
    var name: String? = null
    @SerializedName("@pid")
    var aid: String? = null
    @SerializedName("@offset")
    var offset: String? = null
    @SerializedName("sort")
    var sort: Int? = null
        // don't send back to the server our made-up number (if possible)
        set(sort) = if (sort != null && sort == DEFAULT_SORT_ORDER) {
            field = null
        } else {
            field = sort
        }

    constructor()

    constructor(bookmark: Bookmark) {
        name = bookmark.name
        aid = bookmark.paragraphAid
        offset = Integer.toString(bookmark.offset)
        sort = bookmark.displayOrder
    }

    fun createBookmarkFromDto(annotation: Annotation): Bookmark {
        val bookmark = Bookmark()

        bookmark.name = ""
        name?.let {
            bookmark.name = it
        }

        bookmark.paragraphAid = aid
        bookmark.offset = Integer.parseInt(offset)

        bookmark.displayOrder = DEFAULT_SORT_ORDER
        sort?.let {
            bookmark.displayOrder = it
        }

        bookmark.annotationId = annotation.id

        return bookmark
    }

    companion object {
        const val DEFAULT_SORT_ORDER = 999999
    }
}
