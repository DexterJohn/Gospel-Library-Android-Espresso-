package org.lds.ldssa.ui.sidebar;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.util.annotations.LinkUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SideBarAnnotation_MembersInjector implements MembersInjector<SideBarAnnotation> {
  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<AnnotationManager> annotationManagerProvider;

  private final Provider<LinkUtil> linkUtilProvider;

  public SideBarAnnotation_MembersInjector(
      Provider<InternalIntents> internalIntentsProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<LinkUtil> linkUtilProvider) {
    this.internalIntentsProvider = internalIntentsProvider;
    this.annotationManagerProvider = annotationManagerProvider;
    this.linkUtilProvider = linkUtilProvider;
  }

  public static MembersInjector<SideBarAnnotation> create(
      Provider<InternalIntents> internalIntentsProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<LinkUtil> linkUtilProvider) {
    return new SideBarAnnotation_MembersInjector(
        internalIntentsProvider, annotationManagerProvider, linkUtilProvider);
  }

  @Override
  public void injectMembers(SideBarAnnotation instance) {
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectAnnotationManager(instance, annotationManagerProvider.get());
    injectLinkUtil(instance, linkUtilProvider.get());
  }

  public static void injectInternalIntents(
      SideBarAnnotation instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectAnnotationManager(
      SideBarAnnotation instance, AnnotationManager annotationManager) {
    instance.annotationManager = annotationManager;
  }

  public static void injectLinkUtil(SideBarAnnotation instance, LinkUtil linkUtil) {
    instance.linkUtil = linkUtil;
  }
}
