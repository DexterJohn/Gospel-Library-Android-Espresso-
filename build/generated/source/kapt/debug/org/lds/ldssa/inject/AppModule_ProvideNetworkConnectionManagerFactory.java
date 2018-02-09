package org.lds.ldssa.inject;

import android.net.ConnectivityManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldsaccount.NetworkConnectionManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideNetworkConnectionManagerFactory
    implements Factory<NetworkConnectionManager> {
  private final AppModule module;

  private final Provider<ConnectivityManager> arg0Provider;

  public AppModule_ProvideNetworkConnectionManagerFactory(
      AppModule module, Provider<ConnectivityManager> arg0Provider) {
    this.module = module;
    this.arg0Provider = arg0Provider;
  }

  @Override
  public NetworkConnectionManager get() {
    return Preconditions.checkNotNull(
        module.provideNetworkConnectionManager(arg0Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<NetworkConnectionManager> create(
      AppModule module, Provider<ConnectivityManager> arg0Provider) {
    return new AppModule_ProvideNetworkConnectionManagerFactory(module, arg0Provider);
  }
}
