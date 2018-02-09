package org.lds.ldssa.util

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Return the first visible item. This version safely allows this to be null. todo Move to LdsRecyclerExt
 */
fun <T : RecyclerView> T?.getScrollPosition(): Int {
    val manager = this?.layoutManager
    return when {
        manager == null || manager.itemCount <= 0 -> 0
        manager is LinearLayoutManager -> manager.findFirstCompletelyVisibleItemPosition()
        manager is GridLayoutManager -> manager.findFirstCompletelyVisibleItemPosition()
        else -> 0
    }
}
