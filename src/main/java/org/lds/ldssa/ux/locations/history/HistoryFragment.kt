package org.lds.ldssa.ux.locations.history

import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import butterknife.OnClick
import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder
import kotlinx.android.synthetic.main.fragment_history.*
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.ContentIntentParams
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.model.database.gl.history.History
import org.lds.ldssa.model.database.gl.history.HistoryManager
import org.lds.ldssa.ui.fragment.BaseFragment
import org.lds.ldssa.ui.loader.HistoryLoader
import org.lds.mobile.ui.ext.executeOnValidPosition
import org.lds.mobile.ui.setVisible
import java.util.LinkedList
import javax.inject.Inject

class HistoryFragment : BaseFragment(), LoaderManager.LoaderCallbacks<List<History>>, ClickableViewHolder.OnClickListener {
    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var historyManager: HistoryManager

    private lateinit var adapter: HistoryAdapter

    override fun getLayoutResourceId() = R.layout.fragment_history

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Injector.get().inject(this)

        setupRecyclerView()
        loaderManager.initLoader(0, null, this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?) = HistoryLoader(context)

    override fun onLoadFinished(loader: Loader<List<History>>?, data: List<History>) {
        adapter.set(data)

        emptyStateIndicator.setVisible(adapter.itemCount == 0)
        clearHistoryView.setVisible(adapter.itemCount > 0)
    }

    override fun onLoaderReset(loader: Loader<List<History>>) {
        adapter.clear()
        emptyStateIndicator?.visibility = View.VISIBLE
        clearHistoryView?.visibility = View.GONE
    }

    override fun onClick(viewHolder: ClickableViewHolder) = viewHolder.executeOnValidPosition {
        adapter.getItem(adapter.getChildPosition(it))?.let { history ->
            val params = ContentIntentParams(screenId, history.contentItemId, history.itemUri, Analytics.Referrer.HISTORY)
            params.scrollPosition = history.scrollPosition
            internalIntents.showContent(activity, params)
        }
    }

    @OnClick(R.id.clearHistoryView)
    fun onClearHistoryClicked() {
        onLoadFinished(null, LinkedList())

        showUndoSnackbar(getString(R.string.history_cleared), Runnable { loaderManager.initLoader(0, null, this@HistoryFragment) }, Runnable { historyManager.deleteAll() })
    }

    private fun setupRecyclerView() {
        adapter = HistoryAdapter().apply {
            setOnClickListener(this@HistoryFragment)
        }

        itemsRecyclerView.adapter = adapter
        itemsRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    companion object {
        fun newInstance(screenId: Long): HistoryFragment {
            val args = BaseFragment.getBaseBundle(screenId)

            val fragment = HistoryFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
