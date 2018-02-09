package org.lds.ldssa.ui.loader;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.screen.ScreenManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ScreensLoader_MembersInjector implements MembersInjector<ScreensLoader> {
  private final Provider<ScreenManager> screenManagerProvider;

  public ScreensLoader_MembersInjector(Provider<ScreenManager> screenManagerProvider) {
    this.screenManagerProvider = screenManagerProvider;
  }

  public static MembersInjector<ScreensLoader> create(
      Provider<ScreenManager> screenManagerProvider) {
    return new ScreensLoader_MembersInjector(screenManagerProvider);
  }

  @Override
  public void injectMembers(ScreensLoader instance) {
    injectScreenManager(instance, screenManagerProvider.get());
  }

  public static void injectScreenManager(ScreensLoader instance, ScreenManager screenManager) {
    instance.screenManager = screenManager;
  }
}
