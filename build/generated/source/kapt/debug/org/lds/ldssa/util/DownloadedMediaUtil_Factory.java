package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DownloadedMediaUtil_Factory implements Factory<DownloadedMediaUtil> {
  private final Provider<GLFileUtil> fileUtilProvider;

  private final Provider<DownloadedMediaManager> downloadedMediaManagerProvider;

  private final Provider<GLDownloadManager> downloadManagerProvider;

  public DownloadedMediaUtil_Factory(
      Provider<GLFileUtil> fileUtilProvider,
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<GLDownloadManager> downloadManagerProvider) {
    this.fileUtilProvider = fileUtilProvider;
    this.downloadedMediaManagerProvider = downloadedMediaManagerProvider;
    this.downloadManagerProvider = downloadManagerProvider;
  }

  @Override
  public DownloadedMediaUtil get() {
    return new DownloadedMediaUtil(
        fileUtilProvider.get(),
        downloadedMediaManagerProvider.get(),
        downloadManagerProvider.get());
  }

  public static Factory<DownloadedMediaUtil> create(
      Provider<GLFileUtil> fileUtilProvider,
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<GLDownloadManager> downloadManagerProvider) {
    return new DownloadedMediaUtil_Factory(
        fileUtilProvider, downloadedMediaManagerProvider, downloadManagerProvider);
  }
}
