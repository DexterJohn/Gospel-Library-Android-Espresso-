package org.lds.ldssa.util;

import android.app.Application;
import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItemManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ScreenLauncherUtil_Factory implements Factory<ScreenLauncherUtil> {
  private final Provider<Application> arg0Provider;

  private final Provider<ScreenHistoryItemManager> arg1Provider;

  private final Provider<InternalIntents> arg2Provider;

  private final Provider<ContentItemUtil> arg3Provider;

  private final Provider<ScreenUtil> arg4Provider;

  private final Provider<GLDownloadManager> arg5Provider;

  private final Provider<Gson> arg6Provider;

  public ScreenLauncherUtil_Factory(
      Provider<Application> arg0Provider,
      Provider<ScreenHistoryItemManager> arg1Provider,
      Provider<InternalIntents> arg2Provider,
      Provider<ContentItemUtil> arg3Provider,
      Provider<ScreenUtil> arg4Provider,
      Provider<GLDownloadManager> arg5Provider,
      Provider<Gson> arg6Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
    this.arg5Provider = arg5Provider;
    this.arg6Provider = arg6Provider;
  }

  @Override
  public ScreenLauncherUtil get() {
    return new ScreenLauncherUtil(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get(),
        arg6Provider.get());
  }

  public static Factory<ScreenLauncherUtil> create(
      Provider<Application> arg0Provider,
      Provider<ScreenHistoryItemManager> arg1Provider,
      Provider<InternalIntents> arg2Provider,
      Provider<ContentItemUtil> arg3Provider,
      Provider<ScreenUtil> arg4Provider,
      Provider<GLDownloadManager> arg5Provider,
      Provider<Gson> arg6Provider) {
    return new ScreenLauncherUtil_Factory(
        arg0Provider,
        arg1Provider,
        arg2Provider,
        arg3Provider,
        arg4Provider,
        arg5Provider,
        arg6Provider);
  }
}
