package org.lds.ldssa.model.database.search.searchpreviewsubitem;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchPreviewSubitemManager_Factory
    implements Factory<SearchPreviewSubitemManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public SearchPreviewSubitemManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public SearchPreviewSubitemManager get() {
    return new SearchPreviewSubitemManager(arg0Provider.get());
  }

  public static Factory<SearchPreviewSubitemManager> create(
      Provider<DatabaseManager> arg0Provider) {
    return new SearchPreviewSubitemManager_Factory(arg0Provider);
  }
}
