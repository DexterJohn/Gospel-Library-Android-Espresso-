package org.lds.ldssa.model.webview.content.dto

import org.lds.ldssa.model.database.userdata.bookmark.Bookmark

class DtoWebBookmark {
    var name = ""
    var paragraphAid: String? = null
    var offset = ""

    constructor()

    constructor(bookmark: Bookmark?) {
        bookmark ?: return

        this.name = bookmark.name
        this.paragraphAid = bookmark.paragraphAid
        this.offset = bookmark.offset.toString()
    }
}
