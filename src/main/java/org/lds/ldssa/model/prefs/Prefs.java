package org.lds.ldssa.model.prefs;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.lds.ldssa.BuildConfig;
import org.lds.ldssa.R;
import org.lds.ldssa.model.database.types.HighlightAnnotationStyle;
import org.lds.ldssa.model.prefs.model.ContentServerType;
import org.lds.ldssa.model.prefs.model.HighlightHistoryItem;
import org.lds.ldssa.model.prefs.type.AudioPlaybackSpeedType;
import org.lds.ldssa.model.prefs.type.AudioPlaybackVoiceType;
import org.lds.ldssa.model.prefs.type.ContentTextSizeType;
import org.lds.ldssa.model.prefs.type.DisplayThemeType;
import org.lds.ldssa.model.prefs.type.NotebookSortType;
import org.lds.ldssa.model.prefs.type.TagSortType;
import org.lds.ldssa.util.HighlightColor;
import org.lds.ldssa.util.LanguageUtil;
import org.lds.ldssa.util.ThreeTenUtil;
import org.lds.ldssa.ux.study.plans.StudyPlanTab;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
public class Prefs extends PrefsBase {
    public static final String BACKED_UP_PREFERENCE_NAME = "BACKED_UP_PREFERENCES";
    public static final String DEFAULT_PREFERENCE_NAME = "DEFAULT_PREFERENCES";
    public static final String DAWN_OF_TIME_TEXT = "1970-01-01T01:01:01.001-06:00"; // for use with "since" call to server
    private LocalDateTime dawnOfTime = LocalDate.of(1970, 1, 1).atStartOfDay(); // for use with local annotations/folders to determine what to include in next sync.  cannot be static final

    private final Application application;
    private final SharedPreferences defaultPreferences;
    private final SharedPreferences backedUpPreferences;

    @Inject
    public Prefs(Application application, @Named(DEFAULT_PREFERENCE_NAME) SharedPreferences defaultPreferences,
                 @Named(BACKED_UP_PREFERENCE_NAME) SharedPreferences backedUpPreferences) {
        this.application = application;
        this.defaultPreferences = defaultPreferences;
        this.backedUpPreferences = backedUpPreferences;
    }

    public int getLastInstalledVersionCode() {
        return defaultPreferences.getInt(PrefsConst.PREF_LAST_INSTALLED_VERSION_CODE, 0);
    }

    public void setLastInstalledVersionCode(int versionCode) {
        saveInt(PrefsConst.PREF_LAST_INSTALLED_VERSION_CODE, versionCode, defaultPreferences);
    }

    public long getCurrentPersonId() {
        return defaultPreferences.getLong(PrefsConst.PREF_CURRENT_PERSON_ID, 0L);
    }

    public void setCurrentPersonId(@Nullable String personId) {
        saveString(PrefsConst.PREF_CURRENT_PERSON_ID, personId, defaultPreferences);
    }

    public int getCurrentPersonAnnotationInstanceId() {
        return defaultPreferences.getInt(PrefsConst.PREF_CURRENT_PERSON_ANNOTATION_INSTANCE_ID, 0);
    }

    public void saveCurrentPersonAnnotationInstanceId(int instanceId) {
        saveInt(PrefsConst.PREF_CURRENT_PERSON_ANNOTATION_INSTANCE_ID, instanceId, defaultPreferences);
    }

    public long getLastAnnotationFullSyncTime() {
        return defaultPreferences.getLong(PrefsConst.PREF_ANNOTATION_SYNC_LAST_FULL_SYNC, 0L);
    }

    public void updateLastAnnotationFullSyncTime() {
        saveLong(PrefsConst.PREF_ANNOTATION_SYNC_LAST_FULL_SYNC, System.currentTimeMillis(), defaultPreferences);
    }

    public void setLatestAnnotationSyncResultSuccess() {
        saveString(PrefsConst.PREF_ANNOTATION_SYNC_LATEST_RESULT, "", defaultPreferences);
    }

    public void setLatestAnnotationSyncResultError(String resultText) {
        saveString(PrefsConst.PREF_ANNOTATION_SYNC_LATEST_RESULT, resultText, defaultPreferences);
    }

    @Nullable
    public String getLatestAnnotationSyncResult() {
        return defaultPreferences.getString(PrefsConst.PREF_ANNOTATION_SYNC_LATEST_RESULT, "");
    }

    /**
     * Value used to determine last sync with server (to identify what folders/notebooks to include in this sync)
     */
    @Nonnull
    public LocalDateTime getFoldersLastSyncTs() {
        String key = PrefsConst.PREF_FOLDER_SYNC_SINCE_TIME;
        if (defaultPreferences.contains(key)) {
            return ThreeTenUtil.fromMillis(defaultPreferences.getLong(key, 0L));
        }

        return dawnOfTime;
    }

    public void setFoldersLastSyncTs(LocalDateTime timestamp) {
        saveLong(PrefsConst.PREF_FOLDER_SYNC_SINCE_TIME, ThreeTenUtil.toMillis(timestamp), defaultPreferences);
    }

    /**
     * Value used to determine last sync with server (to identify what annotations to include in this sync)
     */
    @Nonnull
    public LocalDateTime getAnnotationsLastSyncTs() {
        String key = PrefsConst.PREF_ANNOTATION_SYNC_SINCE_TIME;
        if (defaultPreferences.contains(key)) {
            return ThreeTenUtil.fromMillis(defaultPreferences.getLong(key, 0L));
        }

        return dawnOfTime;
    }

    public void setAnnotationsLastSyncTs(LocalDateTime timestamp) {
        saveLong(PrefsConst.PREF_ANNOTATION_SYNC_SINCE_TIME, ThreeTenUtil.toMillis(timestamp), defaultPreferences);

    }

    /**
     * ONLY use this value for sending the "since" value when performing a sync on annotations
     */
    @Nonnull
    public String getAnnotationsServerSinceTs() {
        String defaultValue = DAWN_OF_TIME_TEXT;

        // get old default value if it exists
        // todo remove on/after 4.0.0.17
        String oldKey = PrefsConst.OLD_PREF_SERVER_ANNOTATION_SYNC_SINCE_TIME;
        if (defaultPreferences.contains(oldKey)) {
            defaultValue = ThreeTenUtil.formatIso(ThreeTenUtil.fromMillis(defaultPreferences.getLong(oldKey, 0L)));
            defaultPreferences.edit().remove(oldKey).apply();
        }

        return defaultPreferences.getString(PrefsConst.PREF_SERVER_ANNOTATION_SYNC_SINCE_TIME, defaultValue);
    }

    public void setAnnotationsServerSinceTs(String timestamp) {
        saveString(PrefsConst.PREF_SERVER_ANNOTATION_SYNC_SINCE_TIME, timestamp, defaultPreferences);
    }

    /**
     * ONLY use this value for sending the "since" value when performing a sync on folders
     */
    @Nonnull
    public String getFoldersServerSinceTs() {
        String defaultValue = DAWN_OF_TIME_TEXT;

        // get old default value if it exists
        // todo remove on/after 4.0.0.17
        String oldKey = PrefsConst.OLD_PREF_SERVER_FOLDER_SYNC_SINCE_TIME;
        if (defaultPreferences.contains(oldKey)) {
            defaultValue = ThreeTenUtil.formatIso(ThreeTenUtil.fromMillis(defaultPreferences.getLong(oldKey, 0L)));
            defaultPreferences.edit().remove(oldKey).apply();
        }

        return defaultPreferences.getString(PrefsConst.PREF_SERVER_FOLDER_SYNC_SINCE_TIME, defaultValue);
    }

    public void setFoldersServerSinceTs(String timestamp) {
        saveString(PrefsConst.PREF_SERVER_FOLDER_SYNC_SINCE_TIME, timestamp, defaultPreferences);

    }

    public void resetUserData() {
        remove(PrefsConst.PREF_CURRENT_PERSON_ID, defaultPreferences);
        remove(PrefsConst.PREF_CURRENT_PERSON_ANNOTATION_INSTANCE_ID, defaultPreferences);

        resetAnnotationSyncPrefs();
    }

    public void resetAnnotationSyncPrefs() {
        remove(PrefsConst.PREF_ANNOTATION_SYNC_LAST_FULL_SYNC, defaultPreferences);
        remove(PrefsConst.PREF_ANNOTATION_SYNC_LATEST_RESULT, defaultPreferences);

        setFoldersLastSyncTs(dawnOfTime);
        setFoldersServerSinceTs(DAWN_OF_TIME_TEXT);
        setAnnotationsLastSyncTs(dawnOfTime);
        setAnnotationsServerSinceTs(DAWN_OF_TIME_TEXT);
    }

    public void deleteAll() {
        defaultPreferences.edit().clear().commit();
        backedUpPreferences.edit().clear().commit();
    }

    /**
     * Should ONLY be called from ScreenUtil
     */
    public long getLastVisibleScreenId() {
        long previousTabId = defaultPreferences.getLong(PrefsConst.PREF_LAST_VISIBLE_TAB_ID, 0L);
        return defaultPreferences.getLong(PrefsConst.PREF_LAST_VISIBLE_SCREEN_ID, previousTabId);
    }

    /**
     * Should ONLY be called from ScreenUtil
     */
    public void setLastVisibleScreenId(long screenId) {
        remove(PrefsConst.PREF_LAST_VISIBLE_TAB_ID, defaultPreferences);
        saveLong(PrefsConst.PREF_LAST_VISIBLE_SCREEN_ID, screenId, defaultPreferences);
    }

    public boolean getInitialContentDownloaded() {
        return defaultPreferences.getBoolean(PrefsConst.PREF_INITIAL_CONTENT_DOWNLOADED, false);
    }

    public void setInitialContentDownloaded(boolean value) {
        saveBoolean(PrefsConst.PREF_INITIAL_CONTENT_DOWNLOADED, value, defaultPreferences);
    }

    public void clearCrashes() {
        SharedPreferences.Editor editor = defaultPreferences.edit();
        editor.remove(PrefsConst.PREF_LAST_ERROR_MESSAGE);
        editor.remove(PrefsConst.PREF_LAST_ERROR_DETAILS);
        editor.remove(PrefsConst.PREF_LAST_ERROR_TIME);
        editor.remove(PrefsConst.PREF_LAST_DOWNLOAD_FAILED_ERROR_MESSAGE);
        editor.apply();
    }

    public void updateLastErrorTime() {
        saveLong(PrefsConst.PREF_LAST_ERROR_TIME, System.currentTimeMillis(), defaultPreferences);
    }

    public long getLastErrorTime() {
        return defaultPreferences.getLong(PrefsConst.PREF_LAST_ERROR_TIME, 0L);
    }

    public void setLastSyncErrorCount(int count) {
        saveInt(PrefsConst.PREF_LAST_SYNC_ERROR_COUNT, count, defaultPreferences);
    }

    public int getLastSyncErrorCount() {
        return defaultPreferences.getInt(PrefsConst.PREF_LAST_SYNC_ERROR_COUNT, 0);
    }

    public void setLastErrorMessage(String message) {
        saveString(PrefsConst.PREF_LAST_ERROR_MESSAGE, message, defaultPreferences);
    }

    public String getLastErrorMessage() {
        return defaultPreferences.getString(PrefsConst.PREF_LAST_ERROR_MESSAGE, "");
    }

    public void setLastDownloadFailedErrorMessage(String message) {
        saveString(PrefsConst.PREF_LAST_DOWNLOAD_FAILED_ERROR_MESSAGE, message, defaultPreferences);
    }

    public String getLastDownloadFailedErrorMessage() {
        return defaultPreferences.getString(PrefsConst.PREF_LAST_DOWNLOAD_FAILED_ERROR_MESSAGE, "");
    }

    public void setLastErrorDetails(String details) {
        saveString(PrefsConst.PREF_LAST_ERROR_DETAILS, details, defaultPreferences);
    }

    public String getLastErrorDetails() {
        return defaultPreferences.getString(PrefsConst.PREF_LAST_ERROR_DETAILS, "");
    }

    public void setContentTextSize(ContentTextSizeType textSize) {
        saveInt(PrefsConst.PREF_CONTENT_TEXT_SIZE, textSize.ordinal(), defaultPreferences);
    }

    public ContentTextSizeType getContentTextSize() {
        int ordinal = defaultPreferences.getInt(PrefsConst.PREF_CONTENT_TEXT_SIZE, -1);
        if (ordinal < 0 || ordinal >= ContentTextSizeType.values().length) {
            ordinal = application.getResources().getBoolean(R.bool.tablet) ? ContentTextSizeType.L.ordinal() : ContentTextSizeType.M.ordinal();
        }

        return ContentTextSizeType.get(ordinal);
    }

    public long getMiscSyncWarningTimestamp() {
        return defaultPreferences.getLong(PrefsConst.PREF_MISC_SYNC_WARNING_TIMESTAMP, 0L);
    }

    public void setMiscSyncWarningTimestamp(long epochTime) {
        saveLong(PrefsConst.PREF_MISC_SYNC_WARNING_TIMESTAMP, epochTime, defaultPreferences);
    }

    public LocalDateTime getLastAppUpdateCheck() {
        long timestamp = defaultPreferences.getLong(PrefsConst.PREF_LAST_APP_UPDATE_CHECK_TIME, 0L);
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void updateLastAppUpdateCheck() {
        saveLong(PrefsConst.PREF_LAST_APP_UPDATE_CHECK_TIME, System.currentTimeMillis(), defaultPreferences);
    }

    public LocalDateTime getLastSearchSuggestUpdateCheck() {
        long timestamp = defaultPreferences.getLong(PrefsConst.PREF_LAST_SEARCH_SUGGEST_UPDATE_CHECK_TIME, 0L);
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void updateLastSearchSuggestUpdateCheck() {
        saveLong(PrefsConst.PREF_LAST_SEARCH_SUGGEST_UPDATE_CHECK_TIME, System.currentTimeMillis(), defaultPreferences);
    }

    public void setMobileNetworkLimited(boolean limited) {
        saveBoolean(PrefsConst.PREF_LIMIT_MOBILE_NETWORK, limited, defaultPreferences);
    }

    public boolean isMobileNetworkLimited() {
        return defaultPreferences.getBoolean(PrefsConst.PREF_LIMIT_MOBILE_NETWORK, false);
    }

    public boolean getTrainingFullscreenHelpShown() {
        return defaultPreferences.getBoolean(PrefsConst.PREF_TRAINING_FULLSCREEN_HELP, false);
    }

    public void setTrainingFullscreenHelpShown(boolean shown) {
        saveBoolean(PrefsConst.PREF_TRAINING_FULLSCREEN_HELP, shown, defaultPreferences);
    }

    public boolean isScreensInOverview() {
        return defaultPreferences.getBoolean(PrefsConst.PREF_TABS_IN_OVERVIEW, Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
    }

    public void setScreensInOverview(boolean value) {
        saveBoolean(PrefsConst.PREF_TABS_IN_OVERVIEW, value, defaultPreferences);
    }

    public boolean toggleDeveloperMode() {
        boolean enabled = !isDeveloperModeEnabled();
        saveBoolean(PrefsConst.PREF_DEVELOPER_MODE, enabled, defaultPreferences);
        return enabled;
    }

    public boolean isDeveloperModeEnabled() {
        return defaultPreferences.getBoolean(PrefsConst.PREF_DEVELOPER_MODE, false);
    }

    public int getDeveloperCatalogVersion() {
        String value = defaultPreferences.getString(PrefsConst.PREF_DEV_FORCE_CATALOG_VERSION, "0");
        if (NumberUtils.isNumber(value)) {
            return Integer.parseInt(value);
        } else {
            return 0;
        }
    }

    public int getDeveloperTipsVersion() {
        String value = defaultPreferences.getString(PrefsConst.PREF_DEV_FORCE_TIPS_VERSION, "0");
        if (NumberUtils.isNumber(value)) {
            return Integer.parseInt(value);
        } else {
            return 0;
        }
    }

    public String getDeveloperOverrideDownloadUri() {
        return defaultPreferences.getString(PrefsConst.PREF_DEV_OVERRIDE_DOWNLOAD_URI, "");
    }

    public boolean getDeveloperForceNoRoles() {
        return defaultPreferences.getBoolean(PrefsConst.PREF_DEV_FORCE_NO_ROLES, false);
    }

    public ContentServerType getContentServerType() {
        String ordinal = defaultPreferences.getString(PrefsConst.PREF_DEV_CONTENT_SERVER, BuildConfig.DEFAULT_CONTENT_SERVER_TYPE.ordinal() + "");
        return ContentServerType.values()[Integer.valueOf(ordinal)];
    }

    public void setContentServerType(ContentServerType type) {
        defaultPreferences.edit().putString(PrefsConst.PREF_DEV_CONTENT_SERVER, type.ordinal() + "").apply();
    }

    public boolean isForceCatalogUpdate() {
        return defaultPreferences.getBoolean(PrefsConst.PREF_DEV_FORCE_CATALOG_UPDATE, false);
    }

    public void setForceCatalogUpdate(boolean force) {
        saveBoolean(PrefsConst.PREF_DEV_FORCE_CATALOG_UPDATE, force, defaultPreferences);
    }

    public LocalDateTime getLastCatalogUpdateTime() {
        long timestamp = defaultPreferences.getLong(PrefsConst.PREF_CATALOG_UPDATE_LAST_CHECK, 0L);
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void updateLastCatalogUpdateTime() {
        saveLong(PrefsConst.PREF_CATALOG_UPDATE_LAST_CHECK, System.currentTimeMillis(), defaultPreferences);
    }

    public LocalDateTime getLastCatalogUpdateDownloadTime() {
        long timestamp = defaultPreferences.getLong(PrefsConst.PREF_CATALOG_UPDATE_LAST_DOWNLOAD, 0L);
        if (timestamp == 0) {
            return null;
        }
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void updateLastCatalogUpdateDownloadTime() {
        updateLastCatalogUpdateDownloadTime(System.currentTimeMillis());
    }

    private void updateLastCatalogUpdateDownloadTime(long time) {
        saveLong(PrefsConst.PREF_CATALOG_UPDATE_LAST_DOWNLOAD, time, defaultPreferences);
    }

    public Boolean isCatalogForceUpdateWhenAvailable() {
        return defaultPreferences.getBoolean(PrefsConst.PREF_CATALOG_FORCE_UPDATE_WHEN_AVAILABLE, false);
    }

    public void setCatalogForceUpdateWhenAvailable(Boolean forceUpdateWhenAvailable) {
        saveBoolean(PrefsConst.PREF_CATALOG_FORCE_UPDATE_WHEN_AVAILABLE, forceUpdateWhenAvailable, defaultPreferences);
    }

    public Boolean isCatalogUpdateDownloadTimeoutOverride() {
        return defaultPreferences.getBoolean(PrefsConst.PREF_DEV_CATALOG_DOWNLOAD_TIMEOUT_OVERRIDE, false);
    }

    public void setCatalogUpdateDownloadTimeoutOverride(Boolean override) {
        saveBoolean(PrefsConst.PREF_DEV_CATALOG_DOWNLOAD_TIMEOUT_OVERRIDE, override, defaultPreferences);
    }

    public void resetCatalogUpdateDownloadPrefs() {
        setCatalogForceUpdateWhenAvailable(false);
        updateLastCatalogUpdateDownloadTime(0);
    }

    public LocalDateTime getLastTipsUpdateTime() {
        long timestamp = defaultPreferences.getLong(PrefsConst.PREF_TIPS_UPDATE_LAST_CHECK, 0L);
        return Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public void updateLastTipsUpdateTime() {
        saveLong(PrefsConst.PREF_TIPS_UPDATE_LAST_CHECK, System.currentTimeMillis(), defaultPreferences);
    }

    public List<HighlightHistoryItem> getContentDisplayRecentHighlights() {
        String recentString = defaultPreferences.getString(PrefsConst.PREF_CONTENT_DISPLAY_RECENT_HIGHLIGHTS, null);
        List<HighlightHistoryItem> items =  new ArrayList<>(PrefsConst.RECENT_HIGHLIGHT_HISTORY_ITEM_COUNT);

        if (StringUtils.isNotBlank(recentString)) {
            String[] splitRecentItems = recentString.split(",");
            for (int i = 0; i < splitRecentItems.length && i < PrefsConst.RECENT_HIGHLIGHT_HISTORY_ITEM_COUNT; i++) {
                String[] ordinals = splitRecentItems[i].split("\\.");
                if (ordinals.length != 2) {
                    continue;
                }

                items.add(new HighlightHistoryItem(
                        HighlightColor.get(parseInt(ordinals[0], 0)),
                        HighlightAnnotationStyle.Companion.get(parseInt(ordinals[1], 0))
                ));
            }
        }

        //Add up to the size for defaults
        items.addAll(Arrays.asList(PrefsConst.DEFAULT_HIGHLIGHT_HISTORY_ITEMS).subList(items.size(), PrefsConst.RECENT_HIGHLIGHT_HISTORY_ITEM_COUNT));

        return items;
    }

    public void setContentDisplayRecentHighlights(List<HighlightHistoryItem> items) {
        //Makes sure we only store 5 items
        for (int i = PrefsConst.RECENT_HIGHLIGHT_HISTORY_ITEM_COUNT; i < items.size(); i++) {
            items.remove(i);
        }

        //Construct the string to store it in the format Color.ordinal().Style.ordinal, (e.g. "3.1,4.2")
        StringBuilder builder = new StringBuilder();
        for (HighlightHistoryItem item : items) {
            builder.append(item.getColor().ordinal())
                    .append(".")
                    .append(item.getStyle().ordinal())
                    .append(",");
        }

        saveString(PrefsConst.PREF_CONTENT_DISPLAY_RECENT_HIGHLIGHTS, builder.toString(), defaultPreferences);
    }

    public boolean getContentHideFootnotes() {
        return backedUpPreferences.getBoolean(PrefsConst.PREF_CONTENT_HIDE_FOOTNOTES, false);
    }

    public void setContentHideFootnotes(boolean contentHideFootnotes) {
        saveBoolean(PrefsConst.PREF_CONTENT_HIDE_FOOTNOTES, contentHideFootnotes, backedUpPreferences);
    }

    public DisplayThemeType getGeneralDisplayTheme() {
        String ordinal = backedUpPreferences.getString(PrefsConst.PREF_GENERAL_DISPLAY_THEME, DisplayThemeType.LIGHT.getDisplayOrder() + "");
        return DisplayThemeType.getByDisplayOrder(Integer.valueOf(ordinal));
    }

    public void setGeneralDisplayTheme(DisplayThemeType displayTheme) {
        saveString(PrefsConst.PREF_GENERAL_DISPLAY_THEME, String.valueOf(displayTheme.getDisplayOrder()), backedUpPreferences);
    }

    public boolean getGeneralDisplayAsList() {
        return backedUpPreferences.getBoolean(PrefsConst.PREF_GENERAL_DISPLAY_LIST_LAYOUT, false);
    }

    public void setGeneralDisplayAsList(boolean displayAsList) {
        saveBoolean(PrefsConst.PREF_GENERAL_DISPLAY_LIST_LAYOUT, displayAsList, backedUpPreferences);
    }

    public void setGeneralDisplayMediaSortBySize(boolean sortBySize) {
        saveBoolean(PrefsConst.PREF_GENERAL_DISPLAY_MEDIA_SORT_BY_SIZE, sortBySize, defaultPreferences);
    }

    public boolean getGeneralDisplayMediaSortBySize() {
        return defaultPreferences.getBoolean(PrefsConst.PREF_GENERAL_DISPLAY_MEDIA_SORT_BY_SIZE, false);
    }

    public boolean getPreviewWarningShown() {
        return defaultPreferences.getBoolean(PrefsConst.PREF_PREVIEW_WARNING_SHOWN, false);
    }

    public void setPreviewWarningShown(boolean shown) {
        saveBoolean(PrefsConst.PREF_PREVIEW_WARNING_SHOWN, shown, defaultPreferences);
    }

    public long getPreviewEndedLastMessageTs() {
        return defaultPreferences.getLong(PrefsConst.PREF_PREVIEW_ENDED_LAST_MESSAGE_TS, 0L);
    }

    public void updatePreviewEndedLastMessageTs() {
        saveLong(PrefsConst.PREF_PREVIEW_ENDED_LAST_MESSAGE_TS, System.currentTimeMillis(), defaultPreferences);
    }

    public long getLastSelectedLanguageId(LanguageUtil languageUtil) {
        return defaultPreferences.getLong(PrefsConst.PREF_LAST_SELECTED_LANGUAGE, languageUtil.getDeviceLanguageId());
    }

    public void setLastSelectedLanguageId(long languageId) {
        saveLong(PrefsConst.PREF_LAST_SELECTED_LANGUAGE, languageId, defaultPreferences);
    }

    @Nonnull
    public String getLastViewedWelcomeTipsAppVersion() {
        return defaultPreferences.getString(PrefsConst.PREF_LAST_VIEWED_WELCOME_TIPS_APP_VERSION, "");
    }

    public void setLastViewedWelcomeTipsAppVersion(@Nonnull String version) {
        saveString(PrefsConst.PREF_LAST_VIEWED_WELCOME_TIPS_APP_VERSION, version, defaultPreferences);
    }

    public boolean isAnalyticsEnabled() {
        return defaultPreferences.getBoolean(PrefsConst.PREF_ENABLE_ANALYTICS, true);
    }

    public int getLocationsPagerPosition() {
        return defaultPreferences.getInt(PrefsConst.PREF_LOCATIONS_PAGER_POSITION, 0);
    }

    public void setLocationsPagerPosition(int position) {
        saveInt(PrefsConst.PREF_LOCATIONS_PAGER_POSITION, position, defaultPreferences);
    }

    public int getNotesPagerPosition() {
        return defaultPreferences.getInt(PrefsConst.PREF_NOTES_PAGER_POSITION, 0);
    }

    public void setNotesPagerPosition(int position) {
        saveInt(PrefsConst.PREF_NOTES_PAGER_POSITION, position, defaultPreferences);
    }

    public int getStudyPlansPagerPosition() {
        return defaultPreferences.getInt(PrefsConst.PREF_STUDY_PLANS_PAGER_POSITION, StudyPlanTab.Companion.getDEFAULT_TAB().ordinal());
    }

    public void setStudyPlansPagerPosition(int position) {
        saveInt(PrefsConst.PREF_STUDY_PLANS_PAGER_POSITION, position, defaultPreferences);
    }

    public void registerChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        defaultPreferences.registerOnSharedPreferenceChangeListener(listener);
        backedUpPreferences.registerOnSharedPreferenceChangeListener(listener);
    }

    public void unregisterChangeListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
        defaultPreferences.unregisterOnSharedPreferenceChangeListener(listener);
        backedUpPreferences.unregisterOnSharedPreferenceChangeListener(listener);
    }

    public boolean isObsoleteContentEnabled() {
        return backedUpPreferences.getBoolean(PrefsConst.PREF_ENABLE_OBSOLETE_ITEMS, false);
    }

    public void setObsoleteContentEnabled(boolean value) {
        saveBoolean(PrefsConst.PREF_ENABLE_OBSOLETE_ITEMS, value, backedUpPreferences);
    }

    public boolean isAllowInAppNotifications() {
        return backedUpPreferences.getBoolean(PrefsConst.PREF_ALLOW_IN_APP_NOTIFICATIONS, true);
    }

    public void setAllowInAppNotifications(boolean value) {
        saveBoolean(PrefsConst.PREF_ALLOW_IN_APP_NOTIFICATIONS, value, backedUpPreferences);
    }

    public AudioPlaybackVoiceType getAudioVoice() {
        String value = defaultPreferences.getString(PrefsConst.PREF_AUDIO_VOICE, AudioPlaybackVoiceType.MALE.getPrefValue());
        return AudioPlaybackVoiceType.getByPrefValue(value);
    }

    public void setAudioVoice(AudioPlaybackVoiceType voice) {
        if (voice == AudioPlaybackVoiceType.UNKNOWN) {
            // If an unknown value was passed in default to a male voice
            voice = AudioPlaybackVoiceType.MALE;
        }
        saveString(PrefsConst.PREF_AUDIO_VOICE, voice.getPrefValue(), defaultPreferences);
    }

    public boolean isAudioContinuousPlay() {
        return defaultPreferences.getBoolean(PrefsConst.PREF_AUDIO_CONTINUOUS_PLAY, true);
    }

    public void setAudioContinuousPlay(boolean value) {
        saveBoolean(PrefsConst.PREF_AUDIO_CONTINUOUS_PLAY, value, defaultPreferences);
    }

    public AudioPlaybackSpeedType getAudioPlaybackSpeed() {
        String value = defaultPreferences.getString(PrefsConst.PREF_AUDIO_SPEED, AudioPlaybackSpeedType.SPEED_1_0.getPrefValue() + "");
        return AudioPlaybackSpeedType.getByPrefValue(value);
    }

    public void setAudioPlaybackSpeed(AudioPlaybackSpeedType audioPlaybackSpeedType) {
        saveString(PrefsConst.PREF_AUDIO_SPEED, audioPlaybackSpeedType.getPrefValue(), defaultPreferences);
    }

    public String getLegacyUsername() {
        return defaultPreferences.getString(PrefsConst.PREF_LEGACY_ACCOUNT_USERNAME, "");
    }

    public String getLegacyPassword() {
        return defaultPreferences.getString(PrefsConst.PREF_LEGACY_ACCOUNT_PASSWORD, "");
    }

    public TagSortType getTagSortType() {
        return TagSortType.getByOrdinal(defaultPreferences.getInt(PrefsConst.PREF_SORT_TAG, TagSortType.MOST_RECENT.ordinal()));
    }

    public void setTagSortType(TagSortType sortType) {
        saveInt(PrefsConst.PREF_SORT_TAG, sortType.ordinal(), defaultPreferences);
    }

    public NotebookSortType getNotebookSortType() {
        return NotebookSortType.getByOrdinal(defaultPreferences.getInt(PrefsConst.PREF_SORT_NOTEBOOK, NotebookSortType.MOST_RECENT.ordinal()));
    }

    public void setNotebookSortType(NotebookSortType sortType) {
        saveInt(PrefsConst.PREF_SORT_NOTEBOOK, sortType.ordinal(), defaultPreferences);
    }

    public boolean isSidebarPinned() {
        return defaultPreferences.getBoolean(PrefsConst.PREF_CONTENT_SIDEBAR_PINNED, application.getResources().getBoolean(R.bool.tablet));
    }

    public void setSidebarPinned(boolean value) {
        saveBoolean(PrefsConst.PREF_CONTENT_SIDEBAR_PINNED, value, defaultPreferences);
    }

    public boolean isSidebarOpened() {
        return defaultPreferences.getBoolean(PrefsConst.PREF_CONTENT_SIDEBAR_OPENED, false);
    }

    public void setSidebarOpened(boolean value) {
        saveBoolean(PrefsConst.PREF_CONTENT_SIDEBAR_OPENED, value, defaultPreferences);
    }

    public boolean isDirectDownload() {
        return defaultPreferences.getBoolean(PrefsConst.PREF_DIRECT_DOWNLOAD, false);
    }

    public void setDirectDownload(boolean value) {
        saveBoolean(PrefsConst.PREF_DIRECT_DOWNLOAD, value, defaultPreferences);
    }

    public int getLastCatalogValidatedVersion() {
        return defaultPreferences.getInt(PrefsConst.PREF_LAST_CATALOG_VALIDATED_VERSION, 0);
    }

    public void setLastCatalogValidatedVersion(int value) {
        saveInt(PrefsConst.PREF_LAST_CATALOG_VALIDATED_VERSION, value, defaultPreferences);
    }
}