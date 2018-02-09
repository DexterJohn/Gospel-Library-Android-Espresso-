package org.lds.ldssa.model.database.types

enum class CatalogItemQueryType {
    UNKNOWN,

    // from query
    COLLECTION, // (Scriptures, Magazines)
    COLLECTION_CONTENT_ITEM, // Book (Book of Mormon, Ensign)
    CONTENT_SUB_ITEM, // (Alma 1, Ensign June 2015)


    CUSTOM_COLLECTION, // Favorites, My Collection
    CUSTOM_COLLECTION_CONTENT_ITEM, // Book (Book of Mormon, Ensign)

    // custom (merge cursor)
    NOTES,
    TIPS,
    STUDY_PLANS,

    // other (mainly used for backstack support)
    CONTENT_DIRECTORY_ITEM  // (Alma)
    ;

    val isContentItem: Boolean
        get() {
            when (this) {
                COLLECTION_CONTENT_ITEM, CUSTOM_COLLECTION_CONTENT_ITEM, CONTENT_DIRECTORY_ITEM, CONTENT_SUB_ITEM -> return true
                else -> return false
            }
        }
}
