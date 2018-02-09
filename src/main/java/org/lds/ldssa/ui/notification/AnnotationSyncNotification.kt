package org.lds.ldssa.ui.notification

import android.app.Application
import android.app.Notification
import android.app.NotificationManager
import android.os.Build
import android.support.annotation.StringRes
import android.support.v4.app.NotificationCompat
import org.lds.ldssa.R
import org.lds.ldssa.event.sync.AnnotationSyncStatusEvent
import pocketbus.Bus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnnotationSyncNotification
@Inject constructor(private val application: Application, private val notificationManager: NotificationManager, private val bus: Bus) {

    fun updateNotification(@StringRes progressTextResId: Int) {
        updateNotification(application.getString(progressTextResId))
    }

    private fun updateNotification(progressText: String) {
        notificationManager.notify(NotificationIds.SYNC.notificationId, getNotification(progressText, 0, 0))
    }

    fun updateNotification(@StringRes progressTextResId: Int, maxProgress: Int, progress: Int) {
        updateNotification(application.getString(progressTextResId), maxProgress, progress)
    }

    private fun updateNotification(progressText: String, maxProgress: Int, progress: Int) {
        bus.post(AnnotationSyncStatusEvent(progressText, maxProgress, progress))
        notificationManager.notify(NotificationIds.SYNC.notificationId, getNotification(progressText, maxProgress, progress))
    }

    fun cancelNotification() {
        notificationManager.cancel(NotificationIds.SYNC.notificationId)
    }

    private fun getNotification(progressText: String, maxProgress: Int, progress: Int): Notification {
        val builder = NotificationCompat.Builder(application, NotificationChannels.SYNC.channelId)
        if (maxProgress > 0) {
            builder.setProgress(maxProgress, progress, false)
        } else {
            builder.setProgress(maxProgress, progress, true)
        }

        builder.setSmallIcon(R.drawable.ic_stat_ldssa)
        builder.setContentTitle(application.getString(R.string.syncing_annotations))
        builder.setContentText(progressText)

        builder.setOngoing(true)

        //Set the notification category on lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(Notification.CATEGORY_STATUS)
            builder.setVisibility(Notification.VISIBILITY_PUBLIC)
        }

        return builder.build()
    }
}
