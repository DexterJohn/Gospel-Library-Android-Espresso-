package org.lds.ldssa.model.database.catalog.navigationtrailquery;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.catalog.librarycollection.LibraryCollectionManager;
import org.lds.ldssa.model.database.content.navitem.NavItemManager;
import org.lds.ldssa.util.ContentItemUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NavigationTrailQueryManager_Factory
    implements Factory<NavigationTrailQueryManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<Application> arg1Provider;

  private final Provider<ContentItemUtil> arg2Provider;

  private final Provider<LibraryCollectionManager> arg3Provider;

  private final Provider<NavItemManager> arg4Provider;

  public NavigationTrailQueryManager_Factory(
      Provider<DatabaseManager> arg0Provider,
      Provider<Application> arg1Provider,
      Provider<ContentItemUtil> arg2Provider,
      Provider<LibraryCollectionManager> arg3Provider,
      Provider<NavItemManager> arg4Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
  }

  @Override
  public NavigationTrailQueryManager get() {
    return new NavigationTrailQueryManager(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get());
  }

  public static Factory<NavigationTrailQueryManager> create(
      Provider<DatabaseManager> arg0Provider,
      Provider<Application> arg1Provider,
      Provider<ContentItemUtil> arg2Provider,
      Provider<LibraryCollectionManager> arg3Provider,
      Provider<NavItemManager> arg4Provider) {
    return new NavigationTrailQueryManager_Factory(
        arg0Provider, arg1Provider, arg2Provider, arg3Provider, arg4Provider);
  }
}
