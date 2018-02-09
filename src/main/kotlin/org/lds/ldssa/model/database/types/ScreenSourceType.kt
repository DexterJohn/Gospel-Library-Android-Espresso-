package org.lds.ldssa.model.database.types

// NOTE, do NOT change the order or the number of items in this list unless screen history is cleared (ordinal values are used in the database)
enum class ScreenSourceType(val contentPackageRequired: Boolean) {
    UNKNOWN(false),
    CATALOG_DIRECTORY(false), // (Scriptures, Magazines)
    CUSTOM_CATALOG_DIRECTORY(false),
    CONTENT_DIRECTORY(true), // (Alma)
    CONTENT(true), // (Alma 1, Ensign June 2015)
    NOTES(false),
    NOTEBOOK(false),
    TAG(false), // NOT USED
    TIPS(false),
    SEARCH(false),
    SEARCH_RESULTS(false), // NOT USED
    SETTINGS(false),
    LINKS(false), // NOT USED
    ANNOTATION_VIEW(false),
    STUDY_PLANS(false),
    STUDY_PLAN_ITEMS(false),
}
