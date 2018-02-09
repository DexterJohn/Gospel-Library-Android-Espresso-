package org.lds.ldssa.ux.locations.screens

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.afollestad.materialdialogs.MaterialDialog
import com.devbrackets.android.recyclerext.adapter.helper.SimpleElevationItemTouchHelperCallback
import com.devbrackets.android.recyclerext.adapter.viewholder.MenuViewHolder
import com.devbrackets.android.recyclerext.layoutmanager.AutoColumnGridLayoutManager
import kotlinx.android.synthetic.main.fragment_screens.*
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.model.database.userdata.screen.Screen
import org.lds.ldssa.model.database.userdata.screen.ScreenManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.ui.fragment.BaseFragment
import org.lds.ldssa.ui.loader.ScreensLoader
import org.lds.ldssa.util.ScreenLauncherUtil
import org.lds.ldssa.util.ScreenUtil
import org.lds.ldssa.ux.locations.LocationsActivity
import org.lds.mobile.ui.setVisible
import org.lds.mobile.ui.util.LdsDrawableUtil
import javax.inject.Inject

class ScreensFragment : BaseFragment(), LoaderManager.LoaderCallbacks<List<Screen>>, MenuViewHolder.OnMenuItemSelectedListener {
    @Inject
    lateinit var screenUtil: ScreenUtil
    @Inject
    lateinit var screenLauncherUtil: ScreenLauncherUtil
    @Inject
    lateinit var screenManager: ScreenManager
    @Inject
    lateinit var prefs: Prefs
    @Inject
    lateinit var internalIntents: InternalIntents

    private val adapter by lazy { ScreensAdapter(screenId) }
    private lateinit var itemTouchHelper: ItemTouchHelper

    init {
        // Make sure that the injects occur when newInstance is called
        Injector.get().inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        setupRecyclerView()
        loaderManager.initLoader(0, null, this)
    }

    override fun onPause() {
        super.onPause()
        saveScreenOrder() // do this in onPause() (instead of onStop()) because onStart() is called before onStop() (which can cause issues)
    }

    override fun getLayoutResourceId() = R.layout.fragment_screens

    override fun onCreateLoader(i: Int, bundle: Bundle?) = ScreensLoader(context)

    override fun onLoadFinished(loader: Loader<List<Screen>>, data: List<Screen>) {
        adapter.clear()
        adapter.addAll(data)

        itemTouchHelper.attachToRecyclerView(if (adapter.itemCount == 1) null else screensRecyclerView)
        showListView(true)
    }

    override fun onLoaderReset(loader: Loader<List<Screen>>) {
        adapter.clear()
    }

    fun onNewClick() {
        val screen = screenUtil.newScreen(screenUtil.getLanguageIdForScreen(screenId))
        adapter.add(0, screen)
        adapter.setNewItem(screen.id)

        // delay to allow the user to see the new screen appear, then show screen content
        Handler().postDelayed({
            activity?.let { activity ->
                screenLauncherUtil.onScreenClick(activity, screen.id, true, false)
                activity.finish()
            }
        }, LocationsActivity.ADD_DELAY_LENGTH)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // screens in overview option should ONLY be shown on Lollipop+ devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            inflater.inflate(R.menu.menu_tabs, menu)
            context?.let {
                LdsDrawableUtil.tintAllMenuIconsForTheme(it, menu, R.attr.themeStyleColorToolbarIcon)
            }
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_tabs_in_overview -> {
                internalIntents.showScreenSettings(activity)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onMenuItemSelected(viewHolder: MenuViewHolder, menuItem: MenuItem): Boolean {
        adapter.getItem(viewHolder.adapterPosition)?.let { item ->
            return when (menuItem.itemId) {
                R.id.context_menu_item_rename_tab -> {
                    showRenameScreenDialog(item)
                    true
                }
                R.id.context_menu_item_delete_tab -> {
                    removeScreen(item)
                    true
                }
                else -> false
            }
        }
        return false
    }

    private fun setupRecyclerView() {
        adapter.clickListener = { clickedScreenId ->
            activity?.let { activity ->
                // check to see if the we just clicked on our own tab
                if (screenId == clickedScreenId) {
                    // if so, then just close this screen
                    activity.finish()
                } else {
                    // allow the screen to finish (if in overview)
                    screenLauncherUtil.onScreenClick(activity, clickedScreenId, true, false)
                }
            }
        }
        adapter.setOnTabMenuListener(this)
        screensRecyclerView.adapter = adapter

        val columnWidth = resources.getDimensionPixelSize(R.dimen.tab_card_width)
        val minPadding = resources.getDimensionPixelOffset(R.dimen.tab_min_card_separation_padding)
        activity?.let {
            val manager = AutoColumnGridLayoutManager(it, columnWidth).apply {
                setSpacingMethod(AutoColumnGridLayoutManager.SpacingMethod.ALL)
                setMatchRowAndColumnSpacing(true)
                setMinColumnSpacing(minPadding / 2, minPadding)
            }
            screensRecyclerView.layoutManager = manager
        }

        setupRecyclerTouchHelper()
    }

    private fun setupRecyclerTouchHelper() {
        val dragDirections = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val dragElevationChange = context?.resources?.getDimensionPixelSize(R.dimen.drag_elevation_increase)?.toFloat() ?: 0f

        itemTouchHelper = ItemTouchHelper(object : SimpleElevationItemTouchHelperCallback(dragDirections, 0, dragElevationChange) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val originalPosition = viewHolder.adapterPosition
                val endPosition = target.adapterPosition
                if (originalPosition == RecyclerView.NO_POSITION || endPosition == RecyclerView.NO_POSITION) {
                    return false
                }
                adapter.move(originalPosition, endPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //Swipe not supported
            }
        })

        itemTouchHelper.attachToRecyclerView(screensRecyclerView)
    }

    private fun saveScreenOrder() {
        if (adapter.itemCount == 0) {
            return
        }

        // Save the new tab ordering
        screenManager.beginTransaction()
        val screenCount = adapter.itemCount
        for (i in 0 until screenCount) {
            adapter.getItem(i)?.let {
                it.displayOrder = i
                screenManager.save(it)
            }
        }
        screenManager.endTransaction(true)
    }

    private fun showListView(shown: Boolean) {
        screensRecyclerView.setVisible(shown)
        loadingView.setVisible(!shown)
    }

    private fun removeScreen(screen: Screen) {
        if (screenUtil.removeScreen(activity, screen.id)) {
            adapter.remove(screen)
            if (adapter.itemCount == 1) {
                itemTouchHelper.attachToRecyclerView(null)
            }
        }
    }

    private fun showRenameScreenDialog(screen: Screen) {
        activity?.let {
            MaterialDialog.Builder(it)
                    .title(R.string.rename_screen)
                    .positiveText(R.string.ok)
                    .negativeText(R.string.cancel)
                    .input(null, screen.name) { _, input ->
                        screenUtil.renameScreen(screen.id, input.toString())

                        screen.name = input.toString()
                        adapter.notifyItemChanged(adapter.getPosition(screen))
                    }
                    .show()
        }
    }

    companion object {

        fun newInstance(screenId: Long): ScreensFragment {
            val args = BaseFragment.getBaseBundle(screenId)

            return ScreensFragment().apply {
                arguments = args
            }
        }
    }
}
