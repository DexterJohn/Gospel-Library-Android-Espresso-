package org.lds.ldssa.media.exomedia.helper;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.prefs.Prefs;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GLAudioApi_MembersInjector implements MembersInjector<GLAudioApi> {
  private final Provider<Prefs> prefsProvider;

  public GLAudioApi_MembersInjector(Provider<Prefs> prefsProvider) {
    this.prefsProvider = prefsProvider;
  }

  public static MembersInjector<GLAudioApi> create(Provider<Prefs> prefsProvider) {
    return new GLAudioApi_MembersInjector(prefsProvider);
  }

  @Override
  public void injectMembers(GLAudioApi instance) {
    injectPrefs(instance, prefsProvider.get());
  }

  public static void injectPrefs(GLAudioApi instance, Prefs prefs) {
    instance.prefs = prefs;
  }
}
