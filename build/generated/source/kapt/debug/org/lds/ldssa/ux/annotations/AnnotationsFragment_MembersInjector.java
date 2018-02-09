package org.lds.ldssa.ux.annotations;

import android.arch.lifecycle.ViewModelProvider;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager;
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager;
import org.lds.ldssa.ui.fragment.BaseFragment_MembersInjector;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AnnotationsFragment_MembersInjector
    implements MembersInjector<AnnotationsFragment> {
  private final Provider<Bus> busProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<NotebookManager> notebookManagerProvider;

  private final Provider<SubItemMetadataManager> subItemMetadataManagerProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  public AnnotationsFragment_MembersInjector(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<NotebookManager> notebookManagerProvider,
      Provider<SubItemMetadataManager> subItemMetadataManagerProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    this.busProvider = busProvider;
    this.ccProvider = ccProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.notebookManagerProvider = notebookManagerProvider;
    this.subItemMetadataManagerProvider = subItemMetadataManagerProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<AnnotationsFragment> create(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<NotebookManager> notebookManagerProvider,
      Provider<SubItemMetadataManager> subItemMetadataManagerProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    return new AnnotationsFragment_MembersInjector(
        busProvider,
        ccProvider,
        internalIntentsProvider,
        notebookManagerProvider,
        subItemMetadataManagerProvider,
        viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(AnnotationsFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, busProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, ccProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectNotebookManager(instance, notebookManagerProvider.get());
    injectSubItemMetadataManager(instance, subItemMetadataManagerProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
  }

  public static void injectInternalIntents(
      AnnotationsFragment instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectNotebookManager(
      AnnotationsFragment instance, NotebookManager notebookManager) {
    instance.notebookManager = notebookManager;
  }

  public static void injectSubItemMetadataManager(
      AnnotationsFragment instance, SubItemMetadataManager subItemMetadataManager) {
    instance.subItemMetadataManager = subItemMetadataManager;
  }

  public static void injectViewModelFactory(
      AnnotationsFragment instance, ViewModelProvider.Factory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }
}
