package org.lds.ldssa.media.exomedia.data;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;
import org.lds.ldssa.util.DownloadedMediaUtil;
import org.lds.ldssa.util.VideoUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class VideoItem_MembersInjector implements MembersInjector<VideoItem> {
  private final Provider<VideoUtil> videoUtilProvider;

  private final Provider<DownloadedMediaManager> downloadedMediaManagerProvider;

  private final Provider<DownloadedMediaUtil> downloadedMediaUtilProvider;

  private final Provider<ItemManager> itemManagerProvider;

  public VideoItem_MembersInjector(
      Provider<VideoUtil> videoUtilProvider,
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<DownloadedMediaUtil> downloadedMediaUtilProvider,
      Provider<ItemManager> itemManagerProvider) {
    this.videoUtilProvider = videoUtilProvider;
    this.downloadedMediaManagerProvider = downloadedMediaManagerProvider;
    this.downloadedMediaUtilProvider = downloadedMediaUtilProvider;
    this.itemManagerProvider = itemManagerProvider;
  }

  public static MembersInjector<VideoItem> create(
      Provider<VideoUtil> videoUtilProvider,
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<DownloadedMediaUtil> downloadedMediaUtilProvider,
      Provider<ItemManager> itemManagerProvider) {
    return new VideoItem_MembersInjector(
        videoUtilProvider,
        downloadedMediaManagerProvider,
        downloadedMediaUtilProvider,
        itemManagerProvider);
  }

  @Override
  public void injectMembers(VideoItem instance) {
    injectVideoUtil(instance, videoUtilProvider.get());
    injectDownloadedMediaManager(instance, downloadedMediaManagerProvider.get());
    injectDownloadedMediaUtil(instance, downloadedMediaUtilProvider.get());
    injectItemManager(instance, itemManagerProvider.get());
  }

  public static void injectVideoUtil(VideoItem instance, VideoUtil videoUtil) {
    instance.videoUtil = videoUtil;
  }

  public static void injectDownloadedMediaManager(
      VideoItem instance, DownloadedMediaManager downloadedMediaManager) {
    instance.downloadedMediaManager = downloadedMediaManager;
  }

  public static void injectDownloadedMediaUtil(
      VideoItem instance, DownloadedMediaUtil downloadedMediaUtil) {
    instance.downloadedMediaUtil = downloadedMediaUtil;
  }

  public static void injectItemManager(VideoItem instance, ItemManager itemManager) {
    instance.itemManager = itemManager;
  }
}
