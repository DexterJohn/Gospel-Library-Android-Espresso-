package org.lds.ldssa.ux.study.items;

import android.arch.lifecycle.ViewModelProvider;
import com.google.gson.Gson;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.ui.activity.BaseActivity_MembersInjector;
import org.lds.ldssa.ui.menu.CommonMenu;
import org.lds.ldssa.util.AccountUtil;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.LanguageUtil;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.ThemeUtil;
import org.lds.ldssa.util.ToastUtil;
import org.lds.ldssa.util.UriUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class StudyItemsActivity_MembersInjector
    implements MembersInjector<StudyItemsActivity> {
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

  private final Provider<LanguageUtil> languageUtilProvider;

  private final Provider<NavigationTrailQueryManager> navigationTrailQueryManagerProvider;

  private final Provider<ToastUtil> toastUtilProvider;

  private final Provider<UriUtil> uriUtilProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  public StudyItemsActivity_MembersInjector(
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
      Provider<ThemeUtil> themeUtilProvider,
      Provider<LanguageUtil> languageUtilProvider,
      Provider<NavigationTrailQueryManager> navigationTrailQueryManagerProvider,
      Provider<ToastUtil> toastUtilProvider,
      Provider<UriUtil> uriUtilProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
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
    this.languageUtilProvider = languageUtilProvider;
    this.navigationTrailQueryManagerProvider = navigationTrailQueryManagerProvider;
    this.toastUtilProvider = toastUtilProvider;
    this.uriUtilProvider = uriUtilProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<StudyItemsActivity> create(
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
      Provider<ThemeUtil> themeUtilProvider,
      Provider<LanguageUtil> languageUtilProvider,
      Provider<NavigationTrailQueryManager> navigationTrailQueryManagerProvider,
      Provider<ToastUtil> toastUtilProvider,
      Provider<UriUtil> uriUtilProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    return new StudyItemsActivity_MembersInjector(
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
        themeUtilProvider,
        languageUtilProvider,
        navigationTrailQueryManagerProvider,
        toastUtilProvider,
        uriUtilProvider,
        viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(StudyItemsActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, busProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, screenUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(
        instance, screenLauncherUtilProvider.get());
    BaseActivity_MembersInjector.injectAnalytics(instance, analyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, analyticsUtilProvider.get());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, ccProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, gsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    injectLanguageUtil(instance, languageUtilProvider.get());
    injectNavigationTrailQueryManager(instance, navigationTrailQueryManagerProvider.get());
    injectToastUtil(instance, toastUtilProvider.get());
    injectUriUtil(instance, uriUtilProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
  }

  public static void injectLanguageUtil(StudyItemsActivity instance, LanguageUtil languageUtil) {
    instance.languageUtil = languageUtil;
  }

  public static void injectNavigationTrailQueryManager(
      StudyItemsActivity instance, NavigationTrailQueryManager navigationTrailQueryManager) {
    instance.navigationTrailQueryManager = navigationTrailQueryManager;
  }

  public static void injectToastUtil(StudyItemsActivity instance, ToastUtil toastUtil) {
    instance.toastUtil = toastUtil;
  }

  public static void injectUriUtil(StudyItemsActivity instance, UriUtil uriUtil) {
    instance.uriUtil = uriUtil;
  }

  public static void injectViewModelFactory(
      StudyItemsActivity instance, ViewModelProvider.Factory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }
}
