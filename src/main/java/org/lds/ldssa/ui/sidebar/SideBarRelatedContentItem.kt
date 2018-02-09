package org.lds.ldssa.ui.sidebar

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.side_bar_related_content_item.view.*
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import org.jsoup.Jsoup
import org.jsoup.safety.Whitelist
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.content.relatedcontentitem.RelatedContentItemManager
import org.lds.ldssa.util.AnnotationUiUtil
import org.lds.ldssa.util.ContentRenderer
import org.lds.ldssa.util.RefHtmlParser
import org.lds.ldssa.util.ScreenUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import javax.inject.Inject

class SideBarRelatedContentItem : SideBarView {
    @Inject
    lateinit var cc: CoroutineContextProvider
    @Inject
    lateinit var relatedContentItemManager: RelatedContentItemManager
    @Inject
    lateinit var contentRenderer: ContentRenderer
    @Inject
    lateinit var screenUtil: ScreenUtil
    @Inject
    lateinit var annotationUiUtil: AnnotationUiUtil

    private var screenId: Long = 0
    private var contentItemId: Long = 0
    private var subItemId: Long = 0
    private lateinit var refId: String

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        Injector.get().inject(this)
        val view = LayoutInflater.from(context).inflate(R.layout.side_bar_related_content_item, this, true)
        ButterKnife.bind(this, view)
    }

    fun loadUi(screenId: Long, contentItemId: Long, subItemId: Long, refId: String, title: String?) {
        this.screenId = screenId
        this.contentItemId = contentItemId
        this.subItemId = subItemId
        this.refId = refId

        val relatedContentItem = relatedContentItemManager.findBySubItemIdAndRefId(contentItemId, subItemId, refId) ?: return

        var titleText = relatedContentItem.labelHtml
        if (title != null) {
            titleText += " " + title
        }

        setTitle(Html.fromHtml(Jsoup.clean(titleText, Whitelist.none())))

        setupView()
    }

    private fun setupView() {
        val refRelatedContentItem = relatedContentItemManager.findBySubItemIdAndRefId(contentItemId, subItemId, refId) ?: return

        // title
        val formattedRefContent = Html.fromHtml(refRelatedContentItem.contentHtml).toString().trim { it <= ' ' }
        refContentTextView.text = formattedRefContent

        // items
        launch(cc.ui) {
            val contentDataList = run(coroutineContext + cc.commonPool) {
                val refHtmlParser = RefHtmlParser(refRelatedContentItem.contentHtml)
                refHtmlParser.scriptureRefList.map { scriptureRef ->
                    val contentData = contentRenderer.generateHtmlTextForUriOnPartialContent(screenUtil.getLanguageIdForScreen(screenId), scriptureRef.uri)
                    contentData.title = scriptureRef.title
                    contentData.titleUri = scriptureRef.uri
                    contentData
                }
            }

            contentDataList.forEach { contentData ->
                annotationUiUtil.showContentData(getContext() as FragmentActivity, screenId, refItemContentLayout, contentData, false, null, Analytics.Referrer.RELATED_CONTENT_LINK)
            }
        }
    }

    override fun onCreateOptionsMenu(toolbar: Toolbar) {
        toolbar.inflateMenu(R.menu.menu_side_bar_related_content)
        super.onCreateOptionsMenu(toolbar)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_item_related_content -> {
                val sideBar = sideBar ?: return false
                sideBar.showRelatedContent()
                return true
            }
            else -> return false
        }
    }
}
