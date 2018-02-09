package org.lds.ldssa.ui.web;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.ExternalIntents;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.media.exomedia.manager.PlaylistManager;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.CitationUtil;
import org.lds.ldssa.util.ImageUtil;
import org.lds.ldssa.util.ShareUtil;
import org.lds.ldssa.util.TextHandleUtil;
import org.lds.ldssa.util.ToastUtil;
import org.lds.ldssa.util.UriUtil;
import org.lds.ldssa.util.annotations.AnnotationListUtil;
import org.lds.ldssa.util.annotations.HighlightUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import org.lds.mobile.util.LdsTimeUtil;
import org.lds.mobile.util.LdsUiUtil;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ContentWebView_MembersInjector implements MembersInjector<ContentWebView> {
  private final Provider<Bus> busProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<ExternalIntents> externalIntentsProvider;

  private final Provider<AnnotationManager> annotationManagerProvider;

  private final Provider<AnnotationListUtil> annotationListUtilProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<LdsUiUtil> uiUtilProvider;

  private final Provider<TextHandleUtil> textHandleUtilProvider;

  private final Provider<ContentJsInterface> contentJsInterfaceProvider;

  private final Provider<ContentJsInvoker> contentJsInvokerProvider;

  private final Provider<LdsTimeUtil> timeUtilProvider;

  private final Provider<SubItemManager> subItemManagerProvider;

  private final Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider;

  private final Provider<HighlightUtil> highlightUtilProvider;

  private final Provider<AnalyticsUtil> analyticsUtilProvider;

  private final Provider<UriUtil> uriUtilProvider;

  private final Provider<ImageUtil> imageUtilProvider;

  private final Provider<CitationUtil> citationUtilProvider;

  private final Provider<ShareUtil> shareUtilProvider;

  private final Provider<PlaylistManager> playlistManagerProvider;

  private final Provider<ItemManager> itemManagerProvider;

  private final Provider<ToastUtil> toastUtilProvider;

  public ContentWebView_MembersInjector(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ExternalIntents> externalIntentsProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<AnnotationListUtil> annotationListUtilProvider,
      Provider<Prefs> prefsProvider,
      Provider<LdsUiUtil> uiUtilProvider,
      Provider<TextHandleUtil> textHandleUtilProvider,
      Provider<ContentJsInterface> contentJsInterfaceProvider,
      Provider<ContentJsInvoker> contentJsInvokerProvider,
      Provider<LdsTimeUtil> timeUtilProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider,
      Provider<HighlightUtil> highlightUtilProvider,
      Provider<AnalyticsUtil> analyticsUtilProvider,
      Provider<UriUtil> uriUtilProvider,
      Provider<ImageUtil> imageUtilProvider,
      Provider<CitationUtil> citationUtilProvider,
      Provider<ShareUtil> shareUtilProvider,
      Provider<PlaylistManager> playlistManagerProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<ToastUtil> toastUtilProvider) {
    this.busProvider = busProvider;
    this.ccProvider = ccProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.externalIntentsProvider = externalIntentsProvider;
    this.annotationManagerProvider = annotationManagerProvider;
    this.annotationListUtilProvider = annotationListUtilProvider;
    this.prefsProvider = prefsProvider;
    this.uiUtilProvider = uiUtilProvider;
    this.textHandleUtilProvider = textHandleUtilProvider;
    this.contentJsInterfaceProvider = contentJsInterfaceProvider;
    this.contentJsInvokerProvider = contentJsInvokerProvider;
    this.timeUtilProvider = timeUtilProvider;
    this.subItemManagerProvider = subItemManagerProvider;
    this.paragraphMetadataManagerProvider = paragraphMetadataManagerProvider;
    this.highlightUtilProvider = highlightUtilProvider;
    this.analyticsUtilProvider = analyticsUtilProvider;
    this.uriUtilProvider = uriUtilProvider;
    this.imageUtilProvider = imageUtilProvider;
    this.citationUtilProvider = citationUtilProvider;
    this.shareUtilProvider = shareUtilProvider;
    this.playlistManagerProvider = playlistManagerProvider;
    this.itemManagerProvider = itemManagerProvider;
    this.toastUtilProvider = toastUtilProvider;
  }

  public static MembersInjector<ContentWebView> create(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ExternalIntents> externalIntentsProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<AnnotationListUtil> annotationListUtilProvider,
      Provider<Prefs> prefsProvider,
      Provider<LdsUiUtil> uiUtilProvider,
      Provider<TextHandleUtil> textHandleUtilProvider,
      Provider<ContentJsInterface> contentJsInterfaceProvider,
      Provider<ContentJsInvoker> contentJsInvokerProvider,
      Provider<LdsTimeUtil> timeUtilProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider,
      Provider<HighlightUtil> highlightUtilProvider,
      Provider<AnalyticsUtil> analyticsUtilProvider,
      Provider<UriUtil> uriUtilProvider,
      Provider<ImageUtil> imageUtilProvider,
      Provider<CitationUtil> citationUtilProvider,
      Provider<ShareUtil> shareUtilProvider,
      Provider<PlaylistManager> playlistManagerProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<ToastUtil> toastUtilProvider) {
    return new ContentWebView_MembersInjector(
        busProvider,
        ccProvider,
        internalIntentsProvider,
        externalIntentsProvider,
        annotationManagerProvider,
        annotationListUtilProvider,
        prefsProvider,
        uiUtilProvider,
        textHandleUtilProvider,
        contentJsInterfaceProvider,
        contentJsInvokerProvider,
        timeUtilProvider,
        subItemManagerProvider,
        paragraphMetadataManagerProvider,
        highlightUtilProvider,
        analyticsUtilProvider,
        uriUtilProvider,
        imageUtilProvider,
        citationUtilProvider,
        shareUtilProvider,
        playlistManagerProvider,
        itemManagerProvider,
        toastUtilProvider);
  }

  @Override
  public void injectMembers(ContentWebView instance) {
    injectBus(instance, busProvider.get());
    injectCc(instance, ccProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectExternalIntents(instance, externalIntentsProvider.get());
    injectAnnotationManager(instance, annotationManagerProvider.get());
    injectAnnotationListUtil(instance, annotationListUtilProvider.get());
    injectPrefs(instance, prefsProvider.get());
    injectUiUtil(instance, uiUtilProvider.get());
    injectTextHandleUtil(instance, textHandleUtilProvider.get());
    injectContentJsInterface(instance, contentJsInterfaceProvider.get());
    injectContentJsInvoker(instance, contentJsInvokerProvider.get());
    injectTimeUtil(instance, timeUtilProvider.get());
    injectSubItemManager(instance, subItemManagerProvider.get());
    injectParagraphMetadataManager(instance, paragraphMetadataManagerProvider.get());
    injectHighlightUtil(instance, highlightUtilProvider.get());
    injectAnalyticsUtil(instance, analyticsUtilProvider.get());
    injectUriUtil(instance, uriUtilProvider.get());
    injectImageUtil(instance, imageUtilProvider.get());
    injectCitationUtil(instance, citationUtilProvider.get());
    injectShareUtil(instance, shareUtilProvider.get());
    injectPlaylistManager(instance, playlistManagerProvider.get());
    injectItemManager(instance, itemManagerProvider.get());
    injectToastUtil(instance, toastUtilProvider.get());
  }

  public static void injectBus(ContentWebView instance, Bus bus) {
    instance.bus = bus;
  }

  public static void injectCc(ContentWebView instance, CoroutineContextProvider cc) {
    instance.cc = cc;
  }

  public static void injectInternalIntents(
      ContentWebView instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectExternalIntents(
      ContentWebView instance, ExternalIntents externalIntents) {
    instance.externalIntents = externalIntents;
  }

  public static void injectAnnotationManager(
      ContentWebView instance, AnnotationManager annotationManager) {
    instance.annotationManager = annotationManager;
  }

  public static void injectAnnotationListUtil(
      ContentWebView instance, AnnotationListUtil annotationListUtil) {
    instance.annotationListUtil = annotationListUtil;
  }

  public static void injectPrefs(ContentWebView instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectUiUtil(ContentWebView instance, LdsUiUtil uiUtil) {
    instance.uiUtil = uiUtil;
  }

  public static void injectTextHandleUtil(ContentWebView instance, TextHandleUtil textHandleUtil) {
    instance.textHandleUtil = textHandleUtil;
  }

  public static void injectContentJsInterface(
      ContentWebView instance, ContentJsInterface contentJsInterface) {
    instance.contentJsInterface = contentJsInterface;
  }

  public static void injectContentJsInvoker(
      ContentWebView instance, ContentJsInvoker contentJsInvoker) {
    instance.contentJsInvoker = contentJsInvoker;
  }

  public static void injectTimeUtil(ContentWebView instance, LdsTimeUtil timeUtil) {
    instance.timeUtil = timeUtil;
  }

  public static void injectSubItemManager(ContentWebView instance, SubItemManager subItemManager) {
    instance.subItemManager = subItemManager;
  }

  public static void injectParagraphMetadataManager(
      ContentWebView instance, ParagraphMetadataManager paragraphMetadataManager) {
    instance.paragraphMetadataManager = paragraphMetadataManager;
  }

  public static void injectHighlightUtil(ContentWebView instance, HighlightUtil highlightUtil) {
    instance.highlightUtil = highlightUtil;
  }

  public static void injectAnalyticsUtil(ContentWebView instance, AnalyticsUtil analyticsUtil) {
    instance.analyticsUtil = analyticsUtil;
  }

  public static void injectUriUtil(ContentWebView instance, UriUtil uriUtil) {
    instance.uriUtil = uriUtil;
  }

  public static void injectImageUtil(ContentWebView instance, ImageUtil imageUtil) {
    instance.imageUtil = imageUtil;
  }

  public static void injectCitationUtil(ContentWebView instance, CitationUtil citationUtil) {
    instance.citationUtil = citationUtil;
  }

  public static void injectShareUtil(ContentWebView instance, ShareUtil shareUtil) {
    instance.shareUtil = shareUtil;
  }

  public static void injectPlaylistManager(
      ContentWebView instance, PlaylistManager playlistManager) {
    instance.playlistManager = playlistManager;
  }

  public static void injectItemManager(ContentWebView instance, ItemManager itemManager) {
    instance.itemManager = itemManager;
  }

  public static void injectToastUtil(ContentWebView instance, ToastUtil toastUtil) {
    instance.toastUtil = toastUtil;
  }
}
