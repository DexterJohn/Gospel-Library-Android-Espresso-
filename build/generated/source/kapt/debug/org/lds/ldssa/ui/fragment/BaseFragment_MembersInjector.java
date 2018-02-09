package org.lds.ldssa.ui.fragment;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class BaseFragment_MembersInjector implements MembersInjector<BaseFragment> {
  private final Provider<Bus> busProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  public BaseFragment_MembersInjector(
      Provider<Bus> busProvider, Provider<CoroutineContextProvider> ccProvider) {
    this.busProvider = busProvider;
    this.ccProvider = ccProvider;
  }

  public static MembersInjector<BaseFragment> create(
      Provider<Bus> busProvider, Provider<CoroutineContextProvider> ccProvider) {
    return new BaseFragment_MembersInjector(busProvider, ccProvider);
  }

  @Override
  public void injectMembers(BaseFragment instance) {
    injectBus(instance, busProvider.get());
    injectCc(instance, ccProvider.get());
  }

  public static void injectBus(BaseFragment instance, Bus bus) {
    instance.bus = bus;
  }

  public static void injectCc(BaseFragment instance, CoroutineContextProvider cc) {
    instance.cc = cc;
  }
}
