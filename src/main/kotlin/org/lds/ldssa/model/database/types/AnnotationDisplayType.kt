package org.lds.ldssa.model.database.types

import android.support.annotation.StringRes
import com.google.gson.annotations.SerializedName

import org.lds.ldssa.R

/**
 * Type that is used for showing titles in sidebar and icon in right margin
 */
enum class AnnotationDisplayType(@StringRes val titleRes: Int) {
    @SerializedName("notebook")
    NOTEBOOK(R.string.notebooks),
    @SerializedName("note")
    NOTE(R.string.note),
    @SerializedName("link")
    LINK(R.string.link),
    @SerializedName("tag")
    TAG(R.string.tag),
    @SerializedName("other")
    OTHER(R.string.none)
}
