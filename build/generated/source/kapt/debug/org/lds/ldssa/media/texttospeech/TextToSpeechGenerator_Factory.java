package org.lds.ldssa.media.texttospeech;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TextToSpeechGenerator_Factory implements Factory<TextToSpeechGenerator> {
  private static final TextToSpeechGenerator_Factory INSTANCE = new TextToSpeechGenerator_Factory();

  @Override
  public TextToSpeechGenerator get() {
    return new TextToSpeechGenerator();
  }

  public static Factory<TextToSpeechGenerator> create() {
    return INSTANCE;
  }
}
