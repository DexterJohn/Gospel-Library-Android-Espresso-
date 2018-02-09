package org.lds.ldssa.model.database.tips.tipsmetadata;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TipsMetaDataManager_Factory implements Factory<TipsMetaDataManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public TipsMetaDataManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public TipsMetaDataManager get() {
    return new TipsMetaDataManager(arg0Provider.get());
  }

  public static Factory<TipsMetaDataManager> create(Provider<DatabaseManager> arg0Provider) {
    return new TipsMetaDataManager_Factory(arg0Provider);
  }
}
