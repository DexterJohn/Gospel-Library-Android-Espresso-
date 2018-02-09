package org.lds.ldssa.model.webservice.annotation.dto.annotation

import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.note.Note

class DtoNote {
    var title: String? = null
    var content: String? = null

    constructor()

    constructor(note: Note) {
        title = note.title
        content = note.content
    }

    fun createNoteFromDto(annotation: Annotation): Note {
        val note = Note()
        note.annotationId = annotation.id
        note.title = title ?: ""
        note.content = content

        return note
    }
}
