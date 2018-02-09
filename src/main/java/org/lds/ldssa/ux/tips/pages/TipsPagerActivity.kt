package org.lds.ldssa.ux.tips.pages

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.TextView
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.IndicatorController
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import me.eugeniomarletti.extras.intent.base.Serializable
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.types.TipType
import org.lds.ldssa.ux.tips.pages.tip.TipFragment
import org.lds.mobile.extras.SelfActivityCompanion
import javax.inject.Inject

class TipsPagerActivity : AppIntro() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: TipsPagerViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(TipsPagerViewModel::class.java) }

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // causes theme issues with pre 5.0 devices
            setBarColor(ContextCompat.getColor(this, R.color.theme_welcome_primary))
        }

        isProgressButtonEnabled = false
        showSkipButton(false)

        setCustomIndicator(CustomIndicatorController())

        // all pages MUST be setup in onCreate
        viewModel.tipIdList = intent.tipIds
        setupPages()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                viewModel.logAnalyticsTipViewed(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        if (savedInstanceState == null) {
            pager.currentItem = viewModel.tipIdList.indexOf(intent.showTipId)
        }
    }

    private fun setupPages() {
        val title = getString(if (viewModel.tipType == TipType.REGULAR) R.string.tips else R.string.whats_new)
        viewModel.tipIdList.forEach { tipId ->
            addSlide(TipFragment.newInstance(this@TipsPagerActivity, 0L, tipId, title))
        }
    }

    override fun onBackPressed() {
        finish()
    }

    private inner class CustomIndicatorController : IndicatorController {
        private var textView: TextView? = null
        private var slideCount: Int = 0

        override fun newInstance(context: Context): View? {
            textView = View.inflate(context, R.layout.tips_custom_indicator, null) as TextView
            return textView
        }

        override fun initialize(slideCount: Int) {
            this.slideCount = slideCount
            selectPosition(0)
        }

        override fun selectPosition(index: Int) {
            textView?.text = getString(R.string.find_count, index + 1, slideCount)
        }

        override fun setSelectedIndicatorColor(color: Int) {}
        override fun setUnselectedIndicatorColor(color: Int) {}
    }

    companion object : SelfActivityCompanion<Companion>(TipsPagerActivity::class) {
        var Intent.showTipId by IntentExtra.Long(defaultValue = 0L)
        var Intent.tipIds by IntentExtra.Serializable<ArrayList<Long>>(defaultValue = ArrayList())
    }
}
