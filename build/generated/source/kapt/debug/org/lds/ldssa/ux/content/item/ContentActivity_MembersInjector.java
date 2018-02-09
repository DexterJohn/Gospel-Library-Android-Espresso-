package org.lds.ldssa.ux.content.item;

import com.google.gson.Gson;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.media.exomedia.manager.PlaylistManager;
import org.lds.ldssa.media.texttospeech.TextToSpeechManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager;
import org.lds.ldssa.model.database.content.availablerelatedtypequery.AvailableRelatedTypeQueryManager;
import org.lds.ldssa.model.database.content.playlistitemquery.PlaylistItemQueryManager;
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItemManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;
import org.lds.ldssa.model.database.gl.history.HistoryManager;
import org.lds.ldssa.model.database.gl.mediaplaybackposition.MediaPlaybackPositionManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
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
import org.lds.mobile.util.LdsNetworkUtil;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ContentActivity_MembersInjector implements MembersInjector<ContentActivity> {
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

  private final Provider<NavigationTrailQueryManager> trailQueryManagerProvider;

  private final Provider<SubItemManager> subItemManagerProvider;

  private final Provider<HistoryManager> historyManagerProvider;

  private final Provider<AnnotationManager> annotationManagerProvider;

  private final Provider<PlaylistManager> playlistManagerProvider;

  private final Provider<ItemManager> itemManagerProvider;

  private final Provider<MediaPlaybackPositionManager> mediaPlaybackPositionManagerProvider;

  private final Provider<PlaylistItemQueryManager> playlistItemQueryManagerProvider;

  private final Provider<GLDownloadManager> glDownloadManagerProvider;

  private final Provider<TextToSpeechManager> textToSpeechManagerProvider;

  private final Provider<AvailableRelatedTypeQueryManager> availableRelatedTypeManagerProvider;

  private final Provider<DownloadedMediaManager> downloadedMediaManagerProvider;

  private final Provider<LdsNetworkUtil> networkUtilProvider;

  private final Provider<RelatedAudioItemManager> relatedAudioItemManagerProvider;

  public ContentActivity_MembersInjector(
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
      Provider<NavigationTrailQueryManager> trailQueryManagerProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<HistoryManager> historyManagerProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<PlaylistManager> playlistManagerProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<MediaPlaybackPositionManager> mediaPlaybackPositionManagerProvider,
      Provider<PlaylistItemQueryManager> playlistItemQueryManagerProvider,
      Provider<GLDownloadManager> glDownloadManagerProvider,
      Provider<TextToSpeechManager> textToSpeechManagerProvider,
      Provider<AvailableRelatedTypeQueryManager> availableRelatedTypeManagerProvider,
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<LdsNetworkUtil> networkUtilProvider,
      Provider<RelatedAudioItemManager> relatedAudioItemManagerProvider) {
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
    this.trailQueryManagerProvider = trailQueryManagerProvider;
    this.subItemManagerProvider = subItemManagerProvider;
    this.historyManagerProvider = historyManagerProvider;
    this.annotationManagerProvider = annotationManagerProvider;
    this.playlistManagerProvider = playlistManagerProvider;
    this.itemManagerProvider = itemManagerProvider;
    this.mediaPlaybackPositionManagerProvider = mediaPlaybackPositionManagerProvider;
    this.playlistItemQueryManagerProvider = playlistItemQueryManagerProvider;
    this.glDownloadManagerProvider = glDownloadManagerProvider;
    this.textToSpeechManagerProvider = textToSpeechManagerProvider;
    this.availableRelatedTypeManagerProvider = availableRelatedTypeManagerProvider;
    this.downloadedMediaManagerProvider = downloadedMediaManagerProvider;
    this.networkUtilProvider = networkUtilProvider;
    this.relatedAudioItemManagerProvider = relatedAudioItemManagerProvider;
  }

  public static MembersInjector<ContentActivity> create(
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
      Provider<NavigationTrailQueryManager> trailQueryManagerProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<HistoryManager> historyManagerProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<PlaylistManager> playlistManagerProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<MediaPlaybackPositionManager> mediaPlaybackPositionManagerProvider,
      Provider<PlaylistItemQueryManager> playlistItemQueryManagerProvider,
      Provider<GLDownloadManager> glDownloadManagerProvider,
      Provider<TextToSpeechManager> textToSpeechManagerProvider,
      Provider<AvailableRelatedTypeQueryManager> availableRelatedTypeManagerProvider,
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<LdsNetworkUtil> networkUtilProvider,
      Provider<RelatedAudioItemManager> relatedAudioItemManagerProvider) {
    return new ContentActivity_MembersInjector(
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
        trailQueryManagerProvider,
        subItemManagerProvider,
        historyManagerProvider,
        annotationManagerProvider,
        playlistManagerProvider,
        itemManagerProvider,
        mediaPlaybackPositionManagerProvider,
        playlistItemQueryManagerProvider,
        glDownloadManagerProvider,
        textToSpeechManagerProvider,
        availableRelatedTypeManagerProvider,
        downloadedMediaManagerProvider,
        networkUtilProvider,
        relatedAudioItemManagerProvider);
  }

  @Override
  public void injectMembers(ContentActivity instance) {
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
    injectTrailQueryManager(instance, trailQueryManagerProvider.get());
    injectSubItemManager(instance, subItemManagerProvider.get());
    injectHistoryManager(instance, historyManagerProvider.get());
    injectAnnotationManager(instance, annotationManagerProvider.get());
    injectPlaylistManager(instance, playlistManagerProvider.get());
    injectItemManager(instance, itemManagerProvider.get());
    injectMediaPlaybackPositionManager(instance, mediaPlaybackPositionManagerProvider.get());
    injectPlaylistItemQueryManager(instance, playlistItemQueryManagerProvider.get());
    injectGlDownloadManager(instance, glDownloadManagerProvider.get());
    injectTextToSpeechManager(instance, textToSpeechManagerProvider.get());
    injectAvailableRelatedTypeManager(instance, availableRelatedTypeManagerProvider.get());
    injectDownloadedMediaManager(instance, downloadedMediaManagerProvider.get());
    injectNetworkUtil(instance, networkUtilProvider.get());
    injectRelatedAudioItemManager(instance, relatedAudioItemManagerProvider.get());
  }

  public static void injectShareUtil(ContentActivity instance, ShareUtil shareUtil) {
    instance.shareUtil = shareUtil;
  }

  public static void injectTrailQueryManager(
      ContentActivity instance, NavigationTrailQueryManager trailQueryManager) {
    instance.trailQueryManager = trailQueryManager;
  }

  public static void injectSubItemManager(ContentActivity instance, SubItemManager subItemManager) {
    instance.subItemManager = subItemManager;
  }

  public static void injectHistoryManager(ContentActivity instance, HistoryManager historyManager) {
    instance.historyManager = historyManager;
  }

  public static void injectAnnotationManager(
      ContentActivity instance, AnnotationManager annotationManager) {
    instance.annotationManager = annotationManager;
  }

  public static void injectPlaylistManager(
      ContentActivity instance, PlaylistManager playlistManager) {
    instance.playlistManager = playlistManager;
  }

  public static void injectItemManager(ContentActivity instance, ItemManager itemManager) {
    instance.itemManager = itemManager;
  }

  public static void injectMediaPlaybackPositionManager(
      ContentActivity instance, MediaPlaybackPositionManager mediaPlaybackPositionManager) {
    instance.mediaPlaybackPositionManager = mediaPlaybackPositionManager;
  }

  public static void injectPlaylistItemQueryManager(
      ContentActivity instance, PlaylistItemQueryManager playlistItemQueryManager) {
    instance.playlistItemQueryManager = playlistItemQueryManager;
  }

  public static void injectGlDownloadManager(
      ContentActivity instance, GLDownloadManager glDownloadManager) {
    instance.glDownloadManager = glDownloadManager;
  }

  public static void injectTextToSpeechManager(
      ContentActivity instance, TextToSpeechManager textToSpeechManager) {
    instance.textToSpeechManager = textToSpeechManager;
  }

  public static void injectAvailableRelatedTypeManager(
      ContentActivity instance, AvailableRelatedTypeQueryManager availableRelatedTypeManager) {
    instance.availableRelatedTypeManager = availableRelatedTypeManager;
  }

  public static void injectDownloadedMediaManager(
      ContentActivity instance, DownloadedMediaManager downloadedMediaManager) {
    instance.downloadedMediaManager = downloadedMediaManager;
  }

  public static void injectNetworkUtil(ContentActivity instance, LdsNetworkUtil networkUtil) {
    instance.networkUtil = networkUtil;
  }

  public static void injectRelatedAudioItemManager(
      ContentActivity instance, RelatedAudioItemManager relatedAudioItemManager) {
    instance.relatedAudioItemManager = relatedAudioItemManager;
  }
}
