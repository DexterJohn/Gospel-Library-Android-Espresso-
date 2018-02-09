package org.lds.ldssa.ux.share

import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_share_intent.*
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Parcelable
import me.eugeniomarletti.extras.intent.base.Serializable
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics.Screen
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.util.ShareUtil
import org.lds.mobile.util.LdsStringUtil.containsAny

/**
 * An activity to show the customized view for prompting the user
 * to share.  This is needed to correctly handle the specific share
 * for Facebook, Google+, and Twitter; and to provide a slightly different
 * UI where the supported social networks (mentioned above) are listed at
 * the top and separated from other destinations.
 */
class ShareIntentActivity : BaseActivity() {

    private lateinit var topAppsAdapter: ShareIntentAdapter
    private lateinit var otherAppsAdapter: ShareIntentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)

        setupToolbar()
        setupGrids()
    }

    override fun getLayoutResourceId() = R.layout.activity_share_intent

    override fun getAnalyticsScreenName() = Screen.SHARE_CONTENT

    private fun setupToolbar() {
        setTitle(R.string.share)
        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun setupGrids() {
        val selectItemListener = { resolveInfo: ResolveInfo ->
            onItemSelected(resolveInfo)
        }

        topAppsAdapter = ShareIntentAdapter(this).apply {
            itemSelectedListener = selectItemListener
        }
        topAppGridView.adapter = topAppsAdapter

        otherAppsAdapter = ShareIntentAdapter(this).apply {
            itemSelectedListener = selectItemListener
        }
        bottomAppGridView.adapter = otherAppsAdapter

        populateGrids()
    }

    private fun populateGrids() {
        val sendIntent = Intent(Intent.ACTION_SEND)
        with(IntentOptions) {
            sendIntent.type = intent.mimeType.type
        }

        val activities = packageManager.queryIntentActivities(sendIntent, 0)

        for (info in activities) {
            if (containsAny(info.activityInfo.packageName, ShareUtil.PACKAGE_FACEBOOK, ShareUtil.PACKAGE_GOOGLE_PLUS, ShareUtil.PACKAGE_TWITTER)) {
                topAppsAdapter.add(info)
            } else {
                otherAppsAdapter.add(info)
            }
        }
    }

    private fun onItemSelected(info: ResolveInfo) {
        val intent = intent
        with(IntentOptions) {
            intent.shareResultInfo = info
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    object IntentOptions {
        var Intent.mimeType by IntentExtra.Serializable(defaultValue = ShareUtil.MimeType.TEXT_PLAIN)
        var Intent.shareResultInfo by IntentExtra.Parcelable<ResolveInfo>()
    }
}
