package org.lds.ldssa.ux.annotations.notes

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.activity_notes.*
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Int
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager
import org.lds.ldssa.model.database.types.ScreenSourceType
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItem
import org.lds.ldssa.model.navigation.screens.ScreenHistoryExtrasNotes
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.ui.animation.CollapseAnimation
import org.lds.ldssa.ui.animation.ExpandAnimation
import org.lds.ldssa.util.NotebookUtil
import org.lds.ldssa.util.ScreenLauncherUtil
import org.lds.ldssa.util.ShareUtil
import org.lds.ldssa.util.ToastUtil
import org.lds.ldssa.ux.annotations.AnnotationsFragment
import org.lds.ldssa.ux.annotations.allannotations.AllAnnotationsFragment
import org.lds.ldssa.ux.annotations.notebooks.NotebooksFragment
import org.lds.ldssa.ux.annotations.tags.TagsFragment
import org.lds.ldssa.ux.content.item.ContentRequestCode
import org.lds.mobile.ui.animation.FabHideAnimation
import org.lds.mobile.ui.animation.FabShowAnimation
import java.util.HashMap
import javax.inject.Inject

class NotesActivity : BaseActivity(), ScreenLauncherUtil.ScreenActivity {
    @Inject
    lateinit var shareUtil: ShareUtil
    @Inject
    lateinit var notebookUtil: NotebookUtil
    @Inject
    lateinit var navigationTrailQueryManager: NavigationTrailQueryManager
    @Inject
    lateinit var restoreJournalUtil: RestoreJournalUtil
    @Inject
    lateinit var toastUtil: ToastUtil

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)

        var selectedNotesTabIndex = 0

        with(IntentOptions) {
            selectedNotesTabIndex = intent.selectedTabIndex
        }

        setupPager(selectedNotesTabIndex)

        //Makes sure the fab is visible on load
        updateFabVisibility(selectedNotesTabIndex == TAB_POSITION_NOTEBOOK)

        setTitle(R.string.notes)
        updateNavigationTrail(navigationTrailQueryManager.findDefaultAllForNotesOrTips(getLanguageId()))
        notesTabLayout.setupWithViewPager(notesViewPager)

        getToolbarExpand()?.setStickyView(notesTabLayout)

        notesFloatingActionButton.setOnClickListener {
            if (notesViewPager.currentItem == TAB_POSITION_NOTEBOOK) {
                notebookUtil.promptAdd(this)
            }
        }
    }

    override fun getLayoutResourceId() = R.layout.activity_notes

    override fun getAnalyticsScreenName() = Analytics.Screen.NOTES

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultIntent: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, resultIntent)
            return
        }

        //NOTE: onActivityResults is called before onStart() and since we start the all Activities from this contents
        // the onActivityResult() in the fragment is never called, thus the sticky bus events
        when {
            ContentRequestCode.getCode(requestCode) == ContentRequestCode.REQUEST_APPLICATION_SHARE && resultIntent != null -> shareUtil.processShareRequest(this, resultIntent)
            else -> super.onActivityResult(requestCode, resultCode, resultIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        val inflater = menuInflater
        commonMenu.inflateMenuPre(this, menu, inflater)
        inflater.inflate(R.menu.menu_notes, menu)
        commonMenu.inflateMenuPost(this, menu, inflater)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_restore_journal -> {
                showRestoreJournalDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showRestoreJournalDialog() {
        MaterialDialog.Builder(this)
                .title(R.string.journal_restore)
                .content(R.string.journal_restore_description)
                .positiveText(R.string.journal_create_notebook)
                .negativeText(R.string.no_thanks)
                .onPositive { _, _ -> restoreJournal() }
                .show()
    }

    private fun restoreJournal() {
        launch(cc.ui) {
            val annotationMigrationCount = run(cc.commonPool) {
                restoreJournalUtil.migrateLegacyJournalItems()
            }

            toastUtil.showLong(R.string.journal_restore_result, annotationMigrationCount)
        }
    }

    override fun isScreenHistoryItemEqual(screenHistoryItem: ScreenHistoryItem): Boolean {
        return screenHistoryItem.sourceType == ScreenSourceType.NOTES
    }

    override fun setCurrentScreenHistoryData(screenHistoryItem: ScreenHistoryItem) {
        screenHistoryItem.sourceType = ScreenSourceType.NOTES
        screenHistoryItem.title = getString(R.string.notes)
        screenHistoryItem.setExtras(gson, ScreenHistoryExtrasNotes(notesViewPager.currentItem.toLong()))
    }

    fun actionModeChanged(actionModeStarted: Boolean) {
        if (!actionModeStarted) {
            expandTabs()
            return
        }

        notesViewPager.setPagingEnabled(false)
        val animation = CollapseAnimation(notesTabLayout, null)
        notesTabLayout.startAnimation(animation)

        getToolbarExpand()?.visibility = View.GONE
    }

    private fun expandTabs() {
        notesViewPager.setPagingEnabled(true)
        val animation = ExpandAnimation(notesTabLayout, null)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                //Purposefully left blank
            }

            override fun onAnimationEnd(animation: Animation) {
                getToolbarExpand()?.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(animation: Animation) {
                //Purposefully left blank
            }
        })

        notesTabLayout.startAnimation(animation)
    }

    private fun setupPager(selectedNotesTabIndex: Int) {
        val pagerAdapter = NoteCollectionPagerAdapter(application, getScreenId(), supportFragmentManager)
        notesViewPager.adapter = pagerAdapter
        notesViewPager.offscreenPageLimit = PAGER_OFFSCREEN_PAGE_LIMIT

        if (selectedNotesTabIndex < pagerAdapter.count) {
            notesViewPager.currentItem = selectedNotesTabIndex
        }

        notesViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                //Purposefully left blank
            }

            override fun onPageSelected(position: Int) {
                prefs.notesPagerPosition = position
                updateFabVisibility(position == TAB_POSITION_NOTEBOOK)
                logAnalytics(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                //Purposefully left blank
            }
        })
        logAnalytics(selectedNotesTabIndex)
    }

    private fun updateFabVisibility(visible: Boolean) {
        if (visible && notesFloatingActionButton.visibility != View.VISIBLE) {
            notesFloatingActionButton.startAnimation(FabShowAnimation(notesFloatingActionButton))
        } else if (!visible && notesFloatingActionButton.visibility == View.VISIBLE) {
            notesFloatingActionButton.startAnimation(FabHideAnimation(notesFloatingActionButton))
        }
    }

    private fun logAnalytics(position: Int) {
        val attributes = HashMap<String, String>()
        attributes.put(Analytics.Attribute.TAB, determineTabByPosition(position))
        analytics.postEvent(Analytics.Event.NOTES_TAB_SELECTED, attributes)
    }

    private fun determineTabByPosition(position: Int) = when (position) {
        TAB_POSITION_ALL -> Analytics.Value.ALL
        TAB_POSITION_TAGS -> Analytics.Value.TAGS
        TAB_POSITION_NOTEBOOK -> Analytics.Value.NOTEBOOKS
        else -> Analytics.Value.UNKNOWN
    }

    class NoteCollectionPagerAdapter(application: Application, private val screenId: Long, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

        private val titles: Array<String> = application.resources.getStringArray(R.array.note_collection_tab_titles)

        override fun getCount() = PAGE_COUNT

        override fun getPageTitle(position: Int) = titles[position]

        override fun getItem(position: Int): Fragment {
            return when (position) {
                TAB_POSITION_ALL -> AllAnnotationsFragment.newInstance(screenId)
                TAB_POSITION_TAGS -> TagsFragment.newInstance(screenId)
                TAB_POSITION_NOTEBOOK -> NotebooksFragment.newInstance(screenId)
                else -> AnnotationsFragment.newInstance(screenId)
            }
        }

        companion object {
            private const val PAGE_COUNT = 3
        }
    }

    object IntentOptions {
        var Intent.selectedTabIndex by IntentExtra.Int(defaultValue = 0)
    }

    companion object {
        private val PAGER_OFFSCREEN_PAGE_LIMIT = 2 // load all fragments and don't destroy any of them
        const val TAB_POSITION_ALL = 0
        const val TAB_POSITION_TAGS = 1
        const val TAB_POSITION_NOTEBOOK = 2
    }
}
