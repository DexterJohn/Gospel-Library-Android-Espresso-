package org.lds.ldssa.model.database.types

import android.support.annotation.DrawableRes

import org.lds.ldssa.R

enum class ItemMediaType constructor(val mediaDirName: String?,
                                     @DrawableRes val drawableResId: Int) {
    UNKNOWN(null, 0),
    CONTENT("Content", 0),
    AUDIO("Audio", R.drawable.ic_lds_media_audio_24dp),
    VIDEO("Video", R.drawable.ic_lds_media_video_24dp),
    @Deprecated("Not used... keep here to maintain proper ordinal")
    IMAGE("Images", R.drawable.ic_lds_image_24dp),
    CATALOG("Catalog", 0),
    TIPS("Tips", 0),
    TEXT_TO_SPEECH("Text-to-speech", 0),
    CATALOG_DIFF("Catalog-diff", 0)
}
