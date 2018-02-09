package org.lds.ldssa.media.exomedia.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.devbrackets.android.playlistcore.components.image.ImageProvider
import org.lds.ldssa.R
import org.lds.ldssa.glide.GlideApp
import org.lds.ldssa.glide.GlideRequests
import org.lds.ldssa.media.exomedia.data.MediaItem
import org.lds.mobile.glide.ImageRenditions

class MediaImageProvider(val context: Context, val listener: OnImageUpdatedListener): ImageProvider<MediaItem> {
    interface OnImageUpdatedListener {
        fun onImageUpdated()
    }

    override val notificationIconRes: Int
        get() = R.drawable.ic_stat_audio

    override val remoteViewIconRes: Int
        get() = R.drawable.ic_stat_audio

    override val largeNotificationImage: Bitmap?
        get() = if (notificationImage != null) notificationImage else defaultNotificationImage

    override val remoteViewArtwork: Bitmap?
        get() = artworkImage

    var notificationImageSize: Int = 400

    private var glideRequests: GlideRequests = GlideApp.with(context.applicationContext)

    private val notificationImageTarget = NotificationImageTarget()
    private val remoteViewImageTarget = RemoteViewImageTarget()

    private var defaultNotificationImage: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_stat_ldssa)

    private var notificationImage: Bitmap? = null
    private var artworkImage: Bitmap? = null

    override fun updateImages(playlistItem: MediaItem) {
        glideRequests.asBitmap().load(ImageRenditions(playlistItem.imageRenditions)).override(notificationImageSize, notificationImageSize).fitCenter().into(notificationImageTarget)
        glideRequests.asBitmap().load(ImageRenditions(playlistItem.imageRenditions)).override(notificationImageSize, notificationImageSize).fitCenter().into(remoteViewImageTarget)
    }

    /**
     * A class used to listen to the loading of the large notification images and perform
     * the correct functionality to update the notification once it is loaded.
     *
     *
     * **NOTE:** This is a Glide Image loader class
     */
    private inner class NotificationImageTarget : SimpleTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, glideAnimation: Transition<in Bitmap>) {
            notificationImage = resource
            listener.onImageUpdated()
        }
    }

    /**
     * A class used to listen to the loading of the large lock screen images and perform
     * the correct functionality to update the artwork once it is loaded.
     *
     *
     * **NOTE:** This is a Glide Image loader class
     */
    private inner class RemoteViewImageTarget : SimpleTarget<Bitmap>() {
        override fun onResourceReady(resource: Bitmap, glideAnimation: Transition<in Bitmap>) {
            artworkImage = resource
            listener.onImageUpdated()
        }
    }
}
