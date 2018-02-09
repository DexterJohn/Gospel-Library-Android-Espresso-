package org.lds.ldssa.ui.dialog;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItemManager;
import org.lds.ldssa.model.prefs.Prefs;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DownloadMediaDialogFragment_MembersInjector
    implements MembersInjector<DownloadMediaDialogFragment> {
  private final Provider<Bus> busProvider;

  private final Provider<GLDownloadManager> glDownloadManagerProvider;

  private final Provider<RelatedAudioItemManager> relatedAudioItemManagerProvider;

  private final Provider<Prefs> prefsProvider;

  public DownloadMediaDialogFragment_MembersInjector(
      Provider<Bus> busProvider,
      Provider<GLDownloadManager> glDownloadManagerProvider,
      Provider<RelatedAudioItemManager> relatedAudioItemManagerProvider,
      Provider<Prefs> prefsProvider) {
    this.busProvider = busProvider;
    this.glDownloadManagerProvider = glDownloadManagerProvider;
    this.relatedAudioItemManagerProvider = relatedAudioItemManagerProvider;
    this.prefsProvider = prefsProvider;
  }

  public static MembersInjector<DownloadMediaDialogFragment> create(
      Provider<Bus> busProvider,
      Provider<GLDownloadManager> glDownloadManagerProvider,
      Provider<RelatedAudioItemManager> relatedAudioItemManagerProvider,
      Provider<Prefs> prefsProvider) {
    return new DownloadMediaDialogFragment_MembersInjector(
        busProvider, glDownloadManagerProvider, relatedAudioItemManagerProvider, prefsProvider);
  }

  @Override
  public void injectMembers(DownloadMediaDialogFragment instance) {
    injectBus(instance, busProvider.get());
    injectGlDownloadManager(instance, glDownloadManagerProvider.get());
    injectRelatedAudioItemManager(instance, relatedAudioItemManagerProvider.get());
    injectPrefs(instance, prefsProvider.get());
  }

  public static void injectBus(DownloadMediaDialogFragment instance, Bus bus) {
    instance.bus = bus;
  }

  public static void injectGlDownloadManager(
      DownloadMediaDialogFragment instance, GLDownloadManager glDownloadManager) {
    instance.glDownloadManager = glDownloadManager;
  }

  public static void injectRelatedAudioItemManager(
      DownloadMediaDialogFragment instance, RelatedAudioItemManager relatedAudioItemManager) {
    instance.relatedAudioItemManager = relatedAudioItemManager;
  }

  public static void injectPrefs(DownloadMediaDialogFragment instance, Prefs prefs) {
    instance.prefs = prefs;
  }
}
