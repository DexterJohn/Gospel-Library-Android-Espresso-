package org.lds.ldssa.ux.catalog;

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
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionManager;
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.ldssa.model.database.userdata.customcollectionitem.CustomCollectionItemManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.sync.AnnotationSync;
import org.lds.ldssa.task.TipsUpdateTask;
import org.lds.ldssa.ui.activity.BaseActivity_MembersInjector;
import org.lds.ldssa.ui.menu.CatalogDirectoryMenu;
import org.lds.ldssa.ui.menu.CommonMenu;
import org.lds.ldssa.util.AccountUtil;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.CatalogUpdateUtil;
import org.lds.ldssa.util.ContentItemUtil;
import org.lds.ldssa.util.CustomCollectionUtil;
import org.lds.ldssa.util.LanguageUtil;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.ThemeUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CatalogDirectoryActivity_MembersInjector
    implements MembersInjector<CatalogDirectoryActivity> {
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

  private final Provider<LibraryCollectionManager> libraryCollectionManagerProvider;

  private final Provider<NavigationTrailQueryManager> trailQueryManagerProvider;

  private final Provider<CustomCollectionUtil> customCollectionUtilProvider;

  private final Provider<CatalogDirectoryMenu> catalogDirectoryMenuProvider;

  private final Provider<CustomCollectionItemManager> customCollectionItemManagerProvider;

  private final Provider<AllItemsInCollectionQueryManager> itemsInCollectionManagerProvider;

  private final Provider<DownloadedItemManager> downloadedItemManagerProvider;

  private final Provider<GLDownloadManager> downloadManagerProvider;

  private final Provider<ContentItemUtil> contentItemUtilProvider;

  private final Provider<CatalogUpdateUtil> catalogUpdateUtilProvider;

  private final Provider<TipsUpdateTask> tipsUpdateTaskProvider;

  private final Provider<AnnotationSync> annotationSyncProvider;

  private final Provider<LanguageUtil> languageUtilProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  public CatalogDirectoryActivity_MembersInjector(
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
      Provider<LibraryCollectionManager> libraryCollectionManagerProvider,
      Provider<NavigationTrailQueryManager> trailQueryManagerProvider,
      Provider<CustomCollectionUtil> customCollectionUtilProvider,
      Provider<CatalogDirectoryMenu> catalogDirectoryMenuProvider,
      Provider<CustomCollectionItemManager> customCollectionItemManagerProvider,
      Provider<AllItemsInCollectionQueryManager> itemsInCollectionManagerProvider,
      Provider<DownloadedItemManager> downloadedItemManagerProvider,
      Provider<GLDownloadManager> downloadManagerProvider,
      Provider<ContentItemUtil> contentItemUtilProvider,
      Provider<CatalogUpdateUtil> catalogUpdateUtilProvider,
      Provider<TipsUpdateTask> tipsUpdateTaskProvider,
      Provider<AnnotationSync> annotationSyncProvider,
      Provider<LanguageUtil> languageUtilProvider,
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
    this.libraryCollectionManagerProvider = libraryCollectionManagerProvider;
    this.trailQueryManagerProvider = trailQueryManagerProvider;
    this.customCollectionUtilProvider = customCollectionUtilProvider;
    this.catalogDirectoryMenuProvider = catalogDirectoryMenuProvider;
    this.customCollectionItemManagerProvider = customCollectionItemManagerProvider;
    this.itemsInCollectionManagerProvider = itemsInCollectionManagerProvider;
    this.downloadedItemManagerProvider = downloadedItemManagerProvider;
    this.downloadManagerProvider = downloadManagerProvider;
    this.contentItemUtilProvider = contentItemUtilProvider;
    this.catalogUpdateUtilProvider = catalogUpdateUtilProvider;
    this.tipsUpdateTaskProvider = tipsUpdateTaskProvider;
    this.annotationSyncProvider = annotationSyncProvider;
    this.languageUtilProvider = languageUtilProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<CatalogDirectoryActivity> create(
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
      Provider<LibraryCollectionManager> libraryCollectionManagerProvider,
      Provider<NavigationTrailQueryManager> trailQueryManagerProvider,
      Provider<CustomCollectionUtil> customCollectionUtilProvider,
      Provider<CatalogDirectoryMenu> catalogDirectoryMenuProvider,
      Provider<CustomCollectionItemManager> customCollectionItemManagerProvider,
      Provider<AllItemsInCollectionQueryManager> itemsInCollectionManagerProvider,
      Provider<DownloadedItemManager> downloadedItemManagerProvider,
      Provider<GLDownloadManager> downloadManagerProvider,
      Provider<ContentItemUtil> contentItemUtilProvider,
      Provider<CatalogUpdateUtil> catalogUpdateUtilProvider,
      Provider<TipsUpdateTask> tipsUpdateTaskProvider,
      Provider<AnnotationSync> annotationSyncProvider,
      Provider<LanguageUtil> languageUtilProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    return new CatalogDirectoryActivity_MembersInjector(
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
        libraryCollectionManagerProvider,
        trailQueryManagerProvider,
        customCollectionUtilProvider,
        catalogDirectoryMenuProvider,
        customCollectionItemManagerProvider,
        itemsInCollectionManagerProvider,
        downloadedItemManagerProvider,
        downloadManagerProvider,
        contentItemUtilProvider,
        catalogUpdateUtilProvider,
        tipsUpdateTaskProvider,
        annotationSyncProvider,
        languageUtilProvider,
        viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(CatalogDirectoryActivity instance) {
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
    injectLibraryCollectionManager(instance, libraryCollectionManagerProvider.get());
    injectTrailQueryManager(instance, trailQueryManagerProvider.get());
    injectCustomCollectionUtil(instance, customCollectionUtilProvider.get());
    injectCatalogDirectoryMenu(instance, catalogDirectoryMenuProvider.get());
    injectCustomCollectionItemManager(instance, customCollectionItemManagerProvider.get());
    injectItemsInCollectionManager(instance, itemsInCollectionManagerProvider.get());
    injectDownloadedItemManager(instance, downloadedItemManagerProvider.get());
    injectDownloadManager(instance, downloadManagerProvider.get());
    injectContentItemUtil(instance, contentItemUtilProvider.get());
    injectCatalogUpdateUtil(instance, catalogUpdateUtilProvider.get());
    injectTipsUpdateTaskProvider(instance, tipsUpdateTaskProvider);
    injectAnnotationSync(instance, annotationSyncProvider.get());
    injectLanguageUtil(instance, languageUtilProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
  }

  public static void injectLibraryCollectionManager(
      CatalogDirectoryActivity instance, LibraryCollectionManager libraryCollectionManager) {
    instance.libraryCollectionManager = libraryCollectionManager;
  }

  public static void injectTrailQueryManager(
      CatalogDirectoryActivity instance, NavigationTrailQueryManager trailQueryManager) {
    instance.trailQueryManager = trailQueryManager;
  }

  public static void injectCustomCollectionUtil(
      CatalogDirectoryActivity instance, CustomCollectionUtil customCollectionUtil) {
    instance.customCollectionUtil = customCollectionUtil;
  }

  public static void injectCatalogDirectoryMenu(
      CatalogDirectoryActivity instance, CatalogDirectoryMenu catalogDirectoryMenu) {
    instance.catalogDirectoryMenu = catalogDirectoryMenu;
  }

  public static void injectCustomCollectionItemManager(
      CatalogDirectoryActivity instance, CustomCollectionItemManager customCollectionItemManager) {
    instance.customCollectionItemManager = customCollectionItemManager;
  }

  public static void injectItemsInCollectionManager(
      CatalogDirectoryActivity instance,
      AllItemsInCollectionQueryManager itemsInCollectionManager) {
    instance.itemsInCollectionManager = itemsInCollectionManager;
  }

  public static void injectDownloadedItemManager(
      CatalogDirectoryActivity instance, DownloadedItemManager downloadedItemManager) {
    instance.downloadedItemManager = downloadedItemManager;
  }

  public static void injectDownloadManager(
      CatalogDirectoryActivity instance, GLDownloadManager downloadManager) {
    instance.downloadManager = downloadManager;
  }

  public static void injectContentItemUtil(
      CatalogDirectoryActivity instance, ContentItemUtil contentItemUtil) {
    instance.contentItemUtil = contentItemUtil;
  }

  public static void injectCatalogUpdateUtil(
      CatalogDirectoryActivity instance, CatalogUpdateUtil catalogUpdateUtil) {
    instance.catalogUpdateUtil = catalogUpdateUtil;
  }

  public static void injectTipsUpdateTaskProvider(
      CatalogDirectoryActivity instance, Provider<TipsUpdateTask> tipsUpdateTaskProvider) {
    instance.tipsUpdateTaskProvider = tipsUpdateTaskProvider;
  }

  public static void injectAnnotationSync(
      CatalogDirectoryActivity instance, AnnotationSync annotationSync) {
    instance.annotationSync = annotationSync;
  }

  public static void injectLanguageUtil(
      CatalogDirectoryActivity instance, LanguageUtil languageUtil) {
    instance.languageUtil = languageUtil;
  }

  public static void injectViewModelFactory(
      CatalogDirectoryActivity instance, ViewModelProvider.Factory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }
}
