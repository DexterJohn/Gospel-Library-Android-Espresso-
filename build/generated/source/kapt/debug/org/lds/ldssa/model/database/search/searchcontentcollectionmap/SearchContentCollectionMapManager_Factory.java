package org.lds.ldssa.model.database.search.searchcontentcollectionmap;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchContentCollectionMapManager_Factory
    implements Factory<SearchContentCollectionMapManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public SearchContentCollectionMapManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public SearchContentCollectionMapManager get() {
    return new SearchContentCollectionMapManager(arg0Provider.get());
  }

  public static Factory<SearchContentCollectionMapManager> create(
      Provider<DatabaseManager> arg0Provider) {
    return new SearchContentCollectionMapManager_Factory(arg0Provider);
  }
}
