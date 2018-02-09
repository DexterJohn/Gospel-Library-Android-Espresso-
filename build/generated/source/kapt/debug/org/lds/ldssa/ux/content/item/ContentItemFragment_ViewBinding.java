// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ux.content.item;

import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public final class ContentItemFragment_ViewBinding implements Unbinder {
  private ContentItemFragment target;

  private View view2131427722;

  private View view2131427725;

  private View view2131427723;

  @UiThread
  public ContentItemFragment_ViewBinding(final ContentItemFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, 2131427722, "method 'onMarkTextCloseButtonClick'");
    view2131427722 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onMarkTextCloseButtonClick();
      }
    });
    view = Utils.findRequiredView(source, 2131427725, "method 'onMarkTextPrevClick'");
    view2131427725 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onMarkTextPrevClick();
      }
    });
    view = Utils.findRequiredView(source, 2131427723, "method 'onMarkTextNextClick'");
    view2131427723 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onMarkTextNextClick();
      }
    });
  }

  @Override
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131427722.setOnClickListener(null);
    view2131427722 = null;
    view2131427725.setOnClickListener(null);
    view2131427725 = null;
    view2131427723.setOnClickListener(null);
    view2131427723 = null;
  }
}
