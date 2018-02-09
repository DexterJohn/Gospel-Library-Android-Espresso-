package org.lds.ldssa.ui.activity;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.LanguageUtil;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.ThemeUtil;
import org.lds.ldssa.util.UriUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class UriRouterActivity_MembersInjector implements MembersInjector<UriRouterActivity> {
  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<ScreenUtil> screenUtilProvider;

  private final Provider<ScreenLauncherUtil> screenLauncherUtilProvider;

  private final Provider<UriUtil> uriUtilProvider;

  private final Provider<LanguageManager> languageManagerProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<LanguageUtil> languageUtilProvider;

  private final Provider<ThemeUtil> themeUtilProvider;

  public UriRouterActivity_MembersInjector(
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider,
      Provider<UriUtil> uriUtilProvider,
      Provider<LanguageManager> languageManagerProvider,
      Provider<Prefs> prefsProvider,
      Provider<LanguageUtil> languageUtilProvider,
      Provider<ThemeUtil> themeUtilProvider) {
    this.internalIntentsProvider = internalIntentsProvider;
    this.screenUtilProvider = screenUtilProvider;
    this.screenLauncherUtilProvider = screenLauncherUtilProvider;
    this.uriUtilProvider = uriUtilProvider;
    this.languageManagerProvider = languageManagerProvider;
    this.prefsProvider = prefsProvider;
    this.languageUtilProvider = languageUtilProvider;
    this.themeUtilProvider = themeUtilProvider;
  }

  public static MembersInjector<UriRouterActivity> create(
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider,
      Provider<UriUtil> uriUtilProvider,
      Provider<LanguageManager> languageManagerProvider,
      Provider<Prefs> prefsProvider,
      Provider<LanguageUtil> languageUtilProvider,
      Provider<ThemeUtil> themeUtilProvider) {
    return new UriRouterActivity_MembersInjector(
        internalIntentsProvider,
        screenUtilProvider,
        screenLauncherUtilProvider,
        uriUtilProvider,
        languageManagerProvider,
        prefsProvider,
        languageUtilProvider,
        themeUtilProvider);
  }

  @Override
  public void injectMembers(UriRouterActivity instance) {
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectScreenUtil(instance, screenUtilProvider.get());
    injectScreenLauncherUtil(instance, screenLauncherUtilProvider.get());
    injectUriUtil(instance, uriUtilProvider.get());
    injectLanguageManager(instance, languageManagerProvider.get());
    injectPrefs(instance, prefsProvider.get());
    injectLanguageUtil(instance, languageUtilProvider.get());
    injectThemeUtil(instance, themeUtilProvider.get());
  }

  public static void injectInternalIntents(
      UriRouterActivity instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectScreenUtil(UriRouterActivity instance, ScreenUtil screenUtil) {
    instance.screenUtil = screenUtil;
  }

  public static void injectScreenLauncherUtil(
      UriRouterActivity instance, ScreenLauncherUtil screenLauncherUtil) {
    instance.screenLauncherUtil = screenLauncherUtil;
  }

  public static void injectUriUtil(UriRouterActivity instance, UriUtil uriUtil) {
    instance.uriUtil = uriUtil;
  }

  public static void injectLanguageManager(
      UriRouterActivity instance, LanguageManager languageManager) {
    instance.languageManager = languageManager;
  }

  public static void injectPrefs(UriRouterActivity instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectLanguageUtil(UriRouterActivity instance, LanguageUtil languageUtil) {
    instance.languageUtil = languageUtil;
  }

  public static void injectThemeUtil(UriRouterActivity instance, ThemeUtil themeUtil) {
    instance.themeUtil = themeUtil;
  }
}
