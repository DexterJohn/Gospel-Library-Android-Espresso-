/*
 * Bookmark.kt
 *
 * Created: 02/09/2017 11:39:55
 */



package org.lds.ldssa.model.database.userdata.bookmark


class Bookmark : BookmarkBaseRecord() {
    companion object {
        const val NAME_MAX_LENGTH = 256
    }

    override var name: String
        get() = super.name
        set(value) {
            super.name = value.take(NAME_MAX_LENGTH)
        }
}