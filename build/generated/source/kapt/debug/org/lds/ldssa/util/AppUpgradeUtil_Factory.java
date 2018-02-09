package org.lds.ldssa.util;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldsaccount.LDSAccountPrefs;
import org.lds.ldssa.model.database.gl.tab.TabManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.highlight.HighlightManager;
import org.lds.ldssa.model.database.userdata.screen.ScreenManager;
import org.lds.ldssa.model.database.userdata.screenhistoryitem.ScreenHistoryItemManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.mobile.util.EncryptUtil;
import org.lds.mobile.util.LdsTimeUtil;
import org.lds.mobile.util.LdsZipUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppUpgradeUtil_Factory implements Factory<AppUpgradeUtil> {
  private final Provider<Application> arg0Provider;

  private final Provider<Prefs> arg1Provider;

  private final Provider<LDSAccountPrefs> arg2Provider;

  private final Provider<EncryptUtil> arg3Provider;

  private final Provider<GLFileUtil> arg4Provider;

  private final Provider<LdsTimeUtil> arg5Provider;

  private final Provider<AccountUtil> arg6Provider;

  private final Provider<LdsZipUtil> arg7Provider;

  private final Provider<AnnotationManager> arg8Provider;

  private final Provider<HighlightManager> arg9Provider;

  private final Provider<TabManager> arg10Provider;

  private final Provider<ScreenManager> arg11Provider;

  private final Provider<ScreenHistoryItemManager> arg12Provider;

  private final Provider<UserdataDbUtil> arg13Provider;

  public AppUpgradeUtil_Factory(
      Provider<Application> arg0Provider,
      Provider<Prefs> arg1Provider,
      Provider<LDSAccountPrefs> arg2Provider,
      Provider<EncryptUtil> arg3Provider,
      Provider<GLFileUtil> arg4Provider,
      Provider<LdsTimeUtil> arg5Provider,
      Provider<AccountUtil> arg6Provider,
      Provider<LdsZipUtil> arg7Provider,
      Provider<AnnotationManager> arg8Provider,
      Provider<HighlightManager> arg9Provider,
      Provider<TabManager> arg10Provider,
      Provider<ScreenManager> arg11Provider,
      Provider<ScreenHistoryItemManager> arg12Provider,
      Provider<UserdataDbUtil> arg13Provider) {
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
  }

  @Override
  public AppUpgradeUtil get() {
    return new AppUpgradeUtil(
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
        arg11Provider.get(),
        arg12Provider.get(),
        arg13Provider.get());
  }

  public static Factory<AppUpgradeUtil> create(
      Provider<Application> arg0Provider,
      Provider<Prefs> arg1Provider,
      Provider<LDSAccountPrefs> arg2Provider,
      Provider<EncryptUtil> arg3Provider,
      Provider<GLFileUtil> arg4Provider,
      Provider<LdsTimeUtil> arg5Provider,
      Provider<AccountUtil> arg6Provider,
      Provider<LdsZipUtil> arg7Provider,
      Provider<AnnotationManager> arg8Provider,
      Provider<HighlightManager> arg9Provider,
      Provider<TabManager> arg10Provider,
      Provider<ScreenManager> arg11Provider,
      Provider<ScreenHistoryItemManager> arg12Provider,
      Provider<UserdataDbUtil> arg13Provider) {
    return new AppUpgradeUtil_Factory(
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
        arg13Provider);
  }
}
