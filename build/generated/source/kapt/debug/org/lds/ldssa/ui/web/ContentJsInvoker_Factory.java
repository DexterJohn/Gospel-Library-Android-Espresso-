package org.lds.ldssa.ui.web;

import com.google.gson.Gson;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.util.annotations.AnnotationListUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ContentJsInvoker_Factory implements Factory<ContentJsInvoker> {
  private final Provider<AnnotationListUtil> arg0Provider;

  private final Provider<Gson> arg1Provider;

  public ContentJsInvoker_Factory(
      Provider<AnnotationListUtil> arg0Provider, Provider<Gson> arg1Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
  }

  @Override
  public ContentJsInvoker get() {
    return new ContentJsInvoker(arg0Provider.get(), arg1Provider.get());
  }

  public static Factory<ContentJsInvoker> create(
      Provider<AnnotationListUtil> arg0Provider, Provider<Gson> arg1Provider) {
    return new ContentJsInvoker_Factory(arg0Provider, arg1Provider);
  }
}
