// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;

public class StartupActivity_ViewBinding implements Unbinder {
  private StartupActivity target;

  @UiThread
  public StartupActivity_ViewBinding(StartupActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public StartupActivity_ViewBinding(StartupActivity target, View source) {
    this.target = target;

    target.startupProgressBar = Utils.findRequiredViewAsType(source, R.id.startupProgressBar, "field 'startupProgressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StartupActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.startupProgressBar = null;
  }
}
