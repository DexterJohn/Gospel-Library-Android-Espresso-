// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ux.customcollection.collections;

import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public final class CustomCollectionsActivity_ViewBinding implements Unbinder {
  private CustomCollectionsActivity target;

  private View view2131427854;

  @UiThread
  public CustomCollectionsActivity_ViewBinding(CustomCollectionsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CustomCollectionsActivity_ViewBinding(final CustomCollectionsActivity target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, 2131427854, "method 'onAddNewClick'");
    view2131427854 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onAddNewClick();
      }
    });
  }

  @Override
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131427854.setOnClickListener(null);
    view2131427854 = null;
  }
}
