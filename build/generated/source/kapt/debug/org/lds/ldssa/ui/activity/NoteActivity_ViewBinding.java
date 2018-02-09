// Generated code from Butter Knife. Do not modify!
package org.lds.ldssa.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.lds.ldssa.R;
import org.lds.ldssa.ui.widget.MarkdownControls;
import org.lds.mobile.markdown.widget.MarkdownEditText;

public class NoteActivity_ViewBinding implements Unbinder {
  private NoteActivity target;

  @UiThread
  public NoteActivity_ViewBinding(NoteActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NoteActivity_ViewBinding(NoteActivity target, View source) {
    this.target = target;

    target.markdownEditText = Utils.findRequiredViewAsType(source, R.id.markdownEditText, "field 'markdownEditText'", MarkdownEditText.class);
    target.noteTitleEditText = Utils.findRequiredViewAsType(source, R.id.noteTitleEditText, "field 'noteTitleEditText'", EditText.class);
    target.markdownControls = Utils.findRequiredViewAsType(source, R.id.markdownControls, "field 'markdownControls'", MarkdownControls.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NoteActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.markdownEditText = null;
    target.noteTitleEditText = null;
    target.markdownControls = null;
  }
}
