package org.lds.ldssa.model.database.content.relatedcontentitem;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.util.ContentItemUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RelatedContentItemManager_Factory implements Factory<RelatedContentItemManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<ContentItemUtil> arg1Provider;

  public RelatedContentItemManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<ContentItemUtil> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public RelatedContentItemManager get() {
    return new RelatedContentItemManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<RelatedContentItemManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<ContentItemUtil> arg1Provider) {
    return new RelatedContentItemManager_Factory(arg0Provider, arg1Provider);
  }
}
