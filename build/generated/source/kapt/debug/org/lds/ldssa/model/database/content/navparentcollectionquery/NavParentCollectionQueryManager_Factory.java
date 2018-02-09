package org.lds.ldssa.model.database.content.navparentcollectionquery;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NavParentCollectionQueryManager_Factory
    implements Factory<NavParentCollectionQueryManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public NavParentCollectionQueryManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public NavParentCollectionQueryManager get() {
    return new NavParentCollectionQueryManager(arg0Provider.get());
  }

  public static Factory<NavParentCollectionQueryManager> create(
      Provider<DatabaseManager> arg0Provider) {
    return new NavParentCollectionQueryManager_Factory(arg0Provider);
  }
}
