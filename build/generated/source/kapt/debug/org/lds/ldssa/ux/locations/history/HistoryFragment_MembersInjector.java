package org.lds.ldssa.ux.locations.history;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.gl.history.HistoryManager;
import org.lds.ldssa.ui.fragment.BaseFragment_MembersInjector;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class HistoryFragment_MembersInjector implements MembersInjector<HistoryFragment> {
  private final Provider<Bus> busProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<HistoryManager> historyManagerProvider;

  public HistoryFragment_MembersInjector(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<HistoryManager> historyManagerProvider) {
    this.busProvider = busProvider;
    this.ccProvider = ccProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.historyManagerProvider = historyManagerProvider;
  }

  public static MembersInjector<HistoryFragment> create(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<HistoryManager> historyManagerProvider) {
    return new HistoryFragment_MembersInjector(
        busProvider, ccProvider, internalIntentsProvider, historyManagerProvider);
  }

  @Override
  public void injectMembers(HistoryFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, busProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, ccProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectHistoryManager(instance, historyManagerProvider.get());
  }

  public static void injectInternalIntents(
      HistoryFragment instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectHistoryManager(HistoryFragment instance, HistoryManager historyManager) {
    instance.historyManager = historyManager;
  }
}
