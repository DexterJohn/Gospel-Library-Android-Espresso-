package org.lds.ldssa.model.database.types

import com.google.gson.annotations.SerializedName


enum class AnnotationChangeType {
    // ONLY USE NEW AND TRASHED
    @SerializedName("")
    NONE,
    @SerializedName("new")
    NEW,
    @SerializedName("trash")
    TRASH,
    @SerializedName("delete")
    DELETE
}
