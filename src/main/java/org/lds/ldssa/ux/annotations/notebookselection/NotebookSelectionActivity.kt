package org.lds.ldssa.ux.annotations.notebookselection

import android.app.Activity
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_notebook_selection.*
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.userdata.notebookview.NotebookView
import org.lds.ldssa.model.prefs.type.NotebookSortType
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.util.NotebookUtil
import org.lds.mobile.ui.setVisible
import org.lds.mobile.ui.util.LdsDrawableUtil
import timber.log.Timber
import javax.inject.Inject


class NotebookSelectionActivity : BaseActivity() {

    @Inject
    lateinit var notebookUtil: NotebookUtil
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(NotebookSelectionViewModel::class.java) }

    private var annotationId: Long = 0

    private val adapter = NotebookSelectionAdapter().apply {
        notebookCheckChangedListener = {notebookId, checked ->
            Timber.w("Foo: Checked")
            if (checked) {
                viewModel.addToNotebook(notebookId)
            } else {
                viewModel.removeFromNotebook(notebookId)
            }
        }
    }

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(IntentOptions) {
            annotationId = intent.annotationId
        }

        setupToolbar()
        setupTable()

        notebookSelectionFloatingActionButton.setOnClickListener {
            notebookUtil.promptAdd(this)
        }

        setupViewModelObservers()

        viewModel.setAnnotationId(annotationId)
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_notebook_selection
    }

    override fun getAnalyticsScreenName(): String {
        return Analytics.Screen.NOTE_SELECTION
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_notebook_filter, menu)
        LdsDrawableUtil.tintAllMenuIconsForTheme(this, menu, R.attr.themeStyleColorToolbarActionModeIcon)

        menu.findItem(NotebookSortType.getSortMenuItemId(prefs.notebookSortType)).isChecked = true

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finishWithResult(Activity.RESULT_OK)
                return true
            }
            R.id.notebook_selection_menu_sort_recent -> {
                item.isChecked = true
                viewModel.setSortType(NotebookSortType.MOST_RECENT)
                return true
            }
            R.id.notebook_selection_menu_sort_name -> {
                item.isChecked = true
                viewModel.setSortType(NotebookSortType.ALPHABETICAL)
                return true
            }
            R.id.notebook_selection_menu_sort_count -> {
                item.isChecked = true
                viewModel.setSortType(NotebookSortType.COUNT)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setupViewModelObservers() {
        viewModel.notebookList.observeNotNull { data ->
            displayNotebooks(data)
        }
    }

    private fun setupToolbar() {
        setTitle(R.string.add_to_notebook)

        //Updates the home icon to be a check mark
        getMainToolbar()?.navigationIcon = LdsDrawableUtil.tintDrawableForTheme(this, R.drawable.ic_lds_action_done_24dp, R.attr.colorAccent)
    }

    private fun setupTable() {
        notebookSelectionRecyclerView.layoutManager = LinearLayoutManager(this)
        notebookSelectionRecyclerView.adapter = adapter
    }

    private fun displayNotebooks(data: List<NotebookView>) {
        adapter.list = data

        //Updates the view visibilities
        val hasItems = data.isNotEmpty()
        notebookSelectionRecyclerView.setVisible(hasItems, View.INVISIBLE)
        emptyStateIndicator.setVisible(!hasItems)
    }

    private fun finishWithResult(resultCode: Int) {
        val resultIntent = Intent()
        with(IntentOptions) {
            resultIntent.annotationId = annotationId
        }
        setResult(resultCode, resultIntent)

        finish()
    }

    object IntentOptions {
        var Intent.annotationId by IntentExtra.Long(defaultValue = 0L)
    }
}