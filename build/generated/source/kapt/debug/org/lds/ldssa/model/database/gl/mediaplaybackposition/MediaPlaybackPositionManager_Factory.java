package org.lds.ldssa.model.database.gl.mediaplaybackposition;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MediaPlaybackPositionManager_Factory
    implements Factory<MediaPlaybackPositionManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public MediaPlaybackPositionManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public MediaPlaybackPositionManager get() {
    return new MediaPlaybackPositionManager(arg0Provider.get());
  }

  public static Factory<MediaPlaybackPositionManager> create(
      Provider<DatabaseManager> arg0Provider) {
    return new MediaPlaybackPositionManager_Factory(arg0Provider);
  }
}
