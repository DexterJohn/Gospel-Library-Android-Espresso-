package org.lds.ldssa.model.navigation.screens;

import android.support.annotation.NonNull;

import org.lds.ldssa.model.navigation.DtoNavigationHistoryItemExtra;
import org.lds.ldssa.model.navigation.InvalidExtraException;
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras;

import java.util.ArrayList;
import java.util.List;

public class ScreenHistoryExtrasTips extends NavigationHistoryItemExtras {
    private static final String EXTRA_KEY_SELECTED_TAB_ID = "selectedTabId";
    private static final String EXTRA_KEY_SCROLL_POSITION = "scrollPosition";

    private int selectedTabId;
    private int scrollPosition;

    @SuppressWarnings("unused")
    public ScreenHistoryExtrasTips() {
        // used by ScreenHistoryItem.getExtras()
    }

    public ScreenHistoryExtrasTips(int selectedTabId, int scrollPosition) {
        this.selectedTabId = selectedTabId;
        this.scrollPosition = scrollPosition;
    }

    @NonNull
    public List<DtoNavigationHistoryItemExtra> getExtras() {
        verifyRequiredExtras();

        List<DtoNavigationHistoryItemExtra> extrasList = new ArrayList<>();
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_SELECTED_TAB_ID, selectedTabId));
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_SCROLL_POSITION, scrollPosition));

        return extrasList;
    }

    public void setExtras(@NonNull List<DtoNavigationHistoryItemExtra> extrasList) {
        for (DtoNavigationHistoryItemExtra dtoNavigationHistoryItemExtra : extrasList) {
            switch (dtoNavigationHistoryItemExtra.getKey()) {
                case EXTRA_KEY_SELECTED_TAB_ID:
                    selectedTabId = dtoNavigationHistoryItemExtra.getValueAsInt();
                    break;
                case EXTRA_KEY_SCROLL_POSITION:
                    scrollPosition = dtoNavigationHistoryItemExtra.getValueAsInt();
                    break;
                default:
                    // ignore extra extras
            }
        }

        verifyRequiredExtras();
    }

    private void verifyRequiredExtras() {
        if (selectedTabId < 0) {
            throw new InvalidExtraException(EXTRA_KEY_SELECTED_TAB_ID, selectedTabId);
        }
        if (scrollPosition < 0) {
            throw new InvalidExtraException(EXTRA_KEY_SCROLL_POSITION, scrollPosition);
        }
    }

    public int getSelectedTabId() {
        return selectedTabId;
    }

    public void setSelectedTabId(int selectedTabId) {
        this.selectedTabId = selectedTabId;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }
}
