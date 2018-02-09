package org.lds.ldssa.ui.preference;

import android.content.Context;
import android.support.v7.preference.ListPreference;
import android.util.AttributeSet;

import org.lds.ldssa.model.prefs.PrefsConst;

public class LanguageListPreference extends ListPreference {
    public LanguageListPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public LanguageListPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LanguageListPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LanguageListPreference(Context context) {
        super(context);
    }

    @Override
    protected String getPersistedString(String defaultReturnValue) {
        if (!shouldPersist()) {
            return defaultReturnValue;
        }

        return String.valueOf(getPreferenceManager().getSharedPreferences().getLong(getKey(),
                defaultReturnValue != null ? Long.valueOf(defaultReturnValue) : PrefsConst.DEFAULT_LANGUAGE_ID));
    }

    @Override
    protected boolean persistString(String value) {
        return persistLong(value != null ? Long.parseLong(value) : -1);
    }
}
