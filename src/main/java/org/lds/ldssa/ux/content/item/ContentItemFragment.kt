package org.lds.ldssa.ux.content.item

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import butterknife.OnClick
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_content.*
import kotlinx.android.synthetic.main.content_webview.*
import kotlinx.android.synthetic.main.fragment_content_item.*
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Boolean
import me.eugeniomarletti.extras.bundle.base.Int
import me.eugeniomarletti.extras.bundle.base.Long
import me.eugeniomarletti.extras.bundle.base.Serializable
import me.eugeniomarletti.extras.bundle.base.String
import me.eugeniomarletti.extras.bundle.base.StringArray
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.event.sync.AnnotationSyncFinishedEvent
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.ExternalIntents
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.model.GLContentContext
import org.lds.ldssa.model.database.content.subitem.SubItemManager
import org.lds.ldssa.model.database.types.HighlightAnnotationStyle
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.link.LinkManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.sync.AnnotationSync
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.ui.activity.HighlightPaletteActivity
import org.lds.ldssa.ui.activity.HighlightSelectionActivity
import org.lds.ldssa.ux.annotations.links.LinksActivity
import org.lds.ldssa.ui.activity.NoteActivity
import org.lds.ldssa.ui.adapter.LifecycleViewPager
import org.lds.ldssa.ui.dialog.DownloadMediaDialogFragment
import org.lds.ldssa.ui.fragment.BaseFragment
import org.lds.ldssa.ui.menu.CommonMenu
import org.lds.ldssa.ui.sidebar.SideBarView
import org.lds.ldssa.ui.web.ContentWebView
import org.lds.ldssa.util.AnalyticsUtil
import org.lds.ldssa.util.ContentRenderer
import org.lds.ldssa.util.HighlightColor
import org.lds.ldssa.util.ScreenUtil
import org.lds.ldssa.util.ShareUtil
import org.lds.ldssa.util.annotations.LinkUtil
import org.lds.ldssa.ux.annotations.notebookselection.NotebookSelectionActivity
import org.lds.ldssa.ux.annotations.tagselection.TagSelectionActivity
import org.lds.mobile.ui.util.LdsDrawableUtil
import pocketbus.Subscribe
import pocketbus.ThreadMode
import timber.log.Timber
import java.util.ArrayList
import java.util.Arrays
import javax.inject.Inject

@Suppress("EXPERIMENTAL_FEATURE_WARNING")
class ContentItemFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, LifecycleViewPager.FragmentLifecycle {
    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var externalIntents: ExternalIntents
    @Inject
    lateinit var shareUtil: ShareUtil
    @Inject
    lateinit var commonMenu: CommonMenu
    @Inject
    lateinit var annotationSync: AnnotationSync
    @Inject
    lateinit var annotationManager: AnnotationManager
    @Inject
    lateinit var subItemManager: SubItemManager
    @Inject
    lateinit var screenUtil: ScreenUtil
    @Inject
    lateinit var prefs: Prefs
    @Inject
    lateinit var contentRenderer: ContentRenderer
    @Inject
    lateinit var analytics: Analytics
    @Inject
    lateinit var analyticsUtil: AnalyticsUtil
    @Inject
    lateinit var linkUtil: LinkUtil
    @Inject
    lateinit var linkManager: LinkManager

    // Args
    private var contentItemId: Long = 0
    private var subItemId: Long = 0

    // SaveState
    private var rotationScrollToPosition = 0  // will override scrollToPosition
    private var totalMarkCount = 0
    private var markCurrentPosition = 0
    private var markType = MarkType.FIND_ON_PAGE

    enum class MarkType {
        FIND_ON_PAGE, MARK_ELEMENT
    }

    private var annotations = ArrayList<Annotation>()
    private var scrollAtTop = true // used for media FAB
    private var scrolledToRequestedPosition = false // don't re-scroll if it has already been done

    private var onAllContentRenderedListener: () -> Unit = {}
    var showHideMediaFabListener: (show: Boolean) -> Unit = {}
    var onContentSelectionStartListener: () -> Unit = {}
    var onContentSelectionEndListener: () -> Unit = {}

    // pass-through listeners from ContentWebView
    var onStickyTappedListener: (annotationId: Long) -> Unit = {}
    var toggleFullScreenModeListener: () -> Unit = {}
    var onRefTappedListener: (title: String, contentItemId: Long, subItemId: Long, refId: String) -> Unit = { _, _, _, _ -> }
    var onLinkTappedListener: (title: String, url: String, newScreen: Boolean) -> Unit = { _, _, _ -> }


    private var requestedWebViewBottomMargin = 0
    private var annotationsModelTs: Long = 0
    private var optionsMenu: Menu? = null
    private var startViewingTimeInMs: Long = 0 // Used to track how long the user viewed the content for analytics
    private var analyticsReferrer = Analytics.Referrer.UNKNOWN

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            with(ArgumentOptions) {
                contentItemId = it.contentItemId
                subItemId = it.subItemId
            }
        }
    }

    override fun getLayoutResourceId() = R.layout.fragment_content_item

    override fun onPostViewCreated() {
        // main content color under the webview
        context?.let {
            contentWebView.setBackgroundColor(LdsDrawableUtil.resolvedColorIntResourceId(it, R.attr.themeStyleColorMainContentBackground))
        }

        contentWebView.initView(screenId, contentItemId, subItemId)
        contentWebView.onAllContentRenderedListener = { _, _ -> onAllContentRenderedListener() }
        contentWebView.onContentSelectionStartListener = { onContentSelectionStart() }
        contentWebView.onContentSelectionEndListener = { onContentSelectionEnd() }
        contentWebView.onScrollPositionAidChangedListener = { aid ->
            onScrollPositionAidChanged(aid)
        }

        // pass-through
        contentWebView.onMultipleHighlightsTappedListener = { highlightInfo ->
            internalIntents.showMultipleAnnotationSelection(this, highlightInfo, ContentRequestCode.REQUEST_SELECT_HIGHLIGHT.ordinal)
        }
        contentWebView.onMultipleStickyTappedListener = { highlightInfo ->
            internalIntents.showMultipleAnnotationSelection(this, highlightInfo, ContentRequestCode.REQUEST_SELECT_STICKY.ordinal)
        }
        contentWebView.onStickyTappedListener = { annotationId -> onStickyTappedListener(annotationId) }
        contentWebView.toggleFullScreenModeListener = { toggleFullScreenModeListener() }
        contentWebView.onRefTappedListener = { title, contentItemId, subItemId, refId -> onRefTappedListener(title, contentItemId, subItemId, refId) }
        contentWebView.onLinkTappedListener = { title, url, newScreen -> onLinkTappedListener(title, url, newScreen) }

        contentWebView.onHighlightStyleClickListener = { annotation -> internalIntents.showHighlightPalette(this, screenId, annotation) }
        contentWebView.onHighlightNoteClickListener = { annotationId -> internalIntents.editNote(this, screenId, annotationId) }
        contentWebView.onHighlightTagClickListener = { annotationId -> internalIntents.showTagSelection(this, screenId, annotationId) }
        contentWebView.onHighlightLinkClickListener = { annotationId -> internalIntents.showLinks(this, screenId, annotationId) }
        contentWebView.onHighlightAddToNotebookClickListener = { annotationId -> internalIntents.showNotebookSelection(this, screenId, annotationId) }
        contentWebView.onShowShareDialogForContentSnippet = { contentItemId, subItemId, uri, citation, selectedAnnotationText -> shareUtil.onShowShareDialogForContentSnippet(activity, screenId, contentItemId, subItemId, uri, citation, selectedAnnotationText) }
        contentWebView.onShowSearchActivity = { selectedAnnotationText, contentContext -> internalIntents.showSearchActivity(activity, screenId, searchText = selectedAnnotationText, glContentContext = contentContext) }
        contentWebView.onGoogleSearch = { selectedAnnotationText -> externalIntents.googleSearch(activity, selectedAnnotationText) }
        contentWebView.onDownloadVideoDialog = { contentItemId, subItemId, video, mediaType -> DownloadMediaDialogFragment.newInstance(contentItemId, subItemId, video, mediaType).show(activity?.supportFragmentManager) }
        contentWebView.onShowVideoPlayer = {contentItemId, subItemId, videoId, referrer -> internalIntents.showVideoPlayer(activity, screenId, contentItemId, subItemId, videoId, referrer) }
        contentWebView.onShowImage = {imageDto -> internalIntents.showImage(activity, screenId, imageDto) }
        contentWebView.setFindListener { activeMatchOrdinal, numberOfMatches, isDoneCounting ->
            if (isDoneCounting) {
                onShowMarkTextItemCount(activeMatchOrdinal, numberOfMatches)
            }
        }
        setHasOptionsMenu(true)

        swipeRefreshLayout.setOnRefreshListener(this)

        setupMarkedTextSelectionLayout()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        restoreState(savedInstanceState)
        loadContentAndAnnotations()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentWebView.onScrollChangeListener = { _, _, scrollY, _, _ -> onScrollChange(scrollY) }
        contentWebView.onHtmlRenderingFinishedListener = { _, _ -> onHtmlRenderingFinished() }
    }

    override fun hasBusSubscriptions() = true

    override fun onStart() {
        super.onStart()

        addDisposable(annotationManager.tableChanges()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ -> loadAnnotations() })
    }

    override fun onDestroy() {
        // the WebView must be destroyed or it will stay in memory
        contentWebView?.destroy()

        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (scrolledToRequestedPosition) {
            rotationScrollToPosition = contentWebView.scrollPositionPercentage
        }
        saveState(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onPauseFragment() = logAnalytics()

    override fun onResumeFragment() {
        startViewingTimeInMs = System.currentTimeMillis()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // If the context is no longer available, there is no need to inflate the menu
        context?.let {
            // create actionbar menu
            commonMenu.inflateMenuPre(it, menu, inflater)
            inflater.inflate(R.menu.menu_content, menu)
            commonMenu.inflateMenuPost(it, menu, inflater)

            optionsMenu = menu

            if (prefs.isDeveloperModeEnabled) {
                inflater.inflate(R.menu.menu_content_dev, menu)
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        showHideMediaFabListener(scrollAtTop)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var result = true
        when (item.itemId) {
            R.id.menu_item_show_content_source -> internalIntents.showContentSource(activity, contentItemId, subItemId)
            R.id.menu_item_search -> internalIntents.showSearchActivity(activity, screenId = screenId, glContentContext = GLContentContext(contentItemId = contentItemId, subItemId = subItemId))
            R.id.menu_item_bookmark -> internalIntents.showBookmarksFromContent(this, screenId, contentItemId, subItemId, contentWebView.getTopVisibleParagraphAid())
            R.id.menu_item_share -> shareUtil.showShareDialogForContent(activity, screenId, contentItemId, subItemId)
            else -> {
                result = false
                val uri = subItemManager.findUriById(contentItemId, subItemId)
                optionsMenu?.let { menu ->
                    result = commonMenu.onOptionsItemSelected(
                            activity as BaseActivity, item, menu, screenUtil.getLanguageIdForScreen(screenId),
                            uri, GLContentContext(contentItemId = contentItemId, subItemId = subItemId))
                }
            }
        }
        return result
    }

    private fun onScrollChange(scrollY: Int) {
        if (scrollAtTop == (scrollY == 0)) {
            return
        }

        scrollAtTop = scrollY == 0
        showHideMediaFabListener(scrollAtTop)

        if (scrollAtTop) {
            // Because the contentWebView doesn't fire a scroll request for the related content if it is at the top we need to handle it here.
            onScrollPositionAidChanged(SideBarView.SCROLL_TO_TOP)
        }
    }

    private fun onHtmlRenderingFinished() {
        // User might have navigated away, return if the webview is no longer available
        contentWebView ?: return

        // content just finished loading... load the annotations
        contentWebView.onAnnotationsLoaded(annotations)

        var scrollToParagraphAid = ""
        var markParagraphAids = emptyArray<String>()
        var markTextSqliteOffsets = ""
        var findOnPageText = ""
        var scrollToPosition = 0

        arguments?.let {
            with(ArgumentOptions) {
                scrollToParagraphAid = it.scrollToParagraphAid

                val newList = it.markParagraphAids.filterNotNull().toList()

                markParagraphAids = newList.toTypedArray()
                markTextSqliteOffsets = it.markTextSqliteOffsets
                findOnPageText = it.findOnPageText
                scrollToPosition = it.scrollToPosition
            }
        }

        // Scroll to requested position... don't re-scroll if it has already been done
        if (!scrolledToRequestedPosition) {
            scrolledToRequestedPosition = true

            if (rotationScrollToPosition > 0) {
                contentWebView.scrollToPositionPercent(rotationScrollToPosition)
            } else if (scrollToParagraphAid.isNotBlank()) {
                // scroll to a paragraph or mark text
                if (markParagraphAids.isNotEmpty()) {
                    contentWebView.scrollToParagraphByAid(scrollToParagraphAid, markParagraphAids)
                } else {
                    contentWebView.scrollToParagraphByAid(scrollToParagraphAid)
                }
            } else if (markParagraphAids.isNotEmpty()) {
                contentWebView.setMarkedParagraphAids(Arrays.asList(*markParagraphAids))
            } else if (markTextSqliteOffsets.isNotEmpty()) {
                // scroll to first mark
                contentWebView.requestScrollToMark(0)

                onShowMarkTextItemCount(0, contentRenderer.markCount)
                markType = MarkType.MARK_ELEMENT
            } else if (findOnPageText.isNotEmpty()) {
                contentWebView.findAllAsync(findOnPageText)
                markType = MarkType.FIND_ON_PAGE
            } else if (scrollToPosition > 0) {
                contentWebView.scrollToPositionPercent(scrollToPosition)
            }
        }

        if (requestedWebViewBottomMargin != 0) {
            updateWebViewBottomMargin(requestedWebViewBottomMargin)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    @Subscribe(ThreadMode.MAIN)
    fun handle(event: AnnotationSyncFinishedEvent) {
        if (swipeRefreshLayout == null) {
            // fragment may have gone away...
            return
        }

        if (event.containsAnnotationAndNotebookChanges() || swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
            loadAnnotations()
        }
    }

    fun updateWebViewBottomMargin(extraBottomMargin: Int) {
        requestedWebViewBottomMargin = if (contentWebView != null) {
            contentWebView.updateBottomMargin(extraBottomMargin)
            0
        } else {
            extraBottomMargin
        }
    }

    private fun loadContentAndAnnotations() {
        // don't reload if everything is already loaded
        if (contentWebView.isAllContentLoaded) {
            return
        }

        // load content
        loadContent()

        // load related content (Related content, Annotations, etc)
        loadAnnotations()
    }

    private fun loadContent() {
        with(ArgumentOptions) {
            addJob(launch(cc.ui) {
                val contentData = run(coroutineContext + cc.commonPool) {
                    return@run contentRenderer.generateDefaultHtmlText(contentItemId, subItemId, arguments?.markTextSqliteOffsets, arguments?.markTextSqliteExactPhrase ?: false)
                }

                contentWebView.loadDataWithBaseURL(contentData)
            })
        }
    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = true
        annotationsModelTs = 0 // force all annotations to be redrawn.... (just in case highlights do not look correct on page)
        launch(cc.commonPool) {
            annotationSync.sync()
        }
    }

    private fun onContentSelectionStart() {
        launch(cc.ui) {
            // stop paging
            onContentSelectionStartListener()

            swipeRefreshLayout.isEnabled = false

            //Updates the DocID and Version for use in sharing
            contentWebView.getSelectedAnnotation()?.run {
                docId = contentWebView.contentDocId
                contentVersion = contentWebView.contentDocVersion
            }
        }
    }

    private fun onContentSelectionEnd() {
        launch(cc.ui) {
            swipeRefreshLayout.isEnabled = true

            // restore paging
            onContentSelectionEndListener()
        }
    }

    private fun logAnalytics() {
        if (contentItemId == 0L || startViewingTimeInMs == 0L) {
            return  // The fragment has not yet been initialized or was not viewed
        }

        var referrer: Analytics.Referrer? = null
        var sidebarPinned = Analytics.Value.UNKNOWN
        val activity = activity
        if (activity is ContentActivity) {
            referrer = activity.analyticsReferrer
            sidebarPinned = if (activity.isSidebarPinned()) Analytics.Value.PINNED else Analytics.Value.UNPINNED
        }

        // Default referrer to Unknown if we don't know how the user got to this content
        if (referrer == null) {
            referrer = Analytics.Referrer.UNKNOWN
        }

        with(Analytics.Attribute) {
            val attributes = hashMapOf<String, String>(
                    CONTENT_LANGUAGE to analyticsUtil.findContentLanguageByLanguageId(screenUtil.getLanguageIdForScreen(screenId)),
                    URI to analyticsUtil.findSubItemUriBySubItemId(contentItemId, subItemId),
                    ITEM_URI to analyticsUtil.findItemUriByContentItemId(contentItemId),
                    CONTENT_TYPE to Analytics.Value.TEXT,
                    TITLE to analyticsUtil.findTitleBySubItemId(contentItemId, subItemId),
                    CONTENT_GROUP to analyticsUtil.findContentGroupByContentItemId(contentItemId),
                    CONTENT_VERSION to analyticsUtil.findContentVersionByContentItemId(contentItemId),
                    PERCENTAGE_VIEWED to (contentWebView?.scrollViewedPercentage ?: 0).toString(),
                    SECONDS_VIEWED to Analytics.Value.millisToSeconds(System.currentTimeMillis() - startViewingTimeInMs),
                    REFERRER to referrer!!.value,
                    SOURCE_TYPE to Analytics.Value.LOCAL, // All text content is local
                    SIDEBAR_PIN_STATUS to sidebarPinned
            )
            analytics.postEvent(Analytics.Event.CONTENT_VIEWED, attributes)
        }
        startViewingTimeInMs = 0 // Reset to zero to prevent a second log
    }

    private fun onScrollPositionAidChanged(paragraphAid: String) {
        val activity = activity
        (activity as? ContentActivity)?.scrollSidebarToParagraphAid(paragraphAid)
    }

    private fun setupMarkedTextSelectionLayout() = activity?.let {
        LdsDrawableUtil.tintDrawableForTheme(it, markTextCloseButton.drawable, R.attr.themeStyleColorIconFindOnPage)
        LdsDrawableUtil.tintDrawableForTheme(it, markTextPrevButton.drawable, R.attr.themeStyleColorIconFindOnPage)
        LdsDrawableUtil.tintDrawableForTheme(it, markTextNextButton.drawable, R.attr.themeStyleColorIconFindOnPage)
    }

    @OnClick(R.id.markTextCloseButton)
    fun onMarkTextCloseButtonClick() {
        markedTextSelectionLayout.visibility = View.GONE

        when (markType) {
            MarkType.FIND_ON_PAGE -> contentWebView.clearMatches()
            MarkType.MARK_ELEMENT -> contentWebView.requestRemoveAllMarks()
        }

        totalMarkCount = 0
        markCurrentPosition = 0

        // if we are at the top of the page, show the media buttons
        if (contentWebView.scrollY == 0) {
            // must be set before running animation
            showHideMediaFabListener(true)
        }
    }

    @OnClick(R.id.markTextPrevButton)
    fun onMarkTextPrevClick() {
        when (markType) {
            MarkType.FIND_ON_PAGE -> contentWebView.findNext(false)
            MarkType.MARK_ELEMENT -> {
                if (markCurrentPosition <= 0) {
                    markCurrentPosition = totalMarkCount - 1
                } else {
                    markCurrentPosition--
                }

                updateMarkTextPositionText()
                updateMarkElementTextPosition()
            }
        }
    }

    @OnClick(R.id.markTextNextButton)
    fun onMarkTextNextClick() {
        when (markType) {
            MarkType.FIND_ON_PAGE -> contentWebView.findNext(true)
            MarkType.MARK_ELEMENT -> {
                if (markCurrentPosition >= totalMarkCount - 1) {
                    markCurrentPosition = 0
                } else {
                    markCurrentPosition++
                }

                updateMarkTextPositionText()
                updateMarkElementTextPosition()
            }
        }
    }

    private fun updateMarkTextPositionText() {
        if (totalMarkCount > 0) {
            markTextPositionTextView.text = getString(R.string.marked_text_position, markCurrentPosition + 1, totalMarkCount) // +1 so it is human readable
        } else {
            markTextPositionTextView.setText(R.string.marked_text_position_no_results)
        }
    }

    private fun updateMarkElementTextPosition() {
        contentWebView.requestScrollToMark(markCurrentPosition)
    }

    val isMarkTextPopupVisible: Boolean
        get() = markedTextSelectionLayout != null && markedTextSelectionLayout.visibility == View.VISIBLE

    private fun onShowMarkTextItemCount(currentPosition: Int, totalMarks: Int) {
        // set index positions
        totalMarkCount = totalMarks
        markCurrentPosition = currentPosition

        showHideMediaFabListener(false)
        markedTextSelectionLayout.visibility = View.VISIBLE
        updateMarkTextPositionText()
    }

    /**
     * A task to load the annotations associated with this content and the inverse (bi-directional) links that point to this content
     */
    private fun loadAnnotations() {
        Timber.d("========== loadAnnotations [subItemId: %d] ==========", subItemId)
        if (!annotationManager.isUpdatedSince(annotationsModelTs)) {
            // no changes... return
            return
        }

        if (contentWebView.isInSelectMode()) {
            // don't interrupt changes being made
            return
        }

        annotationsModelTs = annotationManager.getLastTableModifiedTs()

        addJob(launch(cc.ui) {
            this@ContentItemFragment.annotations = run(coroutineContext + cc.commonPool) {
                val annotationList = ArrayList<Annotation>()

                val docId = subItemManager.findDocIdById(contentItemId, subItemId) ?: return@run annotationList

                annotationList.addAll(annotationManager.findAllFullActiveByDocId(docId))
                val inverseLinkList = linkManager.findAllByDocId(docId)

                // Create the inverse links (bi-directional) and add them to the annotations list
                inverseLinkList.forEach { link ->
                    val inverseAnnotation = linkUtil.createInverseLinkAnnotation(link)
                    if (inverseAnnotation != null) {
                        annotationList.add(inverseAnnotation)
                    }
                }

                return@run annotationList
            }

            // if the base html page is already loaded, then go ahead and place the related content
            if (contentWebView?.isHtmlContentLoaded == true) {
                contentWebView.onAnnotationsLoaded(annotations)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultIntent: Intent?) {
        if (resultCode != AppCompatActivity.RESULT_OK || resultIntent == null) {
            super.onActivityResult(requestCode, resultCode, resultIntent)
            return
        }

        when (ContentRequestCode.getCode(requestCode)) {
            ContentRequestCode.REQUEST_BOOKMARK -> loadAnnotations()
            ContentRequestCode.REQUEST_NOTE -> {
                analyticsReferrer = Analytics.Referrer.EXIT_NOTE_MODE
                onAnnotationUpdated(resultIntent.getLongExtra(NoteActivity.EXTRA_ANNOTATION_ID, 0L))
            }
            ContentRequestCode.REQUEST_TAG -> {
                analyticsReferrer = Analytics.Referrer.EXIT_TAG_MODE
                with(TagSelectionActivity.IntentOptions) {
                    onAnnotationUpdated(resultIntent.annotationId)
                }
            }
            ContentRequestCode.REQUEST_LINK -> {
                analyticsReferrer = Analytics.Referrer.EXIT_LINK_MODE
                onAnnotationUpdated(resultIntent.getLongExtra(LinksActivity.EXTRA_ANNOTATION_ID, 0L))
            }
            ContentRequestCode.REQUEST_NOTEBOOK_SELECTION -> {
                analyticsReferrer = Analytics.Referrer.EXIT_NOTEBOOK_MODE
                with(NotebookSelectionActivity.IntentOptions) {
                    onAnnotationUpdated(resultIntent.annotationId)
                }
            }
            ContentRequestCode.REQUEST_HIGHLIGHT_PALETTE -> onHighlightColorStyleUpdated(resultIntent)
            ContentRequestCode.REQUEST_SELECT_HIGHLIGHT -> onHighlightSelectedFromMultiple(resultIntent)
            ContentRequestCode.REQUEST_SELECT_STICKY -> onStickySelectedFromMultiple(resultIntent)
            else -> super.onActivityResult(requestCode, resultCode, resultIntent)
        }
    }

    private fun onHighlightColorStyleUpdated(resultIntent: Intent) {
        val annotationId = resultIntent.getLongExtra(HighlightPaletteActivity.EXTRA_RESULT_ANNOTATION_ID, 0)
        val color = resultIntent.getSerializableExtra(HighlightPaletteActivity.EXTRA_RESULT_HIGHLIGHT_COLOR) as HighlightColor
        val style = resultIntent.getSerializableExtra(HighlightPaletteActivity.EXTRA_RESULT_HIGHLIGHT_STYLE) as HighlightAnnotationStyle

        contentWebView.updateHighlightColorStyle(annotationId, color, style)
    }

    private fun onAnnotationUpdated(annotationId: Long) {
        contentWebView.updateHighlight(annotationId, true)
        contentSideDrawer?.updateAnnotation(annotationId)
    }

    private fun onHighlightSelectedFromMultiple(resultIntent: Intent) {
        with(HighlightSelectionActivity.ResultIntentOptions) {
            val selectedUniqueId = resultIntent.selectedUniqueId
            if (selectedUniqueId.isNotBlank()) {
                contentWebView.selectHighlight(selectedUniqueId)
            }
        }
    }

    private fun onStickySelectedFromMultiple(resultIntent: Intent) {
        with(HighlightSelectionActivity.ResultIntentOptions) {
            val selectedUniqueId = resultIntent.selectedUniqueId
            if (selectedUniqueId.isNotBlank()) {
                if (Annotation.isInverseLinkAnnotation(selectedUniqueId)) {
                    val annotationId = annotationManager.findIdByUniqueId(Annotation.getInverseUniqueId(selectedUniqueId))
                    val id = Annotation.getInverseAnnotationId(annotationId)
                    onStickyTappedListener(id)
                } else {
                    val id = annotationManager.findIdByUniqueId(selectedUniqueId)
                    onStickyTappedListener(id)
                }
            }
        }
    }

    fun getContentWebView(): ContentWebView? {
        return contentWebView
    }

    private fun restoreState(bundle: Bundle?) {
        bundle ?: return

        with(SaveStateOptions) {
            rotationScrollToPosition = bundle.rotationScrollToPosition!!
            totalMarkCount = bundle.totalMarkCount!!
            markCurrentPosition = bundle.markCurrentPosition!!
            markType = bundle.markType
        }
    }

    private fun saveState(bundle: Bundle?) {
        bundle ?: return

        with(SaveStateOptions) {
            bundle.rotationScrollToPosition = rotationScrollToPosition
            bundle.totalMarkCount = totalMarkCount
            bundle.markCurrentPosition = markCurrentPosition
            bundle.markType = markType
        }
    }

    object SaveStateOptions {
        var Bundle.rotationScrollToPosition by BundleExtra.Int()
        var Bundle.totalMarkCount by BundleExtra.Int()
        var Bundle.markCurrentPosition by BundleExtra.Int()
        var Bundle.markType by BundleExtra.Serializable(defaultValue = MarkType.FIND_ON_PAGE)
    }

    object ArgumentOptions {
        // required
        var Bundle.contentItemId by BundleExtra.Long(defaultValue = 0L)
        var Bundle.subItemId by BundleExtra.Long(defaultValue = 0L)
        var Bundle.viewPagerPosition by BundleExtra.Int(defaultValue = 0)

        // not required
        var Bundle.scrollToParagraphAid by BundleExtra.String(defaultValue = "")
        var Bundle.findOnPageText by BundleExtra.String(defaultValue = "")
        var Bundle.markTextSqliteOffsets by BundleExtra.String(defaultValue = "")
        var Bundle.markTextSqliteExactPhrase by BundleExtra.Boolean(defaultValue = false)
        var Bundle.markParagraphAids by BundleExtra.StringArray(defaultValue = emptyArray())
        var Bundle.scrollToPosition by BundleExtra.Int(defaultValue = 0)
    }

    companion object {
        fun newInstance(screenId: Long, contentItemId: Long, subItemId: Long,
                        viewPagerPosition: Int,
                        scrollToParagraphAid: String = "",
                        scrollToPosition: Int = 0,
                        markParagraphAids: Array<String?> = emptyArray(),
                        findOnPageText: String = "",
                        markTextSqliteOffsets: String = "",
                        markTextSqliteExactPhrase: Boolean = false
        ): ContentItemFragment {
            val fragment = ContentItemFragment()

            with(ArgumentOptions) {
                val args = getBaseBundle(screenId)
                args.contentItemId = contentItemId
                args.subItemId = subItemId
                args.scrollToParagraphAid = scrollToParagraphAid
                args.scrollToPosition = scrollToPosition
                args.markParagraphAids = markParagraphAids
                args.findOnPageText = findOnPageText
                args.markTextSqliteOffsets = markTextSqliteOffsets
                args.markTextSqliteExactPhrase = markTextSqliteExactPhrase
                args.viewPagerPosition = viewPagerPosition
                fragment.arguments = args
            }

            return fragment
        }
    }
}
