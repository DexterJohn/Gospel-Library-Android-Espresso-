// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.widget;

import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;

public final class MiniPlaybackControls_ViewBinding implements Unbinder {
  private MiniPlaybackControls target;

  private View view2131427964;

  private View view2131427612;

  private View view2131427932;

  private View view2131427926;

  private View view2131427857;

  @UiThread
  public MiniPlaybackControls_ViewBinding(MiniPlaybackControls target) {
    this(target, target);
  }

  @UiThread
  public MiniPlaybackControls_ViewBinding(final MiniPlaybackControls target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.replayButton, "method 'onReplayClick'");
    view2131427964 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onReplayClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.forwardButton, "method 'onForwardClick'");
    view2131427612 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onForwardClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.previousButton, "method 'onPreviousClick'");
    view2131427932 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPreviousClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.playPauseButton, "method 'onPlayPauseClick'");
    view2131427926 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPlayPauseClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.nextButton, "method 'onNextClick'");
    view2131427857 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onNextClick();
      }
    });
  }

  @Override
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131427964.setOnClickListener(null);
    view2131427964 = null;
    view2131427612.setOnClickListener(null);
    view2131427612 = null;
    view2131427932.setOnClickListener(null);
    view2131427932 = null;
    view2131427926.setOnClickListener(null);
    view2131427926 = null;
    view2131427857.setOnClickListener(null);
    view2131427857 = null;
  }
}
