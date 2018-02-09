package org.lds.ldssa.media.texttospeech;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.prefs.Prefs;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TextToSpeechManager_Factory implements Factory<TextToSpeechManager> {
  private final Provider<Application> arg0Provider;

  private final Provider<Prefs> arg1Provider;

  public TextToSpeechManager_Factory(
      Provider<Application> arg0Provider, Provider<Prefs> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public TextToSpeechManager get() {
    return new TextToSpeechManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<TextToSpeechManager> create(
      Provider<Application> arg0Provider, Provider<Prefs> arg1Provider) {
    return new TextToSpeechManager_Factory(arg0Provider, arg1Provider);
  }
}
