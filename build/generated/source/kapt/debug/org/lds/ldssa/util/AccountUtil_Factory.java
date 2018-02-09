package org.lds.ldssa.util;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldsaccount.LDSAccountPrefs;
import org.lds.ldsaccount.LDSAccountUtil;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.sync.AnnotationSync;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AccountUtil_Factory implements Factory<AccountUtil> {
  private final Provider<Application> arg0Provider;

  private final Provider<Bus> arg1Provider;

  private final Provider<InternalIntents> arg2Provider;

  private final Provider<UserdataDbUtil> arg3Provider;

  private final Provider<Prefs> arg4Provider;

  private final Provider<LDSAccountPrefs> arg5Provider;

  private final Provider<LDSAccountUtil> arg6Provider;

  private final Provider<AnnotationManager> arg7Provider;

  private final Provider<CatalogUpdateUtil> arg8Provider;

  private final Provider<CoroutineContextProvider> arg9Provider;

  private final Provider<AnnotationSync> arg10Provider;

  public AccountUtil_Factory(
      Provider<Application> arg0Provider,
      Provider<Bus> arg1Provider,
      Provider<InternalIntents> arg2Provider,
      Provider<UserdataDbUtil> arg3Provider,
      Provider<Prefs> arg4Provider,
      Provider<LDSAccountPrefs> arg5Provider,
      Provider<LDSAccountUtil> arg6Provider,
      Provider<AnnotationManager> arg7Provider,
      Provider<CatalogUpdateUtil> arg8Provider,
      Provider<CoroutineContextProvider> arg9Provider,
      Provider<AnnotationSync> arg10Provider) {
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
  }

  @Override
  public AccountUtil get() {
    return new AccountUtil(
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
        arg10Provider.get());
  }

  public static Factory<AccountUtil> create(
      Provider<Application> arg0Provider,
      Provider<Bus> arg1Provider,
      Provider<InternalIntents> arg2Provider,
      Provider<UserdataDbUtil> arg3Provider,
      Provider<Prefs> arg4Provider,
      Provider<LDSAccountPrefs> arg5Provider,
      Provider<LDSAccountUtil> arg6Provider,
      Provider<AnnotationManager> arg7Provider,
      Provider<CatalogUpdateUtil> arg8Provider,
      Provider<CoroutineContextProvider> arg9Provider,
      Provider<AnnotationSync> arg10Provider) {
    return new AccountUtil_Factory(
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
        arg10Provider);
  }
}
