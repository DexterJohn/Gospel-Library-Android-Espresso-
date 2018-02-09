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
public final class PrefsModule_ProvideBackedUpSharedPreferencesFactory
    implements Factory<SharedPreferences> {
  private final PrefsModule module;

  private final Provider<Application> applicationProvider;

  public PrefsModule_ProvideBackedUpSharedPreferencesFactory(
      PrefsModule module, Provider<Application> applicationProvider) {
    this.module = module;
    this.applicationProvider = applicationProvider;
  }

  @Override
  public SharedPreferences get() {
    return Preconditions.checkNotNull(
        module.provideBackedUpSharedPreferences(applicationProvider.get()),
        "Cannot return null from a non-@Nullable @Provides method");
  }

  public static Factory<SharedPreferences> create(
      PrefsModule module, Provider<Application> applicationProvider) {
    return new PrefsModule_ProvideBackedUpSharedPreferencesFactory(module, applicationProvider);
  }

  public static SharedPreferences proxyProvideBackedUpSharedPreferences(
      PrefsModule instance, Application application) {
    return instance.provideBackedUpSharedPreferences(application);
  }
}
