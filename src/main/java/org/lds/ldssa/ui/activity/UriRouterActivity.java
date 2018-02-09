package org.lds.ldssa.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.userdata.screen.Screen;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.LanguageUtil;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.ThemeUtil;
import org.lds.ldssa.util.UriUtil;

import javax.inject.Inject;

import timber.log.Timber;


/**
 * VALID gospellibrary:
 * adb shell am start -W -a android.intent.action.VIEW -d "gospellibrary://content/scriptures/bofm/moro/10?verse=32#p32"
 * adb shell am start -W -a android.intent.action.VIEW -d "gospellibrary://content/scriptures/bofm/moro/10?para=p32#p32"
 * adb shell am start -W -a android.intent.action.VIEW -d "gospellibrary://content/scriptures/bofm/1-ne/16?verse=18-25#p18"
 * adb shell am start -W -a android.intent.action.VIEW -d "gospellibrary://content/scriptures/bofm/1-ne/16?para=p18-p25#p18"
 * adb shell am start -W -a android.intent.action.VIEW -d "gospellibrary://content/scriptures/pgp/js-h/1#p76"
 * adb shell am start -W -a android.intent.action.VIEW -d "gospellibrary://content/manual/preach-my-gospel-a-guide-to-missionary-service/how-do-i-prepare-people-for-baptism-and-confirmation"
 *
 * VALID www.lds.org
 * adb shell am start -W -a android.intent.action.VIEW -d "https://www.lds.org/scriptures/bofm/hel/3?lang=eng"
 * adb shell am start -W -a android.intent.action.VIEW -d "http://www.lds.org/scriptures/bofm/hel/5?lang=eng"
 * adb shell am start -W -a android.intent.action.VIEW -d "https://www.lds.org/scriptures/bofm/hel/4?verse=1-2#p1"
 * adb shell am start -W -a android.intent.action.VIEW -d "https://www.lds.org/scriptures/bofm/hel/4?para=p1-p2#p1"
 * adb shell am start -W -a android.intent.action.VIEW -d "https://www.lds.org/general-conference/2015/04/the-plan-of-happiness"
 *
 * VALID deprecated legacy url format
 * adb shell am start -W -a android.intent.action.VIEW -d "https://www.lds.org/scriptures/bofm/3-ne/16.7-20#6"
 * adb shell am start -W -a android.intent.action.VIEW -d "https://www.lds.org/scriptures/bofm/3-ne/16.7-20?lang=dan#6"
 *
 * VALID www.lds.org (requires escaping for command line execution)
 * https://www.lds.org/scriptures/bofm/hel/5?verse=1-2&lang=eng
 * adb shell am start -W -a android.intent.action.VIEW -d "https://www.lds.org/scriptures/bofm/hel/5?verse=1-2\&lang=eng"
 *
 * https://www.lds.org/scriptures/bofm/hel/5?para=p1-p2&lang=spa
 * adb shell am start -W -a android.intent.action.VIEW -d "https://www.lds.org/scriptures/bofm/hel/5?para=p1-p2\&lang=spa"
 *
 * https://www.lds.org/scriptures/bofm/enos/1?verse=18-20&lang=eng#p18
 * adb shell am start -W -a android.intent.action.VIEW -d "https://www.lds.org/scriptures/bofm/enos/1?verse=18-20\&lang=eng#p18"
 *
 * VALID Partial
 * adb shell am start -W -a android.intent.action.VIEW -d "https://www.lds.org/scriptures" (currently NOT supported because no URI in library_collection)
 * adb shell am start -W -a android.intent.action.VIEW -d "https://www.lds.org/scriptures/bofm"
 * adb shell am start -W -a android.intent.action.VIEW -d "https://www.lds.org/scriptures/bofm/hel" (currently NOT supported because uri form in nav_collection)
 *
 * False positives
 * adb shell am start -W -a android.intent.action.VIEW -d "https://www.lds.org/scriptures/bofm"
 * adb shell am start -W -a android.intent.action.VIEW -d "https://www.lds.org/scriptures/bofm/hel/109"
 * adb shell am start -W -a android.intent.action.VIEW -d "https://www.lds.org/scriptures"
 *
 * Extras
 * adb shell am start -W -a android.intent.action.VIEW -d "gospellibrary://content?lang=spa"
 * adb shell am start -W -a android.intent.action.VIEW -d "gospellibrary://content?lang=fra"
 */
public class UriRouterActivity extends AppCompatActivity {
    @Inject
    InternalIntents internalIntents;
    @Inject
    ScreenUtil screenUtil;
    @Inject
    ScreenLauncherUtil screenLauncherUtil;
    @Inject
    UriUtil uriUtil;
    @Inject
    LanguageManager languageManager;
    @Inject
    Prefs prefs;
    @Inject
    LanguageUtil languageUtil;
    @Inject
    ThemeUtil themeUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.INSTANCE.get().inject(this);

        Intent intent = getIntent();

        if (intent == null) {
            screenLauncherUtil.showStartupScreen(this);
            return;
        }

        Uri intentUri = intent.getData();
        Timber.i("Received external uri: %s", intentUri);

        themeUtil.applyTheme(this);   // must set theme or get a crash in support library

        // Get the language from the Uri if it exists
        long languageId = uriUtil.findLanguageIdFromUri(intentUri);
        if (languageId == 0) {
            languageId = prefs.getLastSelectedLanguageId(languageUtil);
        }

        Screen screen = screenUtil.newScreen(languageId);
        internalIntents.showUriActivity(this, screen.getId(), intentUri.toString(), true, false, true, Analytics.Referrer.EXTERNAL_LINK);
    }
}
