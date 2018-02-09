package org.lds.ldssa.inject;

import android.app.Application;
import android.app.NotificationManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideNotificationManagerFactory
    implements Factory<NotificationManager> {
  private final AppModule module;

  private final Provider<Application> arg0Provider;

  public AppModule_ProvideNotificationManagerFactory(
      AppModule module, Provider<Application> arg0Provider) {
    this.module = module;
    this.arg0Provider = arg0Provider;
  }

  @Override
  public NotificationManager get() {
    return Preconditions.checkNotNull(
        module.provideNotificationManager(arg0Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<NotificationManager> create(
      AppModule module, Provider<Application> arg0Provider) {
    return new AppModule_ProvideNotificationManagerFactory(module, arg0Provider);
  }
}
