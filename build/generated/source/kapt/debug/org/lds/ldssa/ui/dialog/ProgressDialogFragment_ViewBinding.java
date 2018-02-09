// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;

public class ProgressDialogFragment_ViewBinding implements Unbinder {
  private ProgressDialogFragment target;

  @UiThread
  public ProgressDialogFragment_ViewBinding(ProgressDialogFragment target, View source) {
    this.target = target;

    target.messageView = Utils.findRequiredViewAsType(source, R.id.progress_dialog_message, "field 'messageView'", TextView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progress_dialog_progress, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ProgressDialogFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.messageView = null;
    target.progressBar = null;
  }
}
