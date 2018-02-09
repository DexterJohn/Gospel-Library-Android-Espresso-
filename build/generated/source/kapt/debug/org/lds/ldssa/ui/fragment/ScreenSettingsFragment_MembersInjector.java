package org.lds.ldssa.ui.fragment;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.ScreenUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ScreenSettingsFragment_MembersInjector
    implements MembersInjector<ScreenSettingsFragment> {
  private final Provider<Prefs> prefsProvider;

  private final Provider<ScreenUtil> screenUtilProvider;

  public ScreenSettingsFragment_MembersInjector(
      Provider<Prefs> prefsProvider, Provider<ScreenUtil> screenUtilProvider) {
    this.prefsProvider = prefsProvider;
    this.screenUtilProvider = screenUtilProvider;
  }

  public static MembersInjector<ScreenSettingsFragment> create(
      Provider<Prefs> prefsProvider, Provider<ScreenUtil> screenUtilProvider) {
    return new ScreenSettingsFragment_MembersInjector(prefsProvider, screenUtilProvider);
  }

  @Override
  public void injectMembers(ScreenSettingsFragment instance) {
    injectPrefs(instance, prefsProvider.get());
    injectScreenUtil(instance, screenUtilProvider.get());
  }

  public static void injectPrefs(ScreenSettingsFragment instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectScreenUtil(ScreenSettingsFragment instance, ScreenUtil screenUtil) {
    instance.screenUtil = screenUtil;
  }
}
