package org.lds.ldssa.download;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.catalogsource.CatalogSourceManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.content.contentmetadata.ContentMetaDataManager;
import org.lds.ldssa.model.database.gl.downloadeditem.DownloadedItemManager;
import org.lds.ldssa.util.ContentItemUpdateUtil;
import org.lds.ldssa.util.ContentItemUtil;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import org.lds.mobile.util.LdsTimeUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RepairMissingDownloadedItems_Factory
    implements Factory<RepairMissingDownloadedItems> {
  private final Provider<CatalogSourceManager> arg0Provider;

  private final Provider<ContentItemUpdateUtil> arg1Provider;

  private final Provider<ContentItemUtil> arg2Provider;

  private final Provider<ContentMetaDataManager> arg3Provider;

  private final Provider<CoroutineContextProvider> arg4Provider;

  private final Provider<DownloadedItemManager> arg5Provider;

  private final Provider<GLDownloadManager> arg6Provider;

  private final Provider<GLFileUtil> arg7Provider;

  private final Provider<ItemManager> arg8Provider;

  private final Provider<LdsTimeUtil> arg9Provider;

  public RepairMissingDownloadedItems_Factory(
      Provider<CatalogSourceManager> arg0Provider,
      Provider<ContentItemUpdateUtil> arg1Provider,
      Provider<ContentItemUtil> arg2Provider,
      Provider<ContentMetaDataManager> arg3Provider,
      Provider<CoroutineContextProvider> arg4Provider,
      Provider<DownloadedItemManager> arg5Provider,
      Provider<GLDownloadManager> arg6Provider,
      Provider<GLFileUtil> arg7Provider,
      Provider<ItemManager> arg8Provider,
      Provider<LdsTimeUtil> arg9Provider) {
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
  public RepairMissingDownloadedItems get() {
    return new RepairMissingDownloadedItems(
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

  public static Factory<RepairMissingDownloadedItems> create(
      Provider<CatalogSourceManager> arg0Provider,
      Provider<ContentItemUpdateUtil> arg1Provider,
      Provider<ContentItemUtil> arg2Provider,
      Provider<ContentMetaDataManager> arg3Provider,
      Provider<CoroutineContextProvider> arg4Provider,
      Provider<DownloadedItemManager> arg5Provider,
      Provider<GLDownloadManager> arg6Provider,
      Provider<GLFileUtil> arg7Provider,
      Provider<ItemManager> arg8Provider,
      Provider<LdsTimeUtil> arg9Provider) {
    return new RepairMissingDownloadedItems_Factory(
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
