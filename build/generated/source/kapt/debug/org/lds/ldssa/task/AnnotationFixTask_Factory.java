package org.lds.ldssa.task;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.bookmark.BookmarkManager;
import org.lds.ldssa.model.database.userdata.tag.TagManager;
import org.lds.ldssa.sync.AnnotationSyncScheduler;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AnnotationFixTask_Factory implements Factory<AnnotationFixTask> {
  private final Provider<AnnotationManager> arg0Provider;

  private final Provider<BookmarkManager> arg1Provider;

  private final Provider<TagManager> arg2Provider;

  private final Provider<AnnotationSyncScheduler> arg3Provider;

  public AnnotationFixTask_Factory(
      Provider<AnnotationManager> arg0Provider,
      Provider<BookmarkManager> arg1Provider,
      Provider<TagManager> arg2Provider,
      Provider<AnnotationSyncScheduler> arg3Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
  }

  @Override
  public AnnotationFixTask get() {
    return new AnnotationFixTask(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get(), arg3Provider.get());
  }

  public static Factory<AnnotationFixTask> create(
      Provider<AnnotationManager> arg0Provider,
      Provider<BookmarkManager> arg1Provider,
      Provider<TagManager> arg2Provider,
      Provider<AnnotationSyncScheduler> arg3Provider) {
    return new AnnotationFixTask_Factory(arg0Provider, arg1Provider, arg2Provider, arg3Provider);
  }
}
