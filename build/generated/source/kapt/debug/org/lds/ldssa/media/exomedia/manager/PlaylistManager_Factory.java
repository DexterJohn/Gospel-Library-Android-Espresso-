package org.lds.ldssa.media.exomedia.manager;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.mobile.media.cast.CastManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PlaylistManager_Factory implements Factory<PlaylistManager> {
  private final Provider<Application> arg0Provider;

  private final Provider<CastManager> arg1Provider;

  public PlaylistManager_Factory(
      Provider<Application> arg0Provider, Provider<CastManager> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public PlaylistManager get() {
    return new PlaylistManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<PlaylistManager> create(
      Provider<Application> arg0Provider, Provider<CastManager> arg1Provider) {
    return new PlaylistManager_Factory(arg0Provider, arg1Provider);
  }
}
