/*
 * Note.kt
 *
 * Created: 02/09/2017 11:39:55
 */



package org.lds.ldssa.model.database.userdata.note


class Note : NoteBaseRecord() {

    companion object {
        const val TITLE_MAX_LENGTH = 256
        const val CONTENT_MAX_LENGTH = 20000
    }

    fun isEmpty(): Boolean {
        return title.isNullOrBlank() && content.isNullOrBlank()
    }

    override var title: String?
        get() = super.title
        set(value) {super.title = value?.take(TITLE_MAX_LENGTH) ?: "" }

    override var content: String?
        get() = super.content
        set(value) {super.content = value?.take(CONTENT_MAX_LENGTH) ?: ""}
}