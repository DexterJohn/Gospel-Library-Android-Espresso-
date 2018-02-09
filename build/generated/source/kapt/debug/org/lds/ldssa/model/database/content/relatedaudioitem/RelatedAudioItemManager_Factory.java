package org.lds.ldssa.model.database.content.relatedaudioitem;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.util.ContentItemUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class RelatedAudioItemManager_Factory implements Factory<RelatedAudioItemManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<ContentItemUtil> arg1Provider;

  public RelatedAudioItemManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<ContentItemUtil> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public RelatedAudioItemManager get() {
    return new RelatedAudioItemManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<RelatedAudioItemManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<ContentItemUtil> arg1Provider) {
    return new RelatedAudioItemManager_Factory(arg0Provider, arg1Provider);
  }
}
