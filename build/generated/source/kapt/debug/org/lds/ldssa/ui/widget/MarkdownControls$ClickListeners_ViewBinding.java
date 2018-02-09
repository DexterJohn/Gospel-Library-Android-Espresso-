// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.widget;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;

public class MarkdownControls$ClickListeners_ViewBinding implements Unbinder {
  private MarkdownControls.ClickListeners target;

  private View view2131427728;

  private View view2131427730;

  private View view2131427729;

  private View view2131427731;

  @UiThread
  public MarkdownControls$ClickListeners_ViewBinding(final MarkdownControls.ClickListeners target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.markdown_controls_bold, "method 'onBoldClick'");
    view2131427728 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onBoldClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.markdown_controls_ordered_list, "method 'onOrderedListClick'");
    view2131427730 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onOrderedListClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.markdown_controls_italic, "method 'onItalicClick'");
    view2131427729 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onItalicClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.markdown_controls_unordered_list, "method 'onUnorderedListClick'");
    view2131427731 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onUnorderedListClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131427728.setOnClickListener(null);
    view2131427728 = null;
    view2131427730.setOnClickListener(null);
    view2131427730 = null;
    view2131427729.setOnClickListener(null);
    view2131427729 = null;
    view2131427731.setOnClickListener(null);
    view2131427731 = null;
  }
}
