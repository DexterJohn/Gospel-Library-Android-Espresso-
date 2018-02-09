package org.lds.ldssa.ui.notification

import android.app.Application
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat

import org.lds.ldssa.R

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppUpdateNotification
@Inject constructor(private val application: Application, private val notificationManager: NotificationManager) {

    fun show() {
        val builder = NotificationCompat.Builder(application, NotificationChannels.GENERAL.channelId)
        builder.setSmallIcon(R.drawable.ic_stat_ldssa)
        builder.setContentTitle(application.getString(R.string.app_update_available))
        builder.setContentText(application.getString(R.string.app_update_available_message))
        builder.setContentIntent(storePendingIntent) //Launch the play store to update
        builder.setOngoing(false) //Allows swipe to dismiss

        //Set the notification category on lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(Notification.CATEGORY_RECOMMENDATION)
            builder.setVisibility(Notification.VISIBILITY_PUBLIC)
        }

        notificationManager.notify(NotificationIds.APP_UPDATE_AVAILABLE.notificationId, builder.build())
    }

    private val storePendingIntent: PendingIntent
        get() {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + application.packageName))
            return PendingIntent.getActivity(application, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }
}
