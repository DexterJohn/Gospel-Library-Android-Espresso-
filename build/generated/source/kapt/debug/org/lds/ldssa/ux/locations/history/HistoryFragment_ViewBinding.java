// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ux.locations.history;

import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public final class HistoryFragment_ViewBinding implements Unbinder {
  private HistoryFragment target;

  private View view2131427459;

  @UiThread
  public HistoryFragment_ViewBinding(final HistoryFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, 2131427459, "method 'onClearHistoryClicked'");
    view2131427459 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClearHistoryClicked();
      }
    });
  }

  @Override
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131427459.setOnClickListener(null);
    view2131427459 = null;
  }
}
