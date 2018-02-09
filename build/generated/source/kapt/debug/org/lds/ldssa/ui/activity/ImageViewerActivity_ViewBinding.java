// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;
import org.lds.ldssa.ui.widget.LoadingView;
import org.lds.mobile.ui.widget.media.TouchImageView;

public class ImageViewerActivity_ViewBinding implements Unbinder {
  private ImageViewerActivity target;

  @UiThread
  public ImageViewerActivity_ViewBinding(ImageViewerActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ImageViewerActivity_ViewBinding(ImageViewerActivity target, View source) {
    this.target = target;

    target.touchImageView = Utils.findRequiredViewAsType(source, R.id.touchImageView, "field 'touchImageView'", TouchImageView.class);
    target.loadingView = Utils.findRequiredViewAsType(source, R.id.loadingView, "field 'loadingView'", LoadingView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ImageViewerActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.touchImageView = null;
    target.loadingView = null;
  }
}
