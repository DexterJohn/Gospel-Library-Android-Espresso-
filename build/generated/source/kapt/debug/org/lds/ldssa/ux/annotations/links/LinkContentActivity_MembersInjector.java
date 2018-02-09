package org.lds.ldssa.ux.annotations.links;

import com.google.gson.Gson;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.link.LinkManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.ui.activity.BaseActivity_MembersInjector;
import org.lds.ldssa.ui.menu.CommonMenu;
import org.lds.ldssa.util.AccountUtil;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.CitationUtil;
import org.lds.ldssa.util.ContentRenderer;
import org.lds.ldssa.util.ScreenLauncherUtil;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.ThemeUtil;
import org.lds.ldssa.util.annotations.LinkUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LinkContentActivity_MembersInjector
    implements MembersInjector<LinkContentActivity> {
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

  private final Provider<LinkUtil> linkUtilProvider;

  private final Provider<LinkManager> linkManagerProvider;

  private final Provider<CitationUtil> citationUtilProvider;

  private final Provider<AnnotationManager> annotationManagerProvider;

  private final Provider<ContentRenderer> contentRendererProvider;

  public LinkContentActivity_MembersInjector(
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
      Provider<LinkUtil> linkUtilProvider,
      Provider<LinkManager> linkManagerProvider,
      Provider<CitationUtil> citationUtilProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<ContentRenderer> contentRendererProvider) {
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
    this.linkUtilProvider = linkUtilProvider;
    this.linkManagerProvider = linkManagerProvider;
    this.citationUtilProvider = citationUtilProvider;
    this.annotationManagerProvider = annotationManagerProvider;
    this.contentRendererProvider = contentRendererProvider;
  }

  public static MembersInjector<LinkContentActivity> create(
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
      Provider<LinkUtil> linkUtilProvider,
      Provider<LinkManager> linkManagerProvider,
      Provider<CitationUtil> citationUtilProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<ContentRenderer> contentRendererProvider) {
    return new LinkContentActivity_MembersInjector(
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
        linkUtilProvider,
        linkManagerProvider,
        citationUtilProvider,
        annotationManagerProvider,
        contentRendererProvider);
  }

  @Override
  public void injectMembers(LinkContentActivity instance) {
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
    injectLinkUtil(instance, linkUtilProvider.get());
    injectLinkManager(instance, linkManagerProvider.get());
    injectCitationUtil(instance, citationUtilProvider.get());
    injectAnnotationManager(instance, annotationManagerProvider.get());
    injectContentRenderer(instance, contentRendererProvider.get());
  }

  public static void injectLinkUtil(LinkContentActivity instance, LinkUtil linkUtil) {
    instance.linkUtil = linkUtil;
  }

  public static void injectLinkManager(LinkContentActivity instance, LinkManager linkManager) {
    instance.linkManager = linkManager;
  }

  public static void injectCitationUtil(LinkContentActivity instance, CitationUtil citationUtil) {
    instance.citationUtil = citationUtil;
  }

  public static void injectAnnotationManager(
      LinkContentActivity instance, AnnotationManager annotationManager) {
    instance.annotationManager = annotationManager;
  }

  public static void injectContentRenderer(
      LinkContentActivity instance, ContentRenderer contentRenderer) {
    instance.contentRenderer = contentRenderer;
  }
}
