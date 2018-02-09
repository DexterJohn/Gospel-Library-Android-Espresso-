package org.lds.ldssa.receiver;

import android.app.Application;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.download.DownloadReceivedTask;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.ldssa.util.CatalogUpdateUtil;
import org.lds.ldssa.util.ToastUtil;
import org.lds.mobile.download.DownloadManagerHelper;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DownloadManagerReceiver_MembersInjector
    implements MembersInjector<DownloadManagerReceiver> {
  private final Provider<Application> applicationProvider;

  private final Provider<Bus> busProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<DownloadReceivedTask> downloadReceivedTaskProvider;

  private final Provider<DownloadManagerHelper> downloadManagerHelperProvider;

  private final Provider<DownloadQueueItemManager> downloadQueueItemManagerProvider;

  private final Provider<CatalogUpdateUtil> catalogUpdateUtilProvider;

  private final Provider<ToastUtil> toastUtilProvider;

  public DownloadManagerReceiver_MembersInjector(
      Provider<Application> applicationProvider,
      Provider<Bus> busProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<DownloadReceivedTask> downloadReceivedTaskProvider,
      Provider<DownloadManagerHelper> downloadManagerHelperProvider,
      Provider<DownloadQueueItemManager> downloadQueueItemManagerProvider,
      Provider<CatalogUpdateUtil> catalogUpdateUtilProvider,
      Provider<ToastUtil> toastUtilProvider) {
    this.applicationProvider = applicationProvider;
    this.busProvider = busProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.downloadReceivedTaskProvider = downloadReceivedTaskProvider;
    this.downloadManagerHelperProvider = downloadManagerHelperProvider;
    this.downloadQueueItemManagerProvider = downloadQueueItemManagerProvider;
    this.catalogUpdateUtilProvider = catalogUpdateUtilProvider;
    this.toastUtilProvider = toastUtilProvider;
  }

  public static MembersInjector<DownloadManagerReceiver> create(
      Provider<Application> applicationProvider,
      Provider<Bus> busProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<DownloadReceivedTask> downloadReceivedTaskProvider,
      Provider<DownloadManagerHelper> downloadManagerHelperProvider,
      Provider<DownloadQueueItemManager> downloadQueueItemManagerProvider,
      Provider<CatalogUpdateUtil> catalogUpdateUtilProvider,
      Provider<ToastUtil> toastUtilProvider) {
    return new DownloadManagerReceiver_MembersInjector(
        applicationProvider,
        busProvider,
        internalIntentsProvider,
        downloadReceivedTaskProvider,
        downloadManagerHelperProvider,
        downloadQueueItemManagerProvider,
        catalogUpdateUtilProvider,
        toastUtilProvider);
  }

  @Override
  public void injectMembers(DownloadManagerReceiver instance) {
    injectApplication(instance, applicationProvider.get());
    injectBus(instance, busProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectDownloadReceivedTaskProvider(instance, downloadReceivedTaskProvider);
    injectDownloadManagerHelper(instance, downloadManagerHelperProvider.get());
    injectDownloadQueueItemManager(instance, downloadQueueItemManagerProvider.get());
    injectCatalogUpdateUtil(instance, catalogUpdateUtilProvider.get());
    injectToastUtil(instance, toastUtilProvider.get());
  }

  public static void injectApplication(DownloadManagerReceiver instance, Application application) {
    instance.application = application;
  }

  public static void injectBus(DownloadManagerReceiver instance, Bus bus) {
    instance.bus = bus;
  }

  public static void injectInternalIntents(
      DownloadManagerReceiver instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectDownloadReceivedTaskProvider(
      DownloadManagerReceiver instance,
      Provider<DownloadReceivedTask> downloadReceivedTaskProvider) {
    instance.downloadReceivedTaskProvider = downloadReceivedTaskProvider;
  }

  public static void injectDownloadManagerHelper(
      DownloadManagerReceiver instance, DownloadManagerHelper downloadManagerHelper) {
    instance.downloadManagerHelper = downloadManagerHelper;
  }

  public static void injectDownloadQueueItemManager(
      DownloadManagerReceiver instance, DownloadQueueItemManager downloadQueueItemManager) {
    instance.downloadQueueItemManager = downloadQueueItemManager;
  }

  public static void injectCatalogUpdateUtil(
      DownloadManagerReceiver instance, CatalogUpdateUtil catalogUpdateUtil) {
    instance.catalogUpdateUtil = catalogUpdateUtil;
  }

  public static void injectToastUtil(DownloadManagerReceiver instance, ToastUtil toastUtil) {
    instance.toastUtil = toastUtil;
  }
}
