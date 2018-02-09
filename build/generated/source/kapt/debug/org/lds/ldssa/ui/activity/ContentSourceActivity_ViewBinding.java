// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;

public class ContentSourceActivity_ViewBinding implements Unbinder {
  private ContentSourceActivity target;

  @UiThread
  public ContentSourceActivity_ViewBinding(ContentSourceActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ContentSourceActivity_ViewBinding(ContentSourceActivity target, View source) {
    this.target = target;

    target.countTextView = Utils.findRequiredViewAsType(source, R.id.countTextView, "field 'countTextView'", TextView.class);
    target.sourceWebView = Utils.findRequiredViewAsType(source, R.id.sourceWebView, "field 'sourceWebView'", WebView.class);
    target.sourceSearchEditText = Utils.findRequiredViewAsType(source, R.id.sourceSearchEditText, "field 'sourceSearchEditText'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ContentSourceActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.countTextView = null;
    target.sourceWebView = null;
    target.sourceSearchEditText = null;
  }
}
