package org.lds.ldssa;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldsaccount.LDSAccountUtil;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.sync.AnnotationSync;
import org.lds.ldssa.task.TipsUpdateTask;
import org.lds.ldssa.util.AppUpdateUtil;
import org.lds.ldssa.util.CatalogUpdateUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GLUpdateLifecycle_Factory implements Factory<GLUpdateLifecycle> {
  private final Provider<Prefs> arg0Provider;

  private final Provider<CoroutineContextProvider> arg1Provider;

  private final Provider<LDSAccountUtil> arg2Provider;

  private final Provider<AppUpdateUtil> arg3Provider;

  private final Provider<CatalogUpdateUtil> arg4Provider;

  private final Provider<TipsUpdateTask> arg5Provider;

  private final Provider<AnnotationSync> arg6Provider;

  private final Provider<InternalIntents> arg7Provider;

  public GLUpdateLifecycle_Factory(
      Provider<Prefs> arg0Provider,
      Provider<CoroutineContextProvider> arg1Provider,
      Provider<LDSAccountUtil> arg2Provider,
      Provider<AppUpdateUtil> arg3Provider,
      Provider<CatalogUpdateUtil> arg4Provider,
      Provider<TipsUpdateTask> arg5Provider,
      Provider<AnnotationSync> arg6Provider,
      Provider<InternalIntents> arg7Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
    this.arg4Provider = arg4Provider;
    this.arg5Provider = arg5Provider;
    this.arg6Provider = arg6Provider;
    this.arg7Provider = arg7Provider;
  }

  @Override
  public GLUpdateLifecycle get() {
    return new GLUpdateLifecycle(
        arg0Provider.get(),
        arg1Provider.get(),
        arg2Provider.get(),
        arg3Provider.get(),
        arg4Provider.get(),
        arg5Provider,
        arg6Provider.get(),
        arg7Provider.get());
  }

  public static Factory<GLUpdateLifecycle> create(
      Provider<Prefs> arg0Provider,
      Provider<CoroutineContextProvider> arg1Provider,
      Provider<LDSAccountUtil> arg2Provider,
      Provider<AppUpdateUtil> arg3Provider,
      Provider<CatalogUpdateUtil> arg4Provider,
      Provider<TipsUpdateTask> arg5Provider,
      Provider<AnnotationSync> arg6Provider,
      Provider<InternalIntents> arg7Provider) {
    return new GLUpdateLifecycle_Factory(
        arg0Provider,
        arg1Provider,
        arg2Provider,
        arg3Provider,
        arg4Provider,
        arg5Provider,
        arg6Provider,
        arg7Provider);
  }
}
