package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.prefs.Prefs;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ImageUtil_Factory implements Factory<ImageUtil> {
  private final Provider<Prefs> prefsProvider;

  private final Provider<GLFileUtil> fileUtilProvider;

  public ImageUtil_Factory(Provider<Prefs> prefsProvider, Provider<GLFileUtil> fileUtilProvider) {
    this.prefsProvider = prefsProvider;
    this.fileUtilProvider = fileUtilProvider;
  }

  @Override
  public ImageUtil get() {
    return new ImageUtil(prefsProvider.get(), fileUtilProvider.get());
  }

  public static Factory<ImageUtil> create(
      Provider<Prefs> prefsProvider, Provider<GLFileUtil> fileUtilProvider) {
    return new ImageUtil_Factory(prefsProvider, fileUtilProvider);
  }
}
