package org.lds.ldssa.ux.signin

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import kotlinx.android.synthetic.main.toolbar_actionbar.*
import org.lds.ldsaccount.LDSAccountEnvironment
import org.lds.ldsaccount.ui.BaseSignInActivity
import org.lds.ldsaccount.ui.SignInFragment
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.util.AccountUtil
import javax.inject.Inject

class SignInActivity : BaseSignInActivity() {
    @Inject
    lateinit var accountUtil: AccountUtil
    @Inject
    lateinit var analytics: Analytics

    private var mainToolbar: Toolbar? = null

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainToolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(mainToolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setHomeButtonEnabled(true)
            mainToolbarTitleTextView.setText(R.string.app_name)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override val layoutResourceId: Int
        get() = R.layout.activity_signin

    override val fragmentContainerId: Int
        get() = R.id.signinFragmentLayout

    override fun getSignInFragment(): SignInFragment {
        return SignInFragment.Builder(LDSAccountEnvironment.PROD)
                .build()
    }

    override fun onSignInSuccess(): Boolean {
        // process the signin
        accountUtil.onSuccessfulSignIn()

        // true to finish the activity automatically
        return true
    }

    override fun onResume() {
        super.onResume()
        analytics.postScreen(Analytics.Screen.SIGN_IN_VIEW)
    }
}
