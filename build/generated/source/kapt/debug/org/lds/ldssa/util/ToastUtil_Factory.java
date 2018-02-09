package org.lds.ldssa.util;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ToastUtil_Factory implements Factory<ToastUtil> {
  private final Provider<Application> arg0Provider;

  private final Provider<CoroutineContextProvider> arg1Provider;

  public ToastUtil_Factory(
      Provider<Application> arg0Provider, Provider<CoroutineContextProvider> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public ToastUtil get() {
    return new ToastUtil(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<ToastUtil> create(
      Provider<Application> arg0Provider, Provider<CoroutineContextProvider> arg1Provider) {
    return new ToastUtil_Factory(arg0Provider, arg1Provider);
  }
}
