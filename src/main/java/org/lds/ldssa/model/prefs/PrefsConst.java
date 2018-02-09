package org.lds.ldssa.model.prefs;

import org.lds.ldssa.model.database.types.HighlightAnnotationStyle;
import org.lds.ldssa.model.prefs.model.HighlightHistoryItem;
import org.lds.ldssa.util.HighlightColor;

/**
 * Constants for the {@link Prefs}
 */
public final class PrefsConst {

    private PrefsConst() {
    }

    // Default preference values
    public static final int RECENT_HIGHLIGHT_HISTORY_ITEM_COUNT = 5;
    public static final HighlightHistoryItem[] DEFAULT_HIGHLIGHT_HISTORY_ITEMS = new HighlightHistoryItem[]{
            new HighlightHistoryItem(HighlightColor.YELLOW, HighlightAnnotationStyle.FILL),
            new HighlightHistoryItem(HighlightColor.RED, HighlightAnnotationStyle.UNDERLINE),
            new HighlightHistoryItem(HighlightColor.GREEN, HighlightAnnotationStyle.FILL),
            new HighlightHistoryItem(HighlightColor.BLUE, HighlightAnnotationStyle.FILL),
            new HighlightHistoryItem(HighlightColor.PINK, HighlightAnnotationStyle.FILL),
    };

    public static final Integer DEFAULT_LANGUAGE_ID = 1; // Default is English

    // Content display preferences
    public static final String PREF_LAST_SELECTED_LANGUAGE = "last_selected_language";
    public static final String PREF_CONTENT_DISPLAY_FONT_OPTIONS = "font_options";
    public static final String PREF_CONTENT_TEXT_SIZE = "text_size";
    public static final String PREF_CONTENT_SIDEBAR_PINNED = "sidebar_pinned";
    public static final String PREF_CONTENT_SIDEBAR_OPENED = "sidebar_opened";

    // General display preferences
    public static final String PREF_GENERAL_DISPLAY_THEME = "display_theme";
    public static final String PREF_GENERAL_DISPLAY_LIST_LAYOUT = "key_show_as_list";

    // Media preferences
    public static final String PREF_LIMIT_MOBILE_NETWORK = "limit_mobile_network";

    static final String PREF_LAST_INSTALLED_VERSION_CODE = "last_installed_version_code";

    // Account preferences
    static final String PREF_CURRENT_PERSON_ID = "person_id";
    static final String PREF_CURRENT_PERSON_ANNOTATION_INSTANCE_ID = "person_annotation_instance_id";


    // === SYNC ===
    // Annotation preferences
    static final String PREF_ANNOTATION_SYNC_LAST_FULL_SYNC = "last_full_sync";
    static final String PREF_ANNOTATION_SYNC_LATEST_RESULT = "last_sync_result";

    // Internal use with internal database
    static final String PREF_FOLDER_SYNC_SINCE_TIME = "folder_sync_since";
    static final String PREF_ANNOTATION_SYNC_SINCE_TIME = "annotation_sync_since";

    // Server Sync Timestamp (pulled from "before" and placed in "since")
    static final String PREF_SERVER_FOLDER_SYNC_SINCE_TIME = "server_folder_sync_since_ts";
    static final String PREF_SERVER_ANNOTATION_SYNC_SINCE_TIME = "server_annotation_sync_since_ts";

    @Deprecated // remove on 4.0.0.17
    static final String OLD_PREF_SERVER_FOLDER_SYNC_SINCE_TIME = "server_folder_sync_since";
    @Deprecated // remove on 4.0.0.17
    static final String OLD_PREF_SERVER_ANNOTATION_SYNC_SINCE_TIME = "server_annotation_sync_since";


    static final String PREF_LAST_SYNC_ERROR_COUNT = "key_last_sync_error_count";

    // === CATALOG ===
    static final String PREF_CATALOG_UPDATE_LAST_CHECK = "last_catalog_update";
    static final String PREF_CATALOG_UPDATE_LAST_DOWNLOAD = "last_catalog_update_download";
    static final String PREF_CATALOG_FORCE_UPDATE_WHEN_AVAILABLE = "catalog_force_update_when_available";

    // === TIPS ===
    static final String PREF_TIPS_UPDATE_LAST_CHECK = "last_tips_update";

    // App info preferences
    @Deprecated
    static final String PREF_LAST_VISIBLE_TAB_ID = "last_visible_tab_id";
    static final String PREF_LAST_VISIBLE_SCREEN_ID = "last_visible_screen_id";

    // Catalog preferences
    static final String PREF_INITIAL_CONTENT_DOWNLOADED = "initial_content_downloaded";
    static final String PREF_DIRECT_DOWNLOAD = "direct_download";
    static final String PREF_CONTENT_DISPLAY_RECENT_HIGHLIGHTS = "recent_highlights";
    public static final String PREF_CONTENT_HIDE_FOOTNOTES = "hide_footnotes";
    public static final String PREF_TABS_IN_OVERVIEW = "tabs_in_overview";
    static final String PREF_GENERAL_DISPLAY_MEDIA_SORT_BY_SIZE = "media_sort_order";

    // Dev Preferences
    public static final String PREF_DEV_ACCOUNT_APP_INFO = "dev_account_app_info";
    public static final String PREF_DEV_CONTENT_SERVER = "dev_content_server";
    public static final String PREF_DEV_CATALOG_DOWNLOAD_TIMEOUT_OVERRIDE = "dev_catalog_download_timeout_override";
    public static final String PREF_DEV_FORCE_CATALOG_UPDATE = "dev_force_catalog_update";
    public static final String PREF_DEV_FORCE_CATALOG_VERSION = "dev_catalog_version";
    public static final String PREF_DEV_FORCE_TIPS_VERSION = "dev_tips_version";
    public static final String PREF_DEV_FORCE_NO_ROLES = "dev_force_no_roles";
    public static final String PREF_DEV_SCRAMBLE_PASSWORD = "dev_account_scramble_password";
    public static final String PREF_DEV_TEST_SYNC_DATA = "dev_test_sync_data";
    public static final String PREF_DEV_OVERRIDE_DOWNLOAD_URI = "dev_override_download_uri";

    // Miscellaneous preferences
    static final String PREF_MISC_SYNC_WARNING_TIMESTAMP = "KEY_SYNC_WARNING_TIMESTAMP";
    static final String PREF_ENABLE_ANALYTICS = "ANALYTICS_ENABLED";
    static final String PREF_LAST_APP_UPDATE_CHECK_TIME = "last_app_update_check_time";
    static final String PREF_LAST_SEARCH_SUGGEST_UPDATE_CHECK_TIME = "last_search_suggest_update_check_time";
    static final String PREF_PREVIEW_WARNING_SHOWN = "pref_preview_warning_shown";
    static final String PREF_PREVIEW_ENDED_LAST_MESSAGE_TS = "pref_preview_last_message_ts";
    static final String PREF_LAST_VIEWED_WELCOME_TIPS_APP_VERSION = "pref_last_viewed_welcome_tips_app_version";
    public static final String PREF_SORT_TAG = "pref_sort_tag";
    public static final String PREF_SORT_NOTEBOOK = "pref_sort_notebook";
    static final String PREF_LAST_CATALOG_VALIDATED_VERSION = "last_catalog_verified_version_int";

    // Training preferences
    static final String PREF_TRAINING_FULLSCREEN_HELP = "did_show_full_screen_help";

    // Audio preferences
    public static final String PREF_AUDIO_SPEED = "audio_speed";
    public static final String PREF_AUDIO_VOICE = "audio_voice";
    public static final String PREF_AUDIO_CONTINUOUS_PLAY = "audio_continuous_play";
    public static final String PREF_TEXT_TO_SPEECH_SETTINGS = "text_to_speech_settings";

    // Other
    static final String PREF_DEVELOPER_MODE = "developer_mode";
    static final String PREF_LOCATIONS_PAGER_POSITION = "locations_pager_position";
    static final String PREF_NOTES_PAGER_POSITION = "notes_pager_position";
    public static final String PREF_ENABLE_OBSOLETE_ITEMS = "pref_enable_obsolete_items";
    static final String PREF_STUDY_PLANS_PAGER_POSITION = "study_plans_pager_position";

    // LocalyticsProfile
    public static final String PREF_ALLOW_IN_APP_NOTIFICATIONS = "pref_allow_in_app_notifications";

    static final String PREF_LAST_ERROR_MESSAGE = "key_last_error_message";
    static final String PREF_LAST_ERROR_DETAILS = "key_last_error_details";
    static final String PREF_LAST_ERROR_TIME = "key_last_error_time";

    static final String PREF_LAST_DOWNLOAD_FAILED_ERROR_MESSAGE = "key_last_download_failed_error_message";


    public static final String PREF_LEGACY_ACCOUNT_USERNAME = "account_username";
    public static final String PREF_LEGACY_ACCOUNT_PASSWORD = "account_password_updated";
}
