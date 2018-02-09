package org.lds.ldssa.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceGroup;

import com.devbrackets.android.playlistcore.data.MediaProgress;

import org.lds.ldssa.R;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.intent.ExternalIntents;
import org.lds.ldssa.media.exomedia.data.AudioItem;
import org.lds.ldssa.media.exomedia.data.MediaItem;
import org.lds.ldssa.media.exomedia.manager.PlaylistManager;
import org.lds.ldssa.media.texttospeech.TextToSpeechManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.content.playlistitemquery.PlaylistItemQuery;
import org.lds.ldssa.model.database.content.playlistitemquery.PlaylistItemQueryManager;
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItemManager;
import org.lds.ldssa.model.database.content.subitem.SubItem;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.prefs.PrefsConst;
import org.lds.ldssa.model.prefs.type.AudioPlaybackSpeedType;
import org.lds.ldssa.model.prefs.type.AudioPlaybackVoiceType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pocketknife.PocketKnife;

import static org.lds.ldssa.media.texttospeech.TextToSpeechManager.TextToSpeechItem;

public class AudioSettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    Prefs prefs;
    @Inject
    PlaylistManager playlistManager;
    @Inject
    PlaylistItemQueryManager playlistItemQueryManager;
    @Inject
    RelatedAudioItemManager relatedAudioItemManager;
    @Inject
    TextToSpeechManager textToSpeechManager;
    @Inject
    ItemManager itemManager;
    @Inject
    SubItemManager subItemManager;
    @Inject
    ExternalIntents externalIntents;

    public AudioPlaybackVoiceType previousVoiceType = null;
    public boolean isPlayingAudio;

    public static AudioSettingsFragment newInstance() {
        return new AudioSettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Injector.INSTANCE.get().inject(this);
        PocketKnife.bindArguments(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.prefs_audio);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (prefs.getAudioVoice() == AudioPlaybackVoiceType.TEXT_TO_SPEECH) {
            isPlayingAudio = textToSpeechManager.isPlaying();
        } else {
            isPlayingAudio = playlistManager.isPlaying();
        }

        registerChangeListener(getPreferenceScreen());
        updatePlaybackSpeedPreferenceDisplay();
        setAudioSpeedPreferenceAvailablility();

        prefs.registerChangeListener(this);
    }

    @Override
    public void onPause() {
        prefs.unregisterChangeListener(this);
        super.onPause();
    }

    /**
     * For changes that have already been persisted
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case PrefsConst.PREF_AUDIO_VOICE:
                updateAudioPlayback();
                setAudioSpeedPreferenceAvailablility();
                break;
            case PrefsConst.PREF_AUDIO_SPEED:
                updatePlaybackSpeedPreferenceDisplay();
                updateAudioPlayback();
                break;
            default:
                //Purposefully left blank
        }
    }

    /**
     * For device specific changes, and changes that have not yet persisted (allow veto of change)
     * NOTE: In this method...  prefs.getXXX() do NOT yet reflect the current saved value
     */
    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        switch (preference.getKey()) {
            case PrefsConst.PREF_AUDIO_VOICE:
                previousVoiceType = prefs.getAudioVoice();
                prefs.setAudioVoice(AudioPlaybackVoiceType.getByPrefValue(value.toString()));
                break;
            case PrefsConst.PREF_AUDIO_CONTINUOUS_PLAY:
                prefs.setAudioContinuousPlay((Boolean) value);
                break;
            case PrefsConst.PREF_AUDIO_SPEED:
                prefs.setAudioPlaybackSpeed(AudioPlaybackSpeedType.getByPrefValue(value.toString()));
                break;
            default:
                //Purposefully left blank
        }

        return true;
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals(PrefsConst.PREF_TEXT_TO_SPEECH_SETTINGS)) {
            externalIntents.showTextToSpeechSettings(getActivity());
        }
        return super.onPreferenceTreeClick(preference);
    }

    private void registerChangeListener(PreferenceGroup preferenceGroup) {
        int preferences = preferenceGroup.getPreferenceCount();
        for (int i = 0; i < preferences; i++) {
            Preference preference = preferenceGroup.getPreference(i);
            if (preference instanceof PreferenceGroup) {
                registerChangeListener((PreferenceGroup) preference);
            } else {
                preference.setOnPreferenceChangeListener(this);
            }
        }
    }

    private void updateAudioPlayback() {
        AudioPlaybackVoiceType currentVoiceType = prefs.getAudioVoice();
        if (previousVoiceType == currentVoiceType) {
            return; // No change, nothing to update
        }

        // Stop text-to-speech playback and start audio playback
        if (previousVoiceType == AudioPlaybackVoiceType.TEXT_TO_SPEECH) {
            TextToSpeechItem currentItem = textToSpeechManager.getCurrentItem();
            textToSpeechManager.invokeStop();
            if (currentItem != null) {
                playAudio(currentItem.getContentItemId(), currentItem.getSubItemId());
            }
            return;
        }

        // Stop audio playback and start text-to-speech
        if (currentVoiceType == AudioPlaybackVoiceType.TEXT_TO_SPEECH) {
            MediaItem currentItem = playlistManager.getCurrentItem();
            playlistManager.invokeStop();
            if (currentItem != null) {
                playTextToSpeech(currentItem.getContentItemId(), currentItem.getSubItemId());
            }
            return;
        }

        // Handle switching between different audio voices
        MediaItem currentItem = playlistManager.getCurrentItem();
        if (currentItem != null) {
            playAudio(currentItem.getContentItemId(), currentItem.getSubItemId());
        }
    }

    private void playAudio(long contentItemId, long subItemId) {
        if (isPlayingAudio) {
            playlistManager.invokePausePlay();
        }

        MediaProgress currentMediaProgress = playlistManager.getCurrentProgress();
        int playbackPosition = 0;
        if (currentMediaProgress != null) {
            playbackPosition = (int) currentMediaProgress.getPosition();
        }

        List<PlaylistItemQuery> audioItems = playlistItemQueryManager.findAllByContentItemId(contentItemId);
        List<MediaItem> playlistItems = new ArrayList<>(audioItems.size());
        for (PlaylistItemQuery item : audioItems) {
            playlistItems.add(new AudioItem(item));
        }

        //Updates the playlistManager and starts the audio
        playlistManager.setParameters(playlistItems, 0);
        playlistManager.setPlaylistId(contentItemId, -1);
        playlistManager.setCurrentItem(subItemId);
        playlistManager.play(playbackPosition, !isPlayingAudio);
    }

    private void playTextToSpeech(long contentItemId, long subItemId) {
        // Build list of items to play
        List<TextToSpeechManager.TextToSpeechItem> itemList = new ArrayList<>();
        String subtitle = itemManager.findTitleById(contentItemId);
        String imageRenditions = itemManager.findImageCoverRenditionsById(contentItemId);

        int startPosition = 0;
        List<SubItem> subItems = subItemManager.findAllByContentItemId(contentItemId);
        for (int i = 0; i < subItems.size(); i++) {
            SubItem subItem = subItems.get(i);
            TextToSpeechManager.TextToSpeechItem textToSpeechItem = new TextToSpeechManager.TextToSpeechItem(contentItemId, subItem.getId(), subItem.getTitle(), subtitle, imageRenditions);
            itemList.add(textToSpeechItem);
            if (subItem.getId() == subItemId) {
                startPosition = i;
            }
        }

        // Start Playback
        textToSpeechManager.setTextToSpeechItems(itemList, startPosition);
        textToSpeechManager.speak(0, isPlayingAudio);
    }

    private void updatePlaybackSpeedPreferenceDisplay() {
        ListPreference playbackSpeedPref = (ListPreference) findPreference(PrefsConst.PREF_AUDIO_SPEED);
        if (playbackSpeedPref != null) {
            AudioPlaybackSpeedType speedType = prefs.getAudioPlaybackSpeed();
            playbackSpeedPref.setSummary(speedType.getStringResId());
            playbackSpeedPref.setValue(speedType.getPrefValue());
        }
    }

    private void setAudioSpeedPreferenceAvailablility() {
        ListPreference playbackSpeedPref = (ListPreference) findPreference(PrefsConst.PREF_AUDIO_SPEED);
        playbackSpeedPref.setEnabled(prefs.getAudioVoice() != AudioPlaybackVoiceType.TEXT_TO_SPEECH);
    }
}
