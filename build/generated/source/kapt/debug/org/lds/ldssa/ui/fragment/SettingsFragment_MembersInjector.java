package org.lds.ldssa.ui.fragment;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldsaccount.LDSAccountPrefs;
import org.lds.ldssa.download.GLDownloadManager;
import org.lds.ldssa.intent.ExternalIntents;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.gl.downloadedmedia.DownloadedMediaManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.sync.AnnotationSync;
import org.lds.ldssa.util.AccountUtil;
import org.lds.ldssa.util.FeedbackUtil;
import org.lds.ldssa.util.ScreenUtil;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SettingsFragment_MembersInjector implements MembersInjector<SettingsFragment> {
  private final Provider<AccountUtil> accountUtilProvider;

  private final Provider<Bus> busProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<FeedbackUtil> feedbackUtilProvider;

  private final Provider<LDSAccountPrefs> ldsAccountPrefsProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<ExternalIntents> externalIntentsProvider;

  private final Provider<DownloadedMediaManager> downloadedMediaManagerProvider;

  private final Provider<GLDownloadManager> downloadManagerProvider;

  private final Provider<ScreenUtil> screenUtilProvider;

  private final Provider<AnnotationSync> annotationSyncProvider;

  public SettingsFragment_MembersInjector(
      Provider<AccountUtil> accountUtilProvider,
      Provider<Bus> busProvider,
      Provider<Prefs> prefsProvider,
      Provider<FeedbackUtil> feedbackUtilProvider,
      Provider<LDSAccountPrefs> ldsAccountPrefsProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ExternalIntents> externalIntentsProvider,
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<GLDownloadManager> downloadManagerProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<AnnotationSync> annotationSyncProvider) {
    this.accountUtilProvider = accountUtilProvider;
    this.busProvider = busProvider;
    this.prefsProvider = prefsProvider;
    this.feedbackUtilProvider = feedbackUtilProvider;
    this.ldsAccountPrefsProvider = ldsAccountPrefsProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.externalIntentsProvider = externalIntentsProvider;
    this.downloadedMediaManagerProvider = downloadedMediaManagerProvider;
    this.downloadManagerProvider = downloadManagerProvider;
    this.screenUtilProvider = screenUtilProvider;
    this.annotationSyncProvider = annotationSyncProvider;
  }

  public static MembersInjector<SettingsFragment> create(
      Provider<AccountUtil> accountUtilProvider,
      Provider<Bus> busProvider,
      Provider<Prefs> prefsProvider,
      Provider<FeedbackUtil> feedbackUtilProvider,
      Provider<LDSAccountPrefs> ldsAccountPrefsProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ExternalIntents> externalIntentsProvider,
      Provider<DownloadedMediaManager> downloadedMediaManagerProvider,
      Provider<GLDownloadManager> downloadManagerProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<AnnotationSync> annotationSyncProvider) {
    return new SettingsFragment_MembersInjector(
        accountUtilProvider,
        busProvider,
        prefsProvider,
        feedbackUtilProvider,
        ldsAccountPrefsProvider,
        internalIntentsProvider,
        externalIntentsProvider,
        downloadedMediaManagerProvider,
        downloadManagerProvider,
        screenUtilProvider,
        annotationSyncProvider);
  }

  @Override
  public void injectMembers(SettingsFragment instance) {
    injectAccountUtil(instance, accountUtilProvider.get());
    injectBus(instance, busProvider.get());
    injectPrefs(instance, prefsProvider.get());
    injectFeedbackUtil(instance, feedbackUtilProvider.get());
    injectLdsAccountPrefs(instance, ldsAccountPrefsProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectExternalIntents(instance, externalIntentsProvider.get());
    injectDownloadedMediaManager(instance, downloadedMediaManagerProvider.get());
    injectDownloadManager(instance, downloadManagerProvider.get());
    injectScreenUtil(instance, screenUtilProvider.get());
    injectAnnotationSync(instance, annotationSyncProvider.get());
  }

  public static void injectAccountUtil(SettingsFragment instance, AccountUtil accountUtil) {
    instance.accountUtil = accountUtil;
  }

  public static void injectBus(SettingsFragment instance, Bus bus) {
    instance.bus = bus;
  }

  public static void injectPrefs(SettingsFragment instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectFeedbackUtil(SettingsFragment instance, FeedbackUtil feedbackUtil) {
    instance.feedbackUtil = feedbackUtil;
  }

  public static void injectLdsAccountPrefs(
      SettingsFragment instance, LDSAccountPrefs ldsAccountPrefs) {
    instance.ldsAccountPrefs = ldsAccountPrefs;
  }

  public static void injectInternalIntents(
      SettingsFragment instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectExternalIntents(
      SettingsFragment instance, ExternalIntents externalIntents) {
    instance.externalIntents = externalIntents;
  }

  public static void injectDownloadedMediaManager(
      SettingsFragment instance, DownloadedMediaManager downloadedMediaManager) {
    instance.downloadedMediaManager = downloadedMediaManager;
  }

  public static void injectDownloadManager(
      SettingsFragment instance, GLDownloadManager downloadManager) {
    instance.downloadManager = downloadManager;
  }

  public static void injectScreenUtil(SettingsFragment instance, ScreenUtil screenUtil) {
    instance.screenUtil = screenUtil;
  }

  public static void injectAnnotationSync(
      SettingsFragment instance, AnnotationSync annotationSync) {
    instance.annotationSync = annotationSync;
  }
}
