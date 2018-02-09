package org.lds.ldssa.model.database.gl.downloadedmedia;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DownloadedMediaManager_Factory implements Factory<DownloadedMediaManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public DownloadedMediaManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public DownloadedMediaManager get() {
    return new DownloadedMediaManager(arg0Provider.get());
  }

  public static Factory<DownloadedMediaManager> create(Provider<DatabaseManager> arg0Provider) {
    return new DownloadedMediaManager_Factory(arg0Provider);
  }
}
