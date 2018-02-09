package org.lds.ldssa.ui.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.annotation.MenuRes
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.PopupMenu
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.flexbox.FlexboxLayout
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import org.dbtools.android.domain.date.DBToolsThreeTenFormatter
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadata
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager
import org.lds.ldssa.model.database.types.HighlightAnnotationStyle
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.highlight.Highlight
import org.lds.ldssa.model.database.userdata.link.Link
import org.lds.ldssa.model.database.userdata.tag.Tag
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.ui.web.ContentData
import org.lds.ldssa.util.AnalyticsUtil
import org.lds.ldssa.util.AnnotationUiUtil
import org.lds.ldssa.util.CitationUtil
import org.lds.ldssa.util.ContentItemUtil
import org.lds.ldssa.util.ContentRenderer
import org.lds.ldssa.util.HighlightColor
import org.lds.ldssa.util.ShareUtil
import org.lds.ldssa.util.UriUtil
import org.lds.ldssa.util.annotations.HighlightUtil
import org.lds.ldssa.util.annotations.NoteUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.markdown.widget.MarkdownTextView
import org.lds.mobile.ui.setVisible
import org.lds.mobile.ui.util.LdsDrawableUtil
import pocketbus.Bus
import java.util.HashSet
import javax.inject.Inject

class AnnotationView : LinearLayout {

    @Inject
    lateinit var bus: Bus
    @Inject
    lateinit var prefs: Prefs
    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var subItemMetadataManager: SubItemMetadataManager
    @Inject
    lateinit var annotationUiUtil: AnnotationUiUtil
    @Inject
    lateinit var contentRenderer: ContentRenderer
    @Inject
    lateinit var citationUtil: CitationUtil
    @Inject
    lateinit var annotationManager: AnnotationManager
    @Inject
    lateinit var noteUtil: NoteUtil
    @Inject
    lateinit var contentItemUtil: ContentItemUtil
    @Inject
    lateinit var itemManager: ItemManager
    @Inject
    lateinit var highlightUtil: HighlightUtil
    @Inject
    lateinit var shareUtil: ShareUtil
    @Inject
    lateinit var uriUtil: UriUtil
    @Inject
    lateinit var analyticsUtil: AnalyticsUtil
    @Inject
    lateinit var cc: CoroutineContextProvider

    @BindView(R.id.lastModifiedTextView)
    lateinit var lastModifiedTextView: TextView
    @BindView(R.id.noteTitleTextView)
    lateinit var noteTitleTextView: MarkdownTextView
    @BindView(R.id.noteMarkdownTextView)
    lateinit var noteMarkdownTextView: MarkdownTextView
    @BindView(R.id.tagsLayout)
    lateinit var tagsLayout: FlexboxLayout
    @BindView(R.id.notebooksLayout)
    lateinit var notebooksLayout: LinearLayout
    @BindView(R.id.linksLayout)
    lateinit var linksLayout: LinearLayout

    @BindView(R.id.highlightLayout)
    lateinit var highlightLayout: LinearLayout
    @BindView(R.id.highlightTitleTextView)
    lateinit var highlightTitleTextView: TextView
    @BindView(R.id.highlightSubTitleTextView)
    lateinit var highlightSubTitleTextView: TextView
    @BindView(R.id.highlightContentTextView)
    lateinit var highlightContentTextView: TextView

    private var screenId: Long = 0
    private lateinit var annotation: Annotation
    private lateinit var inflater: LayoutInflater
    private var inSidebar: Boolean = false

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private fun init(context: Context) {
        Injector.get().inject(this)
        inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.annotation, this, true)
        ButterKnife.bind(this)
    }

    @JvmOverloads
    fun loadUi(screenId: Long, annotation: Annotation, showHighlight: Boolean, showNotebooks: Boolean, highlightNoteText: String? = null) {
        this.screenId = screenId
        this.annotation = annotation

        showLastModified()
        showNotes(highlightNoteText)
        showHighlight(showHighlight)
        showLinks()
        showTags()
        showNotebooks(showNotebooks)
    }

    fun setInSidebar(inSidebar: Boolean) {
        this.inSidebar = inSidebar
    }

    private fun showLastModified() {
        val lastModifiedTs = DBToolsThreeTenFormatter.localDateTimeToLong(annotation.lastModified)
        if (lastModifiedTs != null) {
            lastModifiedTextView.text = DateUtils.formatDateTime(context, lastModifiedTs, AnnotationUiUtil.ANNOTATION_MODIFIED_DATE_FORMAT_FLAGS)
        }
    }

    private fun showNotes(highlightNoteText: String? = null) {
        val note = annotation.note
        if (note == null) {
            noteTitleTextView.text = ""
            noteTitleTextView.visibility = View.GONE
            noteMarkdownTextView.setMarkdown("")
            noteMarkdownTextView.visibility = View.GONE
            return
        }

        val title = note.title
        val content = note.content
        val hasContent = content.isNullOrBlank().not()
        val highlightColorId = LdsDrawableUtil.resolvedColorIntResourceId(context, R.attr.themeStyleColorBackgroundFindOnPage)

        // Title
        if (title.isNullOrBlank().not()) {
            noteTitleTextView.visibility = View.VISIBLE
            noteTitleTextView.setMarkdown(title ?: "", highlightNoteText, highlightColorId)
        } else {
            noteTitleTextView.text = ""
            noteTitleTextView.visibility = View.GONE
        }

        noteTitleTextView.setOnClickListener { _ ->
            internalIntents.editNote(context as Activity, screenId, annotation.id)
        }

        // Content
        if (hasContent) {
            noteMarkdownTextView.visibility = View.VISIBLE
            noteMarkdownTextView.setMarkdown(noteUtil.convertLegacyNoteContent(content) ?: "", highlightNoteText, highlightColorId)
        } else {
            noteMarkdownTextView.setMarkdown("")
            noteMarkdownTextView.visibility = View.GONE
        }

        noteMarkdownTextView.setOnClickListener { _ ->
            internalIntents.editNote(context as Activity, screenId, annotation.id)
        }
    }

    private fun showHighlight(showHighlight: Boolean) {
        if (!showHighlight) {
            highlightLayout.visibility = View.GONE
            return
        }

        val docId = annotation.docId
        val subItemMetadata: SubItemMetadata?
        if (docId != null) {
            subItemMetadata = subItemMetadataManager.findByDocId(docId)
        } else {
            subItemMetadata = null
        }

        if (subItemMetadata != null) {
            highlightLayout.visibility = View.VISIBLE
            val contentItemTitle = itemManager.findTitleById(subItemMetadata.itemId)

            if (contentItemUtil.isItemDownloadedAndOpen(subItemMetadata.itemId)) {
                highlightTitleTextView.text = citationUtil.createCitationText(subItemMetadata.itemId, annotation.id)
                highlightSubTitleTextView.text = contentItemTitle
                highlightContentTextView.text = highlightUtil.getHighlightedAnnotationParagraphs(context, annotation, subItemMetadata)
            } else {
                highlightTitleTextView.text = contentItemTitle
                highlightSubTitleTextView.visibility = View.GONE
                highlightContentTextView.text = context.getString(R.string.annotation_no_content, contentItemTitle)
            }

            val referrer = if (inSidebar) Analytics.Referrer.RELATED_CONTENT_ANNOTATION_LINK else Analytics.Referrer.NOTEBOOK_MARK_LINK

            val highlightClickListener = View.OnClickListener {
                internalIntents.showContentForAnnotation(context as FragmentActivity, screenId,
                        subItemMetadata.itemId, subItemMetadata.subitemId, annotation.getFirstHighlightParagraphAid(), false, referrer)
            }
            highlightTitleTextView.setOnClickListener(highlightClickListener)
            highlightSubTitleTextView.setOnClickListener(highlightClickListener)
            highlightContentTextView.setOnClickListener(highlightClickListener)
        } else {
            highlightLayout.visibility = View.GONE
        }
    }

    private fun showTags() {
        val tags = annotation.tags

        tagsLayout.removeAllViews()
        tagsLayout.setVisible(tags.isNotEmpty())

        for (tag in tags) {
            tagsLayout.addView(createTagView(tag))
        }

        // add +
        tagsLayout.addView(createEditTagView())
    }

    private fun createTagView(tag: Tag): View {
        val annotationTagView = AnnotationTagView(context, tag.name)
        annotationTagView.setTagClickListener(object : AnnotationTagView.OnTagClickListener {
            override fun onDeleteTag(name: String, tagId: Long) {
                // do nothing
            }

            override fun onClickTag(name: String, tagId: Long) {
                internalIntents.showTags(context as Activity, screenId, annotationTagView.text)
            }
        })
        annotationTagView.setDeleteVisible(false)

        return annotationTagView
    }

    private fun createEditTagView(): View {
        val view = inflater.inflate(R.layout.annotation_item_tag_edit, null)
        view.setOnClickListener { _ -> internalIntents.showTagSelection(context as Activity, screenId, annotation.id) }

        return view
    }

    private fun showLinks() {
        val links = annotation.links

        linksLayout.removeAllViews()
        linksLayout.setVisible(links.isNotEmpty())

        for (link in links) {
            linksLayout.addView(createLinkView(link))
        }
    }

    @SuppressLint("InflateParams")
    private fun createLinkView(link: Link): View {
        val view = inflater.inflate(R.layout.annotation_item_link, null)
        val linkView = ButterKnife.findById<LinearLayout>(view, R.id.linkTextLayout)
        val editLinkImageButton = ButterKnife.findById<ImageButton>(view, R.id.editLinkImageButton)

        editLinkImageButton.setOnClickListener { internalIntents.showLinks(context as Activity, screenId, annotation.id) }

        // load content for view in the background
        launch(cc.ui) {
            val contentData = run(coroutineContext + cc.commonPool) {
                return@run loadLinkContentData(link)
            }

            showLinkContentData(link, contentData, linkView)
        }

        // Hide the edit link button if this is an inverse (bi-directional) annotation
        if (annotation.id < 0) {
            editLinkImageButton.visibility = View.GONE
        }

        return view
    }

    private fun loadLinkContentData(link: Link): ContentData {
        val docId = link.docId ?: return ContentData("")

        val subItemMetadata = subItemMetadataManager.findByDocId(docId) ?: return ContentData("")

        val contentItemId = subItemMetadata.itemId
        val subItemId = subItemMetadata.subitemId
        val paragraphAids = HashSet(link.getParagraphAids())

        val contentData = contentRenderer.generateHtmlTextForSubitemAndParagraphs(contentItemId, subItemId, paragraphAids)

        // because the verse data will be missing, create a citation for this link
        val title: String
        if (contentItemUtil.isItemDownloadedAndOpen(contentItemId)) {
            title = citationUtil.createCitationText(contentItemId, subItemId, paragraphAids)
            contentData.title = title
        } else {
            contentData.title = link.name
        }

        return contentData
    }

    private fun showLinkContentData(link: Link, contentData: ContentData, linkView: LinearLayout) {
        if (contentData.content.isEmpty()) {
            return
        }

        val referrer = if (inSidebar) Analytics.Referrer.RELATED_CONTENT_ANNOTATION_LINK else Analytics.Referrer.NOTEBOOK_ANNOTATION_LINK
        val onLinkClick = View.OnClickListener {
            val docId = link.docId ?: return@OnClickListener

            val metadata = subItemMetadataManager.findByDocId(docId) ?: return@OnClickListener

            var scrollToParagraphId: String = link.paragraphAid ?: ""
            // if there are multiple paragraphs, then grab the first one
            if (scrollToParagraphId.contains(",")) {
                scrollToParagraphId = scrollToParagraphId.split(",")[0]
            }

            internalIntents.showContentForLink(context as FragmentActivity, screenId, metadata.itemId, metadata.subitemId, scrollToParagraphId, arrayOf(link.paragraphAid), false, false, referrer)
        }

        annotationUiUtil.showContentData(context as FragmentActivity, screenId, linkView, contentData, true, onLinkClick, referrer)
    }

    private fun showNotebooks(showNotebooks: Boolean) {
        notebooksLayout.removeAllViews()

        if (!showNotebooks) {
            notebooksLayout.visibility = View.GONE
            return
        }

        val notebooks = annotation.notebooks
        if (notebooks.isEmpty()) {
            notebooksLayout.visibility = View.GONE
            return
        }

        notebooksLayout.visibility = View.VISIBLE

        for (notebook in notebooks) {
            val view = inflater.inflate(R.layout.annotation_item_notebook, null)
            val notebookTitleTextView = ButterKnife.findById<TextView>(view, R.id.notebookTitleTextView)
            notebookTitleTextView.text = notebook.name

            notebookTitleTextView.setOnClickListener { internalIntents.showNotebook(context as Activity, screenId, notebook.id) }

            notebooksLayout.addView(view)
        }
    }

    fun setMenuButton(menuButton: ImageView?) {
        if (menuButton == null) {
            return
        }

        menuButton.setOnClickListener { showMenu(menuButton, getMenuResourceId()) }
    }

    @MenuRes
    fun getMenuResourceId(): Int {
        val note = annotation.note
        return if (note != null && (note.title.isNullOrBlank().not() || note.content.isNullOrBlank().not())) {
            R.menu.menu_popup_annotation_with_note
        } else {
            R.menu.menu_popup_annotation
        }
    }

    private fun showMenu(anchor: View, @MenuRes menuResourceId: Int) {
        val menu = PopupMenu(anchor.context, anchor)
        val inflater = menu.menuInflater
        inflater.inflate(menuResourceId, menu.menu)

        // remove developer
        if (prefs.isDeveloperModeEnabled) {
            menu.menu.add("Show Annotation Info").setOnMenuItemClickListener {
                internalIntents.showAnnotationInfo(context as Activity, annotation.id)
                return@setOnMenuItemClickListener true
            }
        }

        menu.setOnMenuItemClickListener { item -> onOptionsItemSelected(item) }
        menu.show()
    }

    fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_popup_add_note, R.id.menu_popup_edit -> {
                internalIntents.editNote(context as Activity, screenId, annotation.id)
                return true
            }
            R.id.menu_popup_move_notebook -> {
                internalIntents.showNotebookSelection(context as Activity, screenId, annotation.id)
                return true
            }
            R.id.menu_popup_delete -> {
                onDeleteAnnotation()
                return true
            }
            R.id.menu_popup_share -> {
                onShareAnnotation()
                return true
            }
            else -> return false
        }
    }

    private fun onDeleteAnnotation() {
        MaterialDialog.Builder(context)
                .title(R.string.delete_annotation_title)
                .content(R.string.delete_annotation_message)
                .positiveText(R.string.delete)
                .negativeText(R.string.cancel)
                .onPositive { _, _ ->
                    annotationManager.trashById(annotation.id)
                    analyticsUtil.logContentAnnotated(annotation, Analytics.ChangeType.DELETE)
                }
                .show()
    }

    private fun onShareAnnotation() {
        shareUtil.showShareDialogForAnnotation(context as Activity, screenId, annotation)
    }

    fun getHighlight(): Highlight {
        val firstHighlight = annotation.getFirstHighlight()
        if (firstHighlight == null) {
            val clearHighlight = Highlight()
            clearHighlight.color = HighlightColor.CLEAR.htmlName
            clearHighlight.style = HighlightAnnotationStyle.FILL.stringValue
            return clearHighlight
        }

        return firstHighlight
    }

    fun setNoteTitleClickListener(clickListener: View.OnClickListener) {
        noteTitleTextView.setOnClickListener(clickListener)
    }

    fun setNoteMarkdownClickListener(clickListener: View.OnClickListener) {
        noteMarkdownTextView.setOnClickListener(clickListener)
    }

    fun setLastModifiedClickListener(clickListener: View.OnClickListener) {
        lastModifiedTextView.setOnClickListener(clickListener)
    }
}
