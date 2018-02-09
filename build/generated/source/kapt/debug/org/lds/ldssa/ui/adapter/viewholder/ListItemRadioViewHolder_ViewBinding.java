// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.adapter.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;

public class ListItemRadioViewHolder_ViewBinding implements Unbinder {
  private ListItemRadioViewHolder target;

  @UiThread
  public ListItemRadioViewHolder_ViewBinding(ListItemRadioViewHolder target, View source) {
    this.target = target;

    target.radioButton = Utils.findRequiredViewAsType(source, R.id.list_item_radio_button, "field 'radioButton'", RadioButton.class);
    target.textView = Utils.findRequiredViewAsType(source, R.id.list_item_radio_text, "field 'textView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ListItemRadioViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.radioButton = null;
    target.textView = null;
  }
}
