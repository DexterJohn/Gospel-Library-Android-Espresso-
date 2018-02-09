package org.lds.ldssa.model.prefs.type;

import android.support.annotation.StringRes;

import org.lds.ldssa.R;

public enum AudioPlaybackSpeedType {
    SPEED_0_5("0.5", 0.5f, R.string.playback_speed_0_5x),
    SPEED_1_0("1", 1f, R.string.playback_speed_1x),
    SPEED_1_25("1.25", 1.25f, R.string.playback_speed_1_25x),
    SPEED_1_5("1.5", 1.5f, R.string.playback_speed_1_5x),
    SPEED_1_75("1.75", 1.75f, R.string.playback_speed_1_75x),
    SPEED_2_0("2", 2f, R.string.playback_speed_2x);


    private String prefValue; // used for value in preference (don't change)
    private float playbackSpeedValue;
    @StringRes
    private int stringResId;

    AudioPlaybackSpeedType(String prefValue, float playbackSpeedValue, @StringRes int stringResId) {
        this.prefValue = prefValue;
        this.playbackSpeedValue = playbackSpeedValue;
        this.stringResId = stringResId;
    }

    public String getPrefValue() {
        return prefValue;
    }

    public float getPlaybackSpeedValue() {
        return playbackSpeedValue;
    }

    @StringRes
    public int getStringResId() {
        return stringResId;
    }

    public static AudioPlaybackSpeedType getByPrefValue(String prefValue) {
        for (AudioPlaybackSpeedType type : values()) {
            if (type.getPrefValue().equals(prefValue)) {
                return type;
            }
        }

        return SPEED_1_0;
    }
}
