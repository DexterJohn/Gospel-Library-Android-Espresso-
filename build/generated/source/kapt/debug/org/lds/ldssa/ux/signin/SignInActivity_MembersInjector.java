package org.lds.ldssa.ux.signin;

import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.util.AccountUtil;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class SignInActivity_MembersInjector implements MembersInjector<SignInActivity> {
  private final Provider<AccountUtil> accountUtilProvider;

  private final Provider<Analytics> analyticsProvider;

  public SignInActivity_MembersInjector(
      Provider<AccountUtil> accountUtilProvider, Provider<Analytics> analyticsProvider) {
    this.accountUtilProvider = accountUtilProvider;
    this.analyticsProvider = analyticsProvider;
  }

  public static MembersInjector<SignInActivity> create(
      Provider<AccountUtil> accountUtilProvider, Provider<Analytics> analyticsProvider) {
    return new SignInActivity_MembersInjector(accountUtilProvider, analyticsProvider);
  }

  @Override
  public void injectMembers(SignInActivity instance) {
    injectAccountUtil(instance, accountUtilProvider.get());
    injectAnalytics(instance, analyticsProvider.get());
  }

  public static void injectAccountUtil(SignInActivity instance, AccountUtil accountUtil) {
    instance.accountUtil = accountUtil;
  }

  public static void injectAnalytics(SignInActivity instance, Analytics analytics) {
    instance.analytics = analytics;
  }
}
