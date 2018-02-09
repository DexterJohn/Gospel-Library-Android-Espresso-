package org.lds.ldssa.download;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldsaccount.LDSAccountUtil;
import org.lds.ldssa.model.database.catalog.catalogmetadata.CatalogMetaDataManager;
import org.lds.ldssa.model.database.gl.downloadqueueitem.DownloadQueueItemManager;
import org.lds.ldssa.model.database.gl.rolecatalog.RoleCatalogManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.CatalogUtil;
import org.lds.ldssa.util.GLFileUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CatalogDownloader_Factory implements Factory<CatalogDownloader> {
  private final Provider<Application> arg0Provider;

  private final Provider<CatalogUtil> arg1Provider;

  private final Provider<GLFileUtil> arg2Provider;

  private final Provider<RoleCatalogManager> arg3Provider;

  private final Provider<Prefs> arg4Provider;

  private final Provider<LDSAccountUtil> arg5Provider;

  private final Provider<DownloadQueueItemManager> arg6Provider;

  private final Provider<GLDownloadManager> arg7Provider;

  private final Provider<CatalogMetaDataManager> arg8Provider;

  public CatalogDownloader_Factory(
      Provider<Application> arg0Provider,
      Provider<CatalogUtil> arg1Provider,
      Provider<GLFileUtil> arg2Provider,
      Provider<RoleCatalogManager> arg3Provider,
      Provider<Prefs> arg4Provider,
      Provider<LDSAccountUtil> arg5Provider,
      Provider<DownloadQueueItemManager> arg6Provider,
      Provider<GLDownloadManager> arg7Provider,
      Provider<CatalogMetaDataManager> arg8Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
    this.arg5Provider = arg5Provider;
    this.arg6Provider = arg6Provider;
    this.arg7Provider = arg7Provider;
    this.arg8Provider = arg8Provider;
  }

  @Override
  public CatalogDownloader get() {
    return new CatalogDownloader(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get(),
        arg6Provider.get(),
        arg7Provider.get(),
        arg8Provider.get());
  }

  public static Factory<CatalogDownloader> create(
      Provider<Application> arg0Provider,
      Provider<CatalogUtil> arg1Provider,
      Provider<GLFileUtil> arg2Provider,
      Provider<RoleCatalogManager> arg3Provider,
      Provider<Prefs> arg4Provider,
      Provider<LDSAccountUtil> arg5Provider,
      Provider<DownloadQueueItemManager> arg6Provider,
      Provider<GLDownloadManager> arg7Provider,
      Provider<CatalogMetaDataManager> arg8Provider) {
    return new CatalogDownloader_Factory(
        arg0Provider,
        arg1Provider,
        arg2Provider,
        arg3Provider,
        arg4Provider,
        arg5Provider,
        arg6Provider,
        arg7Provider,
        arg8Provider);
  }
}
