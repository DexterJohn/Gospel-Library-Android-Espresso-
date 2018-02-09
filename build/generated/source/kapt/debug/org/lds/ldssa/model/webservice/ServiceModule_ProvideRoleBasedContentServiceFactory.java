package org.lds.ldssa.model.webservice;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import org.lds.ldssa.model.webservice.rolecontent.RoleBasedContentService;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ServiceModule_ProvideRoleBasedContentServiceFactory
    implements Factory<RoleBasedContentService> {
  private final ServiceModule module;

  private final Provider<OkHttpClient> clientProvider;

  public ServiceModule_ProvideRoleBasedContentServiceFactory(
      ServiceModule module, Provider<OkHttpClient> clientProvider) {
    this.module = module;
    this.clientProvider = clientProvider;
  }

  @Override
  public RoleBasedContentService get() {
    return Preconditions.checkNotNull(
        module.provideRoleBasedContentService(clientProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<RoleBasedContentService> create(
      ServiceModule module, Provider<OkHttpClient> clientProvider) {
    return new ServiceModule_ProvideRoleBasedContentServiceFactory(module, clientProvider);
  }
}
