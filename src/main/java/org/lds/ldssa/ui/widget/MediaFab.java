package org.lds.ldssa.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

import com.devbrackets.android.playlistcore.data.PlaybackState;
import com.devbrackets.android.playlistcore.listener.PlaylistListener;

import org.jetbrains.annotations.NotNull;
import org.lds.ldssa.R;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.media.exomedia.data.MediaItem;
import org.lds.ldssa.media.exomedia.manager.PlaylistManager;
import org.lds.ldssa.media.texttospeech.TextToSpeechEngine;
import org.lds.ldssa.media.texttospeech.TextToSpeechManager;
import org.lds.mobile.ui.animation.FabHideAnimation;
import org.lds.mobile.ui.animation.FabShowAnimation;
import org.lds.mobile.ui.util.LdsDrawableUtil;

import javax.inject.Inject;

/**
 * Extends the support FAB to provide built in support for hiding/showing
 * when media states change in order both match the {@link MiniPlaybackControls}
 * and to keep the ContentActivity clean
 */
public class MediaFab extends FloatingActionButton implements PlaylistListener<MediaItem>, TextToSpeechManager.TextToSpeechItemListener {
    @Inject
    PlaylistManager playlistManager;
    @Inject
    TextToSpeechManager textToSpeechManager;

    private boolean isVisible = false;
    private boolean isHiddenForScroll = false;
    private boolean isAppFullscreen = false;
    private boolean isCurrentItemAudio;
    private boolean isEnabled = true;
    private boolean isPlayingExoMedia = false;
    private boolean isPlayingTextToSpeech = false;

    public MediaFab(Context context) {
        super(context);
        init();
    }

    public MediaFab(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MediaFab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        playlistManager.unRegisterPlaylistListener(this);
        textToSpeechManager.unRegisterItemListener(this);
    }

    @Override
    public boolean onPlaybackStateChanged(@NonNull PlaybackState playbackState) {
        isPlayingExoMedia = playbackState != PlaybackState.ERROR && playbackState != PlaybackState.STOPPED;

        if (isPlayingExoMedia || isPlayingTextToSpeech) {
            hide();
        } else {
            // TODO: This call should not be called if the mini media playback controls are closed in content that doesn't have audio items.
            show();
        }

        return false;
    }

    @Override
    public boolean onPlaylistItemChanged(@Nullable MediaItem currentItem, boolean hasNext, boolean hasPrevious) {
        isCurrentItemAudio = currentItem != null && currentItem.getMediaType() == PlaylistManager.AUDIO;
        return false;
    }

    @Override
    public void onItemChanged(@NotNull TextToSpeechManager.TextToSpeechItem currentItem, boolean hasNext, boolean hasPrevious) {
        // Nothing to do
    }

    @Override
    public void onPlaybackStateChanged(@NotNull TextToSpeechEngine.PlaybackState playbackState) {
        isPlayingTextToSpeech = playbackState != TextToSpeechEngine.PlaybackState.ERROR && playbackState != TextToSpeechEngine.PlaybackState.STOPPED;

        if (isPlayingExoMedia || isPlayingTextToSpeech) {
            hide();
        } else {
            show();
        }
    }

    public void show() {
        if (!isEnabled) {
            return;
        }
        boolean canShow = !isHiddenForScroll && !isAppFullscreen;
        if (canShow && !isVisible && !(isPlayingExoMedia && isCurrentItemAudio)) {
            isVisible = true;
            startAnimation(new FabShowAnimation(this));
        }
    }

    public void hide() {
        if (isVisible) {
            isVisible = false;
            startAnimation(new FabHideAnimation(this));
        }
    }

    public void setFullscreenEnabled(boolean enabled) {
        isAppFullscreen = enabled;

        if (enabled) {
            hide();
        } else {
            show();
        }
    }

    public void setScrollVisibility(boolean visible) {
        isHiddenForScroll = !visible;

        if (visible) {
            show();
        } else {
            hide();
        }
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
        if (!isEnabled) {
            setScrollVisibility(false);
        }
    }

    private void init() {
        if (isInEditMode()) {
            return;
        }

        Injector.INSTANCE.get().inject(this);
        playlistManager.registerPlaylistListener(this);
        textToSpeechManager.registerItemListener(this);

        setImageDrawable(LdsDrawableUtil.INSTANCE.tintDrawable(getContext(), getDrawable(), R.color.white));
    }
}
