package org.lds.ldssa.ui.widget;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.prefs.Prefs;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LDSCastButton_ThemeCompliantDialogFactory_MembersInjector
    implements MembersInjector<LDSCastButton.ThemeCompliantDialogFactory> {
  private final Provider<Prefs> prefsProvider;

  public LDSCastButton_ThemeCompliantDialogFactory_MembersInjector(Provider<Prefs> prefsProvider) {
    this.prefsProvider = prefsProvider;
  }

  public static MembersInjector<LDSCastButton.ThemeCompliantDialogFactory> create(
      Provider<Prefs> prefsProvider) {
    return new LDSCastButton_ThemeCompliantDialogFactory_MembersInjector(prefsProvider);
  }

  @Override
  public void injectMembers(LDSCastButton.ThemeCompliantDialogFactory instance) {
    injectPrefs(instance, prefsProvider.get());
  }

  public static void injectPrefs(LDSCastButton.ThemeCompliantDialogFactory instance, Prefs prefs) {
    instance.prefs = prefs;
  }
}
