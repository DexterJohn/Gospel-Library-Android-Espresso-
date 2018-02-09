package org.lds.ldssa.util.annotations;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class AnnotationListUtil_Factory implements Factory<AnnotationListUtil> {
  private static final AnnotationListUtil_Factory INSTANCE = new AnnotationListUtil_Factory();

  @Override
  public AnnotationListUtil get() {
    return new AnnotationListUtil();
  }

  public static Factory<AnnotationListUtil> create() {
    return INSTANCE;
  }
}
