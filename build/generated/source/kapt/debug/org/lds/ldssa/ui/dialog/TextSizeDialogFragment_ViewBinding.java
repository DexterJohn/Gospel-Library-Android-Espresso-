// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;

public class TextSizeDialogFragment_ViewBinding implements Unbinder {
  private TextSizeDialogFragment target;

  @UiThread
  public TextSizeDialogFragment_ViewBinding(TextSizeDialogFragment target, View source) {
    this.target = target;

    target.textSizeBar = Utils.findRequiredViewAsType(source, R.id.seekbar, "field 'textSizeBar'", SeekBar.class);
    target.sampleTextView = Utils.findRequiredViewAsType(source, R.id.text, "field 'sampleTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TextSizeDialogFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textSizeBar = null;
    target.sampleTextView = null;
  }
}
