package org.lds.ldssa.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import org.lds.ldssa.ui.adapter.LifecycleViewPager;

public class ContentViewPager extends LifecycleViewPager {
    private boolean pagingEnabled = true;

    public ContentViewPager(Context context) {
        super(context);
    }

    public ContentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setKeepScreenOn(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return pagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (!pagingEnabled) {
            return false;
        }

        boolean handled;
        try {
            handled = super.onInterceptTouchEvent(event);
        } catch (IllegalArgumentException e) {
            handled = false;
        }
        return handled;
    }

    public void setPagingEnabled(boolean enabled) {
        this.pagingEnabled = enabled;
    }

    public boolean isPagingEnabled() {
        return pagingEnabled;
    }
}
