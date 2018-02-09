package org.lds.ldssa.ui.loader;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.gl.history.HistoryManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class HistoryLoader_MembersInjector implements MembersInjector<HistoryLoader> {
  private final Provider<HistoryManager> historyManagerProvider;

  public HistoryLoader_MembersInjector(Provider<HistoryManager> historyManagerProvider) {
    this.historyManagerProvider = historyManagerProvider;
  }

  public static MembersInjector<HistoryLoader> create(
      Provider<HistoryManager> historyManagerProvider) {
    return new HistoryLoader_MembersInjector(historyManagerProvider);
  }

  @Override
  public void injectMembers(HistoryLoader instance) {
    injectHistoryManager(instance, historyManagerProvider.get());
  }

  public static void injectHistoryManager(HistoryLoader instance, HistoryManager historyManager) {
    instance.historyManager = historyManager;
  }
}
