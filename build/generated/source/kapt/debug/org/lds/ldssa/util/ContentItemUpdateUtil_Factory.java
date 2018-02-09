package org.lds.ldssa.util;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.libraryitem.LibraryItemManager;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.ldssa.model.database.userdata.synccontentitemannotationsqueueitem.SyncContentItemAnnotationsQueueItemManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.sync.AnnotationSyncScheduler;
import org.lds.mobile.download.DownloadManagerHelper;
import org.lds.mobile.util.LdsZipUtil;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ContentItemUpdateUtil_Factory implements Factory<ContentItemUpdateUtil> {
  private final Provider<Application> applicationProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<Bus> busProvider;

  private final Provider<Analytics> analyticsProvider;

  private final Provider<GLFileUtil> fileUtilProvider;

  private final Provider<LdsZipUtil> zipUtilProvider;

  private final Provider<ItemManager> itemManagerProvider;

  private final Provider<DownloadedItemManager> downloadedItemManagerProvider;

  private final Provider<DownloadQueueItemManager> downloadQueueItemManagerProvider;

  private final Provider<DownloadManagerHelper> downloadManagerHelperProvider;

  private final Provider<GLDownloadManager> glDownloadManagerProvider;

  private final Provider<LibraryItemManager> libraryItemManagerProvider;

  private final Provider<AnnotationSyncScheduler> annotationSyncSchedulerProvider;

  private final Provider<SyncContentItemAnnotationsQueueItemManager>
      syncAnnotationsForContentItemManagerProvider;

  private final Provider<ContentItemUtil> contentItemUtilProvider;

  public ContentItemUpdateUtil_Factory(
      Provider<Application> applicationProvider,
      Provider<Prefs> prefsProvider,
      Provider<Bus> busProvider,
      Provider<Analytics> analyticsProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<LdsZipUtil> zipUtilProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<DownloadedItemManager> downloadedItemManagerProvider,
      Provider<DownloadQueueItemManager> downloadQueueItemManagerProvider,
      Provider<DownloadManagerHelper> downloadManagerHelperProvider,
      Provider<GLDownloadManager> glDownloadManagerProvider,
      Provider<LibraryItemManager> libraryItemManagerProvider,
      Provider<AnnotationSyncScheduler> annotationSyncSchedulerProvider,
      Provider<SyncContentItemAnnotationsQueueItemManager>
          syncAnnotationsForContentItemManagerProvider,
      Provider<ContentItemUtil> contentItemUtilProvider) {
    this.applicationProvider = applicationProvider;
    this.prefsProvider = prefsProvider;
    this.busProvider = busProvider;
    this.analyticsProvider = analyticsProvider;
    this.fileUtilProvider = fileUtilProvider;
    this.zipUtilProvider = zipUtilProvider;
    this.itemManagerProvider = itemManagerProvider;
    this.downloadedItemManagerProvider = downloadedItemManagerProvider;
    this.downloadQueueItemManagerProvider = downloadQueueItemManagerProvider;
    this.downloadManagerHelperProvider = downloadManagerHelperProvider;
    this.glDownloadManagerProvider = glDownloadManagerProvider;
    this.libraryItemManagerProvider = libraryItemManagerProvider;
    this.annotationSyncSchedulerProvider = annotationSyncSchedulerProvider;
    this.syncAnnotationsForContentItemManagerProvider =
        syncAnnotationsForContentItemManagerProvider;
    this.contentItemUtilProvider = contentItemUtilProvider;
  }

  @Override
  public ContentItemUpdateUtil get() {
    return new ContentItemUpdateUtil(
        applicationProvider.get(),
        prefsProvider.get(),
        busProvider.get(),
        analyticsProvider.get(),
        fileUtilProvider.get(),
        zipUtilProvider.get(),
        itemManagerProvider.get(),
        downloadedItemManagerProvider.get(),
        downloadQueueItemManagerProvider.get(),
        downloadManagerHelperProvider.get(),
        glDownloadManagerProvider.get(),
        libraryItemManagerProvider.get(),
        annotationSyncSchedulerProvider.get(),
        syncAnnotationsForContentItemManagerProvider.get(),
        contentItemUtilProvider.get());
  }

  public static Factory<ContentItemUpdateUtil> create(
      Provider<Application> applicationProvider,
      Provider<Prefs> prefsProvider,
      Provider<Bus> busProvider,
      Provider<Analytics> analyticsProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<LdsZipUtil> zipUtilProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<DownloadedItemManager> downloadedItemManagerProvider,
      Provider<DownloadQueueItemManager> downloadQueueItemManagerProvider,
      Provider<DownloadManagerHelper> downloadManagerHelperProvider,
      Provider<GLDownloadManager> glDownloadManagerProvider,
      Provider<LibraryItemManager> libraryItemManagerProvider,
      Provider<AnnotationSyncScheduler> annotationSyncSchedulerProvider,
      Provider<SyncContentItemAnnotationsQueueItemManager>
          syncAnnotationsForContentItemManagerProvider,
      Provider<ContentItemUtil> contentItemUtilProvider) {
    return new ContentItemUpdateUtil_Factory(
        applicationProvider,
        prefsProvider,
        busProvider,
        analyticsProvider,
        fileUtilProvider,
        zipUtilProvider,
        itemManagerProvider,
        downloadedItemManagerProvider,
        downloadQueueItemManagerProvider,
        downloadManagerHelperProvider,
        glDownloadManagerProvider,
        libraryItemManagerProvider,
        annotationSyncSchedulerProvider,
        syncAnnotationsForContentItemManagerProvider,
        contentItemUtilProvider);
  }
}
