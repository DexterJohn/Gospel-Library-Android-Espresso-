package org.lds.ldssa.util

import android.support.annotation.MainThread
import android.view.View
import org.lds.ldssa.ui.web.ContentWebView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextHandleUtil @Inject
constructor() {
    @MainThread
    fun updateHandle(handle: View, left: Int, top: Int, visibility: Int) {
        if (left != 0 && top != 0) {
            val layoutParams = handle.layoutParams as android.widget.AbsoluteLayout.LayoutParams
            layoutParams.x = left
            layoutParams.y = top
            handle.layoutParams = layoutParams
        }
        if (handle.visibility != visibility) {
            handle.visibility = visibility
        }
    }

    fun getHandleTop(contentWebView: ContentWebView, handle: View?): Int {
        if (handle != null) {
            val layoutParams = handle.layoutParams as android.widget.AbsoluteLayout.LayoutParams
            return layoutParams.y + contentWebView.paddingTop
        }

        return 0
    }
}
