/*
 * Notebook.kt
 *
 * Created: 02/09/2017 11:39:55
 */



package org.lds.ldssa.model.database.userdata.notebook

import java.util.UUID


class Notebook : NotebookBaseRecord() {
    companion object {
        const val NAME_MAX_LENGTH = 256
    }

    fun initUniqueId() {
        if (uniqueId.isNullOrBlank()) {
            uniqueId = UUID.randomUUID().toString() // id should be unique across users
        }
    }

    override var name: String
        get() = super.name
        set(value) {super.name = value.take(NAME_MAX_LENGTH)}
}