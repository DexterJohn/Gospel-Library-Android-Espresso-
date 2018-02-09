package org.lds.ldssa.util

import android.content.Context
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat

import org.lds.ldssa.R
import org.lds.mobile.ui.util.LdsDrawableUtil

enum class HighlightColor constructor(val htmlName: String, @param:AttrRes @field:AttrRes
private val colorResFill: Int, @param:AttrRes @field:AttrRes
                                              private val colorResUnderline: Int) {
    RED("red", R.attr.themeStyleColorHighlightRed, R.attr.themeStyleColorHighlightRedUnderline),
    ORANGE("orange", R.attr.themeStyleColorHighlightOrange, R.attr.themeStyleColorHighlightOrangeUnderline),
    YELLOW("yellow", R.attr.themeStyleColorHighlightYellow, R.attr.themeStyleColorHighlightYellowUnderline),
    GREEN("green", R.attr.themeStyleColorHighlightGreen, R.attr.themeStyleColorHighlightGreenUnderline),
    BLUE("blue", R.attr.themeStyleColorHighlightBlue, R.attr.themeStyleColorHighlightBlueUnderline),
    DARK_BLUE("dark_blue", R.attr.themeStyleColorHighlightDarkBlue, R.attr.themeStyleColorHighlightDarkBlueUnderline),
    PURPLE("purple", R.attr.themeStyleColorHighlightPurple, R.attr.themeStyleColorHighlightPurpleUnderline),
    PINK("pink", R.attr.themeStyleColorHighlightPink, R.attr.themeStyleColorHighlightPinkUnderline),
    BROWN("brown", R.attr.themeStyleColorHighlightBrown, R.attr.themeStyleColorHighlightBrownUnderline),
    GRAY("gray", R.attr.themeStyleColorHighlightGray, R.attr.themeStyleColorHighlightGrayUnderline),
    CLEAR("clear", 0, 0),
    SELECTION("selection", R.attr.colorAccent, R.attr.colorAccent);

    @ColorInt
    fun getColorFill(context: Context): Int {
        return if (this == CLEAR) {
            ContextCompat.getColor(context, android.R.color.transparent)
        } else LdsDrawableUtil.resolvedColorIntResourceId(context, colorResFill)

    }

    @ColorInt
    fun getColorUnderline(context: Context): Int {
        //Special case for clear
        return if (this == CLEAR) {
            ContextCompat.getColor(context, android.R.color.transparent)
        } else LdsDrawableUtil.resolvedColorIntResourceId(context, colorResUnderline)

    }

    companion object {
        @JvmStatic
        operator fun get(ordinal: Int): HighlightColor {
            return HighlightColor.values()[ordinal]
        }

        @JvmStatic
        operator fun get(htmlName: String?): HighlightColor {
            for (color in HighlightColor.values()) {
                if (color.htmlName == htmlName) {
                    return color
                }
            }

            return YELLOW
        }
    }
}
