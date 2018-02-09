package org.lds.ldssa.download;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.content.navitem.NavItemManager;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AllAudioDownloader_Factory implements Factory<AllAudioDownloader> {
  private final Provider<Bus> arg0Provider;

  private final Provider<NavItemManager> arg1Provider;

  public AllAudioDownloader_Factory(
      Provider<Bus> arg0Provider, Provider<NavItemManager> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public AllAudioDownloader get() {
    return new AllAudioDownloader(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<AllAudioDownloader> create(
      Provider<Bus> arg0Provider, Provider<NavItemManager> arg1Provider) {
    return new AllAudioDownloader_Factory(arg0Provider, arg1Provider);
  }
}
