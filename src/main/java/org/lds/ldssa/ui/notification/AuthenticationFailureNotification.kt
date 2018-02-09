package org.lds.ldssa.ui.notification

import android.app.Application
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationCompat

import org.lds.ldssa.R
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.ui.activity.SettingsActivity

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationFailureNotification
@Inject constructor(private val application: Application, private val prefs: Prefs, private val notificationManager: NotificationManager) {
    fun show() {
        val title = application.getString(R.string.sign_in_error)
        val text = application.getString(R.string.please_sign_in_user_fix)

        val intent = Intent(application, SettingsActivity::class.java)
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, prefs.lastVisibleScreenId)

        val builder = NotificationCompat.Builder(application, NotificationChannels.GENERAL.channelId)
                .setTicker(title) // message to show in the status bar onNotify
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_stat_ldssa)
                .setAutoCancel(true) // remove notification on tap

        val contentIntent = PendingIntent.getActivity(application, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(contentIntent)

        notificationManager.notify(NotificationIds.AUTH_FAILURE.notificationId, builder.build())
    }

    fun dismiss() {
        notificationManager.cancel(NotificationIds.AUTH_FAILURE.notificationId)
    }
}
