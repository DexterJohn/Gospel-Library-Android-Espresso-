package org.lds.ldssa.model.database.userdata.customcollectionitem;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.util.UserdataDbUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CustomCollectionItemManager_Factory
    implements Factory<CustomCollectionItemManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<UserdataDbUtil> arg1Provider;

  private final Provider<ItemManager> arg2Provider;

  public CustomCollectionItemManager_Factory(
      Provider<DatabaseManager> arg0Provider,
      Provider<UserdataDbUtil> arg1Provider,
      Provider<ItemManager> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public CustomCollectionItemManager get() {
    return new CustomCollectionItemManager(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<CustomCollectionItemManager> create(
      Provider<DatabaseManager> arg0Provider,
      Provider<UserdataDbUtil> arg1Provider,
      Provider<ItemManager> arg2Provider) {
    return new CustomCollectionItemManager_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}
