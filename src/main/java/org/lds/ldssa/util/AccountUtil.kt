package org.lds.ldssa.util

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.coroutines.experimental.launch
import org.apache.commons.lang3.StringUtils
import org.lds.ldsaccount.LDSAccountPrefs
import org.lds.ldsaccount.LDSAccountUtil
import org.lds.ldssa.R
import org.lds.ldssa.event.account.AccountSignOutEvent
import org.lds.ldssa.intent.InternalIntents
import org.lds.ldssa.job.AnnotationSyncJob
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.sync.AnnotationSync
import org.lds.mobile.coroutine.CoroutineContextProvider
import pocketbus.Bus
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountUtil @Inject
constructor(private val application: Application,
            private val bus: Bus,
            private val internalIntents: InternalIntents,
            private val userdataDbUtil: UserdataDbUtil,
            private val prefs: Prefs,
            private val ldsAccountPrefs: LDSAccountPrefs,
            private val ldsAccountUtil: LDSAccountUtil,
            private val annotationManager: AnnotationManager,
            private val catalogUpdateUtil: CatalogUpdateUtil,
            private val cc: CoroutineContextProvider,
            private val annotationSync: AnnotationSync) {

    fun onSuccessfulSignIn() {
        Timber.d("Sign-in [true]")

        val newUsername = ldsAccountPrefs.username

        if (StringUtils.isBlank(newUsername)) {
            return
        }

        // sync immediately
        launch(cc.commonPool) {
            annotationSync.sync(downloadRoleBasedCatalogPostSync = true)
        }
    }

    fun signOutPrompt(fragment: Fragment) {
        fragment.activity?.let { context ->
            MaterialDialog.Builder(context)
                    .title(R.string.signin_prefs_signout)
                    .content(R.string.dialog_sign_out_confirmation)
                    .positiveText(R.string.ok)
                    .negativeText(R.string.cancel)
                    .onPositive { _, _ -> signOutCheckExistingAnnotationsPrompt(fragment) }
                    .show()
        }
    }

    fun signInPrompt(fragment: Fragment) {
        val count = annotationManager.findCount()

        if (count > 0) {
            signInWithExistingAnnotationsPrompt(fragment, count)
        } else {
            signIn(fragment)
        }
    }

    fun shouldShowSignInMessage(): Boolean {
        if (StringUtils.isBlank(ldsAccountPrefs.username) || !ldsAccountPrefs.hasCredentials()) {
            val lastWarning = prefs.miscSyncWarningTimestamp
            return System.currentTimeMillis() - lastWarning > ONE_DAY
        }

        return false
    }

    fun showSignInMessage(activity: AppCompatActivity) {
        MaterialDialog.Builder(activity)
                .title(R.string.backup_annotations_title)
                .content(R.string.backup_annotations_message)
                .positiveText(R.string.signin)
                .negativeText(R.string.no_thanks)
                .neutralText(R.string.signin_create_account)
                .onPositive { _, _ -> internalIntents.showSignIn(activity) }
                .onNeutral { _, _ ->
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(LDSAccountUtil.DEFAULT_CREATE_URL)
                    activity.startActivity(intent)
                }
                .show()
        prefs.miscSyncWarningTimestamp = System.currentTimeMillis()
    }

    private fun signInWithExistingAnnotationsPrompt(fragment: Fragment, count: Long) {
        fragment.activity?.let { context ->
            MaterialDialog.Builder(context)
                    .title(R.string.existing_annotations)
                    .content(application.getString(R.string.existing_annotations_prompt, count))
                    .positiveText(R.string.keep)
                    .negativeText(R.string.remove)
                    .neutralText(R.string.cancel)
                    .onPositive { _, _ -> signIn(fragment) }
                    .onNegative { _, _ ->
                        removedUserdata()
                        signIn(fragment)
                    }
                    .show()
        }
    }

    private fun signIn(fragment: Fragment) {
        fragment.activity?.let { context ->
            internalIntents.showSignIn(context)
        }
    }

    private fun signOutCheckExistingAnnotationsPrompt(fragment: Fragment) {
        val annotationCount = annotationManager.findCount()

        val context = fragment.activity
        if (annotationCount > 0 && context != null) {
            MaterialDialog.Builder(context)
                    .title(R.string.existing_annotations)
                    .content(application.getString(R.string.existing_annotations_prompt, annotationCount))
                    .positiveText(R.string.keep)
                    .negativeText(R.string.remove)
                    .neutralText(R.string.cancel)
                    .onPositive { _, _ -> signOut(false) }
                    .onNegative { _, _ -> signOut(true) }
                    .show()
        } else {
            signOut(true)
        }
    }

    fun signOut(removeUserdata: Boolean) {
        // stop any existing sync requests
        AnnotationSyncJob.cancelAll()

        // delete the user database
        if (removeUserdata) {
            removedUserdata()
        }

        // clear credentials and cookies
        ldsAccountUtil.signOut()

        // cleanup preferences
        ldsAccountPrefs.reset()
        prefs.resetUserData()

        // cleanup role based catalogs
        if (catalogUpdateUtil.removeAllRoleBasedContent()) {
            internalIntents.restart()
        }

        // notify sign out is complete
        bus.post(AccountSignOutEvent())
    }

    private fun removedUserdata() {
        userdataDbUtil.resetCurrentDatabase()
    }

    companion object {
        private val ONE_DAY = TimeUnit.DAYS.toMillis(1)
    }
}
