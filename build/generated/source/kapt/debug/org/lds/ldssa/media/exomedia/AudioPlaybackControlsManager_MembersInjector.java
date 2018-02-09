package org.lds.ldssa.media.exomedia;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.media.exomedia.manager.PlaylistManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.prefs.Prefs;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AudioPlaybackControlsManager_MembersInjector
    implements MembersInjector<AudioPlaybackControlsManager> {
  private final Provider<PlaylistManager> playlistManagerProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<GLDownloadManager> downloadManagerProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<ItemManager> itemManagerProvider;

  private final Provider<SubItemManager> subItemManagerProvider;

  public AudioPlaybackControlsManager_MembersInjector(
      Provider<PlaylistManager> playlistManagerProvider,
      Provider<Prefs> prefsProvider,
      Provider<GLDownloadManager> downloadManagerProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<SubItemManager> subItemManagerProvider) {
    this.playlistManagerProvider = playlistManagerProvider;
    this.prefsProvider = prefsProvider;
    this.downloadManagerProvider = downloadManagerProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.itemManagerProvider = itemManagerProvider;
    this.subItemManagerProvider = subItemManagerProvider;
  }

  public static MembersInjector<AudioPlaybackControlsManager> create(
      Provider<PlaylistManager> playlistManagerProvider,
      Provider<Prefs> prefsProvider,
      Provider<GLDownloadManager> downloadManagerProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<SubItemManager> subItemManagerProvider) {
    return new AudioPlaybackControlsManager_MembersInjector(
        playlistManagerProvider,
        prefsProvider,
        downloadManagerProvider,
        internalIntentsProvider,
        itemManagerProvider,
        subItemManagerProvider);
  }

  @Override
  public void injectMembers(AudioPlaybackControlsManager instance) {
    injectPlaylistManager(instance, playlistManagerProvider.get());
    injectPrefs(instance, prefsProvider.get());
    injectDownloadManager(instance, downloadManagerProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectItemManager(instance, itemManagerProvider.get());
    injectSubItemManager(instance, subItemManagerProvider.get());
  }

  public static void injectPlaylistManager(
      AudioPlaybackControlsManager instance, PlaylistManager playlistManager) {
    instance.playlistManager = playlistManager;
  }

  public static void injectPrefs(AudioPlaybackControlsManager instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectDownloadManager(
      AudioPlaybackControlsManager instance, GLDownloadManager downloadManager) {
    instance.downloadManager = downloadManager;
  }

  public static void injectInternalIntents(
      AudioPlaybackControlsManager instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectItemManager(
      AudioPlaybackControlsManager instance, ItemManager itemManager) {
    instance.itemManager = itemManager;
  }

  public static void injectSubItemManager(
      AudioPlaybackControlsManager instance, SubItemManager subItemManager) {
    instance.subItemManager = subItemManager;
  }
}
