package org.lds.ldssa.model.webservice;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import org.lds.ldssa.model.webservice.app.AppConfigService;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ServiceModule_ProvideAppConfigServiceFactory
    implements Factory<AppConfigService> {
  private final ServiceModule module;

  private final Provider<OkHttpClient> clientProvider;

  public ServiceModule_ProvideAppConfigServiceFactory(
      ServiceModule module, Provider<OkHttpClient> clientProvider) {
    this.module = module;
    this.clientProvider = clientProvider;
  }

  @Override
  public AppConfigService get() {
    return Preconditions.checkNotNull(
        module.provideAppConfigService(clientProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<AppConfigService> create(
      ServiceModule module, Provider<OkHttpClient> clientProvider) {
    return new ServiceModule_ProvideAppConfigServiceFactory(module, clientProvider);
  }
}
