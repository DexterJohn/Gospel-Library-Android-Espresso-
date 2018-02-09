package org.lds.ldssa.util;

import android.app.ActivityManager;
import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.media.exomedia.manager.PlaylistManager;
import org.lds.ldssa.model.database.gl.recentlanguage.RecentLanguageManager;
import org.lds.ldssa.model.database.userdata.screen.ScreenManager;
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItemManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.search.SearchUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ScreenUtil_Factory implements Factory<ScreenUtil> {
  private final Provider<ScreenManager> arg0Provider;

  private final Provider<Prefs> arg1Provider;

  private final Provider<ScreenHistoryItemManager> arg2Provider;

  private final Provider<PlaylistManager> arg3Provider;

  private final Provider<ImageUtil> arg4Provider;

  private final Provider<ActivityManager> arg5Provider;

  private final Provider<LanguageUtil> arg6Provider;

  private final Provider<RecentLanguageManager> arg7Provider;

  private final Provider<SearchUtil> arg8Provider;

  private final Provider<Gson> arg9Provider;

  public ScreenUtil_Factory(
      Provider<ScreenManager> arg0Provider,
      Provider<Prefs> arg1Provider,
      Provider<ScreenHistoryItemManager> arg2Provider,
      Provider<PlaylistManager> arg3Provider,
      Provider<ImageUtil> arg4Provider,
      Provider<ActivityManager> arg5Provider,
      Provider<LanguageUtil> arg6Provider,
      Provider<RecentLanguageManager> arg7Provider,
      Provider<SearchUtil> arg8Provider,
      Provider<Gson> arg9Provider) {
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
  }

  @Override
  public ScreenUtil get() {
    return new ScreenUtil(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get(),
        arg6Provider.get(),
        arg7Provider.get(),
        arg8Provider.get(),
        arg9Provider.get());
  }

  public static Factory<ScreenUtil> create(
      Provider<ScreenManager> arg0Provider,
      Provider<Prefs> arg1Provider,
      Provider<ScreenHistoryItemManager> arg2Provider,
      Provider<PlaylistManager> arg3Provider,
      Provider<ImageUtil> arg4Provider,
      Provider<ActivityManager> arg5Provider,
      Provider<LanguageUtil> arg6Provider,
      Provider<RecentLanguageManager> arg7Provider,
      Provider<SearchUtil> arg8Provider,
      Provider<Gson> arg9Provider) {
    return new ScreenUtil_Factory(
        arg0Provider,
        arg1Provider,
        arg2Provider,
        arg3Provider,
        arg4Provider,
        arg5Provider,
        arg6Provider,
        arg7Provider,
        arg8Provider,
        arg9Provider);
  }
}
