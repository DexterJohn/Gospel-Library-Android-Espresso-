package org.lds.ldssa.ui.activity;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.model.prefs.Prefs;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ScreenSettingsActivity_MembersInjector
    implements MembersInjector<ScreenSettingsActivity> {
  private final Provider<Prefs> prefsProvider;

  private final Provider<Analytics> analyticsProvider;

  public ScreenSettingsActivity_MembersInjector(
      Provider<Prefs> prefsProvider, Provider<Analytics> analyticsProvider) {
    this.prefsProvider = prefsProvider;
    this.analyticsProvider = analyticsProvider;
  }

  public static MembersInjector<ScreenSettingsActivity> create(
      Provider<Prefs> prefsProvider, Provider<Analytics> analyticsProvider) {
    return new ScreenSettingsActivity_MembersInjector(prefsProvider, analyticsProvider);
  }

  @Override
  public void injectMembers(ScreenSettingsActivity instance) {
    injectPrefs(instance, prefsProvider.get());
    injectAnalytics(instance, analyticsProvider.get());
  }

  public static void injectPrefs(ScreenSettingsActivity instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectAnalytics(ScreenSettingsActivity instance, Analytics analytics) {
    instance.analytics = analytics;
  }
}
