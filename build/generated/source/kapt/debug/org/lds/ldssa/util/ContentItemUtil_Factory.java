package org.lds.ldssa.util;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import org.lds.mobile.download.DownloadManagerHelper;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ContentItemUtil_Factory implements Factory<ContentItemUtil> {
  private final Provider<Application> arg0Provider;

  private final Provider<GLFileUtil> arg1Provider;

  private final Provider<DownloadedItemManager> arg2Provider;

  private final Provider<DatabaseManager> arg3Provider;

  private final Provider<Bus> arg4Provider;

  private final Provider<Analytics> arg5Provider;

  private final Provider<DownloadQueueItemManager> arg6Provider;

  private final Provider<GLDownloadManager> arg7Provider;

  private final Provider<ScreenUtil> arg8Provider;

  private final Provider<ItemManager> arg9Provider;

  private final Provider<CoroutineContextProvider> arg10Provider;

  private final Provider<DownloadManagerHelper> arg11Provider;

  public ContentItemUtil_Factory(
      Provider<Application> arg0Provider,
      Provider<GLFileUtil> arg1Provider,
      Provider<DownloadedItemManager> arg2Provider,
      Provider<DatabaseManager> arg3Provider,
      Provider<Bus> arg4Provider,
      Provider<Analytics> arg5Provider,
      Provider<DownloadQueueItemManager> arg6Provider,
      Provider<GLDownloadManager> arg7Provider,
      Provider<ScreenUtil> arg8Provider,
      Provider<ItemManager> arg9Provider,
      Provider<CoroutineContextProvider> arg10Provider,
      Provider<DownloadManagerHelper> arg11Provider) {
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
  }

  @Override
  public ContentItemUtil get() {
    return new ContentItemUtil(
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
        arg11Provider.get());
  }

  public static Factory<ContentItemUtil> create(
      Provider<Application> arg0Provider,
      Provider<GLFileUtil> arg1Provider,
      Provider<DownloadedItemManager> arg2Provider,
      Provider<DatabaseManager> arg3Provider,
      Provider<Bus> arg4Provider,
      Provider<Analytics> arg5Provider,
      Provider<DownloadQueueItemManager> arg6Provider,
      Provider<GLDownloadManager> arg7Provider,
      Provider<ScreenUtil> arg8Provider,
      Provider<ItemManager> arg9Provider,
      Provider<CoroutineContextProvider> arg10Provider,
      Provider<DownloadManagerHelper> arg11Provider) {
    return new ContentItemUtil_Factory(
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
        arg11Provider);
  }
}
