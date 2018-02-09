package org.lds.ldssa.ui.notification

import android.app.Application
import android.app.Notification
import android.app.NotificationManager
import android.os.Build
import android.support.v4.app.NotificationCompat

import org.lds.ldssa.R

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnnotationSyncFailureNotification
@Inject constructor(private val application: Application, private val notificationManager: NotificationManager) {

    fun show(failureReason: String) {
        val builder = NotificationCompat.Builder(application, NotificationChannels.GENERAL.channelId)
        builder.setSmallIcon(R.drawable.ic_stat_ldssa)
        builder.setContentTitle(application.getString(R.string.sync_notification_failure))
        builder.setContentText(failureReason)

        //Set the notification category on lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(Notification.CATEGORY_STATUS)
            builder.setVisibility(Notification.VISIBILITY_PUBLIC)
        }

        notificationManager.notify(NotificationIds.SYNC_FAILURE.notificationId, builder.build())
    }
}
