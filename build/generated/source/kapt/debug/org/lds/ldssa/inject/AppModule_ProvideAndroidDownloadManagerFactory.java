package org.lds.ldssa.inject;

import android.app.Application;
import android.app.DownloadManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideAndroidDownloadManagerFactory
    implements Factory<DownloadManager> {
  private final AppModule module;

  private final Provider<Application> arg0Provider;

  public AppModule_ProvideAndroidDownloadManagerFactory(
      AppModule module, Provider<Application> arg0Provider) {
    this.module = module;
    this.arg0Provider = arg0Provider;
  }

  @Override
  public DownloadManager get() {
    return Preconditions.checkNotNull(
        module.provideAndroidDownloadManager(arg0Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<DownloadManager> create(
      AppModule module, Provider<Application> arg0Provider) {
    return new AppModule_ProvideAndroidDownloadManagerFactory(module, arg0Provider);
  }
}
