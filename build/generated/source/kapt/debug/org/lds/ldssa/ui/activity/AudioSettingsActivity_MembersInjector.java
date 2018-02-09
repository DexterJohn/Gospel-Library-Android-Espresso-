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
public final class AudioSettingsActivity_MembersInjector
    implements MembersInjector<AudioSettingsActivity> {
  private final Provider<Prefs> prefsProvider;

  private final Provider<Analytics> analyticsProvider;

  public AudioSettingsActivity_MembersInjector(
      Provider<Prefs> prefsProvider, Provider<Analytics> analyticsProvider) {
    this.prefsProvider = prefsProvider;
    this.analyticsProvider = analyticsProvider;
  }

  public static MembersInjector<AudioSettingsActivity> create(
      Provider<Prefs> prefsProvider, Provider<Analytics> analyticsProvider) {
    return new AudioSettingsActivity_MembersInjector(prefsProvider, analyticsProvider);
  }

  @Override
  public void injectMembers(AudioSettingsActivity instance) {
    injectPrefs(instance, prefsProvider.get());
    injectAnalytics(instance, analyticsProvider.get());
  }

  public static void injectPrefs(AudioSettingsActivity instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectAnalytics(AudioSettingsActivity instance, Analytics analytics) {
    instance.analytics = analytics;
  }
}
