package org.lds.ldssa.ui.sidebar;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.util.AnnotationUiUtil;
import org.lds.ldssa.util.ContentRenderer;
import org.lds.ldssa.util.ScreenUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SideBarUri_MembersInjector implements MembersInjector<SideBarUri> {
  private final Provider<CoroutineContextProvider> ccProvider;

  private final Provider<ContentRenderer> contentRendererProvider;

  private final Provider<AnnotationUiUtil> annotationUiUtilProvider;

  private final Provider<ScreenUtil> screenUtilProvider;

  public SideBarUri_MembersInjector(
      Provider<CoroutineContextProvider> ccProvider,
      Provider<ContentRenderer> contentRendererProvider,
      Provider<AnnotationUiUtil> annotationUiUtilProvider,
      Provider<ScreenUtil> screenUtilProvider) {
    this.ccProvider = ccProvider;
    this.contentRendererProvider = contentRendererProvider;
    this.annotationUiUtilProvider = annotationUiUtilProvider;
    this.screenUtilProvider = screenUtilProvider;
  }

  public static MembersInjector<SideBarUri> create(
      Provider<CoroutineContextProvider> ccProvider,
      Provider<ContentRenderer> contentRendererProvider,
      Provider<AnnotationUiUtil> annotationUiUtilProvider,
      Provider<ScreenUtil> screenUtilProvider) {
    return new SideBarUri_MembersInjector(
        ccProvider, contentRendererProvider, annotationUiUtilProvider, screenUtilProvider);
  }

  @Override
  public void injectMembers(SideBarUri instance) {
    injectCc(instance, ccProvider.get());
    injectContentRenderer(instance, contentRendererProvider.get());
    injectAnnotationUiUtil(instance, annotationUiUtilProvider.get());
    injectScreenUtil(instance, screenUtilProvider.get());
  }

  public static void injectCc(SideBarUri instance, CoroutineContextProvider cc) {
    instance.cc = cc;
  }

  public static void injectContentRenderer(SideBarUri instance, ContentRenderer contentRenderer) {
    instance.contentRenderer = contentRenderer;
  }

  public static void injectAnnotationUiUtil(
      SideBarUri instance, AnnotationUiUtil annotationUiUtil) {
    instance.annotationUiUtil = annotationUiUtil;
  }

  public static void injectScreenUtil(SideBarUri instance, ScreenUtil screenUtil) {
    instance.screenUtil = screenUtil;
  }
}
