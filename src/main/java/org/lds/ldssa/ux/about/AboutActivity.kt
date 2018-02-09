package org.lds.ldssa.ux.about

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.util.ThemeUtil
import org.lds.mobile.about.AboutFragment
import org.lds.mobile.about.AppListFragment
import org.lds.mobile.about.CommonLibrary
import org.lds.mobile.about.Library
import org.lds.mobile.about.License
import javax.inject.Inject

class AboutActivity : AppCompatActivity(), AboutFragment.DevModeListener {

    enum class ViewType {
        ABOUT, LIBRARIES, OTHER_APPS
    }

    @Inject
    lateinit var prefs: Prefs
    @Inject
    lateinit var analytics: Analytics
    @Inject
    lateinit var themeUtil: ThemeUtil

    private var mainToolbar: Toolbar? = null

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        themeUtil.applyTheme(this)   // must set theme or get a crash in support library

        setContentView(R.layout.activity_about)

        mainToolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val viewType = intent.getSerializableExtra(EXTRA_VIEW_TYPE) as ViewType
        if (viewType == ViewType.OTHER_APPS) {
            mainToolbarTitleTextView.setText(R.string.featured_apps)
        } else {
            mainToolbarTitleTextView.setText(R.string.app_name)
        }

        // On Rotation
        if (savedInstanceState == null) {
            when (viewType) {
                ViewType.ABOUT -> loadAboutFragment()
                ViewType.OTHER_APPS -> loadOtherAppsFragment()
                else -> {
                    // do nothing
                }
            }
        } else {
            val supportFragmentManager = supportFragmentManager
            val fragment = supportFragmentManager.findFragmentByTag(AboutFragment.TAG)
            (fragment as? AboutFragment)?.setDevModeListener(this)
        }
    }

    override fun onResume() {
        super.onResume()
        analytics.postScreen(Analytics.Screen.ABOUT_VIEW)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun loadAboutFragment() {
        val fragment = AboutFragment.Builder()
                .addCommonLibrary(
                        CommonLibrary.ANDROID_SUPPORT,
                        CommonLibrary.APACHE_COMMONS_LANG,
                        CommonLibrary.BUTTER_KNIFE,
                        CommonLibrary.DAGGER_GOOGLE,
                        CommonLibrary.THREE_TEN_ABP,
                        CommonLibrary.DB_TOOLS,
                        CommonLibrary.POCKET_KNIFE,
                        CommonLibrary.RETROFIT,
                        CommonLibrary.OK_HTTP,
                        CommonLibrary.GSON,
                        CommonLibrary.EXO_MEDIA,
                        CommonLibrary.RECYCLER_EXTENSIONS,
                        CommonLibrary.GLIDE,
                        CommonLibrary.ANDROID_JOB,
                        CommonLibrary.APP_INTRO,
                        CommonLibrary.RX_JAVA,
                        CommonLibrary.RX_ANDROID,
                        CommonLibrary.POCKET_BUS,
                        CommonLibrary.PLAYLIST_CORE,
                        CommonLibrary.MATERIAL_DIALOGS
                )
                .addCustomLibrary(Library("PrettyTime", "Copyright 2012 Lincoln Baxter, III", License.APACHE_V2))
                .addCustomLibrary(Library("MaterialProgressBar", "Copyright 2015 Zhang Hai", License.APACHE_V2))
                .addCustomLibrary(Library("JSoup", "Copyright 2015 Jonathan Hedley", License.MIT))
                .build()
        fragment.setDevModeListener(this)
        supportFragmentManager.beginTransaction()
                .replace(R.id.aboutFragmentLayout, fragment, AboutFragment.TAG)
                .commit()
    }

    private fun loadOtherAppsFragment() {
        val fragment = AppListFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.aboutFragmentLayout, fragment, AppListFragment.TAG)
                .commit()
    }

    override fun onClick(count: Int) {
        // every 5 taps, toggle
        if (count % DEV_MODE_CLICK_COUNT == 0) {
            val enabled = prefs.toggleDeveloperMode()
            val stringResId: Int = if (enabled) R.string.developer_mode_enabled else R.string.developer_mode_disabled
            Snackbar.make(aboutLayout, stringResId, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onLongClick(count: Int): Boolean {
        return false
    }

    companion object {

        private val DEV_MODE_CLICK_COUNT = 5
        const val EXTRA_VIEW_TYPE = "EXTRA_VIEW_TYPE"
    }
}