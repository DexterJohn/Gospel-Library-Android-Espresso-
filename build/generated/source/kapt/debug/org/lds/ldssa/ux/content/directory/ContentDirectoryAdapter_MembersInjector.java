package org.lds.ldssa.ux.content.directory;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.util.UriUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ContentDirectoryAdapter_MembersInjector
    implements MembersInjector<ContentDirectoryAdapter> {
  private final Provider<UriUtil> uriUtilProvider;

  public ContentDirectoryAdapter_MembersInjector(Provider<UriUtil> uriUtilProvider) {
    this.uriUtilProvider = uriUtilProvider;
  }

  public static MembersInjector<ContentDirectoryAdapter> create(Provider<UriUtil> uriUtilProvider) {
    return new ContentDirectoryAdapter_MembersInjector(uriUtilProvider);
  }

  @Override
  public void injectMembers(ContentDirectoryAdapter instance) {
    injectUriUtil(instance, uriUtilProvider.get());
  }

  public static void injectUriUtil(ContentDirectoryAdapter instance, UriUtil uriUtil) {
    instance.uriUtil = uriUtil;
  }
}
