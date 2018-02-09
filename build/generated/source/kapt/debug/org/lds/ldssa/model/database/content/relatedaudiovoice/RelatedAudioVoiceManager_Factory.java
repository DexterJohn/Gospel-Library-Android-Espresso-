package org.lds.ldssa.model.database.content.relatedaudiovoice;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RelatedAudioVoiceManager_Factory implements Factory<RelatedAudioVoiceManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public RelatedAudioVoiceManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public RelatedAudioVoiceManager get() {
    return new RelatedAudioVoiceManager(arg0Provider.get());
  }

  public static Factory<RelatedAudioVoiceManager> create(Provider<DatabaseManager> arg0Provider) {
    return new RelatedAudioVoiceManager_Factory(arg0Provider);
  }
}
