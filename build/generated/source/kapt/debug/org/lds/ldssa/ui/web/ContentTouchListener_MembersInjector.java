package org.lds.ldssa.ui.web;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.util.TextHandleUtil;
import org.lds.mobile.util.LdsUiUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class ContentTouchListener_MembersInjector
    implements MembersInjector<ContentTouchListener> {
  private final Provider<LdsUiUtil> uiUtilProvider;

  private final Provider<TextHandleUtil> textHandleUtilProvider;

  private final Provider<ContentJsInvoker> contentJsInvokerProvider;

  public ContentTouchListener_MembersInjector(
      Provider<LdsUiUtil> uiUtilProvider,
      Provider<TextHandleUtil> textHandleUtilProvider,
      Provider<ContentJsInvoker> contentJsInvokerProvider) {
    this.uiUtilProvider = uiUtilProvider;
    this.textHandleUtilProvider = textHandleUtilProvider;
    this.contentJsInvokerProvider = contentJsInvokerProvider;
  }

  public static MembersInjector<ContentTouchListener> create(
      Provider<LdsUiUtil> uiUtilProvider,
      Provider<TextHandleUtil> textHandleUtilProvider,
      Provider<ContentJsInvoker> contentJsInvokerProvider) {
    return new ContentTouchListener_MembersInjector(
        uiUtilProvider, textHandleUtilProvider, contentJsInvokerProvider);
  }

  @Override
  public void injectMembers(ContentTouchListener instance) {
    injectUiUtil(instance, uiUtilProvider.get());
    injectTextHandleUtil(instance, textHandleUtilProvider.get());
    injectContentJsInvoker(instance, contentJsInvokerProvider.get());
  }

  public static void injectUiUtil(ContentTouchListener instance, LdsUiUtil uiUtil) {
    instance.uiUtil = uiUtil;
  }

  public static void injectTextHandleUtil(
      ContentTouchListener instance, TextHandleUtil textHandleUtil) {
    instance.textHandleUtil = textHandleUtil;
  }

  public static void injectContentJsInvoker(
      ContentTouchListener instance, ContentJsInvoker contentJsInvoker) {
    instance.contentJsInvoker = contentJsInvoker;
  }
}
