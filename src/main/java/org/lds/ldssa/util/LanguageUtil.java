package org.lds.ldssa.util;

import org.lds.ldssa.model.database.catalog.language.Language;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LanguageUtil {

    private final LanguageManager languageManager;

    private long deviceLanguageId = 0L;

    @Inject
    public LanguageUtil(LanguageManager languageManager) {
        this.languageManager = languageManager;
    }

    public long getDeviceLanguageId() {
        if (deviceLanguageId <= 0L) {
            String deviceLocale = Locale.getDefault().getISO3Language();
            deviceLanguageId = languageManager.findIdByLocale(deviceLocale);
            if (deviceLanguageId == 0) {
                deviceLanguageId = languageManager.findIdByLocale("eng");
            }
        }

        return deviceLanguageId;
    }

    public Locale getLocaleByLanguageId(long languageId) {
        Language language = languageManager.findByRowId(languageId);
        if (language == null) {
            return null;
        }
        return new Locale(language.getIso6393());
    }
}
