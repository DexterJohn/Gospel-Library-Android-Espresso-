package org.lds.ldssa.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_highlight_selection.*
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Serializable
import me.eugeniomarletti.extras.intent.base.String
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.model.webview.content.dto.DtoHighlightInfo
import org.lds.ldssa.ui.adapter.HighlightSelectionAdapter
import org.lds.ldssa.util.ThemeUtil
import org.lds.ldssa.util.annotations.HighlightUtil
import java.util.ArrayList
import javax.inject.Inject

class HighlightSelectionActivity : AppCompatActivity(), HighlightSelectionAdapter.OnAnnotationClickListener {

    @Inject
    lateinit var prefs: Prefs
    @Inject
    lateinit var highlightUtil: HighlightUtil
    @Inject
    lateinit var analytics: Analytics
    @Inject
    lateinit var annotationManager: AnnotationManager
    @Inject
    lateinit var themeUtil: ThemeUtil

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        themeUtil.applyTheme(this)
        setContentView(R.layout.activity_highlight_selection)

        val mainToolbar: Toolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)

        mainToolbarTitleTextView.setText(R.string.choose_highlight)

        with(IntentOptions) {
            val highlightInfoList = intent.highlights as List<DtoHighlightInfo>
            loadList(highlightInfoList)
        }
    }

    override fun onResume() {
        super.onResume()
        analytics.postScreen(Analytics.Screen.HIGHLIGHT_SELECTION)
    }

    private fun loadList(highlightInfoList: List<DtoHighlightInfo>) {
        val adapter = HighlightSelectionAdapter(highlightUtil, highlightInfoList, annotationManager)
        adapter.setOnAnnotationClickListener(this)

        chooseHighlightRecyclerView.adapter = adapter
        chooseHighlightRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onAnnotationClick(item: DtoHighlightInfo) {
        val selectedUniqueId = item.uniqueId

        val resultIntent = intent
        with (ResultIntentOptions) {
            resultIntent.selectedUniqueId = selectedUniqueId
        }

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return true
    }

    object IntentOptions {
        var Intent.highlights by IntentExtra.Serializable<ArrayList<DtoHighlightInfo>>()
    }

    object ResultIntentOptions {
        var Intent.selectedUniqueId by IntentExtra.String(defaultValue = "")
    }
}
