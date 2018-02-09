// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.adapter.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;

public class NavigationTrailViewHolder_ViewBinding implements Unbinder {
  private NavigationTrailViewHolder target;

  @UiThread
  public NavigationTrailViewHolder_ViewBinding(NavigationTrailViewHolder target, View source) {
    this.target = target;

    target.titleView = Utils.findRequiredViewAsType(source, R.id.navigation_trail_title, "field 'titleView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NavigationTrailViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.titleView = null;
  }
}
