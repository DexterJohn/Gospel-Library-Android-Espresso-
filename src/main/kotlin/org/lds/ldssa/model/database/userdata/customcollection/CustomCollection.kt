/*
 * CustomCollection.kt
 *
 * Created: 02/09/2017 11:39:55
 */



package org.lds.ldssa.model.database.userdata.customcollection

import java.util.*


class CustomCollection() : CustomCollectionBaseRecord() {

    constructor(title: String) : this() {
        this.title = title
        initUniqueId()
    }

    fun initUniqueId() {
        if (uniqueId.isBlank()) {
            uniqueId = UUID.randomUUID().toString() // id should be unique across users
        }
    }
}