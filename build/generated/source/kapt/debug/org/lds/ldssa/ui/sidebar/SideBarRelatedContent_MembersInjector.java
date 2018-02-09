package org.lds.ldssa.ui.sidebar;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataManager;
import org.lds.ldssa.model.database.content.relatedcontentitem.RelatedContentItemManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.util.annotations.HighlightUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SideBarRelatedContent_MembersInjector
    implements MembersInjector<SideBarRelatedContent> {
  private final Provider<RelatedContentItemManager> relatedContentItemManagerProvider;

  private final Provider<AnnotationManager> annotationManagerProvider;

  private final Provider<SubItemManager> subItemManagerProvider;

  private final Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider;

  private final Provider<HighlightUtil> highlightUtilProvider;

  public SideBarRelatedContent_MembersInjector(
      Provider<RelatedContentItemManager> relatedContentItemManagerProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider,
      Provider<HighlightUtil> highlightUtilProvider) {
    this.relatedContentItemManagerProvider = relatedContentItemManagerProvider;
    this.annotationManagerProvider = annotationManagerProvider;
    this.subItemManagerProvider = subItemManagerProvider;
    this.paragraphMetadataManagerProvider = paragraphMetadataManagerProvider;
    this.highlightUtilProvider = highlightUtilProvider;
  }

  public static MembersInjector<SideBarRelatedContent> create(
      Provider<RelatedContentItemManager> relatedContentItemManagerProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider,
      Provider<HighlightUtil> highlightUtilProvider) {
    return new SideBarRelatedContent_MembersInjector(
        relatedContentItemManagerProvider,
        annotationManagerProvider,
        subItemManagerProvider,
        paragraphMetadataManagerProvider,
        highlightUtilProvider);
  }

  @Override
  public void injectMembers(SideBarRelatedContent instance) {
    injectRelatedContentItemManager(instance, relatedContentItemManagerProvider.get());
    injectAnnotationManager(instance, annotationManagerProvider.get());
    injectSubItemManager(instance, subItemManagerProvider.get());
    injectParagraphMetadataManager(instance, paragraphMetadataManagerProvider.get());
    injectHighlightUtil(instance, highlightUtilProvider.get());
  }

  public static void injectRelatedContentItemManager(
      SideBarRelatedContent instance, RelatedContentItemManager relatedContentItemManager) {
    instance.relatedContentItemManager = relatedContentItemManager;
  }

  public static void injectAnnotationManager(
      SideBarRelatedContent instance, AnnotationManager annotationManager) {
    instance.annotationManager = annotationManager;
  }

  public static void injectSubItemManager(
      SideBarRelatedContent instance, SubItemManager subItemManager) {
    instance.subItemManager = subItemManager;
  }

  public static void injectParagraphMetadataManager(
      SideBarRelatedContent instance, ParagraphMetadataManager paragraphMetadataManager) {
    instance.paragraphMetadataManager = paragraphMetadataManager;
  }

  public static void injectHighlightUtil(
      SideBarRelatedContent instance, HighlightUtil highlightUtil) {
    instance.highlightUtil = highlightUtil;
  }
}
