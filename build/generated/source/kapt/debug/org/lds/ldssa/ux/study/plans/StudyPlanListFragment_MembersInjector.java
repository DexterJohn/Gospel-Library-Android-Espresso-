package org.lds.ldssa.ux.study.plans;

import android.arch.lifecycle.ViewModelProvider;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.ui.fragment.BaseFragment_MembersInjector;
import org.lds.ldssa.util.ToastUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class StudyPlanListFragment_MembersInjector
    implements MembersInjector<StudyPlanListFragment> {
  private final Provider<Bus> busProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  private final Provider<ToastUtil> toastUtilProvider;

  public StudyPlanListFragment_MembersInjector(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider,
      Provider<ToastUtil> toastUtilProvider) {
    this.busProvider = busProvider;
    this.ccProvider = ccProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
    this.toastUtilProvider = toastUtilProvider;
  }

  public static MembersInjector<StudyPlanListFragment> create(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider,
      Provider<ToastUtil> toastUtilProvider) {
    return new StudyPlanListFragment_MembersInjector(
        busProvider,
        ccProvider,
        internalIntentsProvider,
        viewModelFactoryProvider,
        toastUtilProvider);
  }

  @Override
  public void injectMembers(StudyPlanListFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, busProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, ccProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
    injectToastUtil(instance, toastUtilProvider.get());
  }

  public static void injectInternalIntents(
      StudyPlanListFragment instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectViewModelFactory(
      StudyPlanListFragment instance, ViewModelProvider.Factory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }

  public static void injectToastUtil(StudyPlanListFragment instance, ToastUtil toastUtil) {
    instance.toastUtil = toastUtil;
  }
}
