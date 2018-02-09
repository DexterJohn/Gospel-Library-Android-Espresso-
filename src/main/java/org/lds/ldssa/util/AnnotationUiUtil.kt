package org.lds.ldssa.util

import android.app.Application
import android.support.v4.app.FragmentActivity
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.ButterKnife
import kotlinx.coroutines.experimental.launch
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadata
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager
import org.lds.ldssa.model.database.content.subitem.SubItemManager
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.ui.web.ContentData
import org.lds.ldssa.ui.web.ContentWebView
import org.lds.ldssa.ui.widget.ContentExpandableLayout
import org.lds.ldssa.util.annotations.HighlightUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.ui.util.LdsDrawableUtil
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A utility to help display annotation cards so that
 * we only have a single source for the [org.lds.ldssa.ui.adapter.AnnotationsAdapter]
 */
@Singleton
class AnnotationUiUtil
@Inject constructor(private val cc: CoroutineContextProvider,
                    private val highlightUtil: HighlightUtil,
                    private val itemManager: ItemManager,
                    private val citationUtil: CitationUtil,
                    private val internalIntents: InternalIntents,
                    private val subItemManager: SubItemManager,
                    private val subItemMetadataManager: SubItemMetadataManager,
                    private val markdownUtil: MarkdownUtil,
                    private val application: Application,
                    private val contentItemUtil: ContentItemUtil) {

    private fun getHighlightedAnnotationTitle(contentItemId: Long, annotationId: Long): String {
        return citationUtil.createCitationText(contentItemId, annotationId)
    }

    /**
     * Creates a title that represents the specified annotation.  This may
     * represent the title from a note, or the title from the highlight itself.
     * @param annotation The annotation to get the sharable title for
     * @return The title to use when sharing the annotation
     */
    fun getSharableTitle(annotation: Annotation?): String {
        annotation ?: return ""

        annotation.note?.let { note ->
            return note.title ?: ""
        }

        var subItemMetadata: SubItemMetadata? = null
        annotation.docId?.let { docId ->
            subItemMetadata = subItemMetadataManager.findByDocId(docId)
        }

        subItemMetadata?.let {
            return getHighlightedAnnotationTitle(it.itemId, annotation.id)
        }

        return ""
    }

    /**
     * Creates an HTML formatted String that represents the specified
     * annotation card.  This will add the note information when
     * attached to an annotation.
     *
     * This should follow the format of:
     * <pre>
     * Note Title
     * Note Content
     *
     * Highlighted Annotation Title (e.g. Alma 11: 2)
     * Annotation Content (highlighted section)</pre>
     *
     * @param annotation The annotation to get the sharable html text for
     * @return The sharable html text for the annotation
     */
    fun getSharableText(annotation: Annotation?): String {
        annotation ?: return ""

        val builder = StringBuilder()
        // Add Note
        annotation.note?.let { note ->
            if (annotation.note != null) {
                val title = note.title ?: ""
                if (title.isNotBlank()) {
                    builder.append(title).append("\n")
                }

                var markdownHtml = markdownUtil.convertMarkdownToHtml(note.content)

                // remove newlines
                markdownHtml = markdownHtml.replace("\n", "")

                builder.append(markdownHtml)
            }
        }

        // Add Highlight Text
        annotation.docId?.let { docId ->
            subItemMetadataManager.findByDocId(docId)?.let { subItemMetadata ->
                if (contentItemUtil.isItemDownloadedAndOpen(subItemMetadata.itemId)) {
                    builder.append("\n\n")
                    builder.append(highlightUtil.getHighlightedAnnotationParagraphs(application, annotation, subItemMetadata))
                }
            }
        }

        return builder.toString()
    }

    fun showContentData(activity: FragmentActivity,
                        screenId: Long,
                        itemContentLayout: LinearLayout,
                        contentData: ContentData,
                        enableReadMore: Boolean,
                        clickListener: View.OnClickListener?,
                        referrer: Analytics.Referrer) {

        val view = LayoutInflater.from(activity).inflate(if (enableReadMore) R.layout.side_bar_reference_expandable else R.layout.side_bar_reference, null, false)
        val titleTextView = ButterKnife.findById<TextView>(view, R.id.titleTextView)
        val subTitleTextView = ButterKnife.findById<TextView>(view, R.id.subTitleTextView)
        val messageTextView = ButterKnife.findById<TextView>(view, R.id.messageTextView)
        val refContentWebView = ButterKnife.findById<ContentWebView>(view, R.id.contentWebView)
        var expandableLayout: ContentExpandableLayout? = null

        if (enableReadMore) {
            expandableLayout = ButterKnife.findById<ContentExpandableLayout>(view, R.id.expandableLayout)
        }

        if (contentData.subItemId == 0L) {
            // book is NOT downloaded... fall back to default title
            titleTextView.text = contentData.title
        } else {
            // title
            val subItemTitle = subItemManager.findTitleById(contentData.contentItemId, contentData.subItemId)

            // footnotes contain more data then just the verse... so if the title has a : in it, then use the verse data from there
            val verses = if (contentData.title.contains(":")) contentData.title.substringAfter(":") else ""

            if (verses.isNotBlank()) {
                titleTextView.text = application.getString(R.string.citation_chapter_verse_separator_text, subItemTitle, verses)
            } else {
                titleTextView.text = subItemTitle
            }
        }

        if (clickListener == null) {
            val defaultClickListener = View.OnClickListener { internalIntents.showUriActivity(activity, screenId, contentData.titleUri, false, false, false, referrer) }
            titleTextView.setOnClickListener(defaultClickListener)
            subTitleTextView.setOnClickListener(defaultClickListener)
        } else {
            titleTextView.setOnClickListener(clickListener)
            subTitleTextView.setOnClickListener(clickListener)
        }

        // subtitle
        subTitleTextView.text = itemManager.findTitleById(contentData.contentItemId)

        // content
        refContentWebView.visibility = View.VISIBLE
        messageTextView.visibility = View.GONE

        refContentWebView.setBackgroundColor(LdsDrawableUtil.resolvedColorIntResourceId(activity, R.attr.themeStyleColorSideBarContentBackground))
        refContentWebView.onLinkTappedListener = { _, url, newScreen ->
            onLinkTapped(activity, screenId, referrer, url, newScreen)
        }
        refContentWebView.setAnnotatingEnabled(false)
        refContentWebView.isVerticalScrollBarEnabled = false
        refContentWebView.isHorizontalScrollBarEnabled = false
        refContentWebView.loadDataWithBaseURL(contentData)

        if (enableReadMore && expandableLayout != null) {
            val finalExpandableLayout = expandableLayout
            refContentWebView.onHtmlRenderingFinishedListener = { _, _ ->
                onHtmlRenderingFinished(finalExpandableLayout)
            }
        }

        itemContentLayout.addView(view)
    }

    private fun onHtmlRenderingFinished(expandableLayout: ContentExpandableLayout) {
        // make sure this is running on the main thread
        launch(cc.ui) {
            // now that content webview is populated... update the expandableLayout
            expandableLayout.updateContentViewSize()
        }
    }

    private fun onLinkTapped(activity: FragmentActivity, screenId: Long, referrer: Analytics.Referrer, url: String, newScreen: Boolean) {
        launch(cc.ui) {
            val useScreenId = if (newScreen) { ScreenUtil.NEW_SCREEN_ID } else { screenId }
            internalIntents.showUriActivity(activity, useScreenId, url, false, true, false, referrer)
        }
    }

    companion object {
        const val ANNOTATION_MODIFIED_DATE_FORMAT_FLAGS =
                DateUtils.FORMAT_SHOW_WEEKDAY or
                        DateUtils.FORMAT_ABBREV_WEEKDAY or
                        DateUtils.FORMAT_ABBREV_MONTH or
                        DateUtils.FORMAT_SHOW_DATE or
                        DateUtils.FORMAT_SHOW_YEAR
    }
}