package org.lds.ldssa.util.annotations;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.util.ContentParagraphUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class HighlightUtil_Factory implements Factory<HighlightUtil> {
  private final Provider<Prefs> arg0Provider;

  private final Provider<ContentParagraphUtil> arg1Provider;

  public HighlightUtil_Factory(
      Provider<Prefs> arg0Provider, Provider<ContentParagraphUtil> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public HighlightUtil get() {
    return new HighlightUtil(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<HighlightUtil> create(
      Provider<Prefs> arg0Provider, Provider<ContentParagraphUtil> arg1Provider) {
    return new HighlightUtil_Factory(arg0Provider, arg1Provider);
  }
}
