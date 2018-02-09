package org.lds.ldssa.ui.notification

enum class NotificationIds constructor(val notificationId: Int) {
    SYNC(10),
    SYNC_FAILURE(20),
    APP_UPDATE_AVAILABLE(30),
    AUTH_FAILURE(40),
    MEDIA_PLAYBACK(50)
}
