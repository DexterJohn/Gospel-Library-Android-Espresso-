package org.lds.ldssa.util.annotations

import android.content.Context
import android.support.v4.app.FragmentActivity
import com.afollestad.materialdialogs.MaterialDialog
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.tag.Tag
import org.lds.ldssa.model.database.userdata.tag.TagManager
import org.lds.ldssa.sync.AnnotationSyncScheduler
import org.lds.ldssa.util.AnalyticsUtil
import pocketbus.Bus
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A Utility class to help simplify interaction with
 * the Tags database so that we only have one
 * point of entry.
 */
@Singleton
class TagUtil @Inject
constructor(private val bus: Bus,
            private val tagManager: TagManager,
            private val annotationManager: AnnotationManager,
            private val syncScheduler: AnnotationSyncScheduler,
            private val analyticsUtil: AnalyticsUtil) {

    fun add(annotationId: Long, name: String): Tag {
        val newTag = Tag()
        newTag.annotationId = annotationId
        newTag.name = name
        tagManager.save(newTag)

        annotationManager.updateLastModified(annotationId, true)
        logAnalytics(annotationId, Analytics.ChangeType.CREATE)

        return newTag
    }

    fun delete(annotationId: Long, name: String) {
        if (tagManager.deleteByAnnotationIdAndName(annotationId, name) > 0) {
            annotationManager.updateLastModified(annotationId, true)
            logAnalytics(annotationId, Analytics.ChangeType.DELETE)
        }
    }

    fun renameTags(originalName: String, newName: String) {
        tagManager.beginTransaction()
        tagManager.updateName(originalName, newName)

        // Updates the annotations last modified time
        updateAnnotationModifiedTimeForTag(newName) // will NOT fire a sync, but NEEDS to be called within a transaction

        tagManager.endTransaction(true)

        syncScheduler.scheduleSync()
    }

    fun deleteTags(tagNames: List<String>?) {
        tagManager.beginTransaction()
        for (name in tagNames!!) {
            updateAnnotationModifiedTimeForTag(name) // will NOT fire a sync
            tagManager.deleteAllByName(name)
        }
        tagManager.endTransaction(true)

        syncScheduler.scheduleSync()
    }

    /**
     * NOTE: call this function IN a transaction
     */
    private fun updateAnnotationModifiedTimeForTag(name: String) {
        if (!tagManager.inTransaction()) {
            throw IllegalStateException("Must be in a transaction")
        }

        val annotationIds = tagManager.findAllAnnotationIdsByTagName(name)
        for (annotationId in annotationIds) {
            annotationManager.updateLastModified(annotationId, false)
        }
    }

    /**
     * Prompts the user for edit the specified Tag
     *
     * @param activity The activity to display the dialog with
     */
    fun promptEdit(activity: FragmentActivity?, tagName: String) {
        activity ?: return
        MaterialDialog.Builder(activity)
                .title(R.string.edit_tag)
                .positiveText(R.string.ok)
                .negativeText(R.string.cancel)
                .inputRange(0, Tag.NAME_MAX_LENGTH)
                .input(null, tagName) { _, input ->
                    if (input.isNotBlank()) {
                        renameTags(tagName, input.toString())
                    }
                }
                .show()
    }

    /**
     * Prompts the user for Merging the specified tags
     *
     * @param activity The activity to display the dialog with
     */
    fun promptMerge(activity: FragmentActivity?, tagNames: List<String>) {
        activity ?: return
        MaterialDialog.Builder(activity)
                .title(R.string.merged_tag_name)
                .positiveText(R.string.merge)
                .negativeText(R.string.cancel)
                .inputRange(0, Tag.NAME_MAX_LENGTH)
                .input(null, null) { _, input ->
                    if (input.isNotBlank()) {
                        for (tagName in tagNames) {
                            renameTags(tagName, input.toString())
                        }
                    }
                }
                .show()
    }

    /**
     * Prompts the user to delete a specific tag across all annotations
     *
     * @param tagName the name of the tag to delete
     */
    fun promptDelete(context: Context?, tagName: String) {
        val tagNames = ArrayList<String>()
        tagNames.add(tagName)
        promptDelete(context, tagNames)
    }

    /**
     * Prompts the user to delete a list of tags across all annotations
     *
     * @param tagNames the id list of the notebooks to delete
     */
    fun promptDelete(context: Context?, tagNames: List<String>?) {
        if (context == null || tagNames == null || tagNames.isEmpty()) {
            return
        }

        val res = context.resources
        MaterialDialog.Builder(context)
                .title(res.getQuantityString(R.plurals.delete_tag_title, tagNames.size))
                .content(res.getQuantityString(R.plurals.delete_tag_message, tagNames.size))
                .positiveText(R.string.delete)
                .negativeText(R.string.cancel)
                .onPositive { _, _ ->
                    deleteTags(tagNames)
                }
                .show()
    }

    private fun logAnalytics(annotationId: Long, changeType: Analytics.ChangeType) {
        analyticsUtil.logContentAnnotated(annotationId, Analytics.AnnotationType.TAG, changeType)
    }
}