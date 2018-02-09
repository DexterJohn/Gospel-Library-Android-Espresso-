package org.lds.ldssa.model.prefs.type;

import android.support.annotation.StringRes;

import org.lds.ldssa.R;

public enum AudioPlaybackVoiceType {
    UNKNOWN("", 0, -1), // Should never be stored, but items without multiple voices have a "null" for the voice in the database
    MALE("Male", 1, R.string.male),
    FEMALE("Female", 2, R.string.female),
    TEXT_TO_SPEECH("Text-to-speech", 3, R.string.text_to_speech);

    private String prefValue; // used for value in preference (don't change)
    private int voiceId;
    @StringRes
    private int stringResId;

    AudioPlaybackVoiceType(String prefValue, int voiceId, @StringRes int stringResId) {
        this.prefValue = prefValue;
        this.voiceId = voiceId;
        this.stringResId = stringResId;
    }

    public String getPrefValue() {
        return prefValue;
    }


    public int getVoiceId() {
        return voiceId;
    }

    @StringRes
    public int getStringResId() {
        return stringResId;
    }

    public static AudioPlaybackVoiceType getByPrefValue(String prefValue) {
        for (AudioPlaybackVoiceType type : values()) {
            if (type.getPrefValue().equals(prefValue)) {
                return type;
            }
        }

        return MALE;
    }
}
