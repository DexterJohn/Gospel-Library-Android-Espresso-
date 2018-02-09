package org.lds.ldssa.task;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.util.TipsUpdateUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TipsUpdateTask_Factory implements Factory<TipsUpdateTask> {
  private final Provider<TipsUpdateUtil> tipsUpdateUtilProvider;

  public TipsUpdateTask_Factory(Provider<TipsUpdateUtil> tipsUpdateUtilProvider) {
    this.tipsUpdateUtilProvider = tipsUpdateUtilProvider;
  }

  @Override
  public TipsUpdateTask get() {
    return new TipsUpdateTask(tipsUpdateUtilProvider.get());
  }

  public static Factory<TipsUpdateTask> create(Provider<TipsUpdateUtil> tipsUpdateUtilProvider) {
    return new TipsUpdateTask_Factory(tipsUpdateUtilProvider);
  }
}
