package org.lds.ldssa.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.lds.ldssa.R;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.ui.fragment.AudioSettingsFragment;

import javax.inject.Inject;

import pocketknife.PocketKnife;

public class AudioSettingsActivity extends AppCompatActivity {
    @Inject
    Prefs prefs;
    @Inject
    Analytics analytics;

    public AudioSettingsActivity() {
        Injector.INSTANCE.get().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        updateTheme();
        setContentView(R.layout.activity_general_single_fragment_no_toolbar);
        PocketKnife.bindExtras(this);

        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_pos1, AudioSettingsFragment.newInstance()).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void updateTheme() {
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
}