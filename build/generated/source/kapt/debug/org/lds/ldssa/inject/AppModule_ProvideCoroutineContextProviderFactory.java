package org.lds.ldssa.inject;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideCoroutineContextProviderFactory
    implements Factory<CoroutineContextProvider> {
  private final AppModule module;

  public AppModule_ProvideCoroutineContextProviderFactory(AppModule module) {
    this.module = module;
  }

  @Override
  public CoroutineContextProvider get() {
    return Preconditions.checkNotNull(
        module.provideCoroutineContextProvider(),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<CoroutineContextProvider> create(AppModule module) {
    return new AppModule_ProvideCoroutineContextProviderFactory(module);
  }
}
