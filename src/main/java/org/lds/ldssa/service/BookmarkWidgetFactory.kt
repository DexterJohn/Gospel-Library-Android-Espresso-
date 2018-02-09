package org.lds.ldssa.service

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.userdata.bookmark.Bookmark
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkConst
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager
import org.lds.ldssa.ui.widget.BookmarkWidgetProvider
import javax.inject.Inject

class BookmarkWidgetFactory : RemoteViewsService.RemoteViewsFactory {
    private var bookmarks = listOf<Bookmark>()

    @Inject
    @Transient 
    lateinit var bookmarkManager: BookmarkManager
    @Inject
    @Transient 
    lateinit var application: Application

    override fun onCreate() {
        Injector.get().inject(this)
    }

    override fun onDestroy() {
        // nothing
    }

    override fun onDataSetChanged() {
        bookmarks = bookmarkManager.findAll(BookmarkConst.C_DISPLAY_ORDER)
    }

    override fun getCount(): Int {
        return bookmarks.size
    }

    override fun getViewAt(position: Int): RemoteViews? {
        if (bookmarks.isEmpty()) {
            return null
        }

        val rv = RemoteViews(application.packageName, R.layout.widget_launcher_bookmark_item)
        rv.setTextViewText(R.id.text, bookmarks[position].name)

        val extras = Bundle()
        extras.putLong(BookmarkWidgetProvider.BOOKMARK_ID, bookmarks[position].id)

        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        rv.setOnClickFillInIntent(R.id.text, fillInIntent)

        return rv
    }

    override fun getLoadingView(): RemoteViews {
        return RemoteViews(application.packageName, R.layout.widget_launcher_bookmark_loading)
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return false
    }
}
