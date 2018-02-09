package org.lds.ldssa.ux.currentdownloads

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.activity_current_downloads.*
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.download.GLDownloadManager
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.mobile.ui.setVisible
import org.lds.mobile.ui.util.LdsDrawableUtil
import javax.inject.Inject

class CurrentDownloadsActivity : BaseActivity(){

    @Inject
    lateinit var glDownloadManager: GLDownloadManager
    @Inject
    lateinit var downloadQueueItemManager: DownloadQueueItemManager

    private var adapter = CurrentDownloadsAdapter(this).apply {
        cancelDownloadClickListener = {androidDownloadId ->
            glDownloadManager.cancelDownload(androidDownloadId)
        }
    }

    private var cancelAllMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)
        title = getString(R.string.current_downloads)

        // setup recycleView
        downloadsRecyclerView.adapter = adapter
        downloadsRecyclerView.layoutManager = LinearLayoutManager(this)

        downloadQueueItemManager.findAllForDisplayLiveData().observe(this, Observer { data ->
            data ?: return@Observer

            adapter.list = data
            updateVisibilitiesAndTitle()
        })
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_current_downloads
    }

    override fun getAnalyticsScreenName(): String {
        return Analytics.Screen.CURRENT_DOWNLOADS
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.menu_downoading, menu)
        LdsDrawableUtil.tintAllMenuIconsForTheme(this, menu, R.attr.themeStyleColorToolbarIcon)

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)

        cancelAllMenuItem = menu.findItem(R.id.menu_item_cancel_all)
        cancelAllMenuItem?.isVisible = adapter.list.isNotEmpty()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_cancel_all) {
            showCancelAllDialog()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateVisibilitiesAndTitle() {
        val hasContent = adapter.list.isNotEmpty()
        downloadsRecyclerView.setVisible(hasContent)
        emptyStateIndicator.setVisible(!hasContent)

        //Updates the cancel all menu item visibility
        cancelAllMenuItem?.isVisible = hasContent

        //Updates the title
        val title = StringBuilder(getString(R.string.current_downloads))
        if (hasContent) {
            title.append(getString(R.string.space_parentheses_number, adapter.itemCount))
        }

        setTitle(title.toString())
    }

    private fun showCancelAllDialog() {
        MaterialDialog.Builder(this)
                .title(R.string.download_cancel)
                .content(R.string.download_cancel_confirm_all)
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .onPositive { _, _ ->
                    glDownloadManager.cancelAllDownloads()
                }
                .show()
    }
}
