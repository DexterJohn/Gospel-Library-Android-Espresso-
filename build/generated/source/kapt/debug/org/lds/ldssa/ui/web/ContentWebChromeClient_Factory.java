package org.lds.ldssa.ui.web;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ContentWebChromeClient_Factory implements Factory<ContentWebChromeClient> {
  private static final ContentWebChromeClient_Factory INSTANCE =
      new ContentWebChromeClient_Factory();

  @Override
  public ContentWebChromeClient get() {
    return new ContentWebChromeClient();
  }

  public static Factory<ContentWebChromeClient> create() {
    return INSTANCE;
  }
}
