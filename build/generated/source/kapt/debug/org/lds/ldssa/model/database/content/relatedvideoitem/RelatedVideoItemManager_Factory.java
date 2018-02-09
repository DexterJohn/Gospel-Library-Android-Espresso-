package org.lds.ldssa.model.database.content.relatedvideoitem;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.util.ContentItemUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RelatedVideoItemManager_Factory implements Factory<RelatedVideoItemManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<ContentItemUtil> arg1Provider;

  public RelatedVideoItemManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<ContentItemUtil> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public RelatedVideoItemManager get() {
    return new RelatedVideoItemManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<RelatedVideoItemManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<ContentItemUtil> arg1Provider) {
    return new RelatedVideoItemManager_Factory(arg0Provider, arg1Provider);
  }
}
