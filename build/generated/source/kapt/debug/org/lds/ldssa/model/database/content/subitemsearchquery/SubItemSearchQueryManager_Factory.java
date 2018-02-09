package org.lds.ldssa.model.database.content.subitemsearchquery;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.content.allsubitemsinnavcollectionquery.AllSubItemsInNavCollectionQueryManager;
import org.lds.ldssa.search.SearchUtil;
import org.lds.ldssa.util.ContentItemUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SubItemSearchQueryManager_Factory implements Factory<SubItemSearchQueryManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<ContentItemUtil> arg1Provider;

  private final Provider<SearchUtil> arg2Provider;

  private final Provider<AllSubItemsInNavCollectionQueryManager> arg3Provider;

  public SubItemSearchQueryManager_Factory(
      Provider<DatabaseManager> arg0Provider,
      Provider<ContentItemUtil> arg1Provider,
      Provider<SearchUtil> arg2Provider,
      Provider<AllSubItemsInNavCollectionQueryManager> arg3Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
  }

  @Override
  public SubItemSearchQueryManager get() {
    return new SubItemSearchQueryManager(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get(), arg3Provider.get());
  }

  public static Factory<SubItemSearchQueryManager> create(
      Provider<DatabaseManager> arg0Provider,
      Provider<ContentItemUtil> arg1Provider,
      Provider<SearchUtil> arg2Provider,
      Provider<AllSubItemsInNavCollectionQueryManager> arg3Provider) {
    return new SubItemSearchQueryManager_Factory(
        arg0Provider, arg1Provider, arg2Provider, arg3Provider);
  }
}
