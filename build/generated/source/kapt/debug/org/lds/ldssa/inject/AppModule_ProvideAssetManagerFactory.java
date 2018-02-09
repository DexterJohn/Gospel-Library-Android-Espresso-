package org.lds.ldssa.inject;

import android.app.Application;
import android.content.res.AssetManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideAssetManagerFactory implements Factory<AssetManager> {
  private final AppModule module;

  private final Provider<Application> arg0Provider;

  public AppModule_ProvideAssetManagerFactory(
      AppModule module, Provider<Application> arg0Provider) {
    this.module = module;
    this.arg0Provider = arg0Provider;
  }

  @Override
  public AssetManager get() {
    return Preconditions.checkNotNull(
        module.provideAssetManager(arg0Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<AssetManager> create(AppModule module, Provider<Application> arg0Provider) {
    return new AppModule_ProvideAssetManagerFactory(module, arg0Provider);
  }
}
