package org.lds.ldssa.ui.sidebar;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.content.relatedcontentitem.RelatedContentItemManager;
import org.lds.ldssa.util.AnnotationUiUtil;
import org.lds.ldssa.util.ContentRenderer;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SideBarRelatedContentItem_MembersInjector
    implements MembersInjector<SideBarRelatedContentItem> {
  private final Provider<CoroutineContextProvider> ccProvider;

  private final Provider<RelatedContentItemManager> relatedContentItemManagerProvider;

  private final Provider<ContentRenderer> contentRendererProvider;

  private final Provider<ScreenUtil> screenUtilProvider;

  private final Provider<AnnotationUiUtil> annotationUiUtilProvider;

  public SideBarRelatedContentItem_MembersInjector(
      Provider<CoroutineContextProvider> ccProvider,
      Provider<RelatedContentItemManager> relatedContentItemManagerProvider,
      Provider<ContentRenderer> contentRendererProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<AnnotationUiUtil> annotationUiUtilProvider) {
    this.ccProvider = ccProvider;
    this.relatedContentItemManagerProvider = relatedContentItemManagerProvider;
    this.contentRendererProvider = contentRendererProvider;
    this.screenUtilProvider = screenUtilProvider;
    this.annotationUiUtilProvider = annotationUiUtilProvider;
  }

  public static MembersInjector<SideBarRelatedContentItem> create(
      Provider<CoroutineContextProvider> ccProvider,
      Provider<RelatedContentItemManager> relatedContentItemManagerProvider,
      Provider<ContentRenderer> contentRendererProvider,
      Provider<ScreenUtil> screenUtilProvider,
      Provider<AnnotationUiUtil> annotationUiUtilProvider) {
    return new SideBarRelatedContentItem_MembersInjector(
        ccProvider,
        relatedContentItemManagerProvider,
        contentRendererProvider,
        screenUtilProvider,
        annotationUiUtilProvider);
  }

  @Override
  public void injectMembers(SideBarRelatedContentItem instance) {
    injectCc(instance, ccProvider.get());
    injectRelatedContentItemManager(instance, relatedContentItemManagerProvider.get());
    injectContentRenderer(instance, contentRendererProvider.get());
    injectScreenUtil(instance, screenUtilProvider.get());
    injectAnnotationUiUtil(instance, annotationUiUtilProvider.get());
  }

  public static void injectCc(SideBarRelatedContentItem instance, CoroutineContextProvider cc) {
    instance.cc = cc;
  }

  public static void injectRelatedContentItemManager(
      SideBarRelatedContentItem instance, RelatedContentItemManager relatedContentItemManager) {
    instance.relatedContentItemManager = relatedContentItemManager;
  }

  public static void injectContentRenderer(
      SideBarRelatedContentItem instance, ContentRenderer contentRenderer) {
    instance.contentRenderer = contentRenderer;
  }

  public static void injectScreenUtil(SideBarRelatedContentItem instance, ScreenUtil screenUtil) {
    instance.screenUtil = screenUtil;
  }

  public static void injectAnnotationUiUtil(
      SideBarRelatedContentItem instance, AnnotationUiUtil annotationUiUtil) {
    instance.annotationUiUtil = annotationUiUtil;
  }
}
