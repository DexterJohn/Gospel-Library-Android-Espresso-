package org.lds.ldssa.ui.widget;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.AnalyticsUtil;
import org.lds.ldssa.util.AnnotationUiUtil;
import org.lds.ldssa.util.CitationUtil;
import org.lds.ldssa.util.ContentItemUtil;
import org.lds.ldssa.util.ContentRenderer;
import org.lds.ldssa.util.ShareUtil;
import org.lds.ldssa.util.UriUtil;
import org.lds.ldssa.util.annotations.HighlightUtil;
import org.lds.ldssa.util.annotations.NoteUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AnnotationView_MembersInjector implements MembersInjector<AnnotationView> {
  private final Provider<Bus> busProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<SubItemMetadataManager> subItemMetadataManagerProvider;

  private final Provider<AnnotationUiUtil> annotationUiUtilProvider;

  private final Provider<ContentRenderer> contentRendererProvider;

  private final Provider<CitationUtil> citationUtilProvider;

  private final Provider<AnnotationManager> annotationManagerProvider;

  private final Provider<NoteUtil> noteUtilProvider;

  private final Provider<ContentItemUtil> contentItemUtilProvider;

  private final Provider<ItemManager> itemManagerProvider;

  private final Provider<HighlightUtil> highlightUtilProvider;

  private final Provider<ShareUtil> shareUtilProvider;

  private final Provider<UriUtil> uriUtilProvider;

  private final Provider<AnalyticsUtil> analyticsUtilProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  public AnnotationView_MembersInjector(
      Provider<Bus> busProvider,
      Provider<Prefs> prefsProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<SubItemMetadataManager> subItemMetadataManagerProvider,
      Provider<AnnotationUiUtil> annotationUiUtilProvider,
      Provider<ContentRenderer> contentRendererProvider,
      Provider<CitationUtil> citationUtilProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<NoteUtil> noteUtilProvider,
      Provider<ContentItemUtil> contentItemUtilProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<HighlightUtil> highlightUtilProvider,
      Provider<ShareUtil> shareUtilProvider,
      Provider<UriUtil> uriUtilProvider,
      Provider<AnalyticsUtil> analyticsUtilProvider,
      Provider<CoroutineContextProvider> ccProvider) {
    this.busProvider = busProvider;
    this.prefsProvider = prefsProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.subItemMetadataManagerProvider = subItemMetadataManagerProvider;
    this.annotationUiUtilProvider = annotationUiUtilProvider;
    this.contentRendererProvider = contentRendererProvider;
    this.citationUtilProvider = citationUtilProvider;
    this.annotationManagerProvider = annotationManagerProvider;
    this.noteUtilProvider = noteUtilProvider;
    this.contentItemUtilProvider = contentItemUtilProvider;
    this.itemManagerProvider = itemManagerProvider;
    this.highlightUtilProvider = highlightUtilProvider;
    this.shareUtilProvider = shareUtilProvider;
    this.uriUtilProvider = uriUtilProvider;
    this.analyticsUtilProvider = analyticsUtilProvider;
    this.ccProvider = ccProvider;
  }

  public static MembersInjector<AnnotationView> create(
      Provider<Bus> busProvider,
      Provider<Prefs> prefsProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<SubItemMetadataManager> subItemMetadataManagerProvider,
      Provider<AnnotationUiUtil> annotationUiUtilProvider,
      Provider<ContentRenderer> contentRendererProvider,
      Provider<CitationUtil> citationUtilProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<NoteUtil> noteUtilProvider,
      Provider<ContentItemUtil> contentItemUtilProvider,
      Provider<ItemManager> itemManagerProvider,
      Provider<HighlightUtil> highlightUtilProvider,
      Provider<ShareUtil> shareUtilProvider,
      Provider<UriUtil> uriUtilProvider,
      Provider<AnalyticsUtil> analyticsUtilProvider,
      Provider<CoroutineContextProvider> ccProvider) {
    return new AnnotationView_MembersInjector(
        busProvider,
        prefsProvider,
        internalIntentsProvider,
        subItemMetadataManagerProvider,
        annotationUiUtilProvider,
        contentRendererProvider,
        citationUtilProvider,
        annotationManagerProvider,
        noteUtilProvider,
        contentItemUtilProvider,
        itemManagerProvider,
        highlightUtilProvider,
        shareUtilProvider,
        uriUtilProvider,
        analyticsUtilProvider,
        ccProvider);
  }

  @Override
  public void injectMembers(AnnotationView instance) {
    injectBus(instance, busProvider.get());
    injectPrefs(instance, prefsProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectSubItemMetadataManager(instance, subItemMetadataManagerProvider.get());
    injectAnnotationUiUtil(instance, annotationUiUtilProvider.get());
    injectContentRenderer(instance, contentRendererProvider.get());
    injectCitationUtil(instance, citationUtilProvider.get());
    injectAnnotationManager(instance, annotationManagerProvider.get());
    injectNoteUtil(instance, noteUtilProvider.get());
    injectContentItemUtil(instance, contentItemUtilProvider.get());
    injectItemManager(instance, itemManagerProvider.get());
    injectHighlightUtil(instance, highlightUtilProvider.get());
    injectShareUtil(instance, shareUtilProvider.get());
    injectUriUtil(instance, uriUtilProvider.get());
    injectAnalyticsUtil(instance, analyticsUtilProvider.get());
    injectCc(instance, ccProvider.get());
  }

  public static void injectBus(AnnotationView instance, Bus bus) {
    instance.bus = bus;
  }

  public static void injectPrefs(AnnotationView instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectInternalIntents(
      AnnotationView instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectSubItemMetadataManager(
      AnnotationView instance, SubItemMetadataManager subItemMetadataManager) {
    instance.subItemMetadataManager = subItemMetadataManager;
  }

  public static void injectAnnotationUiUtil(
      AnnotationView instance, AnnotationUiUtil annotationUiUtil) {
    instance.annotationUiUtil = annotationUiUtil;
  }

  public static void injectContentRenderer(
      AnnotationView instance, ContentRenderer contentRenderer) {
    instance.contentRenderer = contentRenderer;
  }

  public static void injectCitationUtil(AnnotationView instance, CitationUtil citationUtil) {
    instance.citationUtil = citationUtil;
  }

  public static void injectAnnotationManager(
      AnnotationView instance, AnnotationManager annotationManager) {
    instance.annotationManager = annotationManager;
  }

  public static void injectNoteUtil(AnnotationView instance, NoteUtil noteUtil) {
    instance.noteUtil = noteUtil;
  }

  public static void injectContentItemUtil(
      AnnotationView instance, ContentItemUtil contentItemUtil) {
    instance.contentItemUtil = contentItemUtil;
  }

  public static void injectItemManager(AnnotationView instance, ItemManager itemManager) {
    instance.itemManager = itemManager;
  }

  public static void injectHighlightUtil(AnnotationView instance, HighlightUtil highlightUtil) {
    instance.highlightUtil = highlightUtil;
  }

  public static void injectShareUtil(AnnotationView instance, ShareUtil shareUtil) {
    instance.shareUtil = shareUtil;
  }

  public static void injectUriUtil(AnnotationView instance, UriUtil uriUtil) {
    instance.uriUtil = uriUtil;
  }

  public static void injectAnalyticsUtil(AnnotationView instance, AnalyticsUtil analyticsUtil) {
    instance.analyticsUtil = analyticsUtil;
  }

  public static void injectCc(AnnotationView instance, CoroutineContextProvider cc) {
    instance.cc = cc;
  }
}
