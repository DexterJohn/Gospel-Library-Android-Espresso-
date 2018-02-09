package org.lds.ldssa.ux.content.item;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.intent.ExternalIntents;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.link.LinkManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.sync.AnnotationSync;
import org.lds.ldssa.ui.fragment.BaseFragment_MembersInjector;
import org.lds.ldssa.ui.menu.CommonMenu;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.ContentRenderer;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.ldssa.util.ShareUtil;
import org.lds.ldssa.util.annotations.LinkUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ContentItemFragment_MembersInjector
    implements MembersInjector<ContentItemFragment> {
  private final Provider<Bus> busProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<ExternalIntents> externalIntentsProvider;

  private final Provider<ShareUtil> shareUtilProvider;

  private final Provider<CommonMenu> commonMenuProvider;

  private final Provider<AnnotationSync> annotationSyncProvider;

  private final Provider<AnnotationManager> annotationManagerProvider;

  private final Provider<SubItemManager> subItemManagerProvider;

  private final Provider<ScreenUtil> screenUtilProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<ContentRenderer> contentRendererProvider;

  private final Provider<Analytics> analyticsProvider;

  private final Provider<AnalyticsUtil> analyticsUtilProvider;

  private final Provider<LinkUtil> linkUtilProvider;

  private final Provider<LinkManager> linkManagerProvider;

  public ContentItemFragment_MembersInjector(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ExternalIntents> externalIntentsProvider,
      Provider<ShareUtil> shareUtilProvider,
      Provider<CommonMenu> commonMenuProvider,
      Provider<AnnotationSync> annotationSyncProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<Prefs> prefsProvider,
      Provider<ContentRenderer> contentRendererProvider,
      Provider<Analytics> analyticsProvider,
      Provider<AnalyticsUtil> analyticsUtilProvider,
      Provider<LinkUtil> linkUtilProvider,
      Provider<LinkManager> linkManagerProvider) {
    this.busProvider = busProvider;
    this.ccProvider = ccProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.externalIntentsProvider = externalIntentsProvider;
    this.shareUtilProvider = shareUtilProvider;
    this.commonMenuProvider = commonMenuProvider;
    this.annotationSyncProvider = annotationSyncProvider;
    this.annotationManagerProvider = annotationManagerProvider;
    this.subItemManagerProvider = subItemManagerProvider;
    this.screenUtilProvider = screenUtilProvider;
    this.prefsProvider = prefsProvider;
    this.contentRendererProvider = contentRendererProvider;
    this.analyticsProvider = analyticsProvider;
    this.analyticsUtilProvider = analyticsUtilProvider;
    this.linkUtilProvider = linkUtilProvider;
    this.linkManagerProvider = linkManagerProvider;
  }

  public static MembersInjector<ContentItemFragment> create(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ExternalIntents> externalIntentsProvider,
      Provider<ShareUtil> shareUtilProvider,
      Provider<CommonMenu> commonMenuProvider,
      Provider<AnnotationSync> annotationSyncProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<Prefs> prefsProvider,
      Provider<ContentRenderer> contentRendererProvider,
      Provider<Analytics> analyticsProvider,
      Provider<AnalyticsUtil> analyticsUtilProvider,
      Provider<LinkUtil> linkUtilProvider,
      Provider<LinkManager> linkManagerProvider) {
    return new ContentItemFragment_MembersInjector(
        busProvider,
        ccProvider,
        internalIntentsProvider,
        externalIntentsProvider,
        shareUtilProvider,
        commonMenuProvider,
        annotationSyncProvider,
        annotationManagerProvider,
        subItemManagerProvider,
        screenUtilProvider,
        prefsProvider,
        contentRendererProvider,
        analyticsProvider,
        analyticsUtilProvider,
        linkUtilProvider,
        linkManagerProvider);
  }

  @Override
  public void injectMembers(ContentItemFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, busProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, ccProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectExternalIntents(instance, externalIntentsProvider.get());
    injectShareUtil(instance, shareUtilProvider.get());
    injectCommonMenu(instance, commonMenuProvider.get());
    injectAnnotationSync(instance, annotationSyncProvider.get());
    injectAnnotationManager(instance, annotationManagerProvider.get());
    injectSubItemManager(instance, subItemManagerProvider.get());
    injectScreenUtil(instance, screenUtilProvider.get());
    injectPrefs(instance, prefsProvider.get());
    injectContentRenderer(instance, contentRendererProvider.get());
    injectAnalytics(instance, analyticsProvider.get());
    injectAnalyticsUtil(instance, analyticsUtilProvider.get());
    injectLinkUtil(instance, linkUtilProvider.get());
    injectLinkManager(instance, linkManagerProvider.get());
  }

  public static void injectInternalIntents(
      ContentItemFragment instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectExternalIntents(
      ContentItemFragment instance, ExternalIntents externalIntents) {
    instance.externalIntents = externalIntents;
  }

  public static void injectShareUtil(ContentItemFragment instance, ShareUtil shareUtil) {
    instance.shareUtil = shareUtil;
  }

  public static void injectCommonMenu(ContentItemFragment instance, CommonMenu commonMenu) {
    instance.commonMenu = commonMenu;
  }

  public static void injectAnnotationSync(
      ContentItemFragment instance, AnnotationSync annotationSync) {
    instance.annotationSync = annotationSync;
  }

  public static void injectAnnotationManager(
      ContentItemFragment instance, AnnotationManager annotationManager) {
    instance.annotationManager = annotationManager;
  }

  public static void injectSubItemManager(
      ContentItemFragment instance, SubItemManager subItemManager) {
    instance.subItemManager = subItemManager;
  }

  public static void injectScreenUtil(ContentItemFragment instance, ScreenUtil screenUtil) {
    instance.screenUtil = screenUtil;
  }

  public static void injectPrefs(ContentItemFragment instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectContentRenderer(
      ContentItemFragment instance, ContentRenderer contentRenderer) {
    instance.contentRenderer = contentRenderer;
  }

  public static void injectAnalytics(ContentItemFragment instance, Analytics analytics) {
    instance.analytics = analytics;
  }

  public static void injectAnalyticsUtil(
      ContentItemFragment instance, AnalyticsUtil analyticsUtil) {
    instance.analyticsUtil = analyticsUtil;
  }

  public static void injectLinkUtil(ContentItemFragment instance, LinkUtil linkUtil) {
    instance.linkUtil = linkUtil;
  }

  public static void injectLinkManager(ContentItemFragment instance, LinkManager linkManager) {
    instance.linkManager = linkManager;
  }
}
