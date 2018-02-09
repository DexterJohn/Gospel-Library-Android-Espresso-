package org.lds.ldssa.ui.activity;

import com.google.gson.Gson;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.ui.menu.CommonMenu;
import org.lds.ldssa.util.AccountUtil;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.ThemeUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BaseActivity_MembersInjector implements MembersInjector<BaseActivity> {
  private final Provider<Bus> busProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<LanguageManager> languageManagerProvider;

  private final Provider<AccountUtil> accountUtilProvider;

  private final Provider<ScreenUtil> screenUtilProvider;

  private final Provider<ScreenLauncherUtil> screenLauncherUtilProvider;

  private final Provider<Analytics> analyticsProvider;

  private final Provider<AnalyticsUtil> analyticsUtilProvider;

  private final Provider<CommonMenu> commonMenuProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  private final Provider<Gson> gsonProvider;

  private final Provider<ThemeUtil> themeUtilProvider;

  public BaseActivity_MembersInjector(
      Provider<Bus> busProvider,
      Provider<Prefs> prefsProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<LanguageManager> languageManagerProvider,
      Provider<AccountUtil> accountUtilProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider,
      Provider<Analytics> analyticsProvider,
      Provider<AnalyticsUtil> analyticsUtilProvider,
      Provider<CommonMenu> commonMenuProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<Gson> gsonProvider,
      Provider<ThemeUtil> themeUtilProvider) {
    this.busProvider = busProvider;
    this.prefsProvider = prefsProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.languageManagerProvider = languageManagerProvider;
    this.accountUtilProvider = accountUtilProvider;
    this.screenUtilProvider = screenUtilProvider;
    this.screenLauncherUtilProvider = screenLauncherUtilProvider;
    this.analyticsProvider = analyticsProvider;
    this.analyticsUtilProvider = analyticsUtilProvider;
    this.commonMenuProvider = commonMenuProvider;
    this.ccProvider = ccProvider;
    this.gsonProvider = gsonProvider;
    this.themeUtilProvider = themeUtilProvider;
  }

  public static MembersInjector<BaseActivity> create(
      Provider<Bus> busProvider,
      Provider<Prefs> prefsProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<LanguageManager> languageManagerProvider,
      Provider<AccountUtil> accountUtilProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider,
      Provider<Analytics> analyticsProvider,
      Provider<AnalyticsUtil> analyticsUtilProvider,
      Provider<CommonMenu> commonMenuProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<Gson> gsonProvider,
      Provider<ThemeUtil> themeUtilProvider) {
    return new BaseActivity_MembersInjector(
        busProvider,
        prefsProvider,
        internalIntentsProvider,
        languageManagerProvider,
        accountUtilProvider,
        screenUtilProvider,
        screenLauncherUtilProvider,
        analyticsProvider,
        analyticsUtilProvider,
        commonMenuProvider,
        ccProvider,
        gsonProvider,
        themeUtilProvider);
  }

  @Override
  public void injectMembers(BaseActivity instance) {
    injectBus(instance, busProvider.get());
    injectPrefs(instance, prefsProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectLanguageManager(instance, languageManagerProvider.get());
    injectAccountUtil(instance, accountUtilProvider.get());
    injectScreenUtil(instance, screenUtilProvider.get());
    injectScreenLauncherUtil(instance, screenLauncherUtilProvider.get());
    injectAnalytics(instance, analyticsProvider.get());
    injectAnalyticsUtil(instance, analyticsUtilProvider.get());
    injectCommonMenu(instance, commonMenuProvider.get());
    injectCc(instance, ccProvider.get());
    injectGson(instance, gsonProvider.get());
    injectThemeUtil(instance, themeUtilProvider.get());
  }

  public static void injectBus(BaseActivity instance, Bus bus) {
    instance.bus = bus;
  }

  public static void injectPrefs(BaseActivity instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectInternalIntents(BaseActivity instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectLanguageManager(BaseActivity instance, LanguageManager languageManager) {
    instance.languageManager = languageManager;
  }

  public static void injectAccountUtil(BaseActivity instance, AccountUtil accountUtil) {
    instance.accountUtil = accountUtil;
  }

  public static void injectScreenUtil(BaseActivity instance, ScreenUtil screenUtil) {
    instance.screenUtil = screenUtil;
  }

  public static void injectScreenLauncherUtil(
      BaseActivity instance, ScreenLauncherUtil screenLauncherUtil) {
    instance.screenLauncherUtil = screenLauncherUtil;
  }

  public static void injectAnalytics(BaseActivity instance, Analytics analytics) {
    instance.analytics = analytics;
  }

  public static void injectAnalyticsUtil(BaseActivity instance, AnalyticsUtil analyticsUtil) {
    instance.analyticsUtil = analyticsUtil;
  }

  public static void injectCommonMenu(BaseActivity instance, CommonMenu commonMenu) {
    instance.commonMenu = commonMenu;
  }

  public static void injectCc(BaseActivity instance, CoroutineContextProvider cc) {
    instance.cc = cc;
  }

  public static void injectGson(BaseActivity instance, Gson gson) {
    instance.gson = gson;
  }

  public static void injectThemeUtil(BaseActivity instance, ThemeUtil themeUtil) {
    instance.themeUtil = themeUtil;
  }
}
