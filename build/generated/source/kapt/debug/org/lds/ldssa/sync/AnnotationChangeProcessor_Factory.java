package org.lds.ldssa.sync;

import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.catalog.item.ItemManager;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.synccontentitemannotationsqueueitem.SyncContentItemAnnotationsQueueItemManager;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.mobile.util.LdsTimeUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AnnotationChangeProcessor_Factory implements Factory<AnnotationChangeProcessor> {
  private final Provider<GLFileUtil> arg0Provider;

  private final Provider<LdsTimeUtil> arg1Provider;

  private final Provider<SubItemMetadataManager> arg2Provider;

  private final Provider<AnnotationManager> arg3Provider;

  private final Provider<ItemManager> arg4Provider;

  private final Provider<SyncContentItemAnnotationsQueueItemManager> arg5Provider;

  private final Provider<Gson> arg6Provider;

  public AnnotationChangeProcessor_Factory(
      Provider<GLFileUtil> arg0Provider,
      Provider<LdsTimeUtil> arg1Provider,
      Provider<SubItemMetadataManager> arg2Provider,
      Provider<AnnotationManager> arg3Provider,
      Provider<ItemManager> arg4Provider,
      Provider<SyncContentItemAnnotationsQueueItemManager> arg5Provider,
      Provider<Gson> arg6Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
    this.arg5Provider = arg5Provider;
    this.arg6Provider = arg6Provider;
  }

  @Override
  public AnnotationChangeProcessor get() {
    return new AnnotationChangeProcessor(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider.get(),
        arg6Provider.get());
  }

  public static Factory<AnnotationChangeProcessor> create(
      Provider<GLFileUtil> arg0Provider,
      Provider<LdsTimeUtil> arg1Provider,
      Provider<SubItemMetadataManager> arg2Provider,
      Provider<AnnotationManager> arg3Provider,
      Provider<ItemManager> arg4Provider,
      Provider<SyncContentItemAnnotationsQueueItemManager> arg5Provider,
      Provider<Gson> arg6Provider) {
    return new AnnotationChangeProcessor_Factory(
        arg0Provider,
        arg1Provider,
        arg2Provider,
        arg3Provider,
        arg4Provider,
        arg5Provider,
        arg6Provider);
  }
}
