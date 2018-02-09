package org.lds.ldssa.model.prefs;

import android.app.Application;
import android.content.SharedPreferences;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class Prefs_Factory implements Factory<Prefs> {
  private final Provider<Application> applicationProvider;

  private final Provider<SharedPreferences> defaultPreferencesProvider;

  private final Provider<SharedPreferences> backedUpPreferencesProvider;

  public Prefs_Factory(
      Provider<Application> applicationProvider,
      Provider<SharedPreferences> defaultPreferencesProvider,
      Provider<SharedPreferences> backedUpPreferencesProvider) {
    this.applicationProvider = applicationProvider;
    this.defaultPreferencesProvider = defaultPreferencesProvider;
    this.backedUpPreferencesProvider = backedUpPreferencesProvider;
  }

  @Override
  public Prefs get() {
    return new Prefs(
        applicationProvider.get(),
        defaultPreferencesProvider.get(),
        backedUpPreferencesProvider.get());
  }

  public static Factory<Prefs> create(
      Provider<Application> applicationProvider,
      Provider<SharedPreferences> defaultPreferencesProvider,
      Provider<SharedPreferences> backedUpPreferencesProvider) {
    return new Prefs_Factory(
        applicationProvider, defaultPreferencesProvider, backedUpPreferencesProvider);
  }
}
