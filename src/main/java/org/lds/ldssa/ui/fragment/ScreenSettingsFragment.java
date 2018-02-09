package org.lds.ldssa.ui.fragment;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceGroup;
import android.support.v7.preference.SwitchPreferenceCompat;

import org.lds.ldssa.R;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.prefs.PrefsConst;
import org.lds.ldssa.util.ScreenUtil;

import javax.inject.Inject;

import pocketknife.PocketKnife;

public class ScreenSettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener, SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    Prefs prefs;
    @Inject
    ScreenUtil screenUtil;

    public static ScreenSettingsFragment newInstance() {
        return new ScreenSettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Injector.INSTANCE.get().inject(this);
        PocketKnife.bindArguments(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.prefs_screens);
    }

    @Override
    public void onResume() {
        super.onResume();

        updateDeviceSpecificPreferenceDisplays();

        registerChangeListener(getPreferenceScreen());

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
            case PrefsConst.PREF_TABS_IN_OVERVIEW:
                screenUtil.onScreenInOverviewChanged(getActivity());
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
            case PrefsConst.PREF_TABS_IN_OVERVIEW:
                prefs.setScreensInOverview((Boolean) value);
                break;
            default:
                //Purposefully left blank
        }

        return true;
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

    private void updateDeviceSpecificPreferenceDisplays() {
        Preference tabsInOverviewPref = findPreference(PrefsConst.PREF_TABS_IN_OVERVIEW);
        if (tabsInOverviewPref != null) {
            tabsInOverviewPref.setVisible(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
            ((SwitchPreferenceCompat) tabsInOverviewPref).setChecked(prefs.isScreensInOverview());
        }
    }
}
