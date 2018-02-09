package org.lds.ldssa.ui.loader;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.libraryitem.LibraryItemManager;
import org.lds.ldssa.model.database.content.navcollection.NavCollectionManager;
import org.lds.ldssa.model.database.content.navitem.NavItemManager;
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItemManager;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.VideoUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DownloadableMediaListLoader_MembersInjector
    implements MembersInjector<DownloadableMediaListLoader> {
  private final Provider<VideoUtil> videoUtilProvider;

  private final Provider<ItemManager> itemManagerProvider;

  private final Provider<NavItemManager> navItemManagerProvider;

  private final Provider<NavCollectionManager> navCollectionManagerProvider;

  private final Provider<LibraryItemManager> libraryItemManagerProvider;

  private final Provider<DownloadedMediaManager> downloadedMediaManagerProvider;

  private final Provider<DownloadQueueItemManager> downloadQueueItemManagerProvider;

  private final Provider<RelatedAudioItemManager> relatedAudioItemManagerProvider;

  private final Provider<Prefs> prefsProvider;

  public DownloadableMediaListLoader_MembersInjector(
      Provider<VideoUtil> videoUtilProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<NavItemManager> navItemManagerProvider,
      Provider<NavCollectionManager> navCollectionManagerProvider,
      Provider<LibraryItemManager> libraryItemManagerProvider,
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<DownloadQueueItemManager> downloadQueueItemManagerProvider,
      Provider<RelatedAudioItemManager> relatedAudioItemManagerProvider,
      Provider<Prefs> prefsProvider) {
    this.videoUtilProvider = videoUtilProvider;
    this.itemManagerProvider = itemManagerProvider;
    this.navItemManagerProvider = navItemManagerProvider;
    this.navCollectionManagerProvider = navCollectionManagerProvider;
    this.libraryItemManagerProvider = libraryItemManagerProvider;
    this.downloadedMediaManagerProvider = downloadedMediaManagerProvider;
    this.downloadQueueItemManagerProvider = downloadQueueItemManagerProvider;
    this.relatedAudioItemManagerProvider = relatedAudioItemManagerProvider;
    this.prefsProvider = prefsProvider;
  }

  public static MembersInjector<DownloadableMediaListLoader> create(
      Provider<VideoUtil> videoUtilProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<NavItemManager> navItemManagerProvider,
      Provider<NavCollectionManager> navCollectionManagerProvider,
      Provider<LibraryItemManager> libraryItemManagerProvider,
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<DownloadQueueItemManager> downloadQueueItemManagerProvider,
      Provider<RelatedAudioItemManager> relatedAudioItemManagerProvider,
      Provider<Prefs> prefsProvider) {
    return new DownloadableMediaListLoader_MembersInjector(
        videoUtilProvider,
        itemManagerProvider,
        navItemManagerProvider,
        navCollectionManagerProvider,
        libraryItemManagerProvider,
        downloadedMediaManagerProvider,
        downloadQueueItemManagerProvider,
        relatedAudioItemManagerProvider,
        prefsProvider);
  }

  @Override
  public void injectMembers(DownloadableMediaListLoader instance) {
    injectVideoUtil(instance, videoUtilProvider.get());
    injectItemManager(instance, itemManagerProvider.get());
    injectNavItemManager(instance, navItemManagerProvider.get());
    injectNavCollectionManager(instance, navCollectionManagerProvider.get());
    injectLibraryItemManager(instance, libraryItemManagerProvider.get());
    injectDownloadedMediaManager(instance, downloadedMediaManagerProvider.get());
    injectDownloadQueueItemManager(instance, downloadQueueItemManagerProvider.get());
    injectRelatedAudioItemManager(instance, relatedAudioItemManagerProvider.get());
    injectPrefs(instance, prefsProvider.get());
  }

  public static void injectVideoUtil(DownloadableMediaListLoader instance, VideoUtil videoUtil) {
    instance.videoUtil = videoUtil;
  }

  public static void injectItemManager(
      DownloadableMediaListLoader instance, ItemManager itemManager) {
    instance.itemManager = itemManager;
  }

  public static void injectNavItemManager(
      DownloadableMediaListLoader instance, NavItemManager navItemManager) {
    instance.navItemManager = navItemManager;
  }

  public static void injectNavCollectionManager(
      DownloadableMediaListLoader instance, NavCollectionManager navCollectionManager) {
    instance.navCollectionManager = navCollectionManager;
  }

  public static void injectLibraryItemManager(
      DownloadableMediaListLoader instance, LibraryItemManager libraryItemManager) {
    instance.libraryItemManager = libraryItemManager;
  }

  public static void injectDownloadedMediaManager(
      DownloadableMediaListLoader instance, DownloadedMediaManager downloadedMediaManager) {
    instance.downloadedMediaManager = downloadedMediaManager;
  }

  public static void injectDownloadQueueItemManager(
      DownloadableMediaListLoader instance, DownloadQueueItemManager downloadQueueItemManager) {
    instance.downloadQueueItemManager = downloadQueueItemManager;
  }

  public static void injectRelatedAudioItemManager(
      DownloadableMediaListLoader instance, RelatedAudioItemManager relatedAudioItemManager) {
    instance.relatedAudioItemManager = relatedAudioItemManager;
  }

  public static void injectPrefs(DownloadableMediaListLoader instance, Prefs prefs) {
    instance.prefs = prefs;
  }
}
