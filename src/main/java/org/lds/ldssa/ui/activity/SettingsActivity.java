package org.lds.ldssa.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;

import org.lds.ldssa.R;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.model.database.types.ScreenSourceType;
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItem;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.ui.fragment.SettingsFragment;
import org.lds.ldssa.util.ScreenLauncherUtil;

import javax.inject.Inject;

public class SettingsActivity extends BaseActivity implements ScreenLauncherUtil.ScreenActivity {
    @Inject
    Prefs prefs;
    @Inject
    Analytics analytics;

    public SettingsActivity() {
        Injector.INSTANCE.get().inject(this);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_general_single_fragment_no_toolbar;
    }

    @NonNull
    @Override
    protected String getAnalyticsScreenName() {
        return Analytics.Screen.SETTINGS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applyTheme();
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_pos1, SettingsFragment.newInstance(getScreenId())).commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // This has to be here for onActivityResult to work in the fragment
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void applyTheme() {
        switch (prefs.getGeneralDisplayTheme()) {
            case SEPIA:
                setTheme(R.style.AppTheme_Sepia_Settings);
                break;
            case DARK:
                setTheme(R.style.AppTheme_Dark_Settings);
                break;
            case DARK_BLUE:
                setTheme(R.style.AppTheme_DarkBlue_Settings);
                break;
            case MAGENTA:
                setTheme(R.style.AppTheme_Magenta_Settings);
                break;
            default:
                //Light is the default
        }
    }

    @Override
    public boolean isScreenHistoryItemEqual(@NonNull ScreenHistoryItem currentScreenHistoryItem) {
        return currentScreenHistoryItem.getSourceType() == ScreenSourceType.SETTINGS;
    }

    @Override
    public void setCurrentScreenHistoryData(@NonNull ScreenHistoryItem screenHistoryItem) {
        screenHistoryItem.setSourceType(ScreenSourceType.SETTINGS);
    }
}