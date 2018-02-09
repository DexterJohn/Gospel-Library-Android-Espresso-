package org.lds.ldssa.model.database.content.subitemauthor;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SubitemAuthorManager_Factory implements Factory<SubitemAuthorManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public SubitemAuthorManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public SubitemAuthorManager get() {
    return new SubitemAuthorManager(arg0Provider.get());
  }

  public static Factory<SubitemAuthorManager> create(Provider<DatabaseManager> arg0Provider) {
    return new SubitemAuthorManager_Factory(arg0Provider);
  }
}
