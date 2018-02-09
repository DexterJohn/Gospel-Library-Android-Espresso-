package org.lds.ldssa.download;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldsaccount.LDSAccountAuth;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.ldssa.util.ToastUtil;
import org.lds.mobile.download.DownloadManagerHelper;
import org.lds.mobile.util.LdsNetworkUtil;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GLDownloadManager_Factory implements Factory<GLDownloadManager> {
  private final Provider<Application> arg0Provider;

  private final Provider<Bus> arg1Provider;

  private final Provider<DownloadedItemManager> arg2Provider;

  private final Provider<DownloadedMediaManager> arg3Provider;

  private final Provider<DownloadQueueItemManager> arg4Provider;

  private final Provider<LDSAccountAuth> arg5Provider;

  private final Provider<DownloadManagerHelper> arg6Provider;

  private final Provider<LdsNetworkUtil> arg7Provider;

  private final Provider<GLFileUtil> arg8Provider;

  private final Provider<Prefs> arg9Provider;

  private final Provider<ToastUtil> arg10Provider;

  private final Provider<DownloadedContentProcessor> arg11Provider;

  private final Provider<DownloadedMediaProcessor> arg12Provider;

  private final Provider<DownloadedCatalogProcessor> arg13Provider;

  private final Provider<DownloadedTipsProcessor> arg14Provider;

  private final Provider<MediaDownloader> arg15Provider;

  private final Provider<AllAudioDownloader> arg16Provider;

  private final Provider<ContentDownloader> arg17Provider;

  private final Provider<CancelDownloadsTask> arg18Provider;

  private final Provider<DirectDownloadTask> arg19Provider;

  private final Provider<RepairMissingDownloadedItems> arg20Provider;

  public GLDownloadManager_Factory(
      Provider<Application> arg0Provider,
      Provider<Bus> arg1Provider,
      Provider<DownloadedItemManager> arg2Provider,
      Provider<DownloadedMediaManager> arg3Provider,
      Provider<DownloadQueueItemManager> arg4Provider,
      Provider<LDSAccountAuth> arg5Provider,
      Provider<DownloadManagerHelper> arg6Provider,
      Provider<LdsNetworkUtil> arg7Provider,
      Provider<GLFileUtil> arg8Provider,
      Provider<Prefs> arg9Provider,
      Provider<ToastUtil> arg10Provider,
      Provider<DownloadedContentProcessor> arg11Provider,
      Provider<DownloadedMediaProcessor> arg12Provider,
      Provider<DownloadedCatalogProcessor> arg13Provider,
      Provider<DownloadedTipsProcessor> arg14Provider,
      Provider<MediaDownloader> arg15Provider,
      Provider<AllAudioDownloader> arg16Provider,
      Provider<ContentDownloader> arg17Provider,
      Provider<CancelDownloadsTask> arg18Provider,
      Provider<DirectDownloadTask> arg19Provider,
      Provider<RepairMissingDownloadedItems> arg20Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
    this.arg5Provider = arg5Provider;
    this.arg6Provider = arg6Provider;
    this.arg7Provider = arg7Provider;
    this.arg8Provider = arg8Provider;
    this.arg9Provider = arg9Provider;
    this.arg10Provider = arg10Provider;
    this.arg11Provider = arg11Provider;
    this.arg12Provider = arg12Provider;
    this.arg13Provider = arg13Provider;
    this.arg14Provider = arg14Provider;
    this.arg15Provider = arg15Provider;
    this.arg16Provider = arg16Provider;
    this.arg17Provider = arg17Provider;
    this.arg18Provider = arg18Provider;
    this.arg19Provider = arg19Provider;
    this.arg20Provider = arg20Provider;
  }

  @Override
  public GLDownloadManager get() {
    return new GLDownloadManager(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get(),
        arg6Provider.get(),
        arg7Provider.get(),
        arg8Provider.get(),
        arg9Provider.get(),
        arg10Provider.get(),
        arg11Provider,
        arg12Provider,
        arg13Provider,
        arg14Provider,
        arg15Provider,
        arg16Provider,
        arg17Provider,
        arg18Provider,
        arg19Provider,
        arg20Provider);
  }

  public static Factory<GLDownloadManager> create(
      Provider<Application> arg0Provider,
      Provider<Bus> arg1Provider,
      Provider<DownloadedItemManager> arg2Provider,
      Provider<DownloadedMediaManager> arg3Provider,
      Provider<DownloadQueueItemManager> arg4Provider,
      Provider<LDSAccountAuth> arg5Provider,
      Provider<DownloadManagerHelper> arg6Provider,
      Provider<LdsNetworkUtil> arg7Provider,
      Provider<GLFileUtil> arg8Provider,
      Provider<Prefs> arg9Provider,
      Provider<ToastUtil> arg10Provider,
      Provider<DownloadedContentProcessor> arg11Provider,
      Provider<DownloadedMediaProcessor> arg12Provider,
      Provider<DownloadedCatalogProcessor> arg13Provider,
      Provider<DownloadedTipsProcessor> arg14Provider,
      Provider<MediaDownloader> arg15Provider,
      Provider<AllAudioDownloader> arg16Provider,
      Provider<ContentDownloader> arg17Provider,
      Provider<CancelDownloadsTask> arg18Provider,
      Provider<DirectDownloadTask> arg19Provider,
      Provider<RepairMissingDownloadedItems> arg20Provider) {
    return new GLDownloadManager_Factory(
        arg0Provider,
        arg1Provider,
        arg2Provider,
        arg3Provider,
        arg4Provider,
        arg5Provider,
        arg6Provider,
        arg7Provider,
        arg8Provider,
        arg9Provider,
        arg10Provider,
        arg11Provider,
        arg12Provider,
        arg13Provider,
        arg14Provider,
        arg15Provider,
        arg16Provider,
        arg17Provider,
        arg18Provider,
        arg19Provider,
        arg20Provider);
  }
}
