package org.lds.ldssa.ui.loader;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;
import org.lds.ldssa.model.database.gl.downloadedmediacollection.DownloadedMediaCollectionManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DownloadedMediaLoader_MembersInjector
    implements MembersInjector<DownloadedMediaLoader> {
  private final Provider<DownloadedMediaManager> downloadedMediaManagerProvider;

  private final Provider<DownloadedMediaCollectionManager> downloadedMediaCollectionManagerProvider;

  public DownloadedMediaLoader_MembersInjector(
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<DownloadedMediaCollectionManager> downloadedMediaCollectionManagerProvider) {
    this.downloadedMediaManagerProvider = downloadedMediaManagerProvider;
    this.downloadedMediaCollectionManagerProvider = downloadedMediaCollectionManagerProvider;
  }

  public static MembersInjector<DownloadedMediaLoader> create(
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<DownloadedMediaCollectionManager> downloadedMediaCollectionManagerProvider) {
    return new DownloadedMediaLoader_MembersInjector(
        downloadedMediaManagerProvider, downloadedMediaCollectionManagerProvider);
  }

  @Override
  public void injectMembers(DownloadedMediaLoader instance) {
    injectDownloadedMediaManager(instance, downloadedMediaManagerProvider.get());
    injectDownloadedMediaCollectionManager(
        instance, downloadedMediaCollectionManagerProvider.get());
  }

  public static void injectDownloadedMediaManager(
      DownloadedMediaLoader instance, DownloadedMediaManager downloadedMediaManager) {
    instance.downloadedMediaManager = downloadedMediaManager;
  }

  public static void injectDownloadedMediaCollectionManager(
      DownloadedMediaLoader instance,
      DownloadedMediaCollectionManager downloadedMediaCollectionManager) {
    instance.downloadedMediaCollectionManager = downloadedMediaCollectionManager;
  }
}
