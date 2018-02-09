package org.lds.ldssa.ux.catalog;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.catalogitemquery.CatalogItemQueryManager;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.ldssa.model.database.tips.tip.TipManager;
import org.lds.ldssa.model.database.userdata.customcollection.CustomCollectionManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.LanguageUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CatalogDirectoryViewModel_Factory implements Factory<CatalogDirectoryViewModel> {
  private final Provider<Application> arg0Provider;

  private final Provider<TipManager> arg1Provider;

  private final Provider<LanguageUtil> arg2Provider;

  private final Provider<CatalogItemQueryManager> arg3Provider;

  private final Provider<LanguageManager> arg4Provider;

  private final Provider<Prefs> arg5Provider;

  private final Provider<ScreenUtil> arg6Provider;

  private final Provider<DownloadedItemManager> arg7Provider;

  private final Provider<CustomCollectionManager> arg8Provider;

  private final Provider<CoroutineContextProvider> arg9Provider;

  public CatalogDirectoryViewModel_Factory(
      Provider<Application> arg0Provider,
      Provider<TipManager> arg1Provider,
      Provider<LanguageUtil> arg2Provider,
      Provider<CatalogItemQueryManager> arg3Provider,
      Provider<LanguageManager> arg4Provider,
      Provider<Prefs> arg5Provider,
      Provider<ScreenUtil> arg6Provider,
      Provider<DownloadedItemManager> arg7Provider,
      Provider<CustomCollectionManager> arg8Provider,
      Provider<CoroutineContextProvider> arg9Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
    this.arg5Provider = arg5Provider;
    this.arg6Provider = arg6Provider;
    this.arg7Provider = arg7Provider;
    this.arg8Provider = arg8Provider;
    this.arg9Provider = arg9Provider;
  }

  @Override
  public CatalogDirectoryViewModel get() {
    return new CatalogDirectoryViewModel(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get(),
        arg6Provider.get(),
        arg7Provider.get(),
        arg8Provider.get(),
        arg9Provider.get());
  }

  public static Factory<CatalogDirectoryViewModel> create(
      Provider<Application> arg0Provider,
      Provider<TipManager> arg1Provider,
      Provider<LanguageUtil> arg2Provider,
      Provider<CatalogItemQueryManager> arg3Provider,
      Provider<LanguageManager> arg4Provider,
      Provider<Prefs> arg5Provider,
      Provider<ScreenUtil> arg6Provider,
      Provider<DownloadedItemManager> arg7Provider,
      Provider<CustomCollectionManager> arg8Provider,
      Provider<CoroutineContextProvider> arg9Provider) {
    return new CatalogDirectoryViewModel_Factory(
        arg0Provider,
        arg1Provider,
        arg2Provider,
        arg3Provider,
        arg4Provider,
        arg5Provider,
        arg6Provider,
        arg7Provider,
        arg8Provider,
        arg9Provider);
  }
}
