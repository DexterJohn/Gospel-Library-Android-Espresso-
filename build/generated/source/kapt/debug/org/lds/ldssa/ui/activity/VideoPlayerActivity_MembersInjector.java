package org.lds.ldssa.ui.activity;

import com.google.gson.Gson;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.media.exomedia.manager.PlaylistManager;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.content.navitem.NavItemManager;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;
import org.lds.ldssa.model.database.gl.mediaplaybackposition.MediaPlaybackPositionManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.ui.menu.CommonMenu;
import org.lds.ldssa.util.AccountUtil;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.ShareUtil;
import org.lds.ldssa.util.ThemeUtil;
import org.lds.ldssa.util.VideoUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import org.lds.mobile.media.cast.CastManager;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class VideoPlayerActivity_MembersInjector
    implements MembersInjector<VideoPlayerActivity> {
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

  private final Provider<CastManager> castManagerProvider;

  private final Provider<ShareUtil> shareUtilProvider;

  private final Provider<VideoUtil> videoUtilProvider;

  private final Provider<GLDownloadManager> downloadManagerProvider;

  private final Provider<NavItemManager> navItemManagerProvider;

  private final Provider<GLFileUtil> fileUtilProvider;

  private final Provider<DownloadedMediaManager> downloadedMediaManagerProvider;

  private final Provider<MediaPlaybackPositionManager> mediaPlaybackPositionManagerProvider;

  private final Provider<PlaylistManager> playListManagerProvider;

  public VideoPlayerActivity_MembersInjector(
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
      Provider<CastManager> castManagerProvider,
      Provider<ShareUtil> shareUtilProvider,
      Provider<VideoUtil> videoUtilProvider,
      Provider<GLDownloadManager> downloadManagerProvider,
      Provider<NavItemManager> navItemManagerProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<MediaPlaybackPositionManager> mediaPlaybackPositionManagerProvider,
      Provider<PlaylistManager> playListManagerProvider) {
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
    this.castManagerProvider = castManagerProvider;
    this.shareUtilProvider = shareUtilProvider;
    this.videoUtilProvider = videoUtilProvider;
    this.downloadManagerProvider = downloadManagerProvider;
    this.navItemManagerProvider = navItemManagerProvider;
    this.fileUtilProvider = fileUtilProvider;
    this.downloadedMediaManagerProvider = downloadedMediaManagerProvider;
    this.mediaPlaybackPositionManagerProvider = mediaPlaybackPositionManagerProvider;
    this.playListManagerProvider = playListManagerProvider;
  }

  public static MembersInjector<VideoPlayerActivity> create(
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
      Provider<CastManager> castManagerProvider,
      Provider<ShareUtil> shareUtilProvider,
      Provider<VideoUtil> videoUtilProvider,
      Provider<GLDownloadManager> downloadManagerProvider,
      Provider<NavItemManager> navItemManagerProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<MediaPlaybackPositionManager> mediaPlaybackPositionManagerProvider,
      Provider<PlaylistManager> playListManagerProvider) {
    return new VideoPlayerActivity_MembersInjector(
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
        castManagerProvider,
        shareUtilProvider,
        videoUtilProvider,
        downloadManagerProvider,
        navItemManagerProvider,
        fileUtilProvider,
        downloadedMediaManagerProvider,
        mediaPlaybackPositionManagerProvider,
        playListManagerProvider);
  }

  @Override
  public void injectMembers(VideoPlayerActivity instance) {
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
    injectCastManager(instance, castManagerProvider.get());
    injectShareUtil(instance, shareUtilProvider.get());
    injectVideoUtil(instance, videoUtilProvider.get());
    injectDownloadManager(instance, downloadManagerProvider.get());
    injectNavItemManager(instance, navItemManagerProvider.get());
    injectFileUtil(instance, fileUtilProvider.get());
    injectDownloadedMediaManager(instance, downloadedMediaManagerProvider.get());
    injectMediaPlaybackPositionManager(instance, mediaPlaybackPositionManagerProvider.get());
    injectPlayListManager(instance, playListManagerProvider.get());
  }

  public static void injectCastManager(VideoPlayerActivity instance, CastManager castManager) {
    instance.castManager = castManager;
  }

  public static void injectShareUtil(VideoPlayerActivity instance, ShareUtil shareUtil) {
    instance.shareUtil = shareUtil;
  }

  public static void injectVideoUtil(VideoPlayerActivity instance, VideoUtil videoUtil) {
    instance.videoUtil = videoUtil;
  }

  public static void injectDownloadManager(
      VideoPlayerActivity instance, GLDownloadManager downloadManager) {
    instance.downloadManager = downloadManager;
  }

  public static void injectNavItemManager(
      VideoPlayerActivity instance, NavItemManager navItemManager) {
    instance.navItemManager = navItemManager;
  }

  public static void injectFileUtil(VideoPlayerActivity instance, GLFileUtil fileUtil) {
    instance.fileUtil = fileUtil;
  }

  public static void injectDownloadedMediaManager(
      VideoPlayerActivity instance, DownloadedMediaManager downloadedMediaManager) {
    instance.downloadedMediaManager = downloadedMediaManager;
  }

  public static void injectMediaPlaybackPositionManager(
      VideoPlayerActivity instance, MediaPlaybackPositionManager mediaPlaybackPositionManager) {
    instance.mediaPlaybackPositionManager = mediaPlaybackPositionManager;
  }

  public static void injectPlayListManager(
      VideoPlayerActivity instance, PlaylistManager playListManager) {
    instance.playListManager = playListManager;
  }
}
