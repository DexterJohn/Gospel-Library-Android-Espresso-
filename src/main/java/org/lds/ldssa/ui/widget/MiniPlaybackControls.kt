package org.lds.ldssa.ui.widget

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.SeekBar
import butterknife.ButterKnife
import butterknife.OnClick
import kotlinx.android.synthetic.main.widget_mini_media_playback_controls.view.*
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.media.MiniPlaybackControlsManager
import org.lds.ldssa.media.MiniPlaybackControlsManager.PlaybackItem
import org.lds.ldssa.media.MiniPlaybackControlsManager.Progress
import org.lds.ldssa.media.exomedia.EmptyPlaybackControlsManager
import org.lds.mobile.media.cast.CastManager
import org.lds.mobile.ui.setVisible
import org.lds.mobile.ui.util.LdsDrawableUtil
import javax.inject.Inject

class MiniPlaybackControls : FrameLayout {
    @Inject
    lateinit var castManager: CastManager

    var castEnabled: Boolean = false
        set(value) {
            field = value
            setupCastButton()
        }
    var downloadEnabled: Boolean = true
        set(value) {
            field = value
            setupDownloadMenuItem()
        }
    var playbackManager: MiniPlaybackControlsManager = EmptyPlaybackControlsManager()
        set(value) {
            field = value
            registerPlaybackManager()
        }

    private var visibilityListener: VisibilityListener? = null
    private val visibilityHandler = Handler()

    private var isVisible = false
    private var userInteracting = false

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    /**
     * Initializes the playback controls by setting the layout, preparing helpers, and
     * linking views and callbacks.

     * @param context The context for the owner of the playback controls
     */
    private fun init(context: Context) {
        View.inflate(context, R.layout.widget_mini_media_playback_controls, this)

        if (isInEditMode) {
            return
        }

        Injector.get().inject(this)
        ButterKnife.bind(this)
        setupToolbar()
        isClickable = true

        // Register listeners
        seekBar.setOnSeekBarChangeListener(SeekBarChanged())

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        playbackManager.onDetachedFromWindow()
    }

    override fun setEnabled(enabled: Boolean) {
        previousButton.isEnabled = enabled
        replayButton.isEnabled = enabled
        playPauseButton.isEnabled = enabled
        forwardButton.isEnabled = enabled
        nextButton.isEnabled = enabled

        seekBar.isEnabled = enabled

        disableUnsupportedButtons()
        super.setEnabled(enabled)
    }

    /**
     * Determines if the playback controls are currently visible
     * @return True if the playback controls are currently visible
     */
    fun isVisible(): Boolean {
        return isVisible
    }

    /**
     * Immediately starts the animation to show the controls
     */
    fun show() {
        //We don't want to be able to show the controls if nothing is playing
        if (playbackManager.controlsCanShow()) {
            animateVisibility(true)
            updatePlayPauseButton(playbackManager.isPlaying())
        }
    }

    /**
     * Hides the playback controls immediately
     */
    fun hideImmediate() {
        hideDelayed(0)
    }

    /**
     * After the specified delay the view will be hidden.  If the user is interacting
     * with the controls then we wait until after they are done to start the delay.

     * @param delay The delay in milliseconds to wait to start the hide animation
     */
    private fun hideDelayed(delay: Long) {
        if (delay < 0) {
            return
        }

        //If the user is interacting with controls we don't want to start the delayed hide yet
        if (userInteracting) {
            return
        }

        visibilityHandler.postDelayed({ animateVisibility(false) }, delay)
    }

    fun updatePlayPauseButton(isPlaying: Boolean) {
        if (isPlaying) {
            playPauseButton.setImageDrawable(LdsDrawableUtil.tintDrawable(context, R.drawable.ic_lds_action_pause_24dp, R.color.media_media_player_icon))
        } else {
            playPauseButton.setImageDrawable(LdsDrawableUtil.tintDrawable(context, R.drawable.ic_lds_action_play_24dp, R.color.media_media_player_icon))
        }
    }

    @OnClick(R.id.previousButton)
    fun onPreviousClick() {
        playbackManager.invokePrevious()
    }

    @OnClick(R.id.replayButton)
    fun onReplayClick() {
        val currentProgress = playbackManager.getCurrentProgress() ?: return
        playbackManager.invokeSeekEnded(((currentProgress.currentPosition - REPLAY_FORWARD_MS)))
    }

    @OnClick(R.id.playPauseButton)
    fun onPlayPauseClick() {
        playbackManager.invokePlayPause()
    }

    @OnClick(R.id.forwardButton)
    fun onForwardClick() {
        val currentProgress = playbackManager.getCurrentProgress() ?: return
        playbackManager.invokeSeekEnded(((currentProgress.currentPosition + REPLAY_FORWARD_MS)))
    }

    @OnClick(R.id.nextButton)
    fun onNextClick() {
        playbackManager.invokeNext()
    }

    private fun getPlaybackManagerType(): MiniPlaybackControlsManager.PlaybackType {
        return playbackManager.getPlaybackType()
    }

    private fun registerPlaybackManager() {
        playbackManager.onItemChangedListener = { currentItem, isNextAvailable, isPreviousAvailable, playbackType -> onItemChanged(currentItem, isNextAvailable, isPreviousAvailable, playbackType) }
        playbackManager.onProgressChangedListener = { progress, playbackType -> onProgressChanged(progress, playbackType) }
        playbackManager.onPlaybackStateChangedListener = { playbackState, playbackType -> onPlaybackStateChanged(playbackState, playbackType) }

        // Update the display values
        onPlaybackStateChanged(playbackManager.getCurrentPlaybackState(), playbackManager.getPlaybackType())
        onItemChanged(playbackManager.getCurrentItem(), playbackManager.isNextAvailable(), playbackManager.isPreviousAvailable(), playbackManager.getPlaybackType())
        onProgressChanged(playbackManager.getCurrentProgress(), playbackManager.getPlaybackType())

        playbackManager.playbackControls = this
    }

    private fun setupToolbar() {
        // Navigation
        mediaPlaybackToolbar.navigationIcon = LdsDrawableUtil.tintDrawable(context, R.drawable.ic_lds_action_clear_24dp, R.color.white)
        mediaPlaybackToolbar.setNavigationOnClickListener { playbackManager.invokeStop() }

        // Menu
        mediaPlaybackToolbar.inflateMenu(R.menu.menu_audio)
        mediaPlaybackToolbar.setOnMenuItemClickListener { menuItem -> playbackManager.onOptionsItemSelected(menuItem) }
        val menu = mediaPlaybackToolbar.menu

        // Cast
        castManager.setupCastButton(context, menu, R.id.menu_item_cast)

        // update colors
        val titleColor = ContextCompat.getColor(context, R.color.theme_dark_item_title)
        val subTitleColor = ContextCompat.getColor(context, R.color.theme_dark_item_sub_title)

        mediaPlaybackToolbar.setTitleTextColor(titleColor)
        mediaPlaybackToolbar.setSubtitleTextColor(subTitleColor)
        LdsDrawableUtil.tintAllMenuIcons(menu, titleColor)

        mediaPlaybackToolbar.overflowIcon?.let {
            LdsDrawableUtil.tintDrawable(it, titleColor)
        }
    }

    private fun setupCastButton() {
        val castMenuItem = mediaPlaybackToolbar.menu.findItem(R.id.menu_item_cast) ?: return
        castMenuItem.isEnabled = castEnabled
        castMenuItem.isVisible = castEnabled
    }

    private fun setupDownloadMenuItem() {
        val downloadMenuItem = mediaPlaybackToolbar.menu.findItem(R.id.menu_mini_player_download) ?: return
        downloadMenuItem.isEnabled = downloadEnabled
        downloadMenuItem.isVisible = downloadEnabled
    }

    private fun onItemChanged(currentItem: PlaybackItem?, isNextAvailable: Boolean, isPreviousAvailable: Boolean, playbackType: MiniPlaybackControlsManager.PlaybackType) {
        if (playbackType != playbackManager.getPlaybackType()) {
            return
        }
        nextButton.isEnabled = isNextAvailable
        previousButton.isEnabled = isPreviousAvailable

        if (currentItem == null) {
            hideImmediate()
        } else {
            show()
            updateTitle(currentItem)
        }

        updateTitle(currentItem)
        updateButtonVisibility()
    }

    private fun onPlaybackStateChanged(playbackState: MiniPlaybackControlsManager.PlaybackState?, playbackType: MiniPlaybackControlsManager.PlaybackType) {
        if (playbackType != playbackManager.getPlaybackType()) {
            return
        }
        when (playbackState) {
            MiniPlaybackControlsManager.PlaybackState.ERROR -> hideImmediate()
            MiniPlaybackControlsManager.PlaybackState.STOPPED -> {
                hideImmediate()
            }
            MiniPlaybackControlsManager.PlaybackState.PREPARING -> {
                show()
                setLoading(true)
            }
            MiniPlaybackControlsManager.PlaybackState.PLAYING -> {
                updatePlayPauseButton(true)
                setLoading(false)
            }
            MiniPlaybackControlsManager.PlaybackState.PAUSED -> {
                updatePlayPauseButton(false)
                setLoading(false)
            }
            else -> {
            }
        }
    }

    private fun onProgressChanged(playbackProgress: Progress?, playbackType: MiniPlaybackControlsManager.PlaybackType) {
        if (playbackProgress == null || playbackType != playbackManager.getPlaybackType()) {
            return
        }

        //Only updates the duration when it changes
        if (playbackProgress.duration != seekBar.max && (getPlaybackManagerType() != MiniPlaybackControlsManager.PlaybackType.EXOMEDIA || playbackProgress.duration > 0)) {
            seekBar.max = playbackProgress.duration
            durationTextView.text = playbackManager.formatDurationString(playbackProgress.duration)
        }

        //Updates the current progress
        if (!userInteracting) {
            seekBar.secondaryProgress = playbackProgress.bufferProgress
            seekBar.progress = playbackProgress.currentPosition
            currentPositionTextView.text = playbackManager.formatProgressString(playbackProgress.currentPosition)
        }
    }

    private fun disableUnsupportedButtons() {
        previousButton.isEnabled = playbackManager.isPreviousAvailable()
        nextButton.isEnabled = playbackManager.isNextAvailable()
    }

    /**
     * Performs the control visibility animation for showing or hiding this view
     * @param toVisible True if the view should be visible at the end of the animation
     */
    private fun animateVisibility(toVisible: Boolean) {
        if (isVisible == toVisible) {
            return
        }

        //Makes sure we don't have an animation scheduled
        visibilityHandler.removeCallbacksAndMessages(null)
        clearAnimation()

        val resId = if (toVisible) R.anim.playback_controls_show else R.anim.playback_controls_hide
        val animation = AnimationUtils.loadAnimation(context, resId)
        animation.interpolator = FastOutSlowInInterpolator()

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                setVisible(true)
            }

            override fun onAnimationEnd(animation: Animation) {
                setVisible(isVisible)
                visibilityListener?.onVisibilityChanged(isVisible)
            }

            override fun onAnimationRepeat(animation: Animation) {
                //Purposefully left blank
            }
        })
        startAnimation(animation)

        isVisible = toVisible
    }

    /**
     * Updates the UI to represent the current loading state.
     * @param isLoading True if the media is loading/preparing
     */
    private fun setLoading(isLoading: Boolean) {
        controlsLayout.setVisible(!isLoading, View.INVISIBLE)
        progressLayout.setVisible(!isLoading, View.INVISIBLE)
        loadingProgressBar.setVisible(isLoading, View.INVISIBLE)
    }

    private fun updateTitle(currentItem: PlaybackItem?) {
        mediaPlaybackToolbar.title = currentItem?.title ?: ""
        mediaPlaybackToolbar.subtitle = currentItem?.subtitle ?: ""
    }

    private fun updateButtonVisibility() {
        nextButton.setVisible(playbackManager.isNextAvailable())
        previousButton.setVisible(playbackManager.isPreviousAvailable())
        replayButton.setVisible(playbackManager.isReplayAvailable())
        forwardButton.setVisible(playbackManager.isForwardAvailable())
    }

    /**
     * Listens to the seek bar change events and correctly handles the changes
     */
    private inner class SeekBarChanged : SeekBar.OnSeekBarChangeListener {
        private var seekToPosition: Int = 0

        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            if (!fromUser) {
                return
            }

            seekToPosition = progress
            currentPositionTextView.text = playbackManager.formatProgressString(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            userInteracting = true
            playbackManager.invokeSeekStarted()
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            userInteracting = false
            playbackManager.invokeSeekEnded(seekToPosition)
        }
    }

    interface VisibilityListener {
        fun onVisibilityChanged(visible: Boolean)
    }

    companion object {
        private val REPLAY_FORWARD_MS = 10000 // 10 seconds
    }
}
