package org.lds.ldssa.model.webservice;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import okhttp3.OkHttpClient;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ServiceModule_GetStandardClientFactory implements Factory<OkHttpClient> {
  private final ServiceModule module;

  public ServiceModule_GetStandardClientFactory(ServiceModule module) {
    this.module = module;
  }

  @Override
  public OkHttpClient get() {
    return Preconditions.checkNotNull(
        module.getStandardClient(), "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<OkHttpClient> create(ServiceModule module) {
    return new ServiceModule_GetStandardClientFactory(module);
  }
}
