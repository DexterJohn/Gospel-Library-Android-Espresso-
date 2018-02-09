package org.lds.ldssa.ux.content.directory;

import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQueryManager;
import org.lds.ldssa.model.database.content.contentdirectoryitemquery.ContentDirectoryItemQueryManager;
import org.lds.ldssa.model.database.content.navcollection.NavCollectionManager;
import org.lds.ldssa.model.database.content.navcollectionindexentry.NavCollectionIndexEntryManager;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ContentDirectoryViewModel_Factory implements Factory<ContentDirectoryViewModel> {
  private final Provider<Gson> arg0Provider;

  private final Provider<ContentDirectoryItemQueryManager> arg1Provider;

  private final Provider<NavCollectionManager> arg2Provider;

  private final Provider<NavCollectionIndexEntryManager> arg3Provider;

  private final Provider<ItemManager> arg4Provider;

  private final Provider<ScreenUtil> arg5Provider;

  private final Provider<NavigationTrailQueryManager> arg6Provider;

  private final Provider<CoroutineContextProvider> arg7Provider;

  public ContentDirectoryViewModel_Factory(
      Provider<Gson> arg0Provider,
      Provider<ContentDirectoryItemQueryManager> arg1Provider,
      Provider<NavCollectionManager> arg2Provider,
      Provider<NavCollectionIndexEntryManager> arg3Provider,
      Provider<ItemManager> arg4Provider,
      Provider<ScreenUtil> arg5Provider,
      Provider<NavigationTrailQueryManager> arg6Provider,
      Provider<CoroutineContextProvider> arg7Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
    this.arg5Provider = arg5Provider;
    this.arg6Provider = arg6Provider;
    this.arg7Provider = arg7Provider;
  }

  @Override
  public ContentDirectoryViewModel get() {
    return new ContentDirectoryViewModel(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get(),
        arg6Provider.get(),
        arg7Provider.get());
  }

  public static Factory<ContentDirectoryViewModel> create(
      Provider<Gson> arg0Provider,
      Provider<ContentDirectoryItemQueryManager> arg1Provider,
      Provider<NavCollectionManager> arg2Provider,
      Provider<NavCollectionIndexEntryManager> arg3Provider,
      Provider<ItemManager> arg4Provider,
      Provider<ScreenUtil> arg5Provider,
      Provider<NavigationTrailQueryManager> arg6Provider,
      Provider<CoroutineContextProvider> arg7Provider) {
    return new ContentDirectoryViewModel_Factory(
        arg0Provider,
        arg1Provider,
        arg2Provider,
        arg3Provider,
        arg4Provider,
        arg5Provider,
        arg6Provider,
        arg7Provider);
  }
}
