package org.lds.ldssa.media.exomedia.service;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.media.exomedia.manager.PlaylistManager;
import org.lds.ldssa.model.database.gl.mediaplaybackposition.MediaPlaybackPositionManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.mobile.media.cast.CastManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MediaService_MembersInjector implements MembersInjector<MediaService> {
  private final Provider<PlaylistManager> playlistManagerProvider;

  private final Provider<CastManager> castManagerProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<Analytics> analyticsProvider;

  private final Provider<AnalyticsUtil> analyticsUtilProvider;

  private final Provider<MediaPlaybackPositionManager> mediaPlaybackPositionManagerProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<ScreenUtil> screenUtilProvider;

  public MediaService_MembersInjector(
      Provider<PlaylistManager> playlistManagerProvider,
      Provider<CastManager> castManagerProvider,
      Provider<Prefs> prefsProvider,
      Provider<Analytics> analyticsProvider,
      Provider<AnalyticsUtil> analyticsUtilProvider,
      Provider<MediaPlaybackPositionManager> mediaPlaybackPositionManagerProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ScreenUtil> screenUtilProvider) {
    this.playlistManagerProvider = playlistManagerProvider;
    this.castManagerProvider = castManagerProvider;
    this.prefsProvider = prefsProvider;
    this.analyticsProvider = analyticsProvider;
    this.analyticsUtilProvider = analyticsUtilProvider;
    this.mediaPlaybackPositionManagerProvider = mediaPlaybackPositionManagerProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.screenUtilProvider = screenUtilProvider;
  }

  public static MembersInjector<MediaService> create(
      Provider<PlaylistManager> playlistManagerProvider,
      Provider<CastManager> castManagerProvider,
      Provider<Prefs> prefsProvider,
      Provider<Analytics> analyticsProvider,
      Provider<AnalyticsUtil> analyticsUtilProvider,
      Provider<MediaPlaybackPositionManager> mediaPlaybackPositionManagerProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ScreenUtil> screenUtilProvider) {
    return new MediaService_MembersInjector(
        playlistManagerProvider,
        castManagerProvider,
        prefsProvider,
        analyticsProvider,
        analyticsUtilProvider,
        mediaPlaybackPositionManagerProvider,
        internalIntentsProvider,
        screenUtilProvider);
  }

  @Override
  public void injectMembers(MediaService instance) {
    injectPlaylistManager(instance, playlistManagerProvider.get());
    injectCastManager(instance, castManagerProvider.get());
    injectPrefs(instance, prefsProvider.get());
    injectAnalytics(instance, analyticsProvider.get());
    injectAnalyticsUtil(instance, analyticsUtilProvider.get());
    injectMediaPlaybackPositionManager(instance, mediaPlaybackPositionManagerProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectScreenUtil(instance, screenUtilProvider.get());
  }

  public static void injectPlaylistManager(MediaService instance, PlaylistManager playlistManager) {
    instance.playlistManager = playlistManager;
  }

  public static void injectCastManager(MediaService instance, CastManager castManager) {
    instance.castManager = castManager;
  }

  public static void injectPrefs(MediaService instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectAnalytics(MediaService instance, Analytics analytics) {
    instance.analytics = analytics;
  }

  public static void injectAnalyticsUtil(MediaService instance, AnalyticsUtil analyticsUtil) {
    instance.analyticsUtil = analyticsUtil;
  }

  public static void injectMediaPlaybackPositionManager(
      MediaService instance, MediaPlaybackPositionManager mediaPlaybackPositionManager) {
    instance.mediaPlaybackPositionManager = mediaPlaybackPositionManager;
  }

  public static void injectInternalIntents(MediaService instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectScreenUtil(MediaService instance, ScreenUtil screenUtil) {
    instance.screenUtil = screenUtil;
  }
}
