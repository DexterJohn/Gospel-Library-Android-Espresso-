package org.lds.ldssa.model.database.search.searchhistory;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SearchHistoryManager_Factory implements Factory<SearchHistoryManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public SearchHistoryManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public SearchHistoryManager get() {
    return new SearchHistoryManager(arg0Provider.get());
  }

  public static Factory<SearchHistoryManager> create(Provider<DatabaseManager> arg0Provider) {
    return new SearchHistoryManager_Factory(arg0Provider);
  }
}
