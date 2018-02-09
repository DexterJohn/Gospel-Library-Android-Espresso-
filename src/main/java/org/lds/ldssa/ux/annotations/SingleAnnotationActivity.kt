package org.lds.ldssa.ux.annotations

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_annotation_view.*
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import me.eugeniomarletti.extras.intent.base.String
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager
import org.lds.ldssa.model.database.types.ScreenSourceType
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItem
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasAnnotationView
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.util.ScreenLauncherUtil
import javax.inject.Inject

class SingleAnnotationActivity : BaseActivity(), ScreenLauncherUtil.ScreenActivity {

    @Inject
    lateinit var annotationManager: AnnotationManager
    @Inject
    lateinit var navigationTrailQueryManager: NavigationTrailQueryManager

    private var annotationId = 0L
    private var highlightNoteText: String? = null

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(IntentOptions) {
            annotationId = intent.annotationId

            if (intent.highlightNoteText.isNotEmpty()) {
                highlightNoteText = intent.highlightNoteText
            }
        }

        setTitle(R.string.note)
        updateNavigationTrail(navigationTrailQueryManager.findDefaultAllForAnnotationView(getLanguageId()))

        editNoteFab.setOnClickListener {
            internalIntents.editNote(this, getScreenId(), annotationId, false)
        }

        loadUI()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_annotation_view
    }

    override fun getAnalyticsScreenName(): String {
        return Analytics.Screen.ANNOTATION_VIEW
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        commonMenu.onCreateOptionsMenu(this, menu, menuInflater)
        return true
    }

    override fun onStart() {
        super.onStart()

        addDisposable(annotationManager.tableChanges()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _ -> this@SingleAnnotationActivity.loadUI() })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultIntent: Intent?) {
        if (resultCode != AppCompatActivity.RESULT_OK || resultIntent == null) {
            super.onActivityResult(requestCode, resultCode, resultIntent)
            return
        }

        loadUI()
    }

    override fun isScreenHistoryItemEqual(screenHistoryItem: ScreenHistoryItem): Boolean {
        if (screenHistoryItem.sourceType == ScreenSourceType.ANNOTATION_VIEW) {
            val extras = screenHistoryItem.getExtras(gson, ScreenHistoryExtrasAnnotationView::class.java)
            return extras.annotationId == annotationId
        }
        return false
    }

    override fun setCurrentScreenHistoryData(screenHistoryItem: ScreenHistoryItem) {
        screenHistoryItem.sourceType = ScreenSourceType.ANNOTATION_VIEW
        screenHistoryItem.title = getString(R.string.note)

        // extras
        screenHistoryItem.setExtras(gson, ScreenHistoryExtrasAnnotationView(annotationId))
    }

    private fun loadUI() {
        val annotation = annotationManager.findFullAnnotationByRowId(annotationId)
        if (annotation != null) {
            annotationView.loadUi(getScreenId(), annotation, true, true, highlightNoteText)
            annotationViewScrollView.scrollTo(0, 0)
        }
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, SingleAnnotationActivity::class)

    object IntentOptions {
        var Intent.annotationId by IntentExtra.Long(defaultValue = 0L)
        var Intent.highlightNoteText by IntentExtra.String(defaultValue = "")
    }
}