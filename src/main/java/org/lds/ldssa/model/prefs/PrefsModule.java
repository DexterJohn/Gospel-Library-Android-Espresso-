package org.lds.ldssa.model.prefs;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class PrefsModule {
    @Provides
    @Named(Prefs.BACKED_UP_PREFERENCE_NAME)
    SharedPreferences provideBackedUpSharedPreferences(Application application) {
        return application.getSharedPreferences(Prefs.BACKED_UP_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Named(Prefs.DEFAULT_PREFERENCE_NAME)
    SharedPreferences provideDefaultSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }
}
