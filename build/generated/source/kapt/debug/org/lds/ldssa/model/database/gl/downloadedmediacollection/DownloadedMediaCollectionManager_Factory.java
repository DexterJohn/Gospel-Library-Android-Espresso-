package org.lds.ldssa.model.database.gl.downloadedmediacollection;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DownloadedMediaCollectionManager_Factory
    implements Factory<DownloadedMediaCollectionManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public DownloadedMediaCollectionManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public DownloadedMediaCollectionManager get() {
    return new DownloadedMediaCollectionManager(arg0Provider.get());
  }

  public static Factory<DownloadedMediaCollectionManager> create(
      Provider<DatabaseManager> arg0Provider) {
    return new DownloadedMediaCollectionManager_Factory(arg0Provider);
  }
}
