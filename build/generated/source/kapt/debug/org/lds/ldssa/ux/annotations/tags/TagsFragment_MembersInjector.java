package org.lds.ldssa.ux.annotations.tags;

import android.arch.lifecycle.ViewModelProvider;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.sync.AnnotationSync;
import org.lds.ldssa.ui.fragment.BaseFragment_MembersInjector;
import org.lds.ldssa.util.annotations.TagUtil;
import org.lds.mobile.coroutine.CoroutineContextProvider;
import pocketbus.Bus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TagsFragment_MembersInjector implements MembersInjector<TagsFragment> {
  private final Provider<Bus> busProvider;

  private final Provider<CoroutineContextProvider> ccProvider;

  private final Provider<InternalIntents> internalIntentsProvider;

  private final Provider<TagUtil> tagUtilProvider;

  private final Provider<AnnotationSync> annotationSyncProvider;

  private final Provider<AnnotationManager> annotationManagerProvider;

  private final Provider<Prefs> prefsProvider;

  private final Provider<ViewModelProvider.Factory> viewModelFactoryProvider;

  public TagsFragment_MembersInjector(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<TagUtil> tagUtilProvider,
      Provider<AnnotationSync> annotationSyncProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<Prefs> prefsProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    this.busProvider = busProvider;
    this.ccProvider = ccProvider;
    this.internalIntentsProvider = internalIntentsProvider;
    this.tagUtilProvider = tagUtilProvider;
    this.annotationSyncProvider = annotationSyncProvider;
    this.annotationManagerProvider = annotationManagerProvider;
    this.prefsProvider = prefsProvider;
    this.viewModelFactoryProvider = viewModelFactoryProvider;
  }

  public static MembersInjector<TagsFragment> create(
      Provider<Bus> busProvider,
      Provider<CoroutineContextProvider> ccProvider,
      Provider<InternalIntents> internalIntentsProvider,
      Provider<TagUtil> tagUtilProvider,
      Provider<AnnotationSync> annotationSyncProvider,
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<Prefs> prefsProvider,
      Provider<ViewModelProvider.Factory> viewModelFactoryProvider) {
    return new TagsFragment_MembersInjector(
        busProvider,
        ccProvider,
        internalIntentsProvider,
        tagUtilProvider,
        annotationSyncProvider,
        annotationManagerProvider,
        prefsProvider,
        viewModelFactoryProvider);
  }

  @Override
  public void injectMembers(TagsFragment instance) {
    BaseFragment_MembersInjector.injectBus(instance, busProvider.get());
    BaseFragment_MembersInjector.injectCc(instance, ccProvider.get());
    injectInternalIntents(instance, internalIntentsProvider.get());
    injectTagUtil(instance, tagUtilProvider.get());
    injectAnnotationSync(instance, annotationSyncProvider.get());
    injectAnnotationManager(instance, annotationManagerProvider.get());
    injectPrefs(instance, prefsProvider.get());
    injectViewModelFactory(instance, viewModelFactoryProvider.get());
  }

  public static void injectInternalIntents(TagsFragment instance, InternalIntents internalIntents) {
    instance.internalIntents = internalIntents;
  }

  public static void injectTagUtil(TagsFragment instance, TagUtil tagUtil) {
    instance.tagUtil = tagUtil;
  }

  public static void injectAnnotationSync(TagsFragment instance, AnnotationSync annotationSync) {
    instance.annotationSync = annotationSync;
  }

  public static void injectAnnotationManager(
      TagsFragment instance, AnnotationManager annotationManager) {
    instance.annotationManager = annotationManager;
  }

  public static void injectPrefs(TagsFragment instance, Prefs prefs) {
    instance.prefs = prefs;
  }

  public static void injectViewModelFactory(
      TagsFragment instance, ViewModelProvider.Factory viewModelFactory) {
    instance.viewModelFactory = viewModelFactory;
  }
}
