package org.lds.ldssa.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.util.ScreenLauncherUtil
import org.lds.ldssa.util.ScreenUtil
import javax.inject.Inject

class BookmarkRouterActivity : AppCompatActivity() {
    @Inject
    lateinit var internalIntents: InternalIntents
    @Inject
    lateinit var screenUtil: ScreenUtil
    @Inject
    lateinit var screenLauncherUtil: ScreenLauncherUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)

        val intent = intent
        if (intent == null) {
            screenLauncherUtil.showStartupScreen(this)
            return
        }

        val bookmarkId = intent.getLongExtra(EXTRA_BOOKMARK_ID, -1)
        if (bookmarkId < 0) {
            screenLauncherUtil.showStartupScreen(this)
            return
        }

        internalIntents.showContentFromBookmark(this, screenUtil.newScreenFromScreenId(ScreenUtil.NEW_SCREEN_ID), bookmarkId, true)
    }

    companion object {
        const val EXTRA_BOOKMARK_ID = "EXTRA_BOOKMARK_ID"
    }
}
