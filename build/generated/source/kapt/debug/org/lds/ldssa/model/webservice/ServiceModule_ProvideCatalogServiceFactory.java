package org.lds.ldssa.model.webservice;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import org.lds.ldssa.model.webservice.catalog.CatalogService;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ServiceModule_ProvideCatalogServiceFactory implements Factory<CatalogService> {
  private final ServiceModule module;

  private final Provider<OkHttpClient> clientProvider;

  public ServiceModule_ProvideCatalogServiceFactory(
      ServiceModule module, Provider<OkHttpClient> clientProvider) {
    this.module = module;
    this.clientProvider = clientProvider;
  }

  @Override
  public CatalogService get() {
    return Preconditions.checkNotNull(
        module.provideCatalogService(clientProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<CatalogService> create(
      ServiceModule module, Provider<OkHttpClient> clientProvider) {
    return new ServiceModule_ProvideCatalogServiceFactory(module, clientProvider);
  }
}
