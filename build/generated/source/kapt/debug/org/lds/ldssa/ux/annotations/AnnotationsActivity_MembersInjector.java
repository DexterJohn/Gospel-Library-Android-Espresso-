package org.lds.ldssa.ux.annotations;

import com.google.gson.Gson;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.ui.activity.BaseActivity_MembersInjector;
import org.lds.ldssa.ui.menu.CommonMenu;
import org.lds.ldssa.util.AccountUtil;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.ShareUtil;
import org.lds.ldssa.util.ThemeUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AnnotationsActivity_MembersInjector
    implements MembersInjector<AnnotationsActivity> {
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

  private final Provider<ShareUtil> shareUtilProvider;

  private final Provider<NotebookManager> notebookManagerProvider;

  private final Provider<NavigationTrailQueryManager> navigationTrailQueryManagerProvider;

  private final Provider<AnnotationManager> annotationManagerProvider;

  public AnnotationsActivity_MembersInjector(
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
      Provider<ShareUtil> shareUtilProvider,
      Provider<NotebookManager> notebookManagerProvider,
      Provider<NavigationTrailQueryManager> navigationTrailQueryManagerProvider,
      Provider<AnnotationManager> annotationManagerProvider) {
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
    this.shareUtilProvider = shareUtilProvider;
    this.notebookManagerProvider = notebookManagerProvider;
    this.navigationTrailQueryManagerProvider = navigationTrailQueryManagerProvider;
    this.annotationManagerProvider = annotationManagerProvider;
  }

  public static MembersInjector<AnnotationsActivity> create(
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
      Provider<ShareUtil> shareUtilProvider,
      Provider<NotebookManager> notebookManagerProvider,
      Provider<NavigationTrailQueryManager> navigationTrailQueryManagerProvider,
      Provider<AnnotationManager> annotationManagerProvider) {
    return new AnnotationsActivity_MembersInjector(
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
        shareUtilProvider,
        notebookManagerProvider,
        navigationTrailQueryManagerProvider,
        annotationManagerProvider);
  }

  @Override
  public void injectMembers(AnnotationsActivity instance) {
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
    injectShareUtil(instance, shareUtilProvider.get());
    injectNotebookManager(instance, notebookManagerProvider.get());
    injectNavigationTrailQueryManager(instance, navigationTrailQueryManagerProvider.get());
    injectAnnotationManager(instance, annotationManagerProvider.get());
  }

  public static void injectShareUtil(AnnotationsActivity instance, ShareUtil shareUtil) {
    instance.shareUtil = shareUtil;
  }

  public static void injectNotebookManager(
      AnnotationsActivity instance, NotebookManager notebookManager) {
    instance.notebookManager = notebookManager;
  }

  public static void injectNavigationTrailQueryManager(
      AnnotationsActivity instance, NavigationTrailQueryManager navigationTrailQueryManager) {
    instance.navigationTrailQueryManager = navigationTrailQueryManager;
  }

  public static void injectAnnotationManager(
      AnnotationsActivity instance, AnnotationManager annotationManager) {
    instance.annotationManager = annotationManager;
  }
}
