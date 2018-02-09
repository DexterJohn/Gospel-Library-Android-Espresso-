package org.lds.ldssa.inject;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldsaccount.LDSAccountAuth;
import org.lds.ldsaccount.LDSAccountInterceptor;
import org.lds.ldsaccount.LDSAccountLogger;
import org.lds.ldsaccount.LDSAccountUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideLDSAccountInterceptorFactory
    implements Factory<LDSAccountInterceptor> {
  private final AppModule module;

  private final Provider<LDSAccountLogger> arg0Provider;

  private final Provider<LDSAccountUtil> arg1Provider;

  private final Provider<LDSAccountAuth> arg2Provider;

  public AppModule_ProvideLDSAccountInterceptorFactory(
      AppModule module,
      Provider<LDSAccountLogger> arg0Provider,
      Provider<LDSAccountUtil> arg1Provider,
      Provider<LDSAccountAuth> arg2Provider) {
    this.module = module;
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public LDSAccountInterceptor get() {
    return Preconditions.checkNotNull(
        module.provideLDSAccountInterceptor(
            arg0Provider.get(), arg1Provider.get(), arg2Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<LDSAccountInterceptor> create(
      AppModule module,
      Provider<LDSAccountLogger> arg0Provider,
      Provider<LDSAccountUtil> arg1Provider,
      Provider<LDSAccountAuth> arg2Provider) {
    return new AppModule_ProvideLDSAccountInterceptorFactory(
        module, arg0Provider, arg1Provider, arg2Provider);
  }
}
