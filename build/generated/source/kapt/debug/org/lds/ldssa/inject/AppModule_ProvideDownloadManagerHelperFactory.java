package org.lds.ldssa.inject;

import android.app.DownloadManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.mobile.download.DownloadManagerHelper;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideDownloadManagerHelperFactory
    implements Factory<DownloadManagerHelper> {
  private final AppModule module;

  private final Provider<DownloadManager> arg0Provider;

  public AppModule_ProvideDownloadManagerHelperFactory(
      AppModule module, Provider<DownloadManager> arg0Provider) {
    this.module = module;
    this.arg0Provider = arg0Provider;
  }

  @Override
  public DownloadManagerHelper get() {
    return Preconditions.checkNotNull(
        module.provideDownloadManagerHelper(arg0Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<DownloadManagerHelper> create(
      AppModule module, Provider<DownloadManager> arg0Provider) {
    return new AppModule_ProvideDownloadManagerHelperFactory(module, arg0Provider);
  }
}
