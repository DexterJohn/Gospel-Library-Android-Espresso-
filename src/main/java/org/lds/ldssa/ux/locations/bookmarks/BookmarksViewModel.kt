package org.lds.ldssa.ux.locations.bookmarks

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import org.dbtools.android.domain.DBToolsLiveData
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager
import org.lds.ldssa.model.database.userdata.bookmarkquery.BookmarkQuery
import org.lds.ldssa.util.annotations.BookmarkUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import javax.inject.Inject

class BookmarksViewModel
@Inject constructor(
        private val cc: CoroutineContextProvider,
        private val bookmarkManager: BookmarkManager,
        private val bookmarkUtil: BookmarkUtil
) : ViewModel() {

    val bookmarkList: LiveData<List<BookmarkQuery>>

    var scrollPosition = 0

    init {
        bookmarkList = loadBookmarks()
    }

    private fun loadBookmarks(): LiveData<List<BookmarkQuery>> {
        return DBToolsLiveData.toLiveData(cc.commonPool, listOf(bookmarkManager)) {
            bookmarkUtil.findBookmarksInOrder()
        }
    }
}
