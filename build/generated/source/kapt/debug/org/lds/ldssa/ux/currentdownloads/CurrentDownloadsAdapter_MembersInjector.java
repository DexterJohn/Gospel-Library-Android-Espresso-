package org.lds.ldssa.ux.currentdownloads;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.util.ContentItemUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CurrentDownloadsAdapter_MembersInjector
    implements MembersInjector<CurrentDownloadsAdapter> {
  private final Provider<ContentItemUtil> contentItemUtilProvider;

  public CurrentDownloadsAdapter_MembersInjector(
      Provider<ContentItemUtil> contentItemUtilProvider) {
    this.contentItemUtilProvider = contentItemUtilProvider;
  }

  public static MembersInjector<CurrentDownloadsAdapter> create(
      Provider<ContentItemUtil> contentItemUtilProvider) {
    return new CurrentDownloadsAdapter_MembersInjector(contentItemUtilProvider);
  }

  @Override
  public void injectMembers(CurrentDownloadsAdapter instance) {
    injectContentItemUtil(instance, contentItemUtilProvider.get());
  }

  public static void injectContentItemUtil(
      CurrentDownloadsAdapter instance, ContentItemUtil contentItemUtil) {
    instance.contentItemUtil = contentItemUtil;
  }
}
