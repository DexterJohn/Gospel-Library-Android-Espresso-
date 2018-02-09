package org.lds.ldssa.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.ThemeUtil;

import javax.inject.Inject;

import pocketknife.BindExtra;
import pocketknife.NotRequired;
import pocketknife.PocketKnife;

public class LanguageChangeActivity extends AppCompatActivity {
    public static final String EXTRA_SCREEN_ID = "EXTRA_SCREEN_ID";
    public static final String EXTRA_LANGUAGE_ID = "EXTRA_LANGUAGE_ID";
    public static final String EXTRA_URI = "EXTRA_URI";

    @Inject
    InternalIntents internalIntents;
    @Inject
    ScreenUtil screenUtil;
    @Inject
    ScreenLauncherUtil screenLauncherUtil;
    @Inject
    LanguageManager languageManager;
    @Inject
    Prefs prefs;
    @Inject
    ThemeUtil themeUtil;

    @BindExtra(EXTRA_SCREEN_ID)
    long screenId;
    @BindExtra(EXTRA_LANGUAGE_ID)
    long languageId;
    @NotRequired
    @BindExtra(EXTRA_URI)
    String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.INSTANCE.get().inject(this);

        PocketKnife.bindExtras(this);

        themeUtil.applyTheme(this); // must apply theme or get a crash in support library

        prefs.setLastSelectedLanguageId(languageId);
        screenUtil.updateLanguage(screenId, languageId);

        // try to show the same content in the given uri
        if (uri != null) {
            internalIntents.showUriActivity(this, screenId,  uri, true, false, false, Analytics.Referrer.LANGUAGE_CHANGED);
        } else {
            // default
            internalIntents.showCatalogRoot(this, screenId);
        }
    }
}
