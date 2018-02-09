package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.content.relatedvideoitem.RelatedVideoItemManager;
import org.lds.ldssa.model.database.content.relatedvideoitemsource.RelatedVideoItemSourceManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RelatedVideoUtil_Factory implements Factory<RelatedVideoUtil> {
  private final Provider<RelatedVideoItemManager> relatedVideoItemManagerProvider;

  private final Provider<RelatedVideoItemSourceManager> relatedVideoItemSourceManagerProvider;

  public RelatedVideoUtil_Factory(
      Provider<RelatedVideoItemManager> relatedVideoItemManagerProvider,
      Provider<RelatedVideoItemSourceManager> relatedVideoItemSourceManagerProvider) {
    this.relatedVideoItemManagerProvider = relatedVideoItemManagerProvider;
    this.relatedVideoItemSourceManagerProvider = relatedVideoItemSourceManagerProvider;
  }

  @Override
  public RelatedVideoUtil get() {
    return new RelatedVideoUtil(
        relatedVideoItemManagerProvider.get(), relatedVideoItemSourceManagerProvider.get());
  }

  public static Factory<RelatedVideoUtil> create(
      Provider<RelatedVideoItemManager> relatedVideoItemManagerProvider,
      Provider<RelatedVideoItemSourceManager> relatedVideoItemSourceManagerProvider) {
    return new RelatedVideoUtil_Factory(
        relatedVideoItemManagerProvider, relatedVideoItemSourceManagerProvider);
  }
}
