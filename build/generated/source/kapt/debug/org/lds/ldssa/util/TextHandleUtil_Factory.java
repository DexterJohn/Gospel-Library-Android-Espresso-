package org.lds.ldssa.util;

import dagger.internal.Factory;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class TextHandleUtil_Factory implements Factory<TextHandleUtil> {
  private static final TextHandleUtil_Factory INSTANCE = new TextHandleUtil_Factory();

  @Override
  public TextHandleUtil get() {
    return new TextHandleUtil();
  }

  public static Factory<TextHandleUtil> create() {
    return INSTANCE;
  }
}
