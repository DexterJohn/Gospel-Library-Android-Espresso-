package org.lds.ldssa.download;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.util.ContentItemUpdateUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DownloadedContentProcessor_Factory
    implements Factory<DownloadedContentProcessor> {
  private final Provider<ContentItemUpdateUtil> arg0Provider;

  public DownloadedContentProcessor_Factory(Provider<ContentItemUpdateUtil> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public DownloadedContentProcessor get() {
    return new DownloadedContentProcessor(arg0Provider.get());
  }

  public static Factory<DownloadedContentProcessor> create(
      Provider<ContentItemUpdateUtil> arg0Provider) {
    return new DownloadedContentProcessor_Factory(arg0Provider);
  }
}
