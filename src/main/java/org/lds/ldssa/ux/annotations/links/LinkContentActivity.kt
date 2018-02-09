package org.lds.ldssa.ux.annotations.links

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.content_webview.*
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.link.LinkManager
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.ui.web.ContentData
import org.lds.ldssa.util.CitationUtil
import org.lds.ldssa.util.ContentRenderer
import org.lds.ldssa.util.annotations.LinkUtil
import org.lds.mobile.ui.util.LdsDrawableUtil
import java.util.TreeSet
import javax.inject.Inject

class LinkContentActivity : BaseActivity() {

    @Inject
    lateinit var linkUtil: LinkUtil

    @Inject
    lateinit var linkManager: LinkManager
    @Inject
    lateinit var citationUtil: CitationUtil
    @Inject
    lateinit var annotationManager: AnnotationManager
    @Inject
    lateinit var contentRenderer: ContentRenderer

    private var contentItemId = 0L
    private var subItemId = 0L
    private var annotationId = 0L
    private var linkId = -1L

    private lateinit var saveMenuItem: MenuItem
    private val selectedAids = TreeSet<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.get().inject(this)
        super.onCreate(savedInstanceState)

        with(IntentOptions) {
            contentItemId = intent.contentItemId
            subItemId = intent.subItemId
            annotationId = intent.annotationId
            linkId = intent.linkId
        }

        // main content color under the webview
        contentWebView.setBackgroundColor(LdsDrawableUtil.resolvedColorIntResourceId(this, R.attr.themeStyleColorMainContentBackground))

        contentWebView.initView(getScreenId(), contentItemId, subItemId, true)
        contentWebView.onParagraphMarkedListener = { aid ->
            selectedAids.add(aid)
            updateSaveButton()
        }
        contentWebView.onHtmlRenderingFinishedListener = { _, _ ->
            onHtmlRenderingFinished()
        }
        contentWebView.onParagraphUnMarkedListener = { aid ->
            selectedAids.remove(aid)
            updateSaveButton()
        }

        setTitle(R.string.select_text_to_link)
        val titleAndVerse = citationUtil.createCitationText(annotationId)
        setSubtitle(getString(R.string.link_to, titleAndVerse))

        loadContent()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_link_content, menu)
        saveMenuItem = menu.findItem(R.id.menu_item_save)
        enableSaveMenuItem(!selectedAids.isEmpty())
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menu_item_save -> {
                saveLink()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_link_content
    }

    override fun getAnalyticsScreenName(): String {
        return Analytics.Screen.LINK_CONTENT
    }

    private fun updateSaveButton() {
        enableSaveMenuItem(!selectedAids.isEmpty())
    }

    private fun enableSaveMenuItem(enable: Boolean) {
        runOnUiThread {
            val themeAttrResId = if (enable) R.attr.themeStyleColorToolbarIconDim else R.attr.themeStyleColorToolbarIconDimDisabled
            saveMenuItem.icon = LdsDrawableUtil.tintDrawableForTheme(this@LinkContentActivity, R.drawable.ic_lds_action_done_24dp, themeAttrResId)
            saveMenuItem.isEnabled = enable
        }
    }

    private fun loadContent() {
        val observable = contentRenderer.generateDefaultHtmlTextRx(contentItemId, subItemId, null, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        addDisposable(observable.subscribe { contentData -> onContentLoaded(contentData) })
    }

    private fun onContentLoaded(contentData: ContentData) {
        contentWebView.loadDataWithBaseURL(contentData)
    }

    /**
     * Save link and finish activity
     */
    private fun saveLink() {
        if (selectedAids.isEmpty()) {
            return
        }

        // generate Name
        val linkName = citationUtil.createCitationText(contentItemId, subItemId, selectedAids)

        // perform save (remove existing, as needed)
        if (linkId > 0) {
            linkUtil.update(annotationId, linkId, contentWebView.contentDocId, selectedAids, linkName)
        } else {
            linkUtil.add(annotationId, contentWebView.contentDocId, selectedAids, linkName)
        }

        // finish and notify
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun onHtmlRenderingFinished() {
        if (linkId < 0) {
            return
        }
        val link = linkManager.findByRowId(linkId)
        if (link != null) {
            val paragraphIds = link.getParagraphAids()

            if (!paragraphIds.isEmpty()) {
                selectedAids.addAll(paragraphIds)
                updateSaveButton()

                contentWebView.setMarkedParagraphAids(selectedAids)
                contentWebView.scrollToParagraphByAid(selectedAids.first())
            }
        }
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, LinkContentActivity::class)

    object IntentOptions {
        var Intent.contentItemId by IntentExtra.Long(defaultValue = 0L)
        var Intent.subItemId by IntentExtra.Long(defaultValue = 0L)
        var Intent.annotationId by IntentExtra.Long(defaultValue = 0L)

        // Optional
        var Intent.linkId by IntentExtra.Long(defaultValue = -1L)
    }
}
