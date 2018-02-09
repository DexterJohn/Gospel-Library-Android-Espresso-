package org.lds.ldssa.model.prefs;

import android.content.SharedPreferences;

/**
 * A base preferences class that is similar to the {@link org.lds.mobile.prefs.PrefsBase}
 * with the exception that all save and get methods take a SharedPreferences instead of
 * using an abstract method.  This allows us to use named instances
 */
public abstract class PrefsBase {

    protected void saveString(String key, String value, SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    protected void saveLong(String key, long value, SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    protected void saveInt(String key, int value, SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    protected void saveFloat(String key, float value, SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    protected void saveBoolean(String key, boolean value, SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    protected void remove(String key, SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    protected void reset(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    protected int parseInt(String stringVal, int defaultVal) {
        if (stringVal == null) {
            return defaultVal;
        }

        try {
            return Integer.valueOf(stringVal);
        } catch (Exception e) {
            return defaultVal;
        }
    }
}
