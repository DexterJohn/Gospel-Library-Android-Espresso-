package org.lds.ldssa.ui.menu;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.catalog.languagename.LanguageNameManager;
import org.lds.ldssa.model.database.gl.recentlanguage.RecentLanguageManager;
import org.lds.ldssa.util.LanguageUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class CommonMenu_Factory implements Factory<CommonMenu> {
  private final Provider<InternalIntents> arg0Provider;

  private final Provider<LanguageNameManager> arg1Provider;

  private final Provider<RecentLanguageManager> arg2Provider;

  private final Provider<LanguageUtil> arg3Provider;

  public CommonMenu_Factory(
      Provider<InternalIntents> arg0Provider,
      Provider<LanguageNameManager> arg1Provider,
      Provider<RecentLanguageManager> arg2Provider,
      Provider<LanguageUtil> arg3Provider) {
    this.arg0Provider = arg0Provider;
    this.arg1Provider = arg1Provider;
    this.arg2Provider = arg2Provider;
    this.arg3Provider = arg3Provider;
  }

  @Override
  public CommonMenu get() {
    return new CommonMenu(
        arg0Provider.get(), arg1Provider.get(), arg2Provider.get(), arg3Provider.get());
  }

  public static Factory<CommonMenu> create(
      Provider<InternalIntents> arg0Provider,
      Provider<LanguageNameManager> arg1Provider,
      Provider<RecentLanguageManager> arg2Provider,
      Provider<LanguageUtil> arg3Provider) {
    return new CommonMenu_Factory(arg0Provider, arg1Provider, arg2Provider, arg3Provider);
  }
}
