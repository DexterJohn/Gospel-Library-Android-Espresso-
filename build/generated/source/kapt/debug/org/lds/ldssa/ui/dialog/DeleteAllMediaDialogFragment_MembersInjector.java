package org.lds.ldssa.ui.dialog;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.ldssa.util.ToastUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DeleteAllMediaDialogFragment_MembersInjector
    implements MembersInjector<DeleteAllMediaDialogFragment> {
  private final Provider<DownloadedMediaManager> downloadedMediaManagerProvider;

  private final Provider<ItemManager> itemManagerProvider;

  private final Provider<GLFileUtil> fileUtilProvider;

  private final Provider<Analytics> analyticsProvider;

  private final Provider<ToastUtil> toastUtilProvider;

  public DeleteAllMediaDialogFragment_MembersInjector(
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<Analytics> analyticsProvider,
      Provider<ToastUtil> toastUtilProvider) {
    this.downloadedMediaManagerProvider = downloadedMediaManagerProvider;
    this.itemManagerProvider = itemManagerProvider;
    this.fileUtilProvider = fileUtilProvider;
    this.analyticsProvider = analyticsProvider;
    this.toastUtilProvider = toastUtilProvider;
  }

  public static MembersInjector<DeleteAllMediaDialogFragment> create(
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<Analytics> analyticsProvider,
      Provider<ToastUtil> toastUtilProvider) {
    return new DeleteAllMediaDialogFragment_MembersInjector(
        downloadedMediaManagerProvider,
        itemManagerProvider,
        fileUtilProvider,
        analyticsProvider,
        toastUtilProvider);
  }

  @Override
  public void injectMembers(DeleteAllMediaDialogFragment instance) {
    injectDownloadedMediaManager(instance, downloadedMediaManagerProvider.get());
    injectItemManager(instance, itemManagerProvider.get());
    injectFileUtil(instance, fileUtilProvider.get());
    injectAnalytics(instance, analyticsProvider.get());
    injectToastUtil(instance, toastUtilProvider.get());
  }

  public static void injectDownloadedMediaManager(
      DeleteAllMediaDialogFragment instance, DownloadedMediaManager downloadedMediaManager) {
    instance.downloadedMediaManager = downloadedMediaManager;
  }

  public static void injectItemManager(
      DeleteAllMediaDialogFragment instance, ItemManager itemManager) {
    instance.itemManager = itemManager;
  }

  public static void injectFileUtil(DeleteAllMediaDialogFragment instance, GLFileUtil fileUtil) {
    instance.fileUtil = fileUtil;
  }

  public static void injectAnalytics(DeleteAllMediaDialogFragment instance, Analytics analytics) {
    instance.analytics = analytics;
  }

  public static void injectToastUtil(DeleteAllMediaDialogFragment instance, ToastUtil toastUtil) {
    instance.toastUtil = toastUtil;
  }
}
