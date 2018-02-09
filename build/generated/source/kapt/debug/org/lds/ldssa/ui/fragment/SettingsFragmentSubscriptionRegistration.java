package org.lds.ldssa.ui.fragment;

import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.lds.ldssa.event.account.AccountSignOutEvent;
import org.lds.ldssa.event.sync.AnnotationSyncFinishedEvent;
import org.lds.ldssa.event.sync.AnnotationSyncStatusEvent;
import pocketbus.Subscription;
import pocketbus.SubscriptionRegistration;
import pocketbus.ThreadMode;

public class SettingsFragmentSubscriptionRegistration implements SubscriptionRegistration {
  private final WeakReference<SettingsFragment> targetRef;

  private final List<Subscription<?>> subscriptions;

  private Subscription<AccountSignOutEvent> subscription1 = new Subscription<AccountSignOutEvent>() {
    @Override
    public boolean handle(AccountSignOutEvent event) {
      SettingsFragment target = targetRef.get();
      if (target != null) {
        target.handle(event);
      }
      return target != null;
    }

    @Override
    public Class<AccountSignOutEvent> getEventClass() {
      return AccountSignOutEvent.class;
    }

    @Override
    public ThreadMode getThreadMode() {
      return ThreadMode.MAIN;
    }

    @Override
    public SettingsFragment getTarget() {
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

  private Subscription<AnnotationSyncFinishedEvent> subscription2 = new Subscription<AnnotationSyncFinishedEvent>() {
    @Override
    public boolean handle(AnnotationSyncFinishedEvent event) {
      SettingsFragment target = targetRef.get();
      if (target != null) {
        target.handle(event);
      }
      return target != null;
    }

    @Override
    public Class<AnnotationSyncFinishedEvent> getEventClass() {
      return AnnotationSyncFinishedEvent.class;
    }

    @Override
    public ThreadMode getThreadMode() {
      return ThreadMode.MAIN;
    }

    @Override
    public SettingsFragment getTarget() {
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

  private Subscription<AnnotationSyncStatusEvent> subscription3 = new Subscription<AnnotationSyncStatusEvent>() {
    @Override
    public boolean handle(AnnotationSyncStatusEvent event) {
      SettingsFragment target = targetRef.get();
      if (target != null) {
        target.handle(event);
      }
      return target != null;
    }

    @Override
    public Class<AnnotationSyncStatusEvent> getEventClass() {
      return AnnotationSyncStatusEvent.class;
    }

    @Override
    public ThreadMode getThreadMode() {
      return ThreadMode.MAIN;
    }

    @Override
    public SettingsFragment getTarget() {
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

  public SettingsFragmentSubscriptionRegistration(SettingsFragment target) {
    this.targetRef = new WeakReference<SettingsFragment>(target);
    List<Subscription<?>> subscriptions = new ArrayList<Subscription<?>>();
    subscriptions.add(subscription1);
    subscriptions.add(subscription2);
    subscriptions.add(subscription3);
    this.subscriptions = Collections.unmodifiableList(subscriptions);
  }

  @Override
  public List<Subscription<?>> getSubscriptions() {
    return subscriptions;
  }
}
