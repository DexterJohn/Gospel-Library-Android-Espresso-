package org.lds.ldssa.ux.annotations

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.activity_general_single_fragment_navigation_trail.*
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import me.eugeniomarletti.extras.intent.base.String
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager
import org.lds.ldssa.model.database.types.ScreenSourceType
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItem
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasAnnotations
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.util.ScreenLauncherUtil
import org.lds.ldssa.util.ShareUtil
import org.lds.ldssa.ux.content.item.ContentRequestCode
import org.lds.mobile.ui.animation.FabShowAnimation
import javax.inject.Inject

/**
 * Activity used to show a list of annotations
 * - List of annotations for a specific Tag
 * - List of annotations for a specific Notebook
 */
class AnnotationsActivity : BaseActivity(), ScreenLauncherUtil.ScreenActivity {

    @Inject
    lateinit var shareUtil: ShareUtil
    @Inject
    lateinit var notebookManager: NotebookManager
    @Inject
    lateinit var navigationTrailQueryManager: NavigationTrailQueryManager
    @Inject
    lateinit var annotationManager: AnnotationManager

    private var notebookId = 0L
    private var tagText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)

        with(IntentOptions) {
            notebookId = intent.notebookId
            tagText = intent.tagText
        }

        title = buildTitle()
        updateNavigationTrail(navigationTrailQueryManager.findDefaultAllForNotebook(screenUtil.getLanguageIdForScreen(getScreenId())))
        supportFragmentManager.beginTransaction().replace(R.id.fragment_pos1, AnnotationsFragment.newInstance(getScreenId(), tagText, notebookId)).commit()

        newFloatingActionButton.setOnClickListener {
            internalIntents.editNote(this, getScreenId(), 0, true, if (notebookId > 0) notebookId else 0)
        }

        if (notebookId > 0) {
            newFloatingActionButton.startAnimation(FabShowAnimation(newFloatingActionButton))
        }
    }

    private fun buildTitle(): String {
        return if (tagText != "") tagText else notebookManager.findName(if (notebookId > 0) notebookId else 0)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        commonMenu.onCreateOptionsMenu(this, menu, menuInflater)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultIntent: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, resultIntent)
            return
        }

        //NOTE: onActivityResults is called before onStart() and since we start the all Activities from this contents
        // the onActivityResult() in the fragment is never called, thus the sticky bus events
        when  {
            ContentRequestCode.getCode(requestCode) == ContentRequestCode.REQUEST_APPLICATION_SHARE && resultIntent != null -> shareUtil.processShareRequest(this, resultIntent)
            else -> super.onActivityResult(requestCode, resultCode, resultIntent)
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_general_single_fragment_navigation_trail
    }

    override fun getAnalyticsScreenName(): String {
        return Analytics.Screen.ANNOTATIONS
    }

    override fun isScreenHistoryItemEqual(screenHistoryItem: ScreenHistoryItem): Boolean {
        if (screenHistoryItem.sourceType === ScreenSourceType.NOTEBOOK) {
            val extras = screenHistoryItem.getExtras(gson, ScreenHistoryExtrasAnnotations::class.java)
            return notebookId > 0 && extras.notebookId == notebookId || tagText != "" && tagText == extras.tag
        }

        return false
    }

    override fun setCurrentScreenHistoryData(screenHistoryItem: ScreenHistoryItem) {
        screenHistoryItem.sourceType = ScreenSourceType.NOTEBOOK
        screenHistoryItem.title = buildTitle()
        screenHistoryItem.setExtras(gson, ScreenHistoryExtrasAnnotations(if (notebookId > 0) notebookId else 0, tagText, 0))
    }


    object IntentOptions {
        var Intent.notebookId by IntentExtra.Long(defaultValue = 0L)
        var Intent.tagText by IntentExtra.String(defaultValue = "")
    }
}
