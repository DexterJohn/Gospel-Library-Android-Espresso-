package org.lds.ldssa.download;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MediaDownloader_Factory implements Factory<MediaDownloader> {
  private final Provider<SubItemManager> arg0Provider;

  public MediaDownloader_Factory(Provider<SubItemManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public MediaDownloader get() {
    return new MediaDownloader(arg0Provider.get());
  }

  public static Factory<MediaDownloader> create(Provider<SubItemManager> arg0Provider) {
    return new MediaDownloader_Factory(arg0Provider);
  }
}
