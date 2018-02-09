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

public class SubtleListHeaderViewHolder_ViewBinding implements Unbinder {
  private SubtleListHeaderViewHolder target;

  @UiThread
  public SubtleListHeaderViewHolder_ViewBinding(SubtleListHeaderViewHolder target, View source) {
    this.target = target;

    target.textView = Utils.findRequiredViewAsType(source, R.id.titleTextView, "field 'textView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SubtleListHeaderViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textView = null;
  }
}
