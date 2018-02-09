package org.lds.ldssa.analytics;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.prefs.Prefs;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DefaultAnalytics_MembersInjector implements MembersInjector<DefaultAnalytics> {
  private final Provider<Prefs> prefsProvider;

  public DefaultAnalytics_MembersInjector(Provider<Prefs> prefsProvider) {
    this.prefsProvider = prefsProvider;
  }

  public static MembersInjector<DefaultAnalytics> create(Provider<Prefs> prefsProvider) {
    return new DefaultAnalytics_MembersInjector(prefsProvider);
  }

  @Override
  public void injectMembers(DefaultAnalytics instance) {
    injectPrefs(instance, prefsProvider.get());
  }

  public static void injectPrefs(DefaultAnalytics instance, Prefs prefs) {
    instance.prefs = prefs;
  }
}
