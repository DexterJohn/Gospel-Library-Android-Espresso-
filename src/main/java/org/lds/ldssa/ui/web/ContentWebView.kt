package org.lds.ldssa.ui.web

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.util.AttributeSet
import android.view.ActionMode
import android.view.MotionEvent
import android.view.View
import android.view.accessibility.AccessibilityManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import com.afollestad.materialdialogs.MaterialDialog
import com.devbrackets.android.playlistcore.manager.BasePlaylistManager
import kotlinx.coroutines.experimental.launch
import org.lds.ldssa.BuildConfig
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.ExternalIntents
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.media.exomedia.data.MediaItem
import org.lds.ldssa.media.exomedia.data.VideoItem
import org.lds.ldssa.media.exomedia.manager.PlaylistManager
import org.lds.ldssa.model.GLContentContext
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataManager
import org.lds.ldssa.model.database.content.subitem.SubItemManager
import org.lds.ldssa.model.database.types.HighlightAnnotationStyle
import org.lds.ldssa.model.database.types.ItemMediaType
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.webview.content.dto.DtoHighlightInfo
import org.lds.ldssa.model.webview.content.dto.DtoImage
import org.lds.ldssa.model.webview.content.dto.DtoImages
import org.lds.ldssa.model.webview.content.dto.DtoInlineVideo
import org.lds.ldssa.model.webview.content.dto.DtoInlineVideos
import org.lds.ldssa.model.webview.content.dto.DtoParagraphAidPosition
import org.lds.ldssa.model.webview.content.dto.DtoWebAnnotationProperties
import org.lds.ldssa.model.webview.content.dto.DtoWebTouch
import org.lds.ldssa.ui.animation.WebViewSmoothScrollAnimation
import org.lds.ldssa.ui.widget.ContentHighlightPopupMenu
import org.lds.ldssa.util.AnalyticsUtil
import org.lds.ldssa.util.CitationUtil
import org.lds.ldssa.util.HighlightColor
import org.lds.ldssa.util.ImageUtil
import org.lds.ldssa.util.ShareUtil
import org.lds.ldssa.util.TextHandleUtil
import org.lds.ldssa.util.ToastUtil
import org.lds.ldssa.util.UriUtil
import org.lds.ldssa.util.annotations.AnnotationListUtil
import org.lds.ldssa.util.annotations.HighlightUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.util.LdsTimeUtil
import org.lds.mobile.util.LdsUiUtil
import pocketbus.Bus
import timber.log.Timber
import java.net.URI
import java.net.URISyntaxException
import java.util.ArrayList
import java.util.HashMap
import javax.inject.Inject

/**
 * This class contains webView enhancements for displaying the content in the main content area
 */
class ContentWebView: WebView {

    @Inject
    lateinit var bus: Bus
    @Inject
    lateinit var cc: CoroutineContextProvider
    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var externalIntents: ExternalIntents
    @Inject
    lateinit var annotationManager: AnnotationManager
    @Inject
    lateinit var annotationListUtil: AnnotationListUtil
    @Inject
    lateinit var prefs: Prefs
    @Inject
    lateinit var uiUtil: LdsUiUtil
    @Inject
    lateinit var textHandleUtil: TextHandleUtil
    @Inject
    lateinit var contentJsInterface: ContentJsInterface
    @Inject
    lateinit var contentJsInvoker: ContentJsInvoker
    @Inject
    lateinit var timeUtil: LdsTimeUtil
    @Inject
    lateinit var subItemManager: SubItemManager
    @Inject
    lateinit var paragraphMetadataManager: ParagraphMetadataManager
    @Inject
    lateinit var highlightUtil: HighlightUtil
    @Inject
    lateinit var analyticsUtil: AnalyticsUtil
    @Inject
    lateinit var uriUtil: UriUtil
    @Inject
    lateinit var imageUtil: ImageUtil
    @Inject
    lateinit var citationUtil: CitationUtil
    @Inject
    lateinit var shareUtil: ShareUtil
    @Inject
    lateinit var playlistManager: PlaylistManager
    @Inject
    lateinit var itemManager: ItemManager
    @Inject
    lateinit var toastUtil: ToastUtil


    // =========== Listeners ===========
    var onHtmlRenderingFinishedListener: (contentItemId: Long, subItemId: Long) -> Unit = { _, _ -> }
    var onAllContentRenderedListener: (contentItemId: Long, subItemId: Long) -> Unit = { _, _ -> }
    var onScrollChangeListener: (webView: ContentWebView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) -> Unit = { _, _, _, _, _ -> }


    var onMultipleHighlightsTappedListener: (List<DtoHighlightInfo>) -> Unit = {}
    var onStickyTappedListener: (annotationId: Long) -> Unit = {}
    var onMultipleStickyTappedListener: (List<DtoHighlightInfo>) -> Unit = {}
    var onContentSelectionStartListener: () -> Unit = {}
    var onContentSelectionEndListener: () -> Unit = {}

    var onParagraphMarkedListener: (aid: String) -> Unit = {}
    var onParagraphUnMarkedListener: (aid: String) -> Unit = {}

    var onScrollPositionAidChangedListener: (aid: String) -> Unit = {}

    var onHighlightStyleClickListener: (annotation: Annotation) -> Unit = { _ -> }
    var onHighlightNoteClickListener: (annotationId: Long) -> Unit = { _ -> }
    var onHighlightTagClickListener: (annotationId: Long) -> Unit = { _ -> }
    var onHighlightLinkClickListener: (annotationId: Long) -> Unit = { _ -> }
    var onHighlightAddToNotebookClickListener: (annotationId: Long) -> Unit = { _ -> }
    var onShowShareDialogForContentSnippet: (contentItemId: Long, subItemId: Long, uri: String, citation: String?, selectedAnnotationText: String?) -> Unit = { _, _, _, _, _ -> }
    var onShowSearchActivity: (selectedAnnotationText: String, contentContext: GLContentContext) -> Unit = { _, _ -> }
    var onGoogleSearch: (selectedAnnotationText: String) -> Unit = { _ -> }
    var onDownloadVideoDialog: (contentItemId: Long, subItemId: Long, video: DtoInlineVideo?, mediaType: ItemMediaType) -> Unit = { _, _, _, _ -> }
    var onShowVideoPlayer: (contentItemId: Long, subItemId: Long, videoId: String?, referrer: Analytics.Referrer) -> Unit = { _, _, _, _ -> }
    var onShowImage: (image: DtoImage) -> Unit = { _ -> }

    var onLinkTappedListener: (title: String, url: String, newScreen: Boolean) -> Unit = { _, _, _ -> }
    var onRefTappedListener: (title: String, contentItemId: Long, subItemId: Long, refId: String) -> Unit = { _, _, _, _ -> }
    var toggleFullScreenModeListener: () -> Unit = {}

    // =========== Listeners END ===========

    lateinit var leftHandleImageView: ImageView
    lateinit var rightHandleImageView: ImageView

    private var screenId: Long = 0

    private lateinit var touchListener: ContentTouchListener

    // === CACHE ===
    // location of highlight rects (used for highlight selection)
    private val annotationPropertiesMap = HashMap<String, DtoWebAnnotationProperties>() // <uniqueId, details>

    // screen y offsets for paragraphs with paragraphAids (should be in order from top to bottom) (used to identify bookmark location)
    private val paragraphAidLocations = ArrayList<DtoParagraphAidPosition>()

    var inlineVideos: DtoInlineVideos = DtoInlineVideos()
    var inlineImages: DtoImages = DtoImages()

    private var annotations: MutableList<Annotation> = ArrayList() // list of loaded annotations
    private var selectedAnnotation: Annotation? = null // currently selected annotation
    var selectedAnnotationText = ""
    private val markedParagraphs = ArrayList<String>() // list of paragraphs that are selected/marked

    // page state
    var isHtmlContentLoaded = false
        private set
    private var annotationsLoaded = false
    private var yScroll = 0
    private var linkingMode = false
    private var annotatingEnabled = true

    // Actual content
    private var contentItemId = 0L
    private var subItemId = 0L

    // annotation data cache
    var contentDocId: String? = null
        private set
    var contentDocVersion: Int = 0
        private set
    private var contentUri = ""
    private var contentRendered = false
    private var requestedMarginDelta = 0

    // Scan listener
    private val scrollDelayHandler = Handler()

    private lateinit var highlightPopupMenu: ContentHighlightPopupMenu

    init {
        Injector.get().inject(this)
    }

    constructor(context: Context): super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        isHapticFeedbackEnabled = false
        configureWebView()

        // Listeners
        this.touchListener = ContentTouchListener(this)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configureWebView() {
        var allowJavascript = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            when (BuildConfig.BUILD_TYPE) {
                "debug", "alpha" -> WebView.setWebContentsDebuggingEnabled(true)
            }
        }

        val accessibilityManager = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        if (accessibilityManager.isEnabled && accessibilityManager.isTouchExplorationEnabled) {
            allowJavascript = false
        }

        val webSettings = settings
        webSettings.javaScriptEnabled = allowJavascript
        webSettings.allowFileAccess = true
        webSettings.saveFormData = false
        webSettings.setAppCacheEnabled(true)
        webSettings.loadsImagesAutomatically = true
        webSettings.blockNetworkImage = false
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        webSettings.setSupportZoom(false)
        webSettings.builtInZoomControls = false

        setOnLongClickListener { true } // suppress built-in long click selection and menus
        isHorizontalScrollBarEnabled = false
        scrollBarStyle = WebView.SCROLLBARS_INSIDE_OVERLAY

        setBackgroundColor(Color.argb(1, 0, 0, 0))
        contentJsInterface.init(this)
        addJavascriptInterface(contentJsInterface, "glContentInterface")

        //        setWebChromeClient(chromeClient);
        // By overriding the web view client we can intercept URL clicks.
        webViewClient = ContentWebViewClient()
    }

    @SuppressLint("AddJavascriptInterface")
    fun initView(screenId: Long, contentItemId: Long, subItemId: Long, linkingMode: Boolean = false) {
        this.screenId = screenId
        this.linkingMode = linkingMode
        if (linkingMode) {
            this.annotatingEnabled = false
        }
        setContentItemAndSubItemId(contentItemId, subItemId)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        // support PREVIEW in Android Studio
        if (isInEditMode) {
            return
        }

        leftHandleImageView = findViewById(R.id.leftHandleImageView)
        rightHandleImageView = findViewById(R.id.rightHandleImageView)

        highlightPopupMenu = ContentHighlightPopupMenu(this@ContentWebView, imageUtil).apply {
            onHighlightMarkListener = { onHighlightMarkClick() }
            onHighlightStyleListener = { onHighlightStyleClick() }
            onHighlightNoteListener = { onHighlightNoteClick() }
            onHighlightTagListener = { onHighlightTagClick() }
            onHighlightAddToListener = { onHighlightAddToNotebookClick() }
            onHighlightLinkListener = { onHighlightLinkClick() }
            onHighlightCopyListener = { onHighlightCopyClick() }
            onHighlightShareListener = { onHighlightShareClick() }
            onHighlightSearchListener = { onHighlightSearchClick() }
            onHighlightDefineListener = { onHighlightDefineClick() }
            onHighlightDeleteListener = { onHighlightDeleteClick() }
        }
    }

    fun updateBottomMargin(deltaMargin: Int) {
        if (contentRendered) {
            contentJsInvoker.updateBottomMargin(this@ContentWebView, deltaMargin)
            requestedMarginDelta = 0
        } else {
            requestedMarginDelta = deltaMargin
        }
    }

    /**
     * Returns the current scroll position of the WebView as a
     * percentage [0-100] of the content.

     * @return The percentage [0-{@value #MAX_SCROLL_PERCENT}] the top of the page is at
     */
    val scrollPositionPercentage: Int
        get() {
            val y = yScroll.toFloat()
            val height = contentHeight.toFloat()

            return (y / height * MAX_SCROLL_PERCENT).toInt()
        }

    /**
     * Returns the current viewed percentage (scrolled position plus visible content) of the content as a decimal value
     * @return The percentage of the page that has been scrolled to and is visible
     */
    val scrollViewedPercentage: Double
        get() = (computeVerticalScrollOffset().toDouble() + computeVerticalScrollExtent()) / computeVerticalScrollRange()

    /**
     * Scrolls to the position represented by the `positionPercent`

     * @param positionPercent The percentage [0-{@value #MAX_SCROLL_PERCENT}] the top of the page should be scrolled to
     */
    fun scrollToPositionPercent(positionPercent: Int) {
        val scrollPercent = positionPercent.toFloat() / MAX_SCROLL_PERCENT
        val scrollY = (scrollPercent * contentHeight).toInt()

        startAnimation(WebViewSmoothScrollAnimation(0, scrollY, this))
    }

    fun getAnnotationForUniqueId(uniqueId: String): Annotation? {
        return annotationListUtil.findAnnotationByUniqueId(annotations, uniqueId)
    }

    /**
     * Called when the scroll position of a view changes.
     *
     * @param scrollX Current horizontal scroll origin.
     * @param scrollY Current vertical scroll origin.
     * @param oldScrollX Previous horizontal scroll origin.
     * @param oldScrollY Previous vertical scroll origin.
     */
    override fun onScrollChanged(scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        super.onScrollChanged(scrollX, scrollY, oldScrollX, oldScrollY)
        onScrollChangeListener(this, scrollX, scrollY, oldScrollX, oldScrollY)

        if (selectedAnnotation != null) {
            hideHighlightPopup()
        }

        // Start the scanner to determine the aid visible at the top of the webview
        scrollDelayHandler.removeCallbacksAndMessages(null)

        // A Runnable to prevent multiple scans for the aid at the top of the webview.
        // This is a Runnable so that we can delay scanning for the aid to avoid large amounts of work while the user is still scrolling
        scrollDelayHandler.postDelayed({
            if (selectedAnnotation != null) {
                showHighlightPopup()
            }

            contentJsInvoker.requestAidFromScrollPosition(this@ContentWebView, this@ContentWebView.width / SCROLL_SCAN_X_DIVISOR, SCROLL_SCAN_Y_POSITION)
        }, SCROLL_SCAN_DELAY.toLong())

        yScroll = scrollY
    }

    override fun startActionMode(callback: android.view.ActionMode.Callback): android.view.ActionMode? {
        // this is to prevent the native text selection ActionMode
        return null
    }

    override fun startActionMode(callback: ActionMode.Callback, type: Int): ActionMode? {
        // this is to prevent the native text selection ActionMode
        return null
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val widthChanged = w != oldw && oldw != 0
        if (widthChanged) {
            processAllAnnotationsForDisplay()
        }
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR1 || Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN_MR2) {
            // Android 4.2 and 4.3 devices cannot select footnotes until the user does an initial scroll in the content
            // Fix from https://stackoverflow.com/questions/12090899/android-webview-jellybean-should-not-happen-no-rect-based-test-nodes-found
            onScrollChanged(getScrollX(), getScrollY(), getScrollX(), getScrollY())
        }

        return touchListener.onTouchEvent(ev) || super.onTouchEvent(ev)
    }

    fun isInSelectMode() = leftHandleImageView.visibility == View.VISIBLE

    fun startSelectMode(leftHandleX: Int, leftHandleY: Int, rightHandleX: Int, rightHandleY: Int, highlightTopY: Int, highlightBottomY: Int) {
        launch(cc.ui) {
            textHandleUtil.updateHandle(leftHandleImageView, leftHandleX, leftHandleY, View.VISIBLE)
            textHandleUtil.updateHandle(rightHandleImageView, rightHandleX, rightHandleY, View.VISIBLE)

            highlightPopupMenu.show(highlightTopY, highlightBottomY, leftHandleX, leftHandleY, rightHandleX, rightHandleY)
        }

        onContentSelectionStartListener()
    }

    private fun endSelectMode() {
        onContentSelectionEndListener()

        launch(cc.ui) {
            stopShowingHandles()
            hideHighlightPopup()

            selectedAnnotation?.let { annotation ->
                if (annotation.isNewRecord) {
                    deleteAnnotation(annotation)
                }

                // no annotation is selected... set the value to null
                setSelectedAnnotation(null, "")
            }
        }
    }

    /**
     * Add annotation to List of Annotations stored in ContentWebView
     */
    private fun addAnnotation(annotation: Annotation?) {
        if (annotation == null) {
            return
        }

        // first remove this annotation if it's already in the list (don't call removeAnnotation... as it will remove the highlight
        removeAnnotation(annotation)

        annotations.add(annotation)
    }

    /**
     * Removes annotation from List of Annotations stored in ContentWebView (will not change the UI)
     */
    private fun removeAnnotation(annotation: Annotation?) {
        if (annotation == null) {
            return
        }

        // if the annotation
        val uniqueId = annotation.uniqueId
        if (!annotationListUtil.annotationExistsInList(annotations, uniqueId)) {
            return
        }

        // * remove the annotation from local ArrayList
        annotationListUtil.removeAnnotationFromListByUniqueId(annotations, uniqueId)
    }

    /**
     * Delete (mark as trashed... deleted later) annotation int the database
     */
    private fun deleteAnnotation(annotation: Annotation?) {
        if (annotation == null) {
            return
        }

        // remove the annotation from local ArrayList
        removeAnnotation(annotation)

        // cleanup other cache items
        annotationPropertiesMap.remove(annotation.uniqueId)

        // remove any UI elements
        contentJsInvoker.removeAnnotationDivs(this, annotation.uniqueId)

        // if this is the selected annotation....
        if (selectedAnnotation?.compare(annotation) == true) {
            setSelectedAnnotation(null, "")
        }

        // trash the annotation record from the database (if it exists)
        if (!annotation.isNewRecord) {
            annotationManager.trashById(annotation.id)
            logAnalytics(annotation, Analytics.ChangeType.DELETE)
        }
    }

    private fun stopShowingHandles() {
        textHandleUtil.updateHandle(leftHandleImageView, 0, 0, View.GONE)
        textHandleUtil.updateHandle(rightHandleImageView, 0, 0, View.GONE)
        touchListener.clearDraggingHandleFlags()
    }

    fun setSelectedAnnotation(annotation: Annotation?, selectedText: String) {
        if (annotation == null) {
            selectedAnnotation = null
            selectedAnnotationText = ""
            endSelectMode()
        } else {
            selectedAnnotation = annotation
            selectedAnnotationText = selectedText
        }
    }

    fun getSelectedAnnotation(): Annotation? {
        return selectedAnnotation
    }

    private fun processAllAnnotationsForDisplay() {
        // remove all annotations from webview
        contentJsInvoker.clearAnnotations(this)

        // show selected annotation if it is not in the list of annotations
        var selectedAnnotationUniqueId = ""
        selectedAnnotation?.let { annotation ->
            if (annotation.shouldDisplay() && !annotations.contains(annotation)) {
                selectedAnnotationUniqueId = annotation.uniqueId
                showAnnotation(annotation, true)
            }
        }

        // show all annotations
        for (annotation in annotations) {
            // don't override the selected annotation (it may be in the middle of edit)
            val annotationAlreadyVisible = selectedAnnotationUniqueId == annotation.uniqueId

            if (annotation.shouldDisplay() && !annotationAlreadyVisible) {
                showAnnotation(annotation, false)
            }
        }
    }

    /**
     * Try and select an annotation for the given x, y position
     *
     * @return true if the single tap is handled
     */
    private fun selectHighlight(annotationsInTouchZoneList: List<DtoWebAnnotationProperties>): Boolean {
        if (annotationsInTouchZoneList.size == 1) {
            // SINGLE HIGHLIGHT
            return selectHighlight(annotationsInTouchZoneList[0].uniqueId)
        } else if (annotationsInTouchZoneList.size > 1) {
            // MULTIPLE HIGHLIGHTS
            contentJsInvoker.requestAnnotationPropertiesForAnnotationList(this, annotations, annotationsInTouchZoneList, false)
            return true
        }

        return false
    }

    fun selectHighlight(uniqueId: String): Boolean {
        Timber.d("Highlight annotation [%s] clicked", uniqueId)

        // find the annotation and then select it
        for (annotation in annotations) {
            if (annotation.uniqueId == uniqueId) {
                selectHighlight(annotation)
                return true
            }
        }

        return false
    }

    private fun selectHighlight(annotation: Annotation?) {
        if (annotation != null) {
            for (i in annotations.indices) {
                val existing = annotations[i]
                if (existing.compare(annotation)) {
                    annotations[i] = annotation
                    break
                }
            }

            setSelectedAnnotation(annotation, "") // showAnnotation will retrieve the text
            showAnnotation(annotation, true)
        } else {
            endSelectMode()
        }
    }

    fun updateHighlight(annotationId: Long, selected: Boolean) {
        val updatedAnnotation = annotationManager.findFullAnnotationByRowId(annotationId) ?: return

        val existingAnnotation = annotationListUtil.findAnnotationByUniqueId(annotations, updatedAnnotation.uniqueId) ?: return

        if (updatedAnnotation.lastModified == existingAnnotation.lastModified) {
            // nothing changed, return
            return
        }

        if (selected) {
            // set the new selected Annotation (if there is an actively selected annotation)
            selectedAnnotation?.let { annotation ->
                if (annotation.uniqueId == updatedAnnotation.uniqueId) {
                    setSelectedAnnotation(updatedAnnotation, "") // selectedText will be updated by contentJsInvoker.showHighlight(...)
                }
            }
        }

        annotationListUtil.replaceAnnotation(annotations, updatedAnnotation)

        // Updates the UI to represent the new color
        contentJsInvoker.showHighlight(this, updatedAnnotation, selected)
    }

    /**
     * Called from pallet Activity
     */
    fun updateHighlightColorStyle(annotationId: Long, color: HighlightColor, style: HighlightAnnotationStyle) {
        selectedAnnotation?.let { annotation ->
            if (annotationId != annotation.id) {
                return
            }

            annotation.setAllHighlightColors(color, style)

            // Updates the UI to represent the new color
            contentJsInvoker.showHighlight(this, annotation, true)
        }
    }

    private fun selectSticky(selectedAnnotationProperties: List<DtoWebAnnotationProperties>) {
        if (selectedAnnotationProperties.size == 1) {
            val annotation = annotationListUtil.findAnnotationByUniqueId(annotations, selectedAnnotationProperties[0].uniqueId)
            if (annotation != null) {
                onStickyTappedListener(annotation.id)
            }
        } else if (selectedAnnotationProperties.size > 1) {
            // overlapping stickies
            contentJsInvoker.requestAnnotationPropertiesForAnnotationList(this, annotations, selectedAnnotationProperties, true)
        }
    }

    private fun showAnnotation(annotation: Annotation, selectAnnotation: Boolean) {
        if (annotation.isBookmark()) {
            contentJsInvoker.showBookmarkIndicator(this, annotation)
        } else {
            // Updates the UI to represent the new color
            contentJsInvoker.showHighlight(this, annotation, selectAnnotation)
        }
    }

    override fun toString(): String {
        return Integer.toHexString(System.identityHashCode(this)) + " " + super.toString().replace(javaClass.canonicalName, javaClass.simpleName)
    }

    fun updateAnnotationProperties(annotationProperties: DtoWebAnnotationProperties) {
        annotationPropertiesMap.put(annotationProperties.uniqueId, annotationProperties)
    }

    private fun getAnnotationPropertiesForPosition(x: Int, y: Int): List<DtoWebAnnotationProperties> {
        val scrollYOffset = getPageYOffset()

        val annotationPropertiesList = ArrayList<DtoWebAnnotationProperties>()

        for (annotationProperties in annotationPropertiesMap.values) {
            annotationProperties.rects
                    .filter { it.inBounds(x, y + scrollYOffset) && !Annotation.isInverseLinkAnnotation(annotationProperties.uniqueId) }
                    .forEach { annotationPropertiesList.add(annotationProperties) }
        }

        return annotationPropertiesList
    }

    private fun getAnnotationPropertiesForUniqueIds(uniqueIds: List<String>): List<DtoWebAnnotationProperties> {
        val annotationPropertiesList = ArrayList<DtoWebAnnotationProperties>()

        if (uniqueIds.isEmpty()) {
            return annotationPropertiesList
        }

        for (annotationProperties in annotationPropertiesMap.values) {
            uniqueIds
                    .filter { it == annotationProperties.uniqueId }
                    .forEach { annotationPropertiesList.add(annotationProperties) }
        }

        return annotationPropertiesList
    }

    fun setParagraphAidLocations(paragraphAidLocations: List<DtoParagraphAidPosition>) {
        this.paragraphAidLocations.addAll(paragraphAidLocations)
    }

    fun getTopVisibleParagraphAid(): String {
        val scrollYOffset = getPageYOffset()


        return paragraphAidLocations
                .firstOrNull { it.positionY >= scrollYOffset }
                ?.paragraphAid
                ?: paragraphMetadataManager.findTopMostAid(contentItemId, subItemId)
    }

    /**
     * @return value similar to JavaScript window.pageYOffset
     */
    private fun getPageYOffset(): Int {
        val density = uiUtil.getDeviceDisplayDensity(context)
        return (scrollY / density).toInt()
    }

    fun onHtmlRenderingFinished() {
        contentRendered = true
        if (requestedMarginDelta != 0) {
            updateBottomMargin(requestedMarginDelta)
        }

        setHtmlContentLoaded() // todo needed?

        // get a list of all of the paragraphs and their y positions
        contentJsInvoker.requestParagraphAidPositions(this)

        onHtmlRenderingFinishedListener(contentItemId, subItemId)
    }

    /**
     * Once all Related Content (Annotations, Footnotes, other Related content) has been loaded... finalize the webview

     * @param annotations All annotations
     */
    fun onAnnotationsLoaded(annotations: MutableList<Annotation>) {
        val startMs = System.currentTimeMillis()
        Timber.d("Annotations: Loading into webview: %d", subItemId)

        // Apply all related content
        this.annotations = annotations
        annotationPropertiesMap.clear()
        endSelectMode()
        processAllAnnotationsForDisplay()

        contentJsInvoker.runHtmlCustomizations(this)
        requestOffsets()

        annotationsLoaded = true
        onAllContentRenderedListener(contentItemId, subItemId)

        timeUtil.logTimeElapsedFromNow("AnnotationsLoaded", "Annotations: loaded into webview [$subItemId]", startMs)
    }

    /**
     * request javascript calls to return position offsets for various HTML elements
     */
    private fun requestOffsets() {
        if (isAllContentLoaded) {
            processAllAnnotationsForDisplay()
        }
    }

    fun loadDataWithBaseURL(contentData: ContentData) {
        loadDataWithBaseURL(contentData.baseUrl, contentData.content, PAGE_MIME_TYPE, PAGE_ENCODING, null)
    }

    override fun loadUrl(url: String?) {
        if (url == null) {
            return
        }

        this.post {
            try {
                super@ContentWebView.loadUrl(url)
            } catch (e: Exception) {
                Timber.e(e, "loadUrl: %s", url)
            }
        }
    }

    /**
     * Single tap event coming from javascript callback
     */
    fun onSingleTap(dtoWebTouch: DtoWebTouch) {
        // * end selection
        if (isInSelectMode()) {
            endSelectMode() // annotation will be saved with endSelectMode()
            return
        }

        val xPos = dtoWebTouch.x.toInt()
        val yPos = dtoWebTouch.y.toInt()

        if (linkingMode) {
            contentJsInvoker.requestAidFromTapPosition(this, xPos, yPos)
            return
        }

        // get a list of highlights that are in the touch zone
        val annotationsInTouchZone = getAnnotationPropertiesForPosition(xPos, yPos)
        val highlightTapped = !annotationsInTouchZone.isEmpty()

        val singleTapConflict = highlightTapped && (dtoWebTouch.touchSource == DtoWebTouch.WebTouchSource.LINK || dtoWebTouch.touchSource == DtoWebTouch.WebTouchSource.REF && !prefs.contentHideFootnotes)

        when {
            singleTapConflict -> handleSingleTapConflict(dtoWebTouch, annotationsInTouchZone)
            highlightTapped -> processSingleTapHighlight(annotationsInTouchZone)
            else -> processSingleTap(dtoWebTouch)
        }
    }

    /**
     * Single tap event coming from javascript callback
     */
    private fun processSingleTap(dtoWebTouch: DtoWebTouch) {
        // handle tap based on source
        when (dtoWebTouch.touchSource) {
            DtoWebTouch.WebTouchSource.LINK -> {
                handleLinkTapped(dtoWebTouch, false)
                return
            }
            DtoWebTouch.WebTouchSource.REF -> {
                if (dtoWebTouch.ids.isNotEmpty() && !prefs.contentHideFootnotes) {
                    onRefTappedListener(dtoWebTouch.text, contentItemId, subItemId, dtoWebTouch.ids[0])
                }
                return
            }
            DtoWebTouch.WebTouchSource.NOTE -> {
                val selectedAnnotationProperties = getAnnotationPropertiesForUniqueIds(dtoWebTouch.ids)
                selectSticky(selectedAnnotationProperties)
                return
            }
            DtoWebTouch.WebTouchSource.IMAGE -> {
                if (dtoWebTouch.ids.isNotEmpty()) {
                    showImage(dtoWebTouch.ids[0])
                }
                return
            }
            DtoWebTouch.WebTouchSource.VIDEO -> {
                if (dtoWebTouch.ids.isNotEmpty() && dtoWebTouch.ids[0].isNotBlank()) {
                    playVideo(dtoWebTouch.ids[0])
                } else {
                    toastUtil.showShort(R.string.selected_media_unavailable)
                }
                return
            }
            DtoWebTouch.WebTouchSource.VIDEO_DOWNLOAD -> {
                if (dtoWebTouch.ids.isNotEmpty() && dtoWebTouch.ids[0].isNotBlank()) {
                    downloadVideo(dtoWebTouch.ids[0])
                } else {
                    toastUtil.showShort(R.string.selected_media_unavailable)
                }
                return
            }
            else -> {
            }
        }
    }

    /**
     * Double tap event coming ContentTouchListener
     * NOTE / Known issues:
     * - Double tap might be better handled in javascript... but brings risk and maintainability potential issues
     * - Double tap from ContentTouchListener may cause cross chatter from javascipt onSingleTap callbacks
     */
    fun onDoubleTap() {
        toggleFullScreenModeListener()
    }

    private fun processSingleTapHighlight(annotationsInTouchZone: List<DtoWebAnnotationProperties>) {
        selectHighlight(annotationsInTouchZone)
    }

    private fun handleSingleTapConflict(dtoWebTouch: DtoWebTouch, annotationsInTouchZone: List<DtoWebAnnotationProperties>) {
        val linkTextResId = if (dtoWebTouch.touchSource == DtoWebTouch.WebTouchSource.LINK) R.string.select_link else R.string.select_footnote
        val options = listOf<String>(resources.getString(linkTextResId), context.getString(R.string.select_highlight))

        MaterialDialog.Builder(context)
                .title(dtoWebTouch.text.trim())
                .items(options)
                .itemsCallback { _, _, which, _ ->
                    if (which == 0) { // option 0 == "Select Link"
                        processSingleTap(dtoWebTouch)
                    } else if (which == 1) {// option 1 == "Select Highlight"
                        processSingleTapHighlight(annotationsInTouchZone)
                    }
                }
                .build()
                .show()
    }

    /**
     * Long tap event coming from javascript callback
     */
    fun onLongTap(dtoWebTouch: DtoWebTouch) {
        if (!annotatingEnabled) {
            return
        }

        // make sure that we are not swiping tabs in the viewpager
        if (!isWebPositionSameAsScreenPosition(dtoWebTouch)) {
            return
        }

        when (dtoWebTouch.touchSource) {
            DtoWebTouch.WebTouchSource.LINK -> {
                onLongTapLink(dtoWebTouch)
                return
            }
        }

        launch(cc.ui) {
            // if the highlight popup is already visible... hide it... it will re-open with enterSelectModeFromLongPress(...) (must run on main thread)
            hideHighlightPopup()

            // save touched position
            contentJsInvoker.enterSelectModeFromLongPress(this@ContentWebView, dtoWebTouch.x.toInt(), dtoWebTouch.y.toInt())
        }
    }

    private fun onLongTapLink(dtoWebTouch: DtoWebTouch) {
        val options = arrayOf(resources.getString(R.string.open), context.getString(R.string.open_in_new_screen))

        val builder = AlertDialog.Builder(context)
        builder.setTitle(dtoWebTouch.text)
        builder.setItems(options) { _, which ->
            val newScreen = which == 1 // option 1 == "Open in New Screen"
            handleLinkTapped(dtoWebTouch, newScreen)
        }
        builder.show()
    }

    private fun handleLinkTapped(dtoWebTouch: DtoWebTouch, newScreen: Boolean) {
        val uri = dtoWebTouch.uri
        if (uri.startsWith(MUSIC_SCHEME)) {
            externalIntents.showInLdsMusic(context, uri)
        } else if (uri.startsWith(GL_SCHEME)) {

            if (isSameContent(contentUri, uri)) {
                // on the same page

                val scrollToParagraphAid = uriUtil.getScrollToParagraphAidFromUri(contentItemId, subItemId, uri)
                val paragraphAidList = uriUtil.findAidsByUri(contentItemId, subItemId, uri, false, true)
                val markParagraphAids = paragraphAidList.toTypedArray()

                contentJsInvoker.removeAllParagraphMarks(this)
                contentJsInvoker.scrollToParagraphByAid(this, scrollToParagraphAid, markParagraphAids)
            } else {
                // in other content
                onLinkTappedListener(dtoWebTouch.text, uri, newScreen)
            }
        } else if (uri.startsWith(IMAGE_SCHEME_AUTHORITY)) {
            showImage(Uri.parse(uri).getQueryParameter(ID_QUERY))
        } else {
            externalIntents.showUri(context, uri)
        }
    }

    private fun isSameContent(sourceUriText: String, targetUriText: String): Boolean {
        val targetUri: URI
        try {
            targetUri = URI(targetUriText)
        } catch (e: URISyntaxException) {
            Timber.e(e, "Failed to parse uri [%s]", targetUriText)
            return false
        }

        // get the path and search for the contentItemId by Uri
        val targetUriPath = targetUri.path ?: return false

        return sourceUriText == targetUriPath
    }

    fun onParagraphTapped(aid: String) {
        if (!linkingMode) {
            return
        }
        if (markedParagraphs.contains(aid)) {
            markedParagraphs.remove(aid)
            contentJsInvoker.removeParagraphMarkByAid(this, aid)
            onParagraphUnMarkedListener(aid)

        } else {
            markedParagraphs.add(aid)
            contentJsInvoker.markParagraphByAid(this, aid)
            onParagraphMarkedListener(aid)
        }
    }

    fun setMarkedParagraphAids(paragraphAids: Collection<String>) {
        markedParagraphs.clear()
        markedParagraphs.addAll(paragraphAids)
        for (paragraphAid in paragraphAids) {
            contentJsInvoker.markParagraphByAid(this, paragraphAid)
        }
    }

    private fun isWebPositionSameAsScreenPosition(dtoWebTouch: DtoWebTouch): Boolean {
        val density = uiUtil.getDeviceDisplayDensity(context)
        val webXPos = dtoWebTouch.x * density
        val webYPos = dtoWebTouch.y * density

        val lastTouchPoint = touchListener.lastTouchPoint
        val threshold = WEB_TAP_POSITION_THRESHOLD * density

        val viewX = lastTouchPoint.x
        val viewY = lastTouchPoint.y

        val deltaX = Math.abs(viewX - webXPos)
        val deltaY = Math.abs(viewY - webYPos)

        Timber.d("viewX: %f //  viewY: %f", viewX, viewY) // NOSONAR - Debug
        Timber.d("webX: %f // webY: %f", webXPos, webYPos) // NOSONAR - Debug
        Timber.d("deltaX: %f // deltaY: %f, // threshold: %f", deltaX, deltaY, threshold) // NOSONAR - Debug

        // make sure the x/y reported by javasript is the same approx position on the actual screen
        return deltaX <= threshold && deltaY <= threshold
    }

    fun scrollToParagraphByAid(aid: String) {
        contentJsInvoker.scrollToParagraphByAid(this, aid)
    }

    fun scrollToParagraphByAid(aid: String, markParagraphAids: Array<String>) {
        contentJsInvoker.scrollToParagraphByAid(this, aid, markParagraphAids)
    }

    fun onNotifyScrollPositionAid(aid: String) {
        onScrollPositionAidChangedListener(aid)
    }

    private fun setContentItemAndSubItemId(contentItemId: Long, subItemId: Long) {
        this.contentItemId = contentItemId
        this.subItemId = subItemId

        val subItem = subItemManager.findByRowId(contentItemId, subItemId)

        if (subItem != null) {
            contentDocId = subItem.docId
            contentDocVersion = subItem.docVersion
            contentUri = subItem.uri
        }
    }

    val isAllContentLoaded: Boolean
        get() = isHtmlContentLoaded && annotationsLoaded

    private fun setHtmlContentLoaded() {
        isHtmlContentLoaded = true
    }

    fun setAnnotatingEnabled(annotatingEnabled: Boolean) {
        this.annotatingEnabled = annotatingEnabled
    }

    private fun logAnalytics(annotation: Annotation, changeType: Analytics.ChangeType) {
        analyticsUtil.logContentAnnotated(annotation, Analytics.AnnotationType.MARK, changeType)
    }

    fun requestAidFromScrollPosition() {
        contentJsInvoker.requestAidFromScrollPosition(this, this.width / SCROLL_SCAN_X_DIVISOR, SCROLL_SCAN_Y_POSITION)
    }

    fun requestRemoveAllMarks() {
        contentJsInvoker.requestRemoveAllMarks(this)
    }

    fun requestScrollToMark(position: Int) {
        contentJsInvoker.requestScrollToMark(this, position)
    }


    // ========== Highlight Popup ==========

    private fun onHighlightMarkClick() {
        saveOrMarkAnnotation()
    }

    private fun onHighlightStyleClick() {
        val annotation = getSelectedAnnotation()
        if (annotation != null) {
            onHighlightStyleClickListener(annotation)
        }
    }

    private fun onHighlightNoteClick() {
        saveOrMarkAnnotation()

        val annotation = getSelectedAnnotation()
        if (annotation != null) {
            onHighlightNoteClickListener(annotation.id)
        }
    }

    private fun onHighlightTagClick() {
        saveOrMarkAnnotation()

        val annotation = getSelectedAnnotation()
        if (annotation != null) {
            onHighlightTagClickListener(annotation.id)
        }
    }

    private fun onHighlightLinkClick() {
        saveOrMarkAnnotation()

        val annotation = getSelectedAnnotation()
        if (annotation != null) {
            onHighlightLinkClickListener(annotation.id)
        }
    }

    private fun onHighlightCopyClick() {
        val context = context.applicationContext
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("", selectedAnnotationText)
        clipboard.primaryClip = clip
        endSelectMode()

        val msg = context.getString(R.string.copied_to_clipboard) + ": \n\n" + selectedAnnotationText
        toastUtil.showLong(msg)
    }

    private fun onHighlightShareClick() {
        selectedAnnotation?.let { annotation ->
            val uri = uriUtil.getSharableUri(annotation)
            val citation = citationUtil.createCitationText(annotation)
            onShowShareDialogForContentSnippet(contentItemId, subItemId, uri, citation, selectedAnnotationText)

            if (annotation.isNewRecord) {
                deleteAnnotation(annotation)
                endSelectMode()
            }
        }
    }

    private fun onHighlightAddToNotebookClick() {
        saveOrMarkAnnotation()

        val annotation = getSelectedAnnotation()
        if (annotation != null) {
            onHighlightAddToNotebookClickListener(annotation.id)
        }
    }

    private fun onHighlightSearchClick() {
        onShowSearchActivity(selectedAnnotationText, GLContentContext(0, contentItemId, 0, subItemId))
        if (selectedAnnotation?.isNewRecord == true) {
            deleteAnnotation(selectedAnnotation)
            endSelectMode()
        }
    }

    private fun onHighlightDefineClick() {
        onGoogleSearch(selectedAnnotationText)

        if (selectedAnnotation?.isNewRecord == true) {
            deleteAnnotation(selectedAnnotation)
            endSelectMode()
        }
    }

    private fun onHighlightDeleteClick() {
        val annotation = getSelectedAnnotation() ?: return

        if (annotation.hasContent()) {
            showDeleteAnnotationDialog(annotation)
        } else {
            deleteAnnotation(annotation)
            endSelectMode()
        }
    }

    private fun showDeleteAnnotationDialog(annotation: Annotation) {
        MaterialDialog.Builder(context)
                .title(R.string.delete_annotation_title)
                .content(R.string.delete_annotation_message)
                .positiveText(R.string.delete)
                .negativeText(R.string.cancel)
                .onPositive { _, _ ->
                    deleteAnnotation(annotation)
                    endSelectMode()
                }
                .show()
    }

    // ========== Highlight Popup END ==========

    private fun showHighlightPopup() {
        highlightPopupMenu.show()
    }

    fun hideHighlightPopup() {
        highlightPopupMenu.dismiss()
    }

    private fun saveOrMarkAnnotation() {
        selectedAnnotation?.let { annotation ->
            if (annotation.isNewRecord) {
                removeSelectionHighlight()
                markAnnotationWithRecentStyle(annotation)
            }

            var created = false
            contentJsInvoker.setSelectedAnnotationIdOnHighlights(this, annotation.uniqueId)
            if (!annotationListUtil.annotationExistsInList(annotations, annotation.uniqueId)) {
                addAnnotation(annotation)
                created = true
            }

            annotation.docId = contentDocId
            annotation.contentVersion = contentDocVersion
            annotationManager.save(annotation)
            logAnalytics(annotation, if (created) Analytics.ChangeType.CREATE else Analytics.ChangeType.UPDATE)

            // Updates the UI to represent the new color
            contentJsInvoker.showHighlight(this, annotation, true)
        }
    }

    fun saveSelectedAnnotationFromHandleMove() {
        val annotation = getSelectedAnnotation() ?: return
        annotationManager.save(annotation)
        logAnalytics(annotation, Analytics.ChangeType.UPDATE)
    }

    private fun markAnnotationWithRecentStyle(annotation: Annotation) {
        val recentHighlight = prefs.contentDisplayRecentHighlights[0]
        annotation.setAllHighlightColors(recentHighlight.color, recentHighlight.style)
    }

    /**
     * The initial selection on a highlight is a "fake" highlight using the accent color.  This will
     * remove all divs and references to this temp highlight
     */
    private fun removeSelectionHighlight() {
        contentJsInvoker.removeAnnotationSelectionDivs(this)

        // remove annotationProperties for the selection divs (selection divs have a key of "")
        annotationPropertiesMap.remove("")
    }

    private fun downloadVideo(videoId: String) {
        val videoList = inlineVideos
        for (video in videoList.videos) {
            if (video.videoId == videoId) {
                onDownloadVideoDialog(contentItemId, subItemId, video, ItemMediaType.VIDEO)
                break
            }
        }
    }

    private fun playVideo(startVideoId: String?) {
        //If the playlist is already correct then do nothing
        val currentItem = playlistManager.currentItem
        if (playlistManager.id == contentItemId && playlistManager.secondaryId == subItemId && currentItem != null && currentItem.mediaType == BasePlaylistManager.VIDEO) {
            //NOTE: since the item id is currently (24-06-15) the subItemId we don't compare them like we do for audio
            onShowVideoPlayer(contentItemId, subItemId, currentItem.tertiaryId, Analytics.Referrer.PLAY_BUTTON)
            return
        }

        //Converts the inlineVideos to a list of VideoPlaylistItems
        val playlistItems = ArrayList<MediaItem>()
        val coverRenditions = itemManager.findImageCoverRenditionsById(contentItemId)

        for (video in inlineVideos.videos) {
            if (video.sources.isEmpty()) {
                // If there are no sources available, do not convert the video into a playlist item. If no videos are playable, a message will be display to the user below
                continue
            }

            if (video.title.isBlank()) {
                video.title = subItemManager.findTitleById(contentItemId, subItemId)
            }

            playlistItems.add(VideoItem(video, coverRenditions, contentItemId, subItemId))
        }

        //Makes sure there are items to play
        if (playlistItems.isEmpty()) {
            MaterialDialog.Builder(context)
                    .title(R.string.video_unavailable)
                    .content(R.string.selected_media_unavailable)
                    .positiveText(R.string.ok)
                    .show()
            return
        }

        //Determines the start index
        var startIndex = 0
        if (!startVideoId.isNullOrBlank()) {
            inlineVideos.videos
                    .takeWhile { startVideoId != it.videoId }
                    .forEach { startIndex++ }
        }

        //Starts the actual playback
        playlistManager.setParameters(playlistItems, startIndex)
        playlistManager.setPlaylistId(contentItemId, subItemId)
        onShowVideoPlayer(contentItemId, subItemId, startVideoId, Analytics.Referrer.PLAY_BUTTON)
    }

    private fun showImage(imageId: String) {
        val image: DtoImage? = inlineImages.images.firstOrNull { imageId == it.id }

        if (image != null && !image.sources.isEmpty()) {
            onShowImage(image)
        }
    }

    companion object {
        val GL_SCHEME = "gospellibrary"
        val MUSIC_SCHEME = "ldsmusic"
        val IMAGE_SCHEME_AUTHORITY = "gl://image"
        val ID_QUERY = "id"

        private val PAGE_MIME_TYPE = "text/html"
        private val PAGE_ENCODING = "utf-8"

        private val WEB_TAP_POSITION_THRESHOLD = 8f  // be sure to ALSO change lds.touch.js -> touchInSamePosition() -> threshold

        private val SCROLL_SCAN_DELAY = 250
        private val SCROLL_SCAN_Y_POSITION = 10
        private val MAX_SCROLL_PERCENT = 100
        val SCROLL_SCAN_X_DIVISOR = 4
    }
}