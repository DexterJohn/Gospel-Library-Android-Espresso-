package org.lds.ldssa.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Provides lifecycle for fragments inside of a ViewPager (pause/resume)
 * For full functionality the onActivityResume and onActivityPause methods should be called from corresponding Activity methods
 */
public abstract class LifecycleViewPager extends ViewPager {

    private FragmentStatePagerAdapter adapter;
    int currentPosition;

    public LifecycleViewPager(Context context) {
        super(context);
        init();
    }

    public LifecycleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        addOnPageChangeListener(new FragmentLifecyclePageChangeListener());
    }

    public void setAdapter(FragmentStatePagerAdapter adapter) {
        super.setAdapter(adapter);
        this.adapter = adapter;
    }

    /**
     * This should be called in the OnResume method of the activity to give proper resume functionality to the visible fragment
     */
    public void onActivityResume() {
        Fragment fragmentToResume = adapter.getItem(currentPosition);
        if (fragmentToResume instanceof FragmentLifecycle) {
            ((FragmentLifecycle)fragmentToResume).onResumeFragment();
        }
    }

    /**
     * This should be called in the OnPause method of the activity to give proper pause functionality to the visible fragment
     */
    public void onActivityPause() {
        Fragment fragmentToPause = adapter.getItem(currentPosition);
        if (fragmentToPause instanceof FragmentLifecycle) {
            ((FragmentLifecycle)fragmentToPause).onPauseFragment();
        }
    }

    private class FragmentLifecyclePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int newPosition) {
            // Resume new fragment
            Fragment fragmentToShow = adapter.getItem(newPosition);
            if (fragmentToShow instanceof FragmentLifecycle) {
                ((FragmentLifecycle)fragmentToShow).onResumeFragment();
            }

            // Pause old fragment
            Fragment fragmentToHide = adapter.getItem(currentPosition);
            if (fragmentToHide instanceof FragmentLifecycle) {
                ((FragmentLifecycle)fragmentToHide).onPauseFragment();
            }

            // update the currently visible position
            currentPosition = newPosition;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // Do nothing
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // Do nothing
        }
    }

    public interface FragmentLifecycle {
        void onPauseFragment();
        void onResumeFragment();
    }
}
