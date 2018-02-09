/*
 * Highlight.kt
 *
 * Created: 02/09/2017 11:39:55
 */



package org.lds.ldssa.model.database.userdata.highlight


class Highlight : HighlightBaseRecord() {
    override var offsetStart: Int
        get() = calculateOffsetStart()
        set(value) {super.offsetStart = value}

    fun calculateOffsetStart(): Int {
        val offsetStart = super.offsetStart
        val offsetEnd = super.offsetEnd

        if (offsetStart > offsetEnd && offsetEnd != -1) {
            // There are cases when the sync service may set the startOffset AFTER the endOffset...
            // if this happens, and because GL won't be able to show the highlight, force the startOffset to be the same as the endOffset
            super.offsetStart = offsetEnd
            return offsetEnd
        } else {
            return offsetStart
        }
    }

    fun sameLocationsAndStyle(other: Highlight): Boolean {
        if (this === other) return true
        if (paragraphAid != other.paragraphAid) return false
        if (offsetStart != other.offsetStart) return false
        if (offsetEnd != other.offsetEnd) return false
        if (color != other.color) return false
        if (style != other.style) return false

        return true
    }
}