package org.lds.ldssa.model.database.userdata.customcollection;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.userdata.customcollectionitem.CustomCollectionItemManager;
import org.lds.ldssa.util.UserdataDbUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CustomCollectionManager_Factory implements Factory<CustomCollectionManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<UserdataDbUtil> arg1Provider;

  private final Provider<CustomCollectionItemManager> arg2Provider;

  public CustomCollectionManager_Factory(
      Provider<DatabaseManager> arg0Provider,
      Provider<UserdataDbUtil> arg1Provider,
      Provider<CustomCollectionItemManager> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public CustomCollectionManager get() {
    return new CustomCollectionManager(arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<CustomCollectionManager> create(
      Provider<DatabaseManager> arg0Provider,
      Provider<UserdataDbUtil> arg1Provider,
      Provider<CustomCollectionItemManager> arg2Provider) {
    return new CustomCollectionManager_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}
