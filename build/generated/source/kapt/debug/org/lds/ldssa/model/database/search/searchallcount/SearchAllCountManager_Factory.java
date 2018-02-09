package org.lds.ldssa.model.database.search.searchallcount;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchAllCountManager_Factory implements Factory<SearchAllCountManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public SearchAllCountManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public SearchAllCountManager get() {
    return new SearchAllCountManager(arg0Provider.get());
  }

  public static Factory<SearchAllCountManager> create(Provider<DatabaseManager> arg0Provider) {
    return new SearchAllCountManager_Factory(arg0Provider);
  }
}
