package org.lds.ldssa.model.database.content.subitemtopic;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SubitemTopicManager_Factory implements Factory<SubitemTopicManager> {
  private final Provider<DatabaseManager> arg0Provider;

  public SubitemTopicManager_Factory(Provider<DatabaseManager> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }

  @Override
  public SubitemTopicManager get() {
    return new SubitemTopicManager(arg0Provider.get());
  }

  public static Factory<SubitemTopicManager> create(Provider<DatabaseManager> arg0Provider) {
    return new SubitemTopicManager_Factory(arg0Provider);
  }
}
