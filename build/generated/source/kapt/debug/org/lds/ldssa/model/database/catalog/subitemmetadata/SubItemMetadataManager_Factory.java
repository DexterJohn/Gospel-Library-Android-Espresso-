package org.lds.ldssa.model.database.catalog.subitemmetadata;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SubItemMetadataManager_Factory implements Factory<SubItemMetadataManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public SubItemMetadataManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public SubItemMetadataManager get() {
    return new SubItemMetadataManager(arg0Provider.get());
  }

  public static Factory<SubItemMetadataManager> create(Provider<DatabaseManager> arg0Provider) {
    return new SubItemMetadataManager_Factory(arg0Provider);
  }
}
