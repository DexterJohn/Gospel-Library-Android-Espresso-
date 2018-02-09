package org.lds.ldssa.media.exomedia.data;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;
import org.lds.ldssa.util.DownloadedMediaUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AudioItem_MembersInjector implements MembersInjector<AudioItem> {
  private final Provider<DownloadedMediaManager> downloadedMediaManagerProvider;

  private final Provider<DownloadedMediaUtil> downloadedMediaUtilProvider;

  private final Provider<ItemManager> itemManagerProvider;

  public AudioItem_MembersInjector(
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<DownloadedMediaUtil> downloadedMediaUtilProvider,
      Provider<ItemManager> itemManagerProvider) {
    this.downloadedMediaManagerProvider = downloadedMediaManagerProvider;
    this.downloadedMediaUtilProvider = downloadedMediaUtilProvider;
    this.itemManagerProvider = itemManagerProvider;
  }

  public static MembersInjector<AudioItem> create(
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<DownloadedMediaUtil> downloadedMediaUtilProvider,
      Provider<ItemManager> itemManagerProvider) {
    return new AudioItem_MembersInjector(
        downloadedMediaManagerProvider, downloadedMediaUtilProvider, itemManagerProvider);
  }

  @Override
  public void injectMembers(AudioItem instance) {
    injectDownloadedMediaManager(instance, downloadedMediaManagerProvider.get());
    injectDownloadedMediaUtil(instance, downloadedMediaUtilProvider.get());
    injectItemManager(instance, itemManagerProvider.get());
  }

  public static void injectDownloadedMediaManager(
      AudioItem instance, DownloadedMediaManager downloadedMediaManager) {
    instance.downloadedMediaManager = downloadedMediaManager;
  }

  public static void injectDownloadedMediaUtil(
      AudioItem instance, DownloadedMediaUtil downloadedMediaUtil) {
    instance.downloadedMediaUtil = downloadedMediaUtil;
  }

  public static void injectItemManager(AudioItem instance, ItemManager itemManager) {
    instance.itemManager = itemManager;
  }
}
