package org.lds.ldssa.ui.activity;

import com.google.gson.Gson;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.note.NoteManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.ui.menu.CommonMenu;
import org.lds.ldssa.util.AccountUtil;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.ThemeUtil;
import org.lds.ldssa.util.annotations.AnnotationListUtil;
import org.lds.ldssa.util.annotations.NoteUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import org.lds.mobile.util.LdsKeyboardUtil;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NoteActivity_MembersInjector implements MembersInjector<NoteActivity> {
  private final Provider<Bus> busProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<LanguageManager> languageManagerProvider;

  private final Provider<AccountUtil> accountUtilProvider;

  private final Provider<ScreenUtil> screenUtilProvider;

  private final Provider<ScreenLauncherUtil> screenLauncherUtilProvider;

  private final Provider<Analytics> analyticsProvider;

  private final Provider<AnalyticsUtil> analyticsUtilProvider;

  private final Provider<CommonMenu> commonMenuProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  private final Provider<Gson> gsonProvider;

  private final Provider<ThemeUtil> themeUtilProvider;

  private final Provider<NoteManager> noteManagerProvider;

  private final Provider<AnnotationListUtil> annotationListUtilProvider;

  private final Provider<NoteUtil> noteUtilProvider;

  private final Provider<AnnotationManager> annotationManagerProvider;

  private final Provider<LdsKeyboardUtil> keyboardUtilProvider;

  public NoteActivity_MembersInjector(
      Provider<Bus> busProvider,
      Provider<Prefs> prefsProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<LanguageManager> languageManagerProvider,
      Provider<AccountUtil> accountUtilProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider,
      Provider<Analytics> analyticsProvider,
      Provider<AnalyticsUtil> analyticsUtilProvider,
      Provider<CommonMenu> commonMenuProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<Gson> gsonProvider,
      Provider<ThemeUtil> themeUtilProvider,
      Provider<NoteManager> noteManagerProvider,
      Provider<AnnotationListUtil> annotationListUtilProvider,
      Provider<NoteUtil> noteUtilProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<LdsKeyboardUtil> keyboardUtilProvider) {
    this.busProvider = busProvider;
    this.prefsProvider = prefsProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.languageManagerProvider = languageManagerProvider;
    this.accountUtilProvider = accountUtilProvider;
    this.screenUtilProvider = screenUtilProvider;
    this.screenLauncherUtilProvider = screenLauncherUtilProvider;
    this.analyticsProvider = analyticsProvider;
    this.analyticsUtilProvider = analyticsUtilProvider;
    this.commonMenuProvider = commonMenuProvider;
    this.ccProvider = ccProvider;
    this.gsonProvider = gsonProvider;
    this.themeUtilProvider = themeUtilProvider;
    this.noteManagerProvider = noteManagerProvider;
    this.annotationListUtilProvider = annotationListUtilProvider;
    this.noteUtilProvider = noteUtilProvider;
    this.annotationManagerProvider = annotationManagerProvider;
    this.keyboardUtilProvider = keyboardUtilProvider;
  }

  public static MembersInjector<NoteActivity> create(
      Provider<Bus> busProvider,
      Provider<Prefs> prefsProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<LanguageManager> languageManagerProvider,
      Provider<AccountUtil> accountUtilProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<ScreenLauncherUtil> screenLauncherUtilProvider,
      Provider<Analytics> analyticsProvider,
      Provider<AnalyticsUtil> analyticsUtilProvider,
      Provider<CommonMenu> commonMenuProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<Gson> gsonProvider,
      Provider<ThemeUtil> themeUtilProvider,
      Provider<NoteManager> noteManagerProvider,
      Provider<AnnotationListUtil> annotationListUtilProvider,
      Provider<NoteUtil> noteUtilProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<LdsKeyboardUtil> keyboardUtilProvider) {
    return new NoteActivity_MembersInjector(
        busProvider,
        prefsProvider,
        internalIntentsProvider,
        languageManagerProvider,
        accountUtilProvider,
        screenUtilProvider,
        screenLauncherUtilProvider,
        analyticsProvider,
        analyticsUtilProvider,
        commonMenuProvider,
        ccProvider,
        gsonProvider,
        themeUtilProvider,
        noteManagerProvider,
        annotationListUtilProvider,
        noteUtilProvider,
        annotationManagerProvider,
        keyboardUtilProvider);
  }

  @Override
  public void injectMembers(NoteActivity instance) {
    BaseActivity_MembersInjector.injectBus(instance, busProvider.get());
    BaseActivity_MembersInjector.injectPrefs(instance, prefsProvider.get());
    BaseActivity_MembersInjector.injectInternalIntents(instance, internalIntentsProvider.get());
    BaseActivity_MembersInjector.injectLanguageManager(instance, languageManagerProvider.get());
    BaseActivity_MembersInjector.injectAccountUtil(instance, accountUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenUtil(instance, screenUtilProvider.get());
    BaseActivity_MembersInjector.injectScreenLauncherUtil(
        instance, screenLauncherUtilProvider.get());
    BaseActivity_MembersInjector.injectAnalytics(instance, analyticsProvider.get());
    BaseActivity_MembersInjector.injectAnalyticsUtil(instance, analyticsUtilProvider.get());
    BaseActivity_MembersInjector.injectCommonMenu(instance, commonMenuProvider.get());
    BaseActivity_MembersInjector.injectCc(instance, ccProvider.get());
    BaseActivity_MembersInjector.injectGson(instance, gsonProvider.get());
    BaseActivity_MembersInjector.injectThemeUtil(instance, themeUtilProvider.get());
    injectNoteManager(instance, noteManagerProvider.get());
    injectAnnotationListUtil(instance, annotationListUtilProvider.get());
    injectNoteUtil(instance, noteUtilProvider.get());
    injectAnnotationManager(instance, annotationManagerProvider.get());
    injectKeyboardUtil(instance, keyboardUtilProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectAnalytics(instance, analyticsProvider.get());
    injectPrefs(instance, prefsProvider.get());
  }

  public static void injectNoteManager(NoteActivity instance, NoteManager noteManager) {
    instance.noteManager = noteManager;
  }

  public static void injectAnnotationListUtil(
      NoteActivity instance, AnnotationListUtil annotationListUtil) {
    instance.annotationListUtil = annotationListUtil;
  }

  public static void injectNoteUtil(NoteActivity instance, NoteUtil noteUtil) {
    instance.noteUtil = noteUtil;
  }

  public static void injectAnnotationManager(
      NoteActivity instance, AnnotationManager annotationManager) {
    instance.annotationManager = annotationManager;
  }

  public static void injectKeyboardUtil(NoteActivity instance, LdsKeyboardUtil keyboardUtil) {
    instance.keyboardUtil = keyboardUtil;
  }

  public static void injectInternalIntents(NoteActivity instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectAnalytics(NoteActivity instance, Analytics analytics) {
    instance.analytics = analytics;
  }

  public static void injectPrefs(NoteActivity instance, Prefs prefs) {
    instance.prefs = prefs;
  }
}
