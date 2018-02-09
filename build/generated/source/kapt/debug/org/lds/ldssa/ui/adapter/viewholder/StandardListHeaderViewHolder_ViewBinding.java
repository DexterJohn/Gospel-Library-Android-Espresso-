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

public class StandardListHeaderViewHolder_ViewBinding implements Unbinder {
  private StandardListHeaderViewHolder target;

  @UiThread
  public StandardListHeaderViewHolder_ViewBinding(StandardListHeaderViewHolder target,
      View source) {
    this.target = target;

    target.textView = Utils.findRequiredViewAsType(source, R.id.list_header_standard_text, "field 'textView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StandardListHeaderViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textView = null;
  }
}
