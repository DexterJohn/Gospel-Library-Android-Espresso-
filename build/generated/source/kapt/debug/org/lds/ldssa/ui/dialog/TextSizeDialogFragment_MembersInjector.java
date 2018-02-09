package org.lds.ldssa.ui.dialog;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.prefs.Prefs;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TextSizeDialogFragment_MembersInjector
    implements MembersInjector<TextSizeDialogFragment> {
  private final Provider<Prefs> prefsProvider;

  public TextSizeDialogFragment_MembersInjector(Provider<Prefs> prefsProvider) {
    this.prefsProvider = prefsProvider;
  }

  public static MembersInjector<TextSizeDialogFragment> create(Provider<Prefs> prefsProvider) {
    return new TextSizeDialogFragment_MembersInjector(prefsProvider);
  }

  @Override
  public void injectMembers(TextSizeDialogFragment instance) {
    injectPrefs(instance, prefsProvider.get());
  }

  public static void injectPrefs(TextSizeDialogFragment instance, Prefs prefs) {
    instance.prefs = prefs;
  }
}
