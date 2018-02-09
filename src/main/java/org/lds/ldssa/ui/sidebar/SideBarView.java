package org.lds.ldssa.ui.sidebar;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.widget.FrameLayout;

public abstract class SideBarView extends FrameLayout {

    public static final String SCROLL_TO_TOP = "SCROLL_TO_TOP";

    public SideBarView(Context context) {
        super(context);
    }

    public SideBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SideBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Nullable
    private SideBar sideBar;

    public void onCreateOptionsMenu(Toolbar toolbar) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return false;
    }

    @Nullable
    protected SideBar getSideBar() {
        return sideBar;
    }

    protected void setSideBar(@Nullable SideBar sideBar) {
        this.sideBar = sideBar;
    }

    protected void setTitle(CharSequence title) {
        if (sideBar != null) {
            sideBar.setTitle(title);
        }
    }

    protected void setTitle(@StringRes int titleRes) {
        if (sideBar != null) {
            sideBar.setTitle(titleRes);
        }
    }

    protected void closeSideBar() {
        if (sideBar != null) {
            sideBar.closeSidebar();
        }
    }

    protected void switchToCurrentRelatedContent() {
        if (sideBar != null) {
            sideBar.switchToCurrentRelatedContent();
        }
    }

    protected void scrollToParagraphAid(String paragraphAid) {
        // Left for children to implement as needed
    }
}
