package org.lds.ldssa.ui.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceGroup;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.view.View;

import com.localytics.android.Localytics;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.lds.ldsaccount.LDSAccountPrefs;
import org.lds.ldsaccount.ui.SignInFragment;
import org.lds.ldssa.BuildConfig;
import org.lds.ldssa.R;
import org.lds.ldssa.analytics.LocalyticsProfile;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.event.account.AccountSignOutEvent;
import org.lds.ldssa.event.sync.AnnotationSyncFinishedEvent;
import org.lds.ldssa.event.sync.AnnotationSyncStatusEvent;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.intent.ExternalIntents;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.prefs.PrefsConst;
import org.lds.ldssa.model.prefs.model.ContentServerType;
import org.lds.ldssa.model.prefs.type.DisplayThemeType;
import org.lds.ldssa.sync.AnnotationSync;
import org.lds.ldssa.ui.dialog.TextSizeDialogFragment;
import org.lds.ldssa.util.AccountUtil;
import org.lds.ldssa.util.FeedbackUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.ux.about.AboutActivity;
import org.lds.mobile.about.AboutConst;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import pocketbus.Bus;
import pocketbus.Subscribe;
import pocketbus.ThreadMode;
import pocketknife.BindArgument;
import pocketknife.PocketKnife;

public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String PREF_ACCOUNT_SIGN_IN_OUT = "account_sign_in_out";
    private static final String PREF_ACCOUNT_CREATE = "account_create";
    public static final String ARG_SCREEN_ID = "ARG_SCREEN_ID";

    //Non Preference Keys (i.e. not used to store/retrieve settings)
    private static final String KEY_DOWNLOADED_MEDIA = "prefs_downloaded_media";

    @Inject
    AccountUtil accountUtil;
    @Inject
    Bus bus;
    @Inject
    Prefs prefs;
    @Inject
    FeedbackUtil feedbackUtil;
    @Inject
    LDSAccountPrefs ldsAccountPrefs;
    @Inject
    InternalIntents internalIntents;
    @Inject
    ExternalIntents externalIntents;
    @Inject
    DownloadedMediaManager downloadedMediaManager;
    @Inject
    GLDownloadManager downloadManager;
    @Inject
    ScreenUtil screenUtil;
    @Inject
    AnnotationSync annotationSync;

    @BindArgument(ARG_SCREEN_ID)
    long screenId;

    private PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
    private Preference signInOutPreference;

    private boolean devSettingsAdded = false;

    public static SettingsFragment newInstance(long screenId) {
        Bundle bundle = new Bundle();
        bundle.putLong(ARG_SCREEN_ID, screenId);

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Injector.INSTANCE.get().inject(this);
        PocketKnife.bindArguments(this);
        super.onCreate(savedInstanceState);

        signInOutPreference = findPreference(PREF_ACCOUNT_SIGN_IN_OUT);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.prefs_general);
        addPreferencesFromResource(R.xml.about_preferences);
    }

    private Intent createIntent(AboutActivity.ViewType viewType) {
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        intent.putExtra(AboutActivity.EXTRA_VIEW_TYPE, viewType);
        return intent;
    }

    @Override
    public void onStart() {
        super.onStart();
        bus.register(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (prefs.isDeveloperModeEnabled() && !devSettingsAdded) {
            addPreferencesFromResource(R.xml.prefs_dev);
            devSettingsAdded = true;
        }

        updateLdsAccountPreferences();
        updateThemePreferenceDisplay(true);
        updateAboutPreference();
        updateDownloadedMediaPreference();
        updateDeviceSpecificPreferenceDisplays();
        updateBackedUpPreferences();
        updateServerPreference();
        updateDevPreferences();

        registerChangeListener(getPreferenceScreen());

        prefs.registerChangeListener(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        prefs.unregisterChangeListener(this);
    }

    @Override
    public void onStop() {
        bus.unregister(this);
        super.onStop();
    }

    /**
     * For changes that have already been persisted
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case PrefsConst.PREF_GENERAL_DISPLAY_THEME:
                updateThemePreferenceDisplay(false);
                break;
            case PrefsConst.PREF_TABS_IN_OVERVIEW:
                screenUtil.onScreenInOverviewChanged(getActivity());
                break;
            case PrefsConst.PREF_CONTENT_HIDE_FOOTNOTES:
            case PrefsConst.PREF_GENERAL_DISPLAY_LIST_LAYOUT:
            case PrefsConst.PREF_ENABLE_OBSOLETE_ITEMS:
                updateBackedUpPreferences();
                break;
            case PrefsConst.PREF_ALLOW_IN_APP_NOTIFICATIONS:
                updateInAppNotificationPreference();
                break;
            case PrefsConst.PREF_DEV_FORCE_CATALOG_VERSION:
            case PrefsConst.PREF_DEV_FORCE_TIPS_VERSION:
                updateDevPreferences();
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
            //Values we manually persist (Device Specific)
            case PrefsConst.PREF_LIMIT_MOBILE_NETWORK:
                prefs.setMobileNetworkLimited((Boolean) value);
                break;
            case PrefsConst.PREF_TABS_IN_OVERVIEW:
                prefs.setScreensInOverview((Boolean) value);
                break;
            case PrefsConst.PREF_DEV_CONTENT_SERVER:
                updateServerPreference((ListPreference) preference, ContentServerType.values()[Integer.valueOf((String) value)]);
                break;
            // !!!Backed up Preferences must be persisted in code here!!!
            case PrefsConst.PREF_CONTENT_HIDE_FOOTNOTES:
                prefs.setContentHideFootnotes((Boolean) value);
                return false;
            case PrefsConst.PREF_GENERAL_DISPLAY_LIST_LAYOUT:
                prefs.setGeneralDisplayAsList((Boolean) value);
                return false;
            case PrefsConst.PREF_GENERAL_DISPLAY_THEME:
                prefs.setGeneralDisplayTheme(DisplayThemeType.getByDisplayOrder(Integer.parseInt((String) value)));
                return false;
            case PrefsConst.PREF_ENABLE_OBSOLETE_ITEMS:
                prefs.setObsoleteContentEnabled((Boolean) value);
                return false;
            case PrefsConst.PREF_ALLOW_IN_APP_NOTIFICATIONS:
                prefs.setAllowInAppNotifications((Boolean) value);
            default:
                //Purposefully left blank
        }

        return true;
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        FragmentActivity activity = getActivity();
        if (preference.getKey() != null && activity != null) {
            switch (preference.getKey()) {
                case PrefsConst.PREF_CONTENT_DISPLAY_FONT_OPTIONS:
                    TextSizeDialogFragment fontOptionsDialog = new TextSizeDialogFragment();
                    fontOptionsDialog.show(activity.getSupportFragmentManager(), "");
                    return true;
                case PREF_ACCOUNT_SIGN_IN_OUT:
                    onSignInSignOutClicked();
                    return true;
                case PREF_ACCOUNT_CREATE:
                    externalIntents.showAccountCreation(getActivity());
                    return true;
                case KEY_DOWNLOADED_MEDIA:
                    internalIntents.showMediaFileManager(activity, screenId);
                    return true;
                case AboutConst.KEY_FEATURED_APPS:
                    startActivity(createIntent(AboutActivity.ViewType.OTHER_APPS));
                    return true;
                case AboutConst.KEY_FEEDBACK:
                    feedbackUtil.sendAttachmentBugReport(activity, screenId);
                    return true;
                case AboutConst.KEY_ABOUT:
                    startActivity(createIntent(AboutActivity.ViewType.ABOUT));
                    return true;

                // Developer Settings
                case PrefsConst.PREF_DEV_ACCOUNT_APP_INFO:
                    internalIntents.showAppInfo(getActivity());
                    return true;
                case PrefsConst.PREF_DEV_TEST_SYNC_DATA:
                    annotationSync.testSyncDataWithServer();
                    return true;
                case PrefsConst.PREF_DEV_SCRAMBLE_PASSWORD:
                    ldsAccountPrefs.saveCredentials(ldsAccountPrefs.getUsername(), "test_invalid_password");
                    View view = getView();
                    if (view != null) {
                        Snackbar.make(view, "Password Changed.  Restart app", Snackbar.LENGTH_LONG).show();
                    }
                    return true;

                default:
                    //Purposefully left blank
            }
        }

        return super.onPreferenceTreeClick(preference);
    }

    @Subscribe(ThreadMode.MAIN)
    public void handle(AnnotationSyncFinishedEvent event) {
        signInOutPreference.setSummary(getSignOutSummary());
    }

    @Subscribe(ThreadMode.MAIN)
    public void handle(AnnotationSyncStatusEvent event) {
        signInOutPreference.setSummary(event.getMessage());
    }

    @Subscribe(ThreadMode.MAIN)
    public void handle(AccountSignOutEvent event) {
        updateLdsAccountPreferences();
    }

    private boolean isSignInSuccessful(@Nullable Intent activityResultIntent) {
        return activityResultIntent != null && activityResultIntent.getBooleanExtra(SignInFragment.RESULT_EXTRA_SIGNING_RESULT, false);
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

    private void onSignInSignOutClicked() {
        if (!ldsAccountPrefs.hasCredentials()) {
            accountUtil.signInPrompt(this);
        } else {
            accountUtil.signOutPrompt(this);
        }
    }

    /**
     * Updates the summary for the theme preference
     */
    private void updateThemePreferenceDisplay(boolean initialSetup) {
        ListPreference backgroundPref = (ListPreference) findPreference(PrefsConst.PREF_GENERAL_DISPLAY_THEME);
        if (backgroundPref != null) {
            DisplayThemeType mode = prefs.getGeneralDisplayTheme();
            switch (mode) {
                case SEPIA:
                    backgroundPref.setSummary(R.string.theme_sepia);
                    break;
                case DARK:
                    backgroundPref.setSummary(R.string.theme_night);
                    break;
                case DARK_BLUE:
                    backgroundPref.setSummary(R.string.theme_dark_blue);
                    break;
                case MAGENTA:
                    backgroundPref.setSummary(R.string.theme_magenta);
                    break;
                default:
                    backgroundPref.setSummary(R.string.theme_default);
                    break;
            }
            backgroundPref.setValue(String.valueOf(mode.getDisplayOrder()));
        }

        //Restarts the application for the new theme to take effect
        if (!initialSetup) {
            internalIntents.showSettings(getActivity(), screenId);
        }
    }

    private void updateAboutPreference() {
        Preference preference = findPreference(AboutConst.KEY_ABOUT);
        if (preference != null) {
            preference.setSummary(getContext().getResources().getString(R.string.splash_version, BuildConfig.VERSION_NAME));
        }
    }

    private void updateDownloadedMediaPreference() {
        Preference preference = findPreference(KEY_DOWNLOADED_MEDIA);
        if (preference != null) {
            long downloadedSize = downloadedMediaManager.getTotalDownloadsSize();
            String summary = downloadedSize > 0 ? FileUtils.byteCountToDisplaySize(downloadedSize) : getString(R.string.none);
            preference.setSummary(summary);
        }

    }

    private void updateLdsAccountPreferences() {
        final boolean hasCredentials = ldsAccountPrefs.hasCredentials();

        //Updates the display text for the sign out option
        signInOutPreference.setTitle(hasCredentials ? ldsAccountPrefs.getUsername() : getString(R.string.signin));
        signInOutPreference.setSummary(hasCredentials ? (annotationSync.isSyncInProgress() ? getString(R.string.syncing_annotations) :
                getSignOutSummary()) : getString(R.string.lds_account_summary));

        //Removes the Create Account option
        final Preference accountCreate = findPreference(PREF_ACCOUNT_CREATE);
        accountCreate.setVisible(!(hasCredentials && accountCreate.isVisible()));
    }

    private void updateDeviceSpecificPreferenceDisplays() {
        Preference limitMobileNetworkPref = findPreference(PrefsConst.PREF_LIMIT_MOBILE_NETWORK);
        if (limitMobileNetworkPref != null && limitMobileNetworkPref instanceof SwitchPreferenceCompat) {
            ((SwitchPreferenceCompat) limitMobileNetworkPref).setChecked(prefs.isMobileNetworkLimited());
        }

        Preference tabsInOverviewPref = findPreference(PrefsConst.PREF_TABS_IN_OVERVIEW);
        if (tabsInOverviewPref != null) {
            tabsInOverviewPref.setVisible(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
            ((SwitchPreferenceCompat) tabsInOverviewPref).setChecked(prefs.isScreensInOverview());
        }

        Preference contentServerPref = findPreference(PrefsConst.PREF_DEV_CONTENT_SERVER);
        if (contentServerPref != null && contentServerPref instanceof ListPreference) {
            updateServerPreference((ListPreference) contentServerPref, prefs.getContentServerType());
        }
    }

    private String getSignOutSummary() {
        long lastSyncTime = prefs.getLastAnnotationFullSyncTime();
        String text = lastSyncTime <= 0 ? getString(R.string.never) : prettyTime.format(new Date(lastSyncTime));
        // Using the activity to get the string in case of rotation change
        String summaryString = getString(R.string.settings_last_sync, text);
        if (lastSyncTime > 0) {
            String lastErrorText = prefs.getLatestAnnotationSyncResult();
            if (!StringUtils.isEmpty(lastErrorText)) {
                summaryString += " - (" + lastErrorText + ")";
            }
        }

        return summaryString;
    }

    private void updateServerPreference() {
        ListPreference contentEnvPref = (ListPreference) findPreference(PrefsConst.PREF_DEV_CONTENT_SERVER);
        updateServerPreference(contentEnvPref, prefs.getContentServerType());
    }

    /**
     * Updates the summary for the content environment preference
     */
    private void updateServerPreference(@Nullable ListPreference contentEnvPref, ContentServerType newValue) {
        // if developer settings are not visible... just return
        if (contentEnvPref == null) {
            return;
        }

        switch (newValue) {
            default:
            case PROD:
                contentEnvPref.setSummary(R.string.prod);
                break;
            case BETA:
                contentEnvPref.setSummary(R.string.beta);
                break;
            case STAGING:
                contentEnvPref.setSummary(R.string.staging);
                break;
            case TEMPLATE:
                contentEnvPref.setSummary(R.string.template);
                break;
            case DEV:
                contentEnvPref.setSummary(R.string.dev);
                break;
        }

        ContentServerType serverType = prefs.getContentServerType();
        if (newValue == serverType) {
            return;
        }

        downloadManager.cancelAllDownloads();

        prefs.setContentServerType(newValue);
        prefs.setForceCatalogUpdate(true);

        internalIntents.relaunch(getActivity());
    }

    private void updateBackedUpPreferences() {
        SwitchPreferenceCompat displayAsListPreference = (SwitchPreferenceCompat) findPreference(PrefsConst.PREF_GENERAL_DISPLAY_LIST_LAYOUT);
        displayAsListPreference.setChecked(prefs.getGeneralDisplayAsList());
        SwitchPreferenceCompat hideFootnotesPreference = (SwitchPreferenceCompat) findPreference(PrefsConst.PREF_CONTENT_HIDE_FOOTNOTES);
        hideFootnotesPreference.setChecked(prefs.getContentHideFootnotes());
        SwitchPreferenceCompat obsoletePreference = (SwitchPreferenceCompat) findPreference(PrefsConst.PREF_ENABLE_OBSOLETE_ITEMS);
        obsoletePreference.setChecked(prefs.isObsoleteContentEnabled());
    }

    private void updateInAppNotificationPreference() {
        boolean allowInAppNotifications = prefs.isAllowInAppNotifications();
        SwitchPreferenceCompat allowInAppNotificationsPreference = (SwitchPreferenceCompat) findPreference(PrefsConst.PREF_ALLOW_IN_APP_NOTIFICATIONS);
        allowInAppNotificationsPreference.setChecked(allowInAppNotifications);

        String value;
        if (allowInAppNotifications) {
            value = LocalyticsProfile.Values.TRUE;
        } else {
            value = LocalyticsProfile.Values.FALSE;
        }
        Localytics.setProfileAttribute(LocalyticsProfile.Attributes.ALLOW_IN_APP_NOTIFICATIONS, value, Localytics.ProfileScope.APPLICATION);
    }

    private void updateDevPreferences() {
        if (!devSettingsAdded) {
            // Developer mode is not enabled and there is nothing to update
            return;
        }

        EditTextPreference catalogVersionPref = (EditTextPreference) findPreference(PrefsConst.PREF_DEV_FORCE_CATALOG_VERSION);
        catalogVersionPref.setSummary(prefs.getDeveloperCatalogVersion() + " - Setting to 0 will ignore");
        EditTextPreference tipsVersionPref = (EditTextPreference) findPreference(PrefsConst.PREF_DEV_FORCE_TIPS_VERSION);
        tipsVersionPref.setSummary(prefs.getDeveloperTipsVersion() + " - Setting to 0 will ignore");
    }
}
