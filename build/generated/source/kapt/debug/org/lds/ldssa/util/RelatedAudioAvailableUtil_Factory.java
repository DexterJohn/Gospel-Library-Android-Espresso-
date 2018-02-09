package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.content.relatedaudioitem.RelatedAudioItemManager;
import org.lds.ldssa.model.prefs.Prefs;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RelatedAudioAvailableUtil_Factory implements Factory<RelatedAudioAvailableUtil> {
  private final Provider<Prefs> arg0Provider;

  private final Provider<RelatedAudioItemManager> arg1Provider;

  public RelatedAudioAvailableUtil_Factory(
      Provider<Prefs> arg0Provider, Provider<RelatedAudioItemManager> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public RelatedAudioAvailableUtil get() {
    return new RelatedAudioAvailableUtil(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<RelatedAudioAvailableUtil> create(
      Provider<Prefs> arg0Provider, Provider<RelatedAudioItemManager> arg1Provider) {
    return new RelatedAudioAvailableUtil_Factory(arg0Provider, arg1Provider);
  }
}
