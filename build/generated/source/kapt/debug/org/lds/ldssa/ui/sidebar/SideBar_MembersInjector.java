package org.lds.ldssa.ui.sidebar;

import com.google.gson.Gson;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.gl.sidebarhistoryitem.SidebarHistoryItemManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.ContentItemUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SideBar_MembersInjector implements MembersInjector<SideBar> {
  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<SidebarHistoryItemManager> sidebarHistoryItemManagerProvider;

  private final Provider<AnnotationManager> annotationManagerProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<Analytics> analyticsProvider;

  private final Provider<ContentItemUtil> contentItemUtilProvider;

  private final Provider<Gson> gsonProvider;

  public SideBar_MembersInjector(
      Provider<InternalIntents> internalIntentsProvider,
      Provider<SidebarHistoryItemManager> sidebarHistoryItemManagerProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<Prefs> prefsProvider,
      Provider<Analytics> analyticsProvider,
      Provider<ContentItemUtil> contentItemUtilProvider,
      Provider<Gson> gsonProvider) {
    this.internalIntentsProvider = internalIntentsProvider;
    this.sidebarHistoryItemManagerProvider = sidebarHistoryItemManagerProvider;
    this.annotationManagerProvider = annotationManagerProvider;
    this.prefsProvider = prefsProvider;
    this.analyticsProvider = analyticsProvider;
    this.contentItemUtilProvider = contentItemUtilProvider;
    this.gsonProvider = gsonProvider;
  }

  public static MembersInjector<SideBar> create(
      Provider<InternalIntents> internalIntentsProvider,
      Provider<SidebarHistoryItemManager> sidebarHistoryItemManagerProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<Prefs> prefsProvider,
      Provider<Analytics> analyticsProvider,
      Provider<ContentItemUtil> contentItemUtilProvider,
      Provider<Gson> gsonProvider) {
    return new SideBar_MembersInjector(
        internalIntentsProvider,
        sidebarHistoryItemManagerProvider,
        annotationManagerProvider,
        prefsProvider,
        analyticsProvider,
        contentItemUtilProvider,
        gsonProvider);
  }

  @Override
  public void injectMembers(SideBar instance) {
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectSidebarHistoryItemManager(instance, sidebarHistoryItemManagerProvider.get());
    injectAnnotationManager(instance, annotationManagerProvider.get());
    injectPrefs(instance, prefsProvider.get());
    injectAnalytics(instance, analyticsProvider.get());
    injectContentItemUtil(instance, contentItemUtilProvider.get());
    injectGson(instance, gsonProvider.get());
  }

  public static void injectInternalIntents(SideBar instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectSidebarHistoryItemManager(
      SideBar instance, SidebarHistoryItemManager sidebarHistoryItemManager) {
    instance.sidebarHistoryItemManager = sidebarHistoryItemManager;
  }

  public static void injectAnnotationManager(
      SideBar instance, AnnotationManager annotationManager) {
    instance.annotationManager = annotationManager;
  }

  public static void injectPrefs(SideBar instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectAnalytics(SideBar instance, Analytics analytics) {
    instance.analytics = analytics;
  }

  public static void injectContentItemUtil(SideBar instance, ContentItemUtil contentItemUtil) {
    instance.contentItemUtil = contentItemUtil;
  }

  public static void injectGson(SideBar instance, Gson gson) {
    instance.gson = gson;
  }
}
