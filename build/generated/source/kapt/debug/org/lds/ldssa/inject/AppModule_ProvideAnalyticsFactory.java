package org.lds.ldssa.inject;

import android.app.Application;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AppModule_ProvideAnalyticsFactory implements Factory<Analytics> {
  private final AppModule module;

  private final Provider<Application> arg0Provider;

  public AppModule_ProvideAnalyticsFactory(AppModule module, Provider<Application> arg0Provider) {
    this.module = module;
    this.arg0Provider = arg0Provider;
  }

  @Override
  public Analytics get() {
    return Preconditions.checkNotNull(
        module.provideAnalytics(arg0Provider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<Analytics> create(AppModule module, Provider<Application> arg0Provider) {
    return new AppModule_ProvideAnalyticsFactory(module, arg0Provider);
  }
}
