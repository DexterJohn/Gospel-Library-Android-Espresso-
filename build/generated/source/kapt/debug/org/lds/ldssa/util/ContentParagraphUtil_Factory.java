package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.content.paragraphmetadata.ParagraphMetadataManager;
import org.lds.ldssa.model.database.content.subitemcontent.SubItemContentManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ContentParagraphUtil_Factory implements Factory<ContentParagraphUtil> {
  private final Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider;

  private final Provider<SubItemContentManager> subItemContentManagerProvider;

  public ContentParagraphUtil_Factory(
      Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider,
      Provider<SubItemContentManager> subItemContentManagerProvider) {
    this.paragraphMetadataManagerProvider = paragraphMetadataManagerProvider;
    this.subItemContentManagerProvider = subItemContentManagerProvider;
  }

  @Override
  public ContentParagraphUtil get() {
    return new ContentParagraphUtil(
        paragraphMetadataManagerProvider.get(), subItemContentManagerProvider.get());
  }

  public static Factory<ContentParagraphUtil> create(
      Provider<ParagraphMetadataManager> paragraphMetadataManagerProvider,
      Provider<SubItemContentManager> subItemContentManagerProvider) {
    return new ContentParagraphUtil_Factory(
        paragraphMetadataManagerProvider, subItemContentManagerProvider);
  }
}
