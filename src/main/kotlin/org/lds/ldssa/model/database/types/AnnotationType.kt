package org.lds.ldssa.model.database.types

import com.google.gson.annotations.SerializedName

/**
 * type that is ONLY used with the annotation service
 */
enum class AnnotationType {
    @SerializedName("")
    UNKNOWN,
    @SerializedName("highlight")
    HIGHLIGHT,
    @SerializedName("bookmark")
    BOOKMARK,
    @SerializedName("journal")
    JOURNAL,
    @SerializedName("reference")
    REFERENCE
}
