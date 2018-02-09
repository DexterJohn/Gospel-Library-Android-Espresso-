/*
 * BookmarkQuery.kt
 *
 * Created: 02/09/2017 11:39:55
 */



package org.lds.ldssa.model.database.userdata.bookmarkquery


class BookmarkQuery : BookmarkQueryBaseRecord() {
    fun updateFromOtherItem(otherItem: BookmarkQuery) {
        annotationId = otherItem.annotationId
        paragraphAid = otherItem.paragraphAid
        lastModified = otherItem.lastModified
        name = otherItem.name
        citation = otherItem.citation
    }
}