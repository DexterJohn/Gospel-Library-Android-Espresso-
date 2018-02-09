package org.lds.ldssa.ui.activity

import android.app.ActivityManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.view.ActionMode
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.experimental.Job
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.event.BackgroundSnackbarEvent
import org.lds.ldssa.event.account.AccountSignInPromptEvent
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.model.GLContentContext
import org.lds.ldssa.model.database.catalog.language.LanguageManager
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQuery
import org.lds.ldssa.model.database.types.ScreenSourceType
import org.lds.ldssa.model.database.types.SnackbarAction
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.ui.adapter.NavigationTrailAdapter
import org.lds.ldssa.ui.menu.CommonMenu
import org.lds.ldssa.util.AccountUtil
import org.lds.ldssa.util.AnalyticsUtil
import org.lds.ldssa.util.ScreenLauncherUtil
import org.lds.ldssa.util.ScreenUtil
import org.lds.ldssa.util.ThemeUtil
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.ui.activity.LiveDataObserverActivity
import org.lds.mobile.ui.setVisible
import org.lds.mobile.ui.widget.toolbar.ToolbarExpand
import org.lds.mobile.ui.widget.toolbar.ToolbarExpandLayout
import pocketbus.Bus
import pocketbus.Subscribe
import pocketbus.ThreadMode
import pocketknife.BindExtra
import pocketknife.PocketKnife
import timber.log.Timber
import javax.inject.Inject

abstract class BaseActivity : LiveDataObserverActivity(), ToolbarExpand.ToolbarExpandListener, NavigationTrailAdapter.OnNavigationTrailClickListener {

    @Inject
    lateinit var bus: Bus
    @Inject
    lateinit var prefs: Prefs
    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var languageManager: LanguageManager
    @Inject
    lateinit var accountUtil: AccountUtil
    @Inject
    lateinit var screenUtil: ScreenUtil
    @Inject
    lateinit var screenLauncherUtil: ScreenLauncherUtil
    @Inject
    lateinit var analytics: Analytics
    @Inject
    lateinit var analyticsUtil: AnalyticsUtil
    @Inject
    lateinit var commonMenu: CommonMenu
    @Inject
    lateinit var cc: CoroutineContextProvider
    @Inject
    lateinit var gson: Gson
    @Inject
    lateinit var themeUtil: ThemeUtil

    @BindExtra(EXTRA_SCREEN_ID)
    var baseScreenId: Long = 0

    // kotlin synthetics don't work well in base classes...
    private var mainToolbar: Toolbar? = null
    private var mainToolbarTitleTextView: TextView? = null
    private var toolbarExpand: ToolbarExpand? = null
    private var topLayout: ToolbarExpandLayout? = null
    private var dropArrowImageView: ImageView? = null
    private var navigationTrailRecyclerView: RecyclerView? = null

    private var navigationTrailAdapter: NavigationTrailAdapter? = null
    private var compositeDisposable: CompositeDisposable? = null
    private var compositeJob: Job? = null

    private var actionMode: ActionMode? = null
    private var preventActionModeHide = false
    private var saveScreenState = true // don't save screen state when removing this item from the screen history

    protected var currentSnackbar: Snackbar? = null
    private lateinit var optionsMenu: Menu
        private set

    @LayoutRes
    protected abstract fun getLayoutResourceId(): Int

    protected abstract fun getAnalyticsScreenName(): String

    /**
     * All an activity to specify its Uri for change in language
     *
     * @return language independent Uri of the screen being viewed
     */
    open protected fun getUri(): String? {
        return null
    }

    open protected fun getGlContentContext(): GLContentContext {
        return GLContentContext(0L, 0L, 0L, 0L)
    }

    fun getScreenId(): Long {
        // update the screenId if it is bad
        baseScreenId = screenUtil.verifyScreenId(baseScreenId)
        return baseScreenId
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Injector.get().inject(this)
        PocketKnife.bindExtras(this)

        screenUtil.updateScreenTaskId(this, baseScreenId)

        if (useBaseThemeSetting()) {
            applyTheme()
        }

        super.onCreate(savedInstanceState)

        // setup views
        setContentView(getLayoutResourceId())
        findBaseActivityViews()
        ButterKnife.bind(this)
        PocketKnife.restoreInstanceState(this, savedInstanceState)

        if (mainToolbar != null) {
            setSupportActionBar(mainToolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            enableActionBarBackArrow(true)
        }

        setupToolbarAndTrail()
    }

    private fun findBaseActivityViews() {
        // kotlin synthetics don't work well in BaseActivities
        mainToolbar = findViewById(R.id.mainToolbar)
        mainToolbarTitleTextView = findViewById(R.id.mainToolbarTitleTextView)
        toolbarExpand = findViewById(R.id.toolbarExpand)
        topLayout = findViewById(R.id.topLayout)
        dropArrowImageView = findViewById(R.id.dropArrowImageView)
        navigationTrailRecyclerView = findViewById(R.id.navigationTrailRecyclerView)
    }

    protected open fun applyTheme() {
        themeUtil.applyTheme(this)
    }

    override fun onResume() {
        super.onResume()
        prefs.lastVisibleScreenId = baseScreenId
        analytics.postScreen(getAnalyticsScreenName())
    }

    override fun onPause() {
        if (canSaveScreenState() && saveScreenState && this is ScreenLauncherUtil.ScreenActivity) {
            screenUtil.saveScreenState(this, this)
        }

        super.onPause()
    }

    override fun onStart() {
        super.onStart()
        bus.register(this)
    }

    override fun onStop() {
        if (compositeDisposable != null) {
            compositeDisposable?.clear()
            compositeDisposable = null
        }
        compositeJob?.cancel()
        bus.unregister(this)
        super.onStop()
    }

    override fun onBackPressed() {
        if (canSaveScreenState() && this is ScreenLauncherUtil.ScreenActivity) {
            saveScreenState = false // don't update this screen item (during onPause)
            screenLauncherUtil.onBackPressed(this, this)
        } else {
            super.onBackPressed()
        }
    }

    fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

    fun addJob(job: Job) {
        if (compositeJob == null) {
            compositeJob = Job()
        }
        compositeJob?.attachChild(job)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        PocketKnife.saveInstanceState(this, outState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val success = super.onCreateOptionsMenu(menu)
        this.optionsMenu = menu
        return success
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (commonMenu.onOptionsItemSelected(this, item, optionsMenu, getLanguageId(), getUri(), getGlContentContext())) {
            return true
        }

        if (allowFinishOnHome() && item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        // This code will intercept the hardware menu key and open the overflow menu instead. Remove this when devices with a hardware menu key are no longer supported.
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (mainToolbar != null) {
                (mainToolbar as Toolbar).showOverflowMenu()
                return true
            }
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun onLayoutChange(percent: Float) {
        setArrowRotation(percent)
    }

    protected open fun allowFinishOnHome(): Boolean {
        return true
    }

    private fun useBaseThemeSetting(): Boolean {
        return true
    }

    protected open fun canShowAccountSignIn(): Boolean {
        return true
    }

    private fun canSaveScreenState(): Boolean {
        return true
    }

    private fun enableActionBarBackArrow(enable: Boolean) {
        supportActionBar?.let { actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(enable)
            actionBar.setHomeButtonEnabled(enable)
        }
    }

    protected fun updateNavigationTrail(trail: List<NavigationTrailQuery>?) {
        navigationTrailAdapter?.let { adapter ->
            adapter.changeList(trail)
            if (trail == null || trail.isEmpty()) {
                toolbarExpand?.removeTopDragView()
            } else {
                toolbarExpand?.setTopDragView(mainToolbar)
            }
            showHideToolbarExtra(trail != null && !trail.isEmpty())
        }
    }

    override fun setTitle(title: CharSequence?) {
        title ?: return

        setOverviewTitle(title.toString())

        if (mainToolbarTitleTextView != null) {
            mainToolbarTitleTextView?.text = Html.fromHtml(title.toString())
        } else {
            // some activities use a standard toolbar...
            mainToolbar?.setTitle(title)
        }
    }

    override fun setTitle(@StringRes titleId: Int) {
        setOverviewTitle(getString(titleId))

        if (mainToolbarTitleTextView != null) {
            mainToolbarTitleTextView?.setText(titleId)
        } else {
            // some activities use a standard toolbar...
            mainToolbar?.setTitle(titleId)
        }
    }

    /**
     * Title seen in Android OS "Recent" apps
     */
    private fun setOverviewTitle(title: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && prefs.isScreensInOverview) {
            setTaskDescription(ActivityManager.TaskDescription(title))
        }
    }

    fun setSubTitle(subTitle: String) {
        supportActionBar?.subtitle = subTitle
    }

    override fun onNavigationTrailClick(trailItem: NavigationTrailQuery) {
        when (trailItem.type) {
            ScreenSourceType.CATALOG_DIRECTORY -> {
                val rootCollection = trailItem.id == languageManager.findRootCollectionIdByLanguageId(getLanguageId())
                if (rootCollection) {
                    internalIntents.showCatalogRoot(this, getScreenId())
                } else {
                    internalIntents.showCatalog(this, getScreenId(), trailItem.id)
                }
            }
            ScreenSourceType.CONTENT_DIRECTORY -> internalIntents.showContentDirectory(this, getScreenId(), trailItem.contentItemId, trailItem.id, true, false, Analytics.Referrer.ITEM_NAVIGATION)
            ScreenSourceType.CUSTOM_CATALOG_DIRECTORY -> internalIntents.showCustomCollection(this, getScreenId(), trailItem.id, false)
            ScreenSourceType.NOTES -> internalIntents.showNotes(this, getScreenId())
            else -> Timber.e("Unsupported trail click item: %s", trailItem.type)
        }
    }

    fun setSubtitle(title: CharSequence) {
        supportActionBar?.subtitle = Html.fromHtml(title.toString())
    }

    // If more is added to the mainToolbar then we need handle them here
    private fun showHideToolbarExtra(show: Boolean) {
        val count = navigationTrailAdapter?.itemCount ?: 0
        dropArrowImageView?.setVisible(show && count > 0)
    }

    protected fun collapseNavigationTrail() {
        toolbarExpand?.collapse()
    }

    private fun setArrowRotation(toolBarPercent: Float) {
        if (dropArrowImageView?.visibility == View.GONE) {
            return
        }
        dropArrowImageView?.rotation = ARROW_ROTATION * toolBarPercent
    }

    protected fun setupToolbarAndTrail() {
        toolbarExpand?.let {
            it.setTopDragView(mainToolbar)
            it.setBottomDragView(topLayout!!)
            it.setToolBarExpandListener(this)
            setArrowRotation(it.percent)
        }

        navigationTrailRecyclerView?.let { recyclerView ->
            if (navigationTrailAdapter == null) {
                navigationTrailAdapter = NavigationTrailAdapter()
                recyclerView.layoutManager = LinearLayoutManager(this)
                navigationTrailAdapter?.setOnNavigationTrailClickListener(this)
            }

            recyclerView.adapter = navigationTrailAdapter
        }
    }

    override fun onSupportActionModeStarted(mode: ActionMode) {
        super.onSupportActionModeStarted(mode)
        actionMode = mode
    }

    override fun onSupportActionModeFinished(mode: ActionMode) {
        super.onSupportActionModeFinished(mode)
        actionMode = null
        preventActionModeHide = false
    }

    fun hideActionMode() {
        // this helps us on restore when we have other actions that try to hide
        // it programmatically (like onPageSelected for the reader)
        if (preventActionModeHide) {
            preventActionModeHide = false
            return
        }

        actionMode?.finish()
    }

    fun isActionModeShowing(actionModeCallback: ActionMode.Callback?): Boolean {
        val tag = actionMode?.tag

        return tag != null && actionModeCallback != null && actionModeCallback === tag
    }

    @Suppress("UNUSED_PARAMETER")
    @Subscribe(ThreadMode.MAIN)
    fun handle(event: AccountSignInPromptEvent) {
        if (!canShowAccountSignIn()) {
            return
        }

        bus.removeSticky(AccountSignInPromptEvent::class.java)
        //If the user is not signed in inform them that they will loose annotations on the next update
        if (accountUtil.shouldShowSignInMessage()) {
            accountUtil.showSignInMessage(this)
        }
    }

    @Subscribe(ThreadMode.MAIN)
    fun handle(event: BackgroundSnackbarEvent) {
        val message: String
        if (event.stringRes > 0) {
            message = resources.getString(event.stringRes)
        } else {
            message = event.message
        }

        showActionSnackbar(message, event.action, event.duration)
    }

    protected fun dismissSnackbar() {
        currentSnackbar?.dismiss()
    }

    protected fun showUndoSnackbar(message: CharSequence, undoRunnable: Runnable, commitRunnable: Runnable) {
        showUndoSnackbar(if (topLayout != null) topLayout else window.decorView, message, undoRunnable, commitRunnable)
    }

    private fun showUndoSnackbar(parentView: View?, message: CharSequence,
                                 undoRunnable: Runnable, commitRunnable: Runnable) {
        parentView ?: return

        currentSnackbar = Snackbar.make(parentView, message, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo) { undoRunnable.run() }
                .addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(snackbar: Snackbar?, @DismissEvent event: Int) {
                        if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                            commitRunnable.run()
                        }

                        currentSnackbar = null
                    }
                })

        currentSnackbar?.show()
    }

    protected open fun showActionSnackbar(message: CharSequence, action: SnackbarAction, duration: Int) {
        showActionSnackbar(if (topLayout != null) topLayout else window.decorView, message, action, duration)
    }

    protected fun showActionSnackbar(parentView: View?, message: CharSequence, action: SnackbarAction, duration: Int) {
        parentView ?: return

        currentSnackbar = Snackbar.make(parentView, message, duration)
                .addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        currentSnackbar = null
                    }
                })

        when (action) {
            SnackbarAction.OK -> currentSnackbar?.setAction(R.string.ok) { dismissSnackbar() }
            SnackbarAction.VIEW_DOWNLOADS -> currentSnackbar?.setAction(R.string.view) { internalIntents.showCurrentDownloads(this@BaseActivity, baseScreenId) }
            else -> {
                // Don't add an action
            }
        }

        currentSnackbar?.show()
    }

    fun getLanguageId(): Long {
        return screenUtil.getLanguageIdForScreen(baseScreenId)
    }

    fun getMainToolbar() = mainToolbar

    fun getToolbarExpand() = toolbarExpand

    companion object {
        const val EXTRA_SCREEN_ID = "EXTRA_SCREEN_ID"
        const val EXTRA_REFERRER = "EXTRA_REFERRER"

        private const val ARROW_ROTATION = -180
    }
}
