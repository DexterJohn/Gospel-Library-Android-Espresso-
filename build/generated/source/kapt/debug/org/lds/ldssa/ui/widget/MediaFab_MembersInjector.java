package org.lds.ldssa.ui.widget;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.media.exomedia.manager.PlaylistManager;
import org.lds.ldssa.media.texttospeech.TextToSpeechManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MediaFab_MembersInjector implements MembersInjector<MediaFab> {
  private final Provider<PlaylistManager> playlistManagerProvider;

  private final Provider<TextToSpeechManager> textToSpeechManagerProvider;

  public MediaFab_MembersInjector(
      Provider<PlaylistManager> playlistManagerProvider,
      Provider<TextToSpeechManager> textToSpeechManagerProvider) {
    this.playlistManagerProvider = playlistManagerProvider;
    this.textToSpeechManagerProvider = textToSpeechManagerProvider;
  }

  public static MembersInjector<MediaFab> create(
      Provider<PlaylistManager> playlistManagerProvider,
      Provider<TextToSpeechManager> textToSpeechManagerProvider) {
    return new MediaFab_MembersInjector(playlistManagerProvider, textToSpeechManagerProvider);
  }

  @Override
  public void injectMembers(MediaFab instance) {
    injectPlaylistManager(instance, playlistManagerProvider.get());
    injectTextToSpeechManager(instance, textToSpeechManagerProvider.get());
  }

  public static void injectPlaylistManager(MediaFab instance, PlaylistManager playlistManager) {
    instance.playlistManager = playlistManager;
  }

  public static void injectTextToSpeechManager(
      MediaFab instance, TextToSpeechManager textToSpeechManager) {
    instance.textToSpeechManager = textToSpeechManager;
  }
}
