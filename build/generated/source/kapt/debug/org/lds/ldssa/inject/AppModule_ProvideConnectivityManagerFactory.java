package org.lds.ldssa.inject;

import android.app.Application;
import android.net.ConnectivityManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideConnectivityManagerFactory
    implements Factory<ConnectivityManager> {
  private final AppModule module;

  private final Provider<Application> arg0Provider;

  public AppModule_ProvideConnectivityManagerFactory(
      AppModule module, Provider<Application> arg0Provider) {
    this.module = module;
    this.arg0Provider = arg0Provider;
  }

  @Override
  public ConnectivityManager get() {
    return Preconditions.checkNotNull(
        module.provideConnectivityManager(arg0Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<ConnectivityManager> create(
      AppModule module, Provider<Application> arg0Provider) {
    return new AppModule_ProvideConnectivityManagerFactory(module, arg0Provider);
  }
}
