package org.lds.ldssa.model.database.search.searchcollection;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchCollectionManager_Factory implements Factory<SearchCollectionManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public SearchCollectionManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public SearchCollectionManager get() {
    return new SearchCollectionManager(arg0Provider.get());
  }

  public static Factory<SearchCollectionManager> create(Provider<DatabaseManager> arg0Provider) {
    return new SearchCollectionManager_Factory(arg0Provider);
  }
}
