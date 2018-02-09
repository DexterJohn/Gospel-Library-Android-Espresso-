package org.lds.ldssa.ux.downloadedmedia;

import android.arch.lifecycle.ViewModelProvider;
import com.google.gson.Gson;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.media.exomedia.manager.PlaylistManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.content.playlistitemquery.PlaylistItemQueryManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.ui.activity.BaseActivity_MembersInjector;
import org.lds.ldssa.ui.menu.CommonMenu;
import org.lds.ldssa.util.AccountUtil;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.ThemeUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DownloadedMediaActivity_MembersInjector
    implements MembersInjector<DownloadedMediaActivity> {
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

  private final Provider<CoroutineContextProvider> ccAndCoroutineContextProvider;

  private final Provider<Gson> gsonProvider;

  private final Provider<ThemeUtil> themeUtilProvider;

  private final Provider<GLFileUtil> fileUtilProvider;

  private final Provider<ItemManager> itemManagerProvider;

  private final Provider<PlaylistManager> playlistManagerProvider;

  private final Provider<PlaylistItemQueryManager> playlistItemQueryManagerProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  public DownloadedMediaActivity_MembersInjector(
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
      Provider<CoroutineContextProvider> ccAndCoroutineContextProvider,
      Provider<Gson> gsonProvider,
      Provider<ThemeUtil> themeUtilProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<PlaylistManager> playlistManagerProvider,
      Provider<PlaylistItemQueryManager> playlistItemQueryManagerProvider,
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
    this.ccAndCoroutineContextProvider = ccAndCoroutineContextProvider;
    this.gsonProvider = gsonProvider;
    this.themeUtilProvider = themeUtilProvider;
    this.fileUtilProvider = fileUtilProvider;
    this.itemManagerProvider = itemManagerProvider;
    this.playlistManagerProvider = playlistManagerProvider;
    this.playlistItemQueryManagerProvider = playlistItemQueryManagerProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<DownloadedMediaActivity> create(
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
      Provider<CoroutineContextProvider> ccAndCoroutineContextProvider,
      Provider<Gson> gsonProvider,
      Provider<ThemeUtil> themeUtilProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<PlaylistManager> playlistManagerProvider,
      Provider<PlaylistItemQueryManager> playlistItemQueryManagerProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    return new DownloadedMediaActivity_MembersInjector(
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
        ccAndCoroutineContextProvider,
        gsonProvider,
        themeUtilProvider,
        fileUtilProvider,
        itemManagerProvider,
        playlistManagerProvider,
        playlistItemQueryManagerProvider,
        viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(DownloadedMediaActivity instance) {
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
    BaseActivity_MembersInjector.injectCc(instance, ccAndCoroutineContextProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, gsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    injectFileUtil(instance, fileUtilProvider.get());
    injectItemManager(instance, itemManagerProvider.get());
    injectPlaylistManager(instance, playlistManagerProvider.get());
    injectPlaylistItemQueryManager(instance, playlistItemQueryManagerProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
    injectCoroutineContextProvider(instance, ccAndCoroutineContextProvider.get());
  }

  public static void injectFileUtil(DownloadedMediaActivity instance, GLFileUtil fileUtil) {
    instance.fileUtil = fileUtil;
  }

  public static void injectItemManager(DownloadedMediaActivity instance, ItemManager itemManager) {
    instance.itemManager = itemManager;
  }

  public static void injectPlaylistManager(
      DownloadedMediaActivity instance, PlaylistManager playlistManager) {
    instance.playlistManager = playlistManager;
  }

  public static void injectPlaylistItemQueryManager(
      DownloadedMediaActivity instance, PlaylistItemQueryManager playlistItemQueryManager) {
    instance.playlistItemQueryManager = playlistItemQueryManager;
  }

  public static void injectViewModelFactory(
      DownloadedMediaActivity instance, ViewModelProvider.Factory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }

  public static void injectCoroutineContextProvider(
      DownloadedMediaActivity instance, CoroutineContextProvider coroutineContextProvider) {
    instance.coroutineContextProvider = coroutineContextProvider;
  }
}
