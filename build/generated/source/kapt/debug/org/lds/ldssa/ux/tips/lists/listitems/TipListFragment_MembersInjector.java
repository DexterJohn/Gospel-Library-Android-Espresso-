package org.lds.ldssa.ux.tips.lists.listitems;

import android.arch.lifecycle.ViewModelProvider;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.ui.fragment.BaseFragment_MembersInjector;
import org.lds.ldssa.util.LanguageUtil;
import org.lds.ldssa.util.TipsUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TipListFragment_MembersInjector implements MembersInjector<TipListFragment> {
  private final Provider<Bus> busProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<LanguageUtil> languageUtilProvider;

  private final Provider<TipsUtil> tipsUtilProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  public TipListFragment_MembersInjector(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<LanguageUtil> languageUtilProvider,
      Provider<TipsUtil> tipsUtilProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    this.busProvider = busProvider;
    this.ccProvider = ccProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.languageUtilProvider = languageUtilProvider;
    this.tipsUtilProvider = tipsUtilProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<TipListFragment> create(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<LanguageUtil> languageUtilProvider,
      Provider<TipsUtil> tipsUtilProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    return new TipListFragment_MembersInjector(
        busProvider,
        ccProvider,
        internalIntentsProvider,
        languageUtilProvider,
        tipsUtilProvider,
        viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(TipListFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, busProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, ccProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectLanguageUtil(instance, languageUtilProvider.get());
    injectTipsUtil(instance, tipsUtilProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
  }

  public static void injectInternalIntents(
      TipListFragment instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectLanguageUtil(TipListFragment instance, LanguageUtil languageUtil) {
    instance.languageUtil = languageUtil;
  }

  public static void injectTipsUtil(TipListFragment instance, TipsUtil tipsUtil) {
    instance.tipsUtil = tipsUtil;
  }

  public static void injectViewModelFactory(
      TipListFragment instance, ViewModelProvider.Factory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }
}
