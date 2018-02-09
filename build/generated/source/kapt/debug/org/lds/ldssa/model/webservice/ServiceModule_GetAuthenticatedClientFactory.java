package org.lds.ldssa.model.webservice;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;
import org.lds.ldsaccount.LDSAccountAuth;
import org.lds.ldsaccount.LDSAccountInterceptor;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ServiceModule_GetAuthenticatedClientFactory implements Factory<OkHttpClient> {
  private final ServiceModule module;

  private final Provider<LDSAccountInterceptor> ldsAccountInterceptorProvider;

  private final Provider<LDSAccountAuth> ldsAccountAuthProvider;

  public ServiceModule_GetAuthenticatedClientFactory(
      ServiceModule module,
      Provider<LDSAccountInterceptor> ldsAccountInterceptorProvider,
      Provider<LDSAccountAuth> ldsAccountAuthProvider) {
    this.module = module;
    this.ldsAccountInterceptorProvider = ldsAccountInterceptorProvider;
    this.ldsAccountAuthProvider = ldsAccountAuthProvider;
  }

  @Override
  public OkHttpClient get() {
    return Preconditions.checkNotNull(
        module.getAuthenticatedClient(
            ldsAccountInterceptorProvider.get(), ldsAccountAuthProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<OkHttpClient> create(
      ServiceModule module,
      Provider<LDSAccountInterceptor> ldsAccountInterceptorProvider,
      Provider<LDSAccountAuth> ldsAccountAuthProvider) {
    return new ServiceModule_GetAuthenticatedClientFactory(
        module, ldsAccountInterceptorProvider, ldsAccountAuthProvider);
  }
}
