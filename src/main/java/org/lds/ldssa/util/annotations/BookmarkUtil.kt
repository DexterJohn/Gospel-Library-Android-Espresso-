package org.lds.ldssa.util.annotations

import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.content.subitem.SubItemManager
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.bookmark.Bookmark
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager
import org.lds.ldssa.model.database.userdata.bookmarkquery.BookmarkQuery
import org.lds.ldssa.model.database.userdata.bookmarkquery.BookmarkQueryManager
import org.lds.ldssa.sync.AnnotationSyncScheduler
import org.lds.ldssa.util.AnalyticsUtil
import org.lds.ldssa.util.CitationUtil
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookmarkUtil @Inject
constructor(private val annotationManager: AnnotationManager,
            private val subItemManager: SubItemManager,
            private val itemManager: ItemManager,
            private val bookmarkManager: BookmarkManager,
            private val citationUtil: CitationUtil,
            private val analyticsUtil: AnalyticsUtil,
            private val bookmarkQueryManager: BookmarkQueryManager,
            private val syncScheduler: AnnotationSyncScheduler) {

    /**
     * @return id of newly created bookmark, or -1 if it could not be created
     */
    suspend fun add(name: String, contentItemId: Long, subItemId: Long, paragraphAid: String?): Long {
        val docId = findDocIdForBookmark(contentItemId, subItemId)
        if (docId.isNullOrBlank()) {
            return -1L
        }

        // increment display order of existing bookmarks (making room for this new on at the top)
        bookmarkManager.incrementAllDisplayOrders()

        // create new bookmarkINCREMENT_DISPLAY_ORDER
        val bookmark = Bookmark()
        bookmark.name = name
        bookmark.paragraphAid = paragraphAid
        bookmark.offset = 0 // currently not used
        bookmark.citation = citationUtil.createCitationText(contentItemId, subItemId, paragraphAid!!)
        bookmark.displayOrder = 0

        val annotation = Annotation()
        annotation.docId = docId
        annotation.bookmark = bookmark

        val success = annotationManager.save(annotation)

        logAnalytics(annotation, Analytics.ChangeType.CREATE)

        return if (success) bookmark.id else -1
    }

    suspend fun update(bookmarkId: Long, contentItemId: Long, subItemId: Long, paragraphAid: String?): Boolean {
        var updated = false

        val bookmark = bookmarkManager.findByRowId(bookmarkId) ?: return false

        val annotation = annotationManager.findByRowId(bookmark.annotationId) ?: return false

        val citation = citationUtil.createCitationText(contentItemId, subItemId, paragraphAid!!)

        // if the name and the citation are the same... then make sure they remain the same
        if (bookmark.name == bookmark.citation) {
            bookmark.name = citation
        }
        bookmark.citation = citation
        bookmark.paragraphAid = paragraphAid
        bookmark.offset = 0 // currently not used
        if (bookmarkManager.save(bookmark)) {
            // update annotation
            val docId = findDocIdForBookmark(contentItemId, subItemId)
            annotation.docId = docId

            annotationManager.save(annotation) // this call will update the last modified timestamp and sync

            updated = true
        }

        logAnalytics(annotation, Analytics.ChangeType.UPDATE)

        return updated
    }

    suspend fun findBookmarksInOrder(): List<BookmarkQuery> {
        val bookmarkQueryList = bookmarkQueryManager.findAllOrderByDisplayOrderAndLastModified()

        // if any citations are missing... fill them in here
        for (bookmarkQueryItem in bookmarkQueryList) {
            if (bookmarkQueryItem.citation.isBlank()) {
                val citation = citationUtil.createCitationText(bookmarkQueryItem.docId, bookmarkQueryItem.paragraphAid)

                if (citation.isNotBlank()) {
                    bookmarkQueryItem.citation = citation
                    bookmarkManager.updateCitation(bookmarkQueryItem.id, citation)
                }
            }
        }
        return bookmarkQueryList
    }

    //todo should be a suspend function
    fun findTitleByAnnotationId(annotationId: Long): String {
        val docId = annotationManager.findDocIdById(annotationId)
        return if (docId != null) {
            itemManager.findTitleByDocId(docId)
        } else {
            ""
        }
    }

    suspend fun rename(bookmarkId: Long, name: String) {
        bookmarkManager.updateName(bookmarkId, name)
        annotationManager.updateLastModified(bookmarkManager.findAnnotationIdById(bookmarkId), true)
    }

    suspend fun delete(bookmarkId: Long) {
        val annotationId = bookmarkManager.findAnnotationIdById(bookmarkId)
        annotationManager.trashById(annotationId) // sync will happen in annotationManager
        logAnalytics(annotationId, Analytics.ChangeType.DELETE)
    }

    private fun logAnalytics(annotationId: Long, changeType: Analytics.ChangeType) {
        analyticsUtil.logContentAnnotated(annotationId, Analytics.AnnotationType.BOOKMARK, changeType)
    }

    private fun logAnalytics(annotation: Annotation, changeType: Analytics.ChangeType) {
        analyticsUtil.logContentAnnotated(annotation, Analytics.AnnotationType.BOOKMARK, changeType)
    }

    suspend fun findDocIdForBookmark(contentItemId: Long, subItemId: Long): String? {
        return subItemManager.findDocIdById(contentItemId, subItemId)
    }

    // Run worker update within the same transaction as the loop to update the order column.
    suspend fun updateBookmarks(updatedBookmarks: List<BookmarkQuery>? = null, worker: suspend () -> Boolean): Boolean {
        var success = false
        bookmarkManager.beginTransaction()
        try {
            worker()
            val bookmarks = updatedBookmarks ?: findBookmarksInOrder()

            var changed = false
            for (newDisplayOrder in 0 until bookmarks.size) {
                val bookmarkQueryItem = bookmarks[newDisplayOrder]
                changed = changed || bookmarkQueryItem.displayOrder != newDisplayOrder
                if (changed) {
                    bookmarkManager.updateDisplayOrder(bookmarkQueryItem.id, newDisplayOrder)
                    annotationManager.updateLastModified(bookmarkQueryItem.annotationId, false)
                }
            }
            syncScheduler.scheduleSync()
            success = true
        } catch (e: Exception) {
            Timber.e(e, "Couldn't update bookmarks")
        } finally {
            bookmarkManager.endTransaction(success)
        }
        return success
    }
}
