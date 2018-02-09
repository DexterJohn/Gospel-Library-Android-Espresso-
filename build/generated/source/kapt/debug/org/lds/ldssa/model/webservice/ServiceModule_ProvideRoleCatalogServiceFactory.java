package org.lds.ldssa.model.webservice;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import org.lds.ldssa.model.webservice.catalog.RoleCatalogService;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ServiceModule_ProvideRoleCatalogServiceFactory
    implements Factory<RoleCatalogService> {
  private final ServiceModule module;

  private final Provider<OkHttpClient> clientProvider;

  public ServiceModule_ProvideRoleCatalogServiceFactory(
      ServiceModule module, Provider<OkHttpClient> clientProvider) {
    this.module = module;
    this.clientProvider = clientProvider;
  }

  @Override
  public RoleCatalogService get() {
    return Preconditions.checkNotNull(
        module.provideRoleCatalogService(clientProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<RoleCatalogService> create(
      ServiceModule module, Provider<OkHttpClient> clientProvider) {
    return new ServiceModule_ProvideRoleCatalogServiceFactory(module, clientProvider);
  }
}
