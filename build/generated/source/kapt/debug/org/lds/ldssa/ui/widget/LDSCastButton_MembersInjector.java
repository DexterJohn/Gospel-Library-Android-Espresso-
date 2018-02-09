package org.lds.ldssa.ui.widget;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.mobile.media.cast.CastManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LDSCastButton_MembersInjector implements MembersInjector<LDSCastButton> {
  private final Provider<CastManager> castManagerProvider;

  public LDSCastButton_MembersInjector(Provider<CastManager> castManagerProvider) {
    this.castManagerProvider = castManagerProvider;
  }

  public static MembersInjector<LDSCastButton> create(Provider<CastManager> castManagerProvider) {
    return new LDSCastButton_MembersInjector(castManagerProvider);
  }

  @Override
  public void injectMembers(LDSCastButton instance) {
    injectCastManager(instance, castManagerProvider.get());
  }

  public static void injectCastManager(LDSCastButton instance, CastManager castManager) {
    instance.castManager = castManager;
  }
}
