package org.lds.ldssa.util;

import android.app.Application;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager;
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataManager;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.highlight.HighlightManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CitationUtil_Factory implements Factory<CitationUtil> {
  private final Provider<Application> applicationProvider;

  private final Provider<SubItemManager> subItemManagerProvider;

  private final Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider;

  private final Provider<SubItemMetadataManager> subItemMetadataManagerProvider;

  private final Provider<ContentItemUtil> contentItemUtilProvider;

  private final Provider<AnnotationManager> annotationManagerProvider;

  private final Provider<HighlightManager> highlightManagerProvider;

  public CitationUtil_Factory(
      Provider<Application> applicationProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider,
      Provider<SubItemMetadataManager> subItemMetadataManagerProvider,
      Provider<ContentItemUtil> contentItemUtilProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<HighlightManager> highlightManagerProvider) {
    this.applicationProvider = applicationProvider;
    this.subItemManagerProvider = subItemManagerProvider;
    this.paragraphMetadataManagerProvider = paragraphMetadataManagerProvider;
    this.subItemMetadataManagerProvider = subItemMetadataManagerProvider;
    this.contentItemUtilProvider = contentItemUtilProvider;
    this.annotationManagerProvider = annotationManagerProvider;
    this.highlightManagerProvider = highlightManagerProvider;
  }

  @Override
  public CitationUtil get() {
    return new CitationUtil(
        applicationProvider.get(),
        subItemManagerProvider.get(),
        paragraphMetadataManagerProvider.get(),
        subItemMetadataManagerProvider.get(),
        contentItemUtilProvider.get(),
        annotationManagerProvider.get(),
        highlightManagerProvider.get());
  }

  public static Factory<CitationUtil> create(
      Provider<Application> applicationProvider,
      Provider<SubItemManager> subItemManagerProvider,
      Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider,
      Provider<SubItemMetadataManager> subItemMetadataManagerProvider,
      Provider<ContentItemUtil> contentItemUtilProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<HighlightManager> highlightManagerProvider) {
    return new CitationUtil_Factory(
        applicationProvider,
        subItemManagerProvider,
        paragraphMetadataManagerProvider,
        subItemMetadataManagerProvider,
        contentItemUtilProvider,
        annotationManagerProvider,
        highlightManagerProvider);
  }
}
