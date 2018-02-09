package org.lds.ldssa.model.database.types

import android.support.annotation.DrawableRes
import org.lds.ldssa.R

/**
 * NOTE: we store the string value in the database, this
 * is used for easy access
 */
enum class HighlightAnnotationStyle constructor(val stringValue: String, @DrawableRes vararg drawableRes: Int) {
    FILL("", R.drawable.ic_highlight_fill_24dp),
    UNDERLINE("red-underline", R.drawable.ic_highlight_underline_bottom_24dp, R.drawable.ic_highlight_style_a_24dp);

    @DrawableRes
    val drawableRes: IntArray = drawableRes

    fun hasDrawableRes(): Boolean {
        return drawableRes.isNotEmpty()
    }

    companion object {
        operator fun get(ordinal: Int): HighlightAnnotationStyle {
            return HighlightAnnotationStyle.values()[ordinal]
        }

        operator fun get(value: String?): HighlightAnnotationStyle {
            if (value == null) {
                return FILL
            }

            return if (isUnderline(value)) UNDERLINE else FILL
        }

        fun isUnderline(value: String?): Boolean {
            return value != null && value.isNotBlank()
        }
    }
}
