package org.lds.ldssa.ux.welcome

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import com.github.paolorotolo.appintro.AppIntro
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Serializable
import org.lds.ldsaccount.LDSAccountEnvironment
import org.lds.ldsaccount.LDSAccountUtil
import org.lds.ldsaccount.ui.SignInFragment
import org.lds.ldssa.BuildConfig
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.util.AccountUtil
import org.lds.ldssa.util.ScreenLauncherUtil
import org.lds.ldssa.ux.tips.pages.tip.TipFragment
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.extras.SelfActivityCompanion
import javax.inject.Inject

class WelcomeActivity : AppIntro(), SignInFragment.OnSignInListener {
    @Inject
    lateinit var prefs: Prefs
    @Inject
    lateinit var cc: CoroutineContextProvider
    @Inject
    lateinit var ldsAccountUtil: LDSAccountUtil
    @Inject
    lateinit var screenLauncherUtil: ScreenLauncherUtil
    @Inject
    lateinit var accountUtil: AccountUtil
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: WelcomeViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(WelcomeViewModel::class.java) }

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // all pages MUST be setup in onCreate
        viewModel.tipIdList = intent.tipIds
        setupPages()
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        if (!ldsAccountUtil.hasCredentials()) {
            val slides = slides
            slides.indices.forEach { i ->
                if (slides[i] is SignInFragment) {
                    pager.currentItem = i
                    return
                }
            }
        }

        loadMainActivity()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        loadMainActivity()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val currentPosition = pager.currentItem
        if (currentPosition > 0) {
            pager.currentItem = currentPosition - 1
            return
        }

        finish()
    }

    override fun onSignInSuccess(): Boolean {
        // process the signin
        accountUtil.onSuccessfulSignIn()

        loadMainActivity()
        return true
    }

    private fun setupPages() {
        setSkipText(getString(R.string.skip))
        setDoneText(getString(R.string.done))

        // Add all welcome tips
        val title = getString(R.string.whats_new)
        viewModel.tipIdList.forEach { tipId ->
            addSlide(TipFragment.newInstance(this@WelcomeActivity, 0L, tipId, title))
        }

        // Add sign-in
        if (!ldsAccountUtil.hasCredentials()) {
            addSlide(getSignInFragment())
                setDoneText(getString(R.string.skip))
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // causes theme issues with pre 5.0 devices
            setBarColor(ContextCompat.getColor(this, R.color.theme_welcome_primary))
        }

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                viewModel.logAnalyticsTipViewed(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        pager.adapter?.notifyDataSetChanged()
    }

    private fun getSignInFragment(): SignInFragment {
        val signInFragment = SignInFragment.Builder(LDSAccountEnvironment.PROD)
                .message(getString(R.string.lds_account_summary))
                .requestFocus(false)
                .showToolbar(true)
                .toolbarTitle(getString(R.string.signin))
                .build()

        signInFragment.setSignInListener(this)
        return signInFragment
    }

    private fun loadMainActivity() {
        // indicate that the welcome screen has been viewed
        prefs.lastViewedWelcomeTipsAppVersion = BuildConfig.WELCOME_TIPS_VERSION

        screenLauncherUtil.showStartupScreen(this)
    }

    companion object : SelfActivityCompanion<Companion>(WelcomeActivity::class) {
        var Intent.tipIds by IntentExtra.Serializable<ArrayList<Long>>(defaultValue = ArrayList())
    }
}
