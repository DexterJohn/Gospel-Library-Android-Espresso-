package org.lds.ldssa.model.webservice;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import org.lds.ldssa.model.webservice.annotation.LDSAnnotationService;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ServiceModule_GetAnnotationServiceFactory
    implements Factory<LDSAnnotationService> {
  private final ServiceModule module;

  private final Provider<OkHttpClient> clientProvider;

  public ServiceModule_GetAnnotationServiceFactory(
      ServiceModule module, Provider<OkHttpClient> clientProvider) {
    this.module = module;
    this.clientProvider = clientProvider;
  }

  @Override
  public LDSAnnotationService get() {
    return Preconditions.checkNotNull(
        module.getAnnotationService(clientProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<LDSAnnotationService> create(
      ServiceModule module, Provider<OkHttpClient> clientProvider) {
    return new ServiceModule_GetAnnotationServiceFactory(module, clientProvider);
  }
}
