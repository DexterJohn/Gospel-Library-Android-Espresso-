package org.lds.ldssa.ux.locations

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import kotlinx.android.synthetic.main.activity_locations.*
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import me.eugeniomarletti.extras.intent.base.String
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.ui.adapter.LocationTabType
import org.lds.mobile.ui.animation.FabHideAnimation
import org.lds.mobile.ui.animation.FabShowAnimation
import java.util.ArrayList

class LocationsActivity : BaseActivity(), ViewPager.OnPageChangeListener {

    private lateinit var pagerAdapter: LocationsPagerAdapter

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupPager()

        locationsFab.setOnClickListener {
            pagerAdapter.onNewClick(locationsViewPager.currentItem)
        }
    }

    override fun onPause() {
        prefs.locationsPagerPosition = locationsViewPager.currentItem
        super.onPause()
    }

    override fun onResume() {
        super.onResume()

        //Makes sure the fab is visible on load
        onPageSelected(locationsViewPager.currentItem)
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_locations
    }

    override fun getAnalyticsScreenName(): String {
        return Analytics.Screen.LOCATIONS
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        //Purposefully left blank
    }

    override fun onPageSelected(position: Int) {
        showFab(pagerAdapter.showFab(position))
    }

    override fun onPageScrollStateChanged(state: Int) {
        //Purposefully left blank
    }

    override fun canShowAccountSignIn(): Boolean {
        return false
    }

    private fun setupPager() {
        val visibleTabs = ArrayList<LocationTabType>()
        visibleTabs.add(LocationTabType.BOOKMARKS)
        visibleTabs.add(LocationTabType.TABS)
        visibleTabs.add(LocationTabType.HISTORY)

        with (IntentOptions) {
            pagerAdapter = LocationsPagerAdapter(supportFragmentManager, getScreenId(), visibleTabs, intent.contentItemId, intent.subItemId, intent.referenceParagraphAid)
        }
        locationsViewPager.adapter = pagerAdapter

        locationsViewPager.addOnPageChangeListener(this)
        locationsViewPager.offscreenPageLimit = PAGER_OFFSCREEN_PAGE_LIMIT

        // select position... make sure that the position is selectable
        var selectedPosition = prefs.locationsPagerPosition
        if (selectedPosition > visibleTabs.size - 1) {// -1 because selectedPosition is 0 based
            selectedPosition = 0
        }
        locationsViewPager.currentItem = selectedPosition

        locationsTabLayout.setupWithViewPager(locationsViewPager)
    }

    private fun showFab(visible: Boolean) {
        val currentlyVisible = locationsFab.visibility == View.VISIBLE
        if (visible == currentlyVisible) {
            return
        }

        if (visible) {
            locationsFab.startAnimation(FabShowAnimation(locationsFab))
        } else {
            locationsFab.startAnimation(FabHideAnimation(locationsFab))
        }
    }

    override fun finish() {
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        super.finish()
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, LocationsActivity::class) {
        private const val PAGER_OFFSCREEN_PAGE_LIMIT = 2 // load all fragments and don't destroy any of them
        const val ADD_DELAY_LENGTH = 300L
    }

    object IntentOptions {
        var Intent.contentItemId by IntentExtra.Long(defaultValue = 0L)
        var Intent.subItemId by IntentExtra.Long(defaultValue = 0L)
        var Intent.referenceParagraphAid by IntentExtra.String(defaultValue = "")
    }
}
