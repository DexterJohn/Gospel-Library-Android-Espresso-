package org.lds.ldssa.model.database.content.navcollectionindexentry;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.util.ContentItemUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NavCollectionIndexEntryManager_Factory
    implements Factory<NavCollectionIndexEntryManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<ContentItemUtil> arg1Provider;

  public NavCollectionIndexEntryManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<ContentItemUtil> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public NavCollectionIndexEntryManager get() {
    return new NavCollectionIndexEntryManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<NavCollectionIndexEntryManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<ContentItemUtil> arg1Provider) {
    return new NavCollectionIndexEntryManager_Factory(arg0Provider, arg1Provider);
  }
}
