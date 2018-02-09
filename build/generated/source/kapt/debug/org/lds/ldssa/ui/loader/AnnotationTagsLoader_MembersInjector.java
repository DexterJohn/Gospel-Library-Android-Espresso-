package org.lds.ldssa.ui.loader;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.model.database.userdata.tag.TagManager;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AnnotationTagsLoader_MembersInjector
    implements MembersInjector<AnnotationTagsLoader> {
  private final Provider<TagManager> tagManagerProvider;

  public AnnotationTagsLoader_MembersInjector(Provider<TagManager> tagManagerProvider) {
    this.tagManagerProvider = tagManagerProvider;
  }

  public static MembersInjector<AnnotationTagsLoader> create(
      Provider<TagManager> tagManagerProvider) {
    return new AnnotationTagsLoader_MembersInjector(tagManagerProvider);
  }

  @Override
  public void injectMembers(AnnotationTagsLoader instance) {
    injectTagManager(instance, tagManagerProvider.get());
  }

  public static void injectTagManager(AnnotationTagsLoader instance, TagManager tagManager) {
    instance.tagManager = tagManager;
  }
}
