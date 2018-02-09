package org.lds.ldssa.model.database.catalog.itemcategory;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ItemCategoryManager_Factory implements Factory<ItemCategoryManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public ItemCategoryManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public ItemCategoryManager get() {
    return new ItemCategoryManager(arg0Provider.get());
  }

  public static Factory<ItemCategoryManager> create(Provider<DatabaseManager> arg0Provider) {
    return new ItemCategoryManager_Factory(arg0Provider);
  }
}
