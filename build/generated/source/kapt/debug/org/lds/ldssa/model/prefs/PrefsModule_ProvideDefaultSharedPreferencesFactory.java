package org.lds.ldssa.model.prefs;

import android.app.Application;
import android.content.SharedPreferences;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PrefsModule_ProvideDefaultSharedPreferencesFactory
    implements Factory<SharedPreferences> {
  private final PrefsModule module;

  private final Provider<Application> applicationProvider;

  public PrefsModule_ProvideDefaultSharedPreferencesFactory(
      PrefsModule module, Provider<Application> applicationProvider) {
    this.module = module;
    this.applicationProvider = applicationProvider;
  }

  @Override
  public SharedPreferences get() {
    return Preconditions.checkNotNull(
        module.provideDefaultSharedPreferences(applicationProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<SharedPreferences> create(
      PrefsModule module, Provider<Application> applicationProvider) {
    return new PrefsModule_ProvideDefaultSharedPreferencesFactory(module, applicationProvider);
  }

  public static SharedPreferences proxyProvideDefaultSharedPreferences(
      PrefsModule instance, Application application) {
    return instance.provideDefaultSharedPreferences(application);
  }
}
