package org.lds.ldssa.ux.tips.pages.tip;

import android.arch.lifecycle.ViewModelProvider;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.ui.fragment.BaseFragment_MembersInjector;
import org.lds.ldssa.util.GLFileUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TipFragment_MembersInjector implements MembersInjector<TipFragment> {
  private final Provider<Bus> busProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  private final Provider<GLFileUtil> fileUtilProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  public TipFragment_MembersInjector(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<Prefs> prefsProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    this.busProvider = busProvider;
    this.ccProvider = ccProvider;
    this.fileUtilProvider = fileUtilProvider;
    this.prefsProvider = prefsProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<TipFragment> create(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<GLFileUtil> fileUtilProvider,
      Provider<Prefs> prefsProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    return new TipFragment_MembersInjector(
        busProvider, ccProvider, fileUtilProvider, prefsProvider, viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(TipFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, busProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, ccProvider.get());
    injectFileUtil(instance, fileUtilProvider.get());
    injectPrefs(instance, prefsProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
  }

  public static void injectFileUtil(TipFragment instance, GLFileUtil fileUtil) {
    instance.fileUtil = fileUtil;
  }

  public static void injectPrefs(TipFragment instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectViewModelFactory(
      TipFragment instance, ViewModelProvider.Factory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }
}
