package org.lds.ldssa.download;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.catalogsource.CatalogSourceManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.gl.rolecatalog.RoleCatalogManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.GLFileUtil;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ContentDownloader_Factory implements Factory<ContentDownloader> {
  private final Provider<Bus> arg0Provider;

  private final Provider<Prefs> arg1Provider;

  private final Provider<ItemManager> arg2Provider;

  private final Provider<GLFileUtil> arg3Provider;

  private final Provider<CatalogSourceManager> arg4Provider;

  private final Provider<RoleCatalogManager> arg5Provider;

  public ContentDownloader_Factory(
      Provider<Bus> arg0Provider,
      Provider<Prefs> arg1Provider,
      Provider<ItemManager> arg2Provider,
      Provider<GLFileUtil> arg3Provider,
      Provider<CatalogSourceManager> arg4Provider,
      Provider<RoleCatalogManager> arg5Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
    this.arg5Provider = arg5Provider;
  }

  @Override
  public ContentDownloader get() {
    return new ContentDownloader(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get());
  }

  public static Factory<ContentDownloader> create(
      Provider<Bus> arg0Provider,
      Provider<Prefs> arg1Provider,
      Provider<ItemManager> arg2Provider,
      Provider<GLFileUtil> arg3Provider,
      Provider<CatalogSourceManager> arg4Provider,
      Provider<RoleCatalogManager> arg5Provider) {
    return new ContentDownloader_Factory(
        arg0Provider, arg1Provider, arg2Provider, arg3Provider, arg4Provider, arg5Provider);
  }
}
