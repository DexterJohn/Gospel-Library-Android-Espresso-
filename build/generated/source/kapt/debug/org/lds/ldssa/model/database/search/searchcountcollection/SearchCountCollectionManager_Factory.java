package org.lds.ldssa.model.database.search.searchcountcollection;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchCountCollectionManager_Factory
    implements Factory<SearchCountCollectionManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public SearchCountCollectionManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public SearchCountCollectionManager get() {
    return new SearchCountCollectionManager(arg0Provider.get());
  }

  public static Factory<SearchCountCollectionManager> create(
      Provider<DatabaseManager> arg0Provider) {
    return new SearchCountCollectionManager_Factory(arg0Provider);
  }
}
