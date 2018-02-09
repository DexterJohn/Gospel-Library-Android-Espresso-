package org.lds.ldssa.ux.search;

import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.lds.ldssa.event.SearchFinishedEvent;
import pocketbus.Subscription;
import pocketbus.SubscriptionRegistration;
import pocketbus.ThreadMode;

public class SearchPresenterSubscriptionRegistration implements SubscriptionRegistration {
  private final WeakReference<SearchPresenter> targetRef;

  private final List<Subscription<?>> subscriptions;

  private Subscription<SearchFinishedEvent> subscription1 = new Subscription<SearchFinishedEvent>() {
    @Override
    public boolean handle(SearchFinishedEvent event) {
      SearchPresenter target = targetRef.get();
      if (target != null) {
        target.handle(event);
      }
      return target != null;
    }

    @Override
    public Class<SearchFinishedEvent> getEventClass() {
      return SearchFinishedEvent.class;
    }

    @Override
    public ThreadMode getThreadMode() {
      return ThreadMode.MAIN;
    }

    @Override
    public SearchPresenter getTarget() {
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

  public SearchPresenterSubscriptionRegistration(SearchPresenter target) {
    this.targetRef = new WeakReference<SearchPresenter>(target);
    List<Subscription<?>> subscriptions = new ArrayList<Subscription<?>>();
    subscriptions.add(subscription1);
    this.subscriptions = Collections.unmodifiableList(subscriptions);
  }

  @Override
  public List<Subscription<?>> getSubscriptions() {
    return subscriptions;
  }
}
