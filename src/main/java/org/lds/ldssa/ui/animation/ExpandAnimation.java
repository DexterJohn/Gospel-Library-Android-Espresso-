package org.lds.ldssa.ui.animation;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * An animation to support exiting action mode for the Tags
 * and Notebook fragments.
 */
public class ExpandAnimation extends Animation implements Animation.AnimationListener {
    private static final long DURATION = 250;

    @Nullable
    private RecyclerView recyclerView;
    private View filterView;

    private int filterViewEndHeight;
    private int recyclerViewOriginalTopPadding;

    @SuppressWarnings("FieldCanBeLocal")
    private ViewGroup.LayoutParams filterViewParams;

    public ExpandAnimation(View filterView, @Nullable RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.filterView = filterView;

        recyclerViewOriginalTopPadding = recyclerView != null ? recyclerView.getPaddingTop() : 0;

        //We measure the requested size for the filterView
        filterView.setVisibility(View.VISIBLE);
        filterViewParams = filterView.getLayoutParams();
        filterView.measure(filterViewParams.width, filterViewParams.height);
        filterViewEndHeight = filterView.getMeasuredHeight();
        
        setDuration(DURATION);
        setAnimationListener(this);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int heightChange = (int)(filterViewEndHeight * interpolatedTime);

        if (recyclerView != null) {
            int newTopPadding = recyclerViewOriginalTopPadding + heightChange;
            recyclerView.setPadding(recyclerView.getPaddingLeft(), newTopPadding, recyclerView.getPaddingRight(), recyclerView.getPaddingBottom());
        }

        filterViewParams.height = heightChange;
        filterView.requestLayout();
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        filterView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        filterView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        //Purposefully left blank
    }
}
