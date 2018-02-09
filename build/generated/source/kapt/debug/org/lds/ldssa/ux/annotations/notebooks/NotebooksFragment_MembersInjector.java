package org.lds.ldssa.ux.annotations.notebooks;

import android.arch.lifecycle.ViewModelProvider;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.sync.AnnotationSync;
import org.lds.ldssa.ui.fragment.BaseFragment_MembersInjector;
import org.lds.ldssa.util.NotebookUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class NotebooksFragment_MembersInjector implements MembersInjector<NotebooksFragment> {
  private final Provider<Bus> busProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<NotebookUtil> notebookUtilProvider;

  private final Provider<AnnotationSync> annotationSyncProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  public NotebooksFragment_MembersInjector(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<NotebookUtil> notebookUtilProvider,
      Provider<AnnotationSync> annotationSyncProvider,
      Provider<Prefs> prefsProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    this.busProvider = busProvider;
    this.ccProvider = ccProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.notebookUtilProvider = notebookUtilProvider;
    this.annotationSyncProvider = annotationSyncProvider;
    this.prefsProvider = prefsProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<NotebooksFragment> create(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<NotebookUtil> notebookUtilProvider,
      Provider<AnnotationSync> annotationSyncProvider,
      Provider<Prefs> prefsProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    return new NotebooksFragment_MembersInjector(
        busProvider,
        ccProvider,
        internalIntentsProvider,
        notebookUtilProvider,
        annotationSyncProvider,
        prefsProvider,
        viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(NotebooksFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, busProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, ccProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectNotebookUtil(instance, notebookUtilProvider.get());
    injectAnnotationSync(instance, annotationSyncProvider.get());
    injectPrefs(instance, prefsProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
  }

  public static void injectInternalIntents(
      NotebooksFragment instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectNotebookUtil(NotebooksFragment instance, NotebookUtil notebookUtil) {
    instance.notebookUtil = notebookUtil;
  }

  public static void injectAnnotationSync(
      NotebooksFragment instance, AnnotationSync annotationSync) {
    instance.annotationSync = annotationSync;
  }

  public static void injectPrefs(NotebooksFragment instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectViewModelFactory(
      NotebooksFragment instance, ViewModelProvider.Factory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }
}
