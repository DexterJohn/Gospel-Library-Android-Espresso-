package org.lds.ldssa.ux.about;

import com.google.gson.Gson;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.link.LinkManager;
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager;
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AnnotationInfoActivity_MembersInjector
    implements MembersInjector<AnnotationInfoActivity> {
  private final Provider<AnnotationManager> annotationManagerProvider;

  private final Provider<LinkManager> linkManagerProvider;

  private final Provider<NotebookAnnotationManager> notebookAnnotationManagerProvider;

  private final Provider<NotebookManager> notebookManagerProvider;

  private final Provider<Gson> gsonProvider;

  public AnnotationInfoActivity_MembersInjector(
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<LinkManager> linkManagerProvider,
      Provider<NotebookAnnotationManager> notebookAnnotationManagerProvider,
      Provider<NotebookManager> notebookManagerProvider,
      Provider<Gson> gsonProvider) {
    this.annotationManagerProvider = annotationManagerProvider;
    this.linkManagerProvider = linkManagerProvider;
    this.notebookAnnotationManagerProvider = notebookAnnotationManagerProvider;
    this.notebookManagerProvider = notebookManagerProvider;
    this.gsonProvider = gsonProvider;
  }

  public static MembersInjector<AnnotationInfoActivity> create(
      Provider<AnnotationManager> annotationManagerProvider,
      Provider<LinkManager> linkManagerProvider,
      Provider<NotebookAnnotationManager> notebookAnnotationManagerProvider,
      Provider<NotebookManager> notebookManagerProvider,
      Provider<Gson> gsonProvider) {
    return new AnnotationInfoActivity_MembersInjector(
        annotationManagerProvider,
        linkManagerProvider,
        notebookAnnotationManagerProvider,
        notebookManagerProvider,
        gsonProvider);
  }

  @Override
  public void injectMembers(AnnotationInfoActivity instance) {
    injectAnnotationManager(instance, annotationManagerProvider.get());
    injectLinkManager(instance, linkManagerProvider.get());
    injectNotebookAnnotationManager(instance, notebookAnnotationManagerProvider.get());
    injectNotebookManager(instance, notebookManagerProvider.get());
    injectGson(instance, gsonProvider.get());
  }

  public static void injectAnnotationManager(
      AnnotationInfoActivity instance, AnnotationManager annotationManager) {
    instance.annotationManager = annotationManager;
  }

  public static void injectLinkManager(AnnotationInfoActivity instance, LinkManager linkManager) {
    instance.linkManager = linkManager;
  }

  public static void injectNotebookAnnotationManager(
      AnnotationInfoActivity instance, NotebookAnnotationManager notebookAnnotationManager) {
    instance.notebookAnnotationManager = notebookAnnotationManager;
  }

  public static void injectNotebookManager(
      AnnotationInfoActivity instance, NotebookManager notebookManager) {
    instance.notebookManager = notebookManager;
  }

  public static void injectGson(AnnotationInfoActivity instance, Gson gson) {
    instance.gson = gson;
  }
}
