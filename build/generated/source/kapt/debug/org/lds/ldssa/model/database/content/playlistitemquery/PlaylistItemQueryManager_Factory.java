package org.lds.ldssa.model.database.content.playlistitemquery;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.content.author.AuthorManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.ContentItemUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PlaylistItemQueryManager_Factory implements Factory<PlaylistItemQueryManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<ContentItemUtil> arg1Provider;

  private final Provider<ItemManager> arg2Provider;

  private final Provider<AuthorManager> arg3Provider;

  private final Provider<Prefs> arg4Provider;

  public PlaylistItemQueryManager_Factory(
      Provider<DatabaseManager> arg0Provider,
      Provider<ContentItemUtil> arg1Provider,
      Provider<ItemManager> arg2Provider,
      Provider<AuthorManager> arg3Provider,
      Provider<Prefs> arg4Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
  }

  @Override
  public PlaylistItemQueryManager get() {
    return new PlaylistItemQueryManager(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get());
  }

  public static Factory<PlaylistItemQueryManager> create(
      Provider<DatabaseManager> arg0Provider,
      Provider<ContentItemUtil> arg1Provider,
      Provider<ItemManager> arg2Provider,
      Provider<AuthorManager> arg3Provider,
      Provider<Prefs> arg4Provider) {
    return new PlaylistItemQueryManager_Factory(
        arg0Provider, arg1Provider, arg2Provider, arg3Provider, arg4Provider);
  }
}
