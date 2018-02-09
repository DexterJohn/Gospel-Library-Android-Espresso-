package org.lds.ldssa.util.annotations;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.tag.TagManager;
import org.lds.ldssa.sync.AnnotationSyncScheduler;
import org.lds.ldssa.util.AnalyticsUtil;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TagUtil_Factory implements Factory<TagUtil> {
  private final Provider<Bus> arg0Provider;

  private final Provider<TagManager> arg1Provider;

  private final Provider<AnnotationManager> arg2Provider;

  private final Provider<AnnotationSyncScheduler> arg3Provider;

  private final Provider<AnalyticsUtil> arg4Provider;

  public TagUtil_Factory(
      Provider<Bus> arg0Provider,
      Provider<TagManager> arg1Provider,
      Provider<AnnotationManager> arg2Provider,
      Provider<AnnotationSyncScheduler> arg3Provider,
      Provider<AnalyticsUtil> arg4Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
  }

  @Override
  public TagUtil get() {
    return new TagUtil(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get());
  }

  public static Factory<TagUtil> create(
      Provider<Bus> arg0Provider,
      Provider<TagManager> arg1Provider,
      Provider<AnnotationManager> arg2Provider,
      Provider<AnnotationSyncScheduler> arg3Provider,
      Provider<AnalyticsUtil> arg4Provider) {
    return new TagUtil_Factory(
        arg0Provider, arg1Provider, arg2Provider, arg3Provider, arg4Provider);
  }
}
