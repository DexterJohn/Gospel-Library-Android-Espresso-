package org.lds.ldssa.ux.catalog;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.ldssa.util.ContentItemUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CatalogDirectoryAdapter_MembersInjector
    implements MembersInjector<CatalogDirectoryAdapter> {
  private final Provider<DownloadQueueItemManager> downloadQueueItemManagerProvider;

  private final Provider<ContentItemUtil> contentItemUtilProvider;

  public CatalogDirectoryAdapter_MembersInjector(
      Provider<DownloadQueueItemManager> downloadQueueItemManagerProvider,
      Provider<ContentItemUtil> contentItemUtilProvider) {
    this.downloadQueueItemManagerProvider = downloadQueueItemManagerProvider;
    this.contentItemUtilProvider = contentItemUtilProvider;
  }

  public static MembersInjector<CatalogDirectoryAdapter> create(
      Provider<DownloadQueueItemManager> downloadQueueItemManagerProvider,
      Provider<ContentItemUtil> contentItemUtilProvider) {
    return new CatalogDirectoryAdapter_MembersInjector(
        downloadQueueItemManagerProvider, contentItemUtilProvider);
  }

  @Override
  public void injectMembers(CatalogDirectoryAdapter instance) {
    injectDownloadQueueItemManager(instance, downloadQueueItemManagerProvider.get());
    injectContentItemUtil(instance, contentItemUtilProvider.get());
  }

  public static void injectDownloadQueueItemManager(
      CatalogDirectoryAdapter instance, DownloadQueueItemManager downloadQueueItemManager) {
    instance.downloadQueueItemManager = downloadQueueItemManager;
  }

  public static void injectContentItemUtil(
      CatalogDirectoryAdapter instance, ContentItemUtil contentItemUtil) {
    instance.contentItemUtil = contentItemUtil;
  }
}
