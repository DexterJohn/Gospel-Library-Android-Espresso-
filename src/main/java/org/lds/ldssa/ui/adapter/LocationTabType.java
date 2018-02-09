package org.lds.ldssa.ui.adapter;

import android.support.annotation.StringRes;

import org.lds.ldssa.R;

public enum LocationTabType {
    BOOKMARKS(R.string.bookmarks), TABS(R.string.screens), HISTORY(R.string.history);

    @StringRes
    private int titleResId;

    LocationTabType(@StringRes int titleResId) {
        this.titleResId = titleResId;
    }

    public int getTitleResId() {
        return titleResId;
    }
}
