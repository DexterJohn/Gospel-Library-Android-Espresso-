package org.lds.ldssa.ui.fragment;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.ExternalIntents;
import org.lds.ldssa.media.exomedia.manager.PlaylistManager;
import org.lds.ldssa.media.texttospeech.TextToSpeechManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.content.playlistitemquery.PlaylistItemQueryManager;
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItemManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.prefs.Prefs;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AudioSettingsFragment_MembersInjector
    implements MembersInjector<AudioSettingsFragment> {
  private final Provider<Prefs> prefsProvider;

  private final Provider<PlaylistManager> playlistManagerProvider;

  private final Provider<PlaylistItemQueryManager> playlistItemQueryManagerProvider;

  private final Provider<RelatedAudioItemManager> relatedAudioItemManagerProvider;

  private final Provider<TextToSpeechManager> textToSpeechManagerProvider;

  private final Provider<ItemManager> itemManagerProvider;

  private final Provider<SubItemManager> subItemManagerProvider;

  private final Provider<ExternalIntents> externalIntentsProvider;

  public AudioSettingsFragment_MembersInjector(
      Provider<Prefs> prefsProvider,
      Provider<PlaylistManager> playlistManagerProvider,
      Provider<PlaylistItemQueryManager> playlistItemQueryManagerProvider,
      Provider<RelatedAudioItemManager> relatedAudioItemManagerProvider,
      Provider<TextToSpeechManager> textToSpeechManagerProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<ExternalIntents> externalIntentsProvider) {
    this.prefsProvider = prefsProvider;
    this.playlistManagerProvider = playlistManagerProvider;
    this.playlistItemQueryManagerProvider = playlistItemQueryManagerProvider;
    this.relatedAudioItemManagerProvider = relatedAudioItemManagerProvider;
    this.textToSpeechManagerProvider = textToSpeechManagerProvider;
    this.itemManagerProvider = itemManagerProvider;
    this.subItemManagerProvider = subItemManagerProvider;
    this.externalIntentsProvider = externalIntentsProvider;
  }

  public static MembersInjector<AudioSettingsFragment> create(
      Provider<Prefs> prefsProvider,
      Provider<PlaylistManager> playlistManagerProvider,
      Provider<PlaylistItemQueryManager> playlistItemQueryManagerProvider,
      Provider<RelatedAudioItemManager> relatedAudioItemManagerProvider,
      Provider<TextToSpeechManager> textToSpeechManagerProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<ExternalIntents> externalIntentsProvider) {
    return new AudioSettingsFragment_MembersInjector(
        prefsProvider,
        playlistManagerProvider,
        playlistItemQueryManagerProvider,
        relatedAudioItemManagerProvider,
        textToSpeechManagerProvider,
        itemManagerProvider,
        subItemManagerProvider,
        externalIntentsProvider);
  }

  @Override
  public void injectMembers(AudioSettingsFragment instance) {
    injectPrefs(instance, prefsProvider.get());
    injectPlaylistManager(instance, playlistManagerProvider.get());
    injectPlaylistItemQueryManager(instance, playlistItemQueryManagerProvider.get());
    injectRelatedAudioItemManager(instance, relatedAudioItemManagerProvider.get());
    injectTextToSpeechManager(instance, textToSpeechManagerProvider.get());
    injectItemManager(instance, itemManagerProvider.get());
    injectSubItemManager(instance, subItemManagerProvider.get());
    injectExternalIntents(instance, externalIntentsProvider.get());
  }

  public static void injectPrefs(AudioSettingsFragment instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectPlaylistManager(
      AudioSettingsFragment instance, PlaylistManager playlistManager) {
    instance.playlistManager = playlistManager;
  }

  public static void injectPlaylistItemQueryManager(
      AudioSettingsFragment instance, PlaylistItemQueryManager playlistItemQueryManager) {
    instance.playlistItemQueryManager = playlistItemQueryManager;
  }

  public static void injectRelatedAudioItemManager(
      AudioSettingsFragment instance, RelatedAudioItemManager relatedAudioItemManager) {
    instance.relatedAudioItemManager = relatedAudioItemManager;
  }

  public static void injectTextToSpeechManager(
      AudioSettingsFragment instance, TextToSpeechManager textToSpeechManager) {
    instance.textToSpeechManager = textToSpeechManager;
  }

  public static void injectItemManager(AudioSettingsFragment instance, ItemManager itemManager) {
    instance.itemManager = itemManager;
  }

  public static void injectSubItemManager(
      AudioSettingsFragment instance, SubItemManager subItemManager) {
    instance.subItemManager = subItemManager;
  }

  public static void injectExternalIntents(
      AudioSettingsFragment instance, ExternalIntents externalIntents) {
    instance.externalIntents = externalIntents;
  }
}
