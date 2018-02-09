package org.lds.ldssa.ui.widget;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.mobile.media.cast.CastManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MiniPlaybackControls_MembersInjector
    implements MembersInjector<MiniPlaybackControls> {
  private final Provider<CastManager> castManagerProvider;

  public MiniPlaybackControls_MembersInjector(Provider<CastManager> castManagerProvider) {
    this.castManagerProvider = castManagerProvider;
  }

  public static MembersInjector<MiniPlaybackControls> create(
      Provider<CastManager> castManagerProvider) {
    return new MiniPlaybackControls_MembersInjector(castManagerProvider);
  }

  @Override
  public void injectMembers(MiniPlaybackControls instance) {
    injectCastManager(instance, castManagerProvider.get());
  }

  public static void injectCastManager(MiniPlaybackControls instance, CastManager castManager) {
    instance.castManager = castManager;
  }
}
