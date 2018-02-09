package org.lds.ldssa.service

import android.content.Intent
import android.widget.RemoteViewsService

class BookmarkWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsService.RemoteViewsFactory {
        return BookmarkWidgetFactory()
    }
}
