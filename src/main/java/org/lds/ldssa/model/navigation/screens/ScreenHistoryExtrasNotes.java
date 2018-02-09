package org.lds.ldssa.model.navigation.screens;

import org.lds.ldssa.model.navigation.DtoNavigationHistoryItemExtra;
import org.lds.ldssa.model.navigation.InvalidExtraException;
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras;

import java.util.ArrayList;
import java.util.List;

public class ScreenHistoryExtrasNotes extends NavigationHistoryItemExtras {
    private static final String EXTRA_KEY_SELECTED_TAB_ID = "selectedTabId";

    private long selectedTabId;

    @SuppressWarnings("unused")
    public ScreenHistoryExtrasNotes() {
        // used by ScreenHistoryItem.getExtras()
    }

    public ScreenHistoryExtrasNotes(long selectedTabId) {
        this.selectedTabId = selectedTabId;
    }

    public List<DtoNavigationHistoryItemExtra> getExtras() {
        verifyRequiredExtras();

        List<DtoNavigationHistoryItemExtra> extrasList = new ArrayList<>();
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_SELECTED_TAB_ID, selectedTabId));

        return extrasList;
    }

    public void setExtras(List<DtoNavigationHistoryItemExtra> extrasList) {
        for (DtoNavigationHistoryItemExtra dtoNavigationHistoryItemExtra : extrasList) {
            switch (dtoNavigationHistoryItemExtra.getKey()) {
                case EXTRA_KEY_SELECTED_TAB_ID:
                    selectedTabId = dtoNavigationHistoryItemExtra.getValueAsInt();
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
    }

    public long getSelectedTabId() {
        return selectedTabId;
    }

    public void setSelectedTabId(long selectedTabId) {
        this.selectedTabId = selectedTabId;
    }
}
