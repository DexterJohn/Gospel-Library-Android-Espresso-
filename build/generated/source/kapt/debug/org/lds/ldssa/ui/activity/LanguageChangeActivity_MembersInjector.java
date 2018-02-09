package org.lds.ldssa.ui.activity;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.ThemeUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LanguageChangeActivity_MembersInjector
    implements MembersInjector<LanguageChangeActivity> {
  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<ScreenUtil> screenUtilProvider;

  private final Provider<ScreenLauncherUtil> screenLauncherUtilProvider;

  private final Provider<LanguageManager> languageManagerProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<ThemeUtil> themeUtilProvider;

  public LanguageChangeActivity_MembersInjector(
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider,
      Provider<LanguageManager> languageManagerProvider,
      Provider<Prefs> prefsProvider,
      Provider<ThemeUtil> themeUtilProvider) {
    this.internalIntentsProvider = internalIntentsProvider;
    this.screenUtilProvider = screenUtilProvider;
    this.screenLauncherUtilProvider = screenLauncherUtilProvider;
    this.languageManagerProvider = languageManagerProvider;
    this.prefsProvider = prefsProvider;
    this.themeUtilProvider = themeUtilProvider;
  }

  public static MembersInjector<LanguageChangeActivity> create(
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider,
      Provider<LanguageManager> languageManagerProvider,
      Provider<Prefs> prefsProvider,
      Provider<ThemeUtil> themeUtilProvider) {
    return new LanguageChangeActivity_MembersInjector(
        internalIntentsProvider,
        screenUtilProvider,
        screenLauncherUtilProvider,
        languageManagerProvider,
        prefsProvider,
        themeUtilProvider);
  }

  @Override
  public void injectMembers(LanguageChangeActivity instance) {
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectScreenUtil(instance, screenUtilProvider.get());
    injectScreenLauncherUtil(instance, screenLauncherUtilProvider.get());
    injectLanguageManager(instance, languageManagerProvider.get());
    injectPrefs(instance, prefsProvider.get());
    injectThemeUtil(instance, themeUtilProvider.get());
  }

  public static void injectInternalIntents(
      LanguageChangeActivity instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectScreenUtil(LanguageChangeActivity instance, ScreenUtil screenUtil) {
    instance.screenUtil = screenUtil;
  }

  public static void injectScreenLauncherUtil(
      LanguageChangeActivity instance, ScreenLauncherUtil screenLauncherUtil) {
    instance.screenLauncherUtil = screenLauncherUtil;
  }

  public static void injectLanguageManager(
      LanguageChangeActivity instance, LanguageManager languageManager) {
    instance.languageManager = languageManager;
  }

  public static void injectPrefs(LanguageChangeActivity instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectThemeUtil(LanguageChangeActivity instance, ThemeUtil themeUtil) {
    instance.themeUtil = themeUtil;
  }
}
