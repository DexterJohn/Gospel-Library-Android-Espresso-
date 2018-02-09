package org.lds.ldssa.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

/**
 * A utility to simplify hiding and showing a view above a RecyclerView
 * based on the scroll state of the RecyclerView.
 * todo move to commons or change to coordinator layout??
 */
public class ScrollViewHider extends RecyclerView.OnScrollListener {
    private RecyclerView recyclerView;
    private View hidingView;

    private boolean completelyHidden;
    private boolean completelyVisible = true;

    private int currentViewTop = Integer.MIN_VALUE;
    private int setTop = 0;
    private int viewHeight = 0;

    public ScrollViewHider(RecyclerView recyclerView, View hidingView) {
        this.recyclerView = recyclerView;
        this.hidingView = hidingView;

        ViewTreeObserver observer = recyclerView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new LayoutListener());
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        //Make sure to populate the top initially
        if (currentViewTop == Integer.MIN_VALUE) {
            currentViewTop = ((RelativeLayout.LayoutParams) hidingView.getLayoutParams()).topMargin;
            setTop = currentViewTop;
            viewHeight = hidingView.getHeight();
        }

        //Perform the hidingView location changes
        if (dy > 0 && !completelyHidden) {
            hideView(dy);
        } else if (dy < 0 && !completelyVisible) {
            showView(dy);
        }
    }

    /**
     * Hides the view by the specified amount, limiting the change the height
     * of the view itself.
     *
     * @param dy The amount to hide the view by.  This should be positive
     */
    private void hideView(int dy) {
        completelyVisible = false;
        currentViewTop -= dy;

        //Updates the layout params
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) hidingView.getLayoutParams();
        params.topMargin = currentViewTop;
        hidingView.setLayoutParams(params);

        if (currentViewTop + viewHeight <= 0) {
            completelyHidden = true;
        }
    }

    /**
     * Shows the view by the specified amount, limiting the change to the amount
     * the view has been hidden.
     *
     * @param dy The amount to show the view by.  This should be negative
     */
    private void showView(int dy) {
        completelyHidden = false;

        currentViewTop -= dy;
        if (currentViewTop > setTop) {
            currentViewTop = setTop;
        }

        //Updates the layout params
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) hidingView.getLayoutParams();
        params.topMargin = currentViewTop;
        hidingView.setLayoutParams(params);

        if (currentViewTop == setTop) {
            completelyVisible = true;
        }
    }

    private class LayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        private boolean setup = false;

        @Override
        public void onGlobalLayout() {
            if (!setup) {
                setup = true;
                recyclerView.addOnScrollListener(ScrollViewHider.this);
                recyclerView.setPadding(0, hidingView.getHeight(), 0, 0);
            }
        }
    }
}

