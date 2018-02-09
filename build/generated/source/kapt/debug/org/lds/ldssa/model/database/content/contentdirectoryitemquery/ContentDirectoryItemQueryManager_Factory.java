package org.lds.ldssa.model.database.content.contentdirectoryitemquery;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.content.navcollection.NavCollectionManager;
import org.lds.ldssa.util.ContentItemUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ContentDirectoryItemQueryManager_Factory
    implements Factory<ContentDirectoryItemQueryManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<NavCollectionManager> arg1Provider;

  private final Provider<ContentItemUtil> arg2Provider;

  public ContentDirectoryItemQueryManager_Factory(
      Provider<DatabaseManager> arg0Provider,
      Provider<NavCollectionManager> arg1Provider,
      Provider<ContentItemUtil> arg2Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
  }

  @Override
  public ContentDirectoryItemQueryManager get() {
    return new ContentDirectoryItemQueryManager(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get());
  }

  public static Factory<ContentDirectoryItemQueryManager> create(
      Provider<DatabaseManager> arg0Provider,
      Provider<NavCollectionManager> arg1Provider,
      Provider<ContentItemUtil> arg2Provider) {
    return new ContentDirectoryItemQueryManager_Factory(arg0Provider, arg1Provider, arg2Provider);
  }
}
