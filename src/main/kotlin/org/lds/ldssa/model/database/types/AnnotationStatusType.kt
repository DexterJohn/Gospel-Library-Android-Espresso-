package org.lds.ldssa.model.database.types

import com.google.gson.annotations.SerializedName


enum class AnnotationStatusType {
    // ONLY USE TRASHED (DELETED is set by SERVICE)
    @SerializedName("")
    ACTIVE,
    @SerializedName("trashed")
    TRASHED,
    @SerializedName("deleted")
    DELETED
}
