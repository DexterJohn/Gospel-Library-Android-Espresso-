package org.lds.ldssa.inject;

import android.app.Application;
import android.view.WindowManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideWindowManagerFactory implements Factory<WindowManager> {
  private final AppModule module;

  private final Provider<Application> arg0Provider;

  public AppModule_ProvideWindowManagerFactory(
      AppModule module, Provider<Application> arg0Provider) {
    this.module = module;
    this.arg0Provider = arg0Provider;
  }

  @Override
  public WindowManager get() {
    return Preconditions.checkNotNull(
        module.provideWindowManager(arg0Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<WindowManager> create(
      AppModule module, Provider<Application> arg0Provider) {
    return new AppModule_ProvideWindowManagerFactory(module, arg0Provider);
  }
}
