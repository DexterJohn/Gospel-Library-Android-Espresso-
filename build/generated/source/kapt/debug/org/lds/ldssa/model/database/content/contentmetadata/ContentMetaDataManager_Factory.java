package org.lds.ldssa.model.database.content.contentmetadata;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.util.ContentItemUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ContentMetaDataManager_Factory implements Factory<ContentMetaDataManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<ContentItemUtil> arg1Provider;

  public ContentMetaDataManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<ContentItemUtil> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public ContentMetaDataManager get() {
    return new ContentMetaDataManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<ContentMetaDataManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<ContentItemUtil> arg1Provider) {
    return new ContentMetaDataManager_Factory(arg0Provider, arg1Provider);
  }
}
