package org.lds.ldssa.model.database.userdata.synccontentitemannotationsqueueitem;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.DatabaseManager;
import org.lds.ldssa.util.UserdataDbUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SyncContentItemAnnotationsQueueItemManager_Factory
    implements Factory<SyncContentItemAnnotationsQueueItemManager> {
  private final Provider<DatabaseManager> arg0Provider;

  private final Provider<UserdataDbUtil> arg1Provider;

  public SyncContentItemAnnotationsQueueItemManager_Factory(
      Provider<DatabaseManager> arg0Provider, Provider<UserdataDbUtil> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public SyncContentItemAnnotationsQueueItemManager get() {
    return new SyncContentItemAnnotationsQueueItemManager(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<SyncContentItemAnnotationsQueueItemManager> create(
      Provider<DatabaseManager> arg0Provider, Provider<UserdataDbUtil> arg1Provider) {
    return new SyncContentItemAnnotationsQueueItemManager_Factory(arg0Provider, arg1Provider);
  }
}
