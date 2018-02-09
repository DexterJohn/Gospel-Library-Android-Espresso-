package org.lds.ldssa.ui.activity;

import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.lds.ldssa.event.BackgroundSnackbarEvent;
import org.lds.ldssa.event.account.AccountSignInPromptEvent;
import pocketbus.Subscription;
import pocketbus.SubscriptionRegistration;
import pocketbus.ThreadMode;

public class BaseActivitySubscriptionRegistration implements SubscriptionRegistration {
  private final WeakReference<BaseActivity> targetRef;

  private final List<Subscription<?>> subscriptions;

  private Subscription<BackgroundSnackbarEvent> subscription1 = new Subscription<BackgroundSnackbarEvent>() {
    @Override
    public boolean handle(BackgroundSnackbarEvent event) {
      BaseActivity target = targetRef.get();
      if (target != null) {
        target.handle(event);
      }
      return target != null;
    }

    @Override
    public Class<BackgroundSnackbarEvent> getEventClass() {
      return BackgroundSnackbarEvent.class;
    }

    @Override
    public ThreadMode getThreadMode() {
      return ThreadMode.MAIN;
    }

    @Override
    public BaseActivity getTarget() {
      return targetRef.get();
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Subscription<?>) {
        return this.getTarget() != null && this.getTarget().equals(((Subscription<?>)o).getTarget());
      }
      return false;
    }
  };

  private Subscription<AccountSignInPromptEvent> subscription2 = new Subscription<AccountSignInPromptEvent>() {
    @Override
    public boolean handle(AccountSignInPromptEvent event) {
      BaseActivity target = targetRef.get();
      if (target != null) {
        target.handle(event);
      }
      return target != null;
    }

    @Override
    public Class<AccountSignInPromptEvent> getEventClass() {
      return AccountSignInPromptEvent.class;
    }

    @Override
    public ThreadMode getThreadMode() {
      return ThreadMode.MAIN;
    }

    @Override
    public BaseActivity getTarget() {
      return targetRef.get();
    }

    @Override
    public boolean equals(Object o) {
      if (o instanceof Subscription<?>) {
        return this.getTarget() != null && this.getTarget().equals(((Subscription<?>)o).getTarget());
      }
      return false;
    }
  };

  public BaseActivitySubscriptionRegistration(BaseActivity target) {
    this.targetRef = new WeakReference<BaseActivity>(target);
    List<Subscription<?>> subscriptions = new ArrayList<Subscription<?>>();
    subscriptions.add(subscription1);
    subscriptions.add(subscription2);
    this.subscriptions = Collections.unmodifiableList(subscriptions);
  }

  @Override
  public List<Subscription<?>> getSubscriptions() {
    return subscriptions;
  }
}
