package org.lds.ldssa.ux.tips.lists.listitems

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_tips_list.*
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Boolean
import me.eugeniomarletti.extras.bundle.base.Int
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.ui.fragment.BaseFragment
import org.lds.ldssa.util.LanguageUtil
import org.lds.ldssa.util.TipsUtil
import org.lds.ldssa.util.getScrollPosition
import org.lds.mobile.extras.SelfFragmentCompanion
import org.lds.mobile.ui.setVisible
import javax.inject.Inject

class TipListFragment : BaseFragment() {

    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var languageUtil: LanguageUtil
    @Inject
    lateinit var tipsUtil: TipsUtil
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: TipListViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(TipListViewModel::class.java) }

    private val adapter: TipListAdapter by lazy {
        TipListAdapter(cc).apply {
            onClickListener = { tipQuery ->
                val tipIds = tipsUtil.getTipIds(languageUtil.deviceLanguageId, viewModel.getTipType())
                internalIntents.showAllTipDetails(activity, tipQuery.id, tipIds)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)

        setupViewModelObservers()

        arguments?.let {
            viewModel.setTipListData(it.byName)
        }
    }

    override fun onPostViewCreated() {
        super.onPostViewCreated()
        setupRecyclerView()
    }

    override fun getLayoutResourceId() = R.layout.fragment_tips_list

    fun getScrollPosition() = tipRecyclerView.getScrollPosition()

    private fun setupViewModelObservers() {
        viewModel.tipsList.observeNotNull { tipsList ->
            emptyStateIndicator.setVisible(tipsList.isEmpty())
            adapter.items = tipsList
        }
    }

    private fun setupRecyclerView() {
        tipRecyclerView.adapter = adapter
        tipRecyclerView.layoutManager = LinearLayoutManager(context)
    }


    companion object : SelfFragmentCompanion<Companion>(TipListFragment::class) {
        private var Bundle.byName by BundleExtra.Boolean(false)
        private var Bundle.scrollPosition by BundleExtra.Int(0)

        fun newInstance(context: Context, screenId: Long, byName: Boolean, scrollPosition: Int): TipListFragment {
            return instantiate(context) {
                BaseFragment.getBaseBundle(it, screenId)
                it.byName = byName
                it.scrollPosition = scrollPosition
            }
        }
    }
}
