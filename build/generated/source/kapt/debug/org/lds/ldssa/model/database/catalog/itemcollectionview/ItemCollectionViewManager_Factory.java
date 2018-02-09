package org.lds.ldssa.model.database.catalog.itemcollectionview;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ItemCollectionViewManager_Factory implements Factory<ItemCollectionViewManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public ItemCollectionViewManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public ItemCollectionViewManager get() {
    return new ItemCollectionViewManager(arg0Provider.get());
  }

  public static Factory<ItemCollectionViewManager> create(Provider<DatabaseManager> arg0Provider) {
    return new ItemCollectionViewManager_Factory(arg0Provider);
  }
}
