package org.lds.ldssa.ui.sidebar

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.text.Html
import android.util.AttributeSet
import android.view.LayoutInflater
import butterknife.ButterKnife
import kotlinx.android.synthetic.main.side_bar_content.view.*
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.util.AnnotationUiUtil
import org.lds.ldssa.util.ContentRenderer
import org.lds.ldssa.util.ScreenUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import javax.inject.Inject

class SideBarUri : SideBarView {
    @Inject
    lateinit var cc:CoroutineContextProvider
    @Inject
    lateinit var contentRenderer: ContentRenderer
    @Inject
    lateinit var annotationUiUtil: AnnotationUiUtil
    @Inject
    lateinit var screenUtil: ScreenUtil

    private var screenId: Long = 0

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
        val view = LayoutInflater.from(context).inflate(R.layout.side_bar_content, this, true)
        ButterKnife.bind(this, view)
    }

    fun loadUi(screenId: Long, title: String, uri: String) {
        this.screenId = screenId

        // title
        setTitle(Html.fromHtml(title))

        // items
        launch(cc.ui) {
            val contentData = run(coroutineContext + cc.commonPool) {
                val contentData = contentRenderer.generateHtmlTextForUriOnPartialContent(screenUtil.getLanguageIdForScreen(screenId), uri)
                contentData.title = title
                contentData.titleUri = uri
                contentData
            }

            annotationUiUtil.showContentData(this@SideBarUri.context as FragmentActivity, screenId, uriItemContentLayout, contentData, false, null, Analytics.Referrer.RELATED_CONTENT_LINK)
        }
    }
}
