package org.lds.ldssa.ux.customcollection.items;

import android.arch.lifecycle.ViewModelProvider;
import com.google.gson.Gson;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.catalog.allitemsincollectionquery.AllItemsInCollectionQueryManager;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.ldssa.model.database.userdata.customcollection.CustomCollectionManager;
import org.lds.ldssa.model.database.userdata.customcollectionitem.CustomCollectionItemManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.ui.activity.BaseActivity_MembersInjector;
import org.lds.ldssa.ui.menu.CommonMenu;
import org.lds.ldssa.ui.menu.CustomCollectionDirectoryMenu;
import org.lds.ldssa.util.AccountUtil;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.ContentItemUtil;
import org.lds.ldssa.util.CustomCollectionUtil;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.ThemeUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CustomCollectionDirectoryActivity_MembersInjector
    implements MembersInjector<CustomCollectionDirectoryActivity> {
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

  private final Provider<CustomCollectionManager> customCollectionManagerProvider;

  private final Provider<CustomCollectionItemManager> customCollectionItemManagerProvider;

  private final Provider<NavigationTrailQueryManager> navigationTrailQueryManagerProvider;

  private final Provider<GLDownloadManager> downloadManagerProvider;

  private final Provider<CustomCollectionDirectoryMenu> customCollectionDirectoryMenuProvider;

  private final Provider<ContentItemUtil> contentItemUtilProvider;

  private final Provider<DownloadedItemManager> downloadedItemManagerProvider;

  private final Provider<AllItemsInCollectionQueryManager> itemsInCollectionManagerProvider;

  private final Provider<CustomCollectionUtil> customCollectionUtilProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  public CustomCollectionDirectoryActivity_MembersInjector(
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
      Provider<CustomCollectionManager> customCollectionManagerProvider,
      Provider<CustomCollectionItemManager> customCollectionItemManagerProvider,
      Provider<NavigationTrailQueryManager> navigationTrailQueryManagerProvider,
      Provider<GLDownloadManager> downloadManagerProvider,
      Provider<CustomCollectionDirectoryMenu> customCollectionDirectoryMenuProvider,
      Provider<ContentItemUtil> contentItemUtilProvider,
      Provider<DownloadedItemManager> downloadedItemManagerProvider,
      Provider<AllItemsInCollectionQueryManager> itemsInCollectionManagerProvider,
      Provider<CustomCollectionUtil> customCollectionUtilProvider,
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
    this.customCollectionManagerProvider = customCollectionManagerProvider;
    this.customCollectionItemManagerProvider = customCollectionItemManagerProvider;
    this.navigationTrailQueryManagerProvider = navigationTrailQueryManagerProvider;
    this.downloadManagerProvider = downloadManagerProvider;
    this.customCollectionDirectoryMenuProvider = customCollectionDirectoryMenuProvider;
    this.contentItemUtilProvider = contentItemUtilProvider;
    this.downloadedItemManagerProvider = downloadedItemManagerProvider;
    this.itemsInCollectionManagerProvider = itemsInCollectionManagerProvider;
    this.customCollectionUtilProvider = customCollectionUtilProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<CustomCollectionDirectoryActivity> create(
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
      Provider<CustomCollectionManager> customCollectionManagerProvider,
      Provider<CustomCollectionItemManager> customCollectionItemManagerProvider,
      Provider<NavigationTrailQueryManager> navigationTrailQueryManagerProvider,
      Provider<GLDownloadManager> downloadManagerProvider,
      Provider<CustomCollectionDirectoryMenu> customCollectionDirectoryMenuProvider,
      Provider<ContentItemUtil> contentItemUtilProvider,
      Provider<DownloadedItemManager> downloadedItemManagerProvider,
      Provider<AllItemsInCollectionQueryManager> itemsInCollectionManagerProvider,
      Provider<CustomCollectionUtil> customCollectionUtilProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    return new CustomCollectionDirectoryActivity_MembersInjector(
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
        customCollectionManagerProvider,
        customCollectionItemManagerProvider,
        navigationTrailQueryManagerProvider,
        downloadManagerProvider,
        customCollectionDirectoryMenuProvider,
        contentItemUtilProvider,
        downloadedItemManagerProvider,
        itemsInCollectionManagerProvider,
        customCollectionUtilProvider,
        viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(CustomCollectionDirectoryActivity instance) {
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
    injectCustomCollectionManager(instance, customCollectionManagerProvider.get());
    injectCustomCollectionItemManager(instance, customCollectionItemManagerProvider.get());
    injectNavigationTrailQueryManager(instance, navigationTrailQueryManagerProvider.get());
    injectDownloadManager(instance, downloadManagerProvider.get());
    injectCustomCollectionDirectoryMenu(instance, customCollectionDirectoryMenuProvider.get());
    injectContentItemUtil(instance, contentItemUtilProvider.get());
    injectDownloadedItemManager(instance, downloadedItemManagerProvider.get());
    injectItemsInCollectionManager(instance, itemsInCollectionManagerProvider.get());
    injectCustomCollectionUtil(instance, customCollectionUtilProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
  }

  public static void injectCustomCollectionManager(
      CustomCollectionDirectoryActivity instance, CustomCollectionManager customCollectionManager) {
    instance.customCollectionManager = customCollectionManager;
  }

  public static void injectCustomCollectionItemManager(
      CustomCollectionDirectoryActivity instance,
      CustomCollectionItemManager customCollectionItemManager) {
    instance.customCollectionItemManager = customCollectionItemManager;
  }

  public static void injectNavigationTrailQueryManager(
      CustomCollectionDirectoryActivity instance,
      NavigationTrailQueryManager navigationTrailQueryManager) {
    instance.navigationTrailQueryManager = navigationTrailQueryManager;
  }

  public static void injectDownloadManager(
      CustomCollectionDirectoryActivity instance, GLDownloadManager downloadManager) {
    instance.downloadManager = downloadManager;
  }

  public static void injectCustomCollectionDirectoryMenu(
      CustomCollectionDirectoryActivity instance,
      CustomCollectionDirectoryMenu customCollectionDirectoryMenu) {
    instance.customCollectionDirectoryMenu = customCollectionDirectoryMenu;
  }

  public static void injectContentItemUtil(
      CustomCollectionDirectoryActivity instance, ContentItemUtil contentItemUtil) {
    instance.contentItemUtil = contentItemUtil;
  }

  public static void injectDownloadedItemManager(
      CustomCollectionDirectoryActivity instance, DownloadedItemManager downloadedItemManager) {
    instance.downloadedItemManager = downloadedItemManager;
  }

  public static void injectItemsInCollectionManager(
      CustomCollectionDirectoryActivity instance,
      AllItemsInCollectionQueryManager itemsInCollectionManager) {
    instance.itemsInCollectionManager = itemsInCollectionManager;
  }

  public static void injectCustomCollectionUtil(
      CustomCollectionDirectoryActivity instance, CustomCollectionUtil customCollectionUtil) {
    instance.customCollectionUtil = customCollectionUtil;
  }

  public static void injectViewModelFactory(
      CustomCollectionDirectoryActivity instance, ViewModelProvider.Factory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }
}
