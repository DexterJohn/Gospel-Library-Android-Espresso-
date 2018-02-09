package org.lds.ldssa.media.texttospeech;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TextToSpeechService_MembersInjector
    implements MembersInjector<TextToSpeechService> {
  private final Provider<TextToSpeechEngine> textToSpeechEngineProvider;

  public TextToSpeechService_MembersInjector(
      Provider<TextToSpeechEngine> textToSpeechEngineProvider) {
    this.textToSpeechEngineProvider = textToSpeechEngineProvider;
  }

  public static MembersInjector<TextToSpeechService> create(
      Provider<TextToSpeechEngine> textToSpeechEngineProvider) {
    return new TextToSpeechService_MembersInjector(textToSpeechEngineProvider);
  }

  @Override
  public void injectMembers(TextToSpeechService instance) {
    injectTextToSpeechEngine(instance, textToSpeechEngineProvider.get());
  }

  public static void injectTextToSpeechEngine(
      TextToSpeechService instance, TextToSpeechEngine textToSpeechEngine) {
    instance.textToSpeechEngine = textToSpeechEngine;
  }
}
