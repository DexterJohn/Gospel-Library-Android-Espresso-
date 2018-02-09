package org.lds.ldssa.task

import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager
import org.lds.ldssa.model.database.userdata.tag.Tag
import org.lds.ldssa.model.database.userdata.tag.TagManager
import org.lds.ldssa.sync.AnnotationSyncScheduler
import timber.log.Timber
import javax.inject.Inject

class AnnotationFixTask @Inject
constructor(private val annotationManager: AnnotationManager,
            private val bookmarkManager: BookmarkManager,
            private val tagManager: TagManager,
            private val syncScheduler: AnnotationSyncScheduler) {

    fun execute(): Boolean {
        // prevent Fix from happening over and over (in case AnnotationFixTask cannot fix the issue)
        val tagsUpdated = cleanupInvalidTags()
        val bookmarksUpdated = cleanupInvalidBookmarks()

        // only if a change/fix happened, perform a sync
        if (tagsUpdated || bookmarksUpdated) {
            syncScheduler.scheduleSync()
        }

        return true
    }

    /**
     * Example: Tag names with a comma
     */
    fun cleanupInvalidTags(): Boolean {
        try {
            val tags = tagManager.findAllWithInvalidCharacter()

            if (tags.isEmpty()) {
                return false
            }

            Timber.e("Cleaning invalid tags - count: [%d]", tags.size)

            tagManager.inTransaction {
                for (tag in tags) {
                    tag.name = tag.name.replace(Tag.ILLEGAL_CHAR.toString().toRegex(), "")
                    tagManager.save(tag)
                    annotationManager.updateLastModified(tag.annotationId, false)
                }

                true // end transaction
            }

            // verify that there are no more issues
            return tagManager.findAllWithInvalidCharacter().isEmpty()
        } catch (e: Exception) {
            tagManager.endTransaction(false)
            return false
        }
    }

    private fun cleanupInvalidBookmarks(): Boolean {
        try {
            val bookmarks = bookmarkManager.findAllWithInvalidName()
            if (bookmarks.isEmpty()) {
                return false
            }

            Timber.e("Cleaning invalid bookmarks - count: [%d]", bookmarks.size)

            bookmarkManager.inTransaction {
                for (bookmark in bookmarks) {
                    // setName() will trim the value automatically
                    bookmark.name = bookmark.name
                    bookmarkManager.save(bookmark)
                    annotationManager.updateLastModified(bookmark.annotationId, false)
                }

                true // end transaction
            }

            // verify that there are no more issues
            return bookmarkManager.findAllWithInvalidName().isEmpty()
        } catch (e: Exception) {
            bookmarkManager.endTransaction(false)
            return false
        }
    }
}
