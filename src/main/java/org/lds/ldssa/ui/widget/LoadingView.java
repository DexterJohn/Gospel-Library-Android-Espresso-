package org.lds.ldssa.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.lds.ldssa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingView extends FrameLayout {

    @BindView(R.id.widget_loading_text)
    TextView textView;

    public LoadingView(Context context) {
        super(context);
        init(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.widget_loading, this, true);
        ButterKnife.bind(this);

        if (attrs != null) {
            readAttributes(context, attrs);
        }
    }

    private void readAttributes(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.text});
        if (typedArray != null) {
            CharSequence text = typedArray.getText(0);
            setText(text);

            typedArray.recycle();
        }
    }

    public void setText(@Nullable CharSequence text) {
        textView.setText(text);
        textView.setVisibility(text == null ? View.GONE : View.VISIBLE);
    }

    public String getText() {
        return textView.getText().toString();
    }
}
