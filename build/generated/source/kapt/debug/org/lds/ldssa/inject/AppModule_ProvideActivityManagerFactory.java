package org.lds.ldssa.inject;

import android.app.ActivityManager;
import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideActivityManagerFactory implements Factory<ActivityManager> {
  private final AppModule module;

  private final Provider<Application> arg0Provider;

  public AppModule_ProvideActivityManagerFactory(
      AppModule module, Provider<Application> arg0Provider) {
    this.module = module;
    this.arg0Provider = arg0Provider;
  }

  @Override
  public ActivityManager get() {
    return Preconditions.checkNotNull(
        module.provideActivityManager(arg0Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<ActivityManager> create(
      AppModule module, Provider<Application> arg0Provider) {
    return new AppModule_ProvideActivityManagerFactory(module, arg0Provider);
  }
}
