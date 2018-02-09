package org.lds.ldssa.inject;

import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.mobile.media.cast.CastManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideCastManagerFactory implements Factory<CastManager> {
  private final AppModule module;

  private final Provider<Application> arg0Provider;

  public AppModule_ProvideCastManagerFactory(AppModule module, Provider<Application> arg0Provider) {
    this.module = module;
    this.arg0Provider = arg0Provider;
  }

  @Override
  public CastManager get() {
    return Preconditions.checkNotNull(
        module.provideCastManager(arg0Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<CastManager> create(AppModule module, Provider<Application> arg0Provider) {
    return new AppModule_ProvideCastManagerFactory(module, arg0Provider);
  }
}
