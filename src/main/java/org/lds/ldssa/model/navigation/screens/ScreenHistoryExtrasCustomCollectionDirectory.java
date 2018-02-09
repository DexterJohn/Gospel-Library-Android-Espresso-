package org.lds.ldssa.model.navigation.screens;

import android.support.annotation.NonNull;

import org.lds.ldssa.model.navigation.DtoNavigationHistoryItemExtra;
import org.lds.ldssa.model.navigation.InvalidExtraException;
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras;

import java.util.ArrayList;
import java.util.List;

public class ScreenHistoryExtrasCustomCollectionDirectory extends NavigationHistoryItemExtras {
    private static final String EXTRA_KEY_CUSTOM_COLLECTION_ID = "customCollectionId";
    private static final String EXTRA_KEY_SCROLL_POSITION = "scrollPosition";

    private long customCollectionId;
    private int scrollPosition;

    @SuppressWarnings("unused")
    public ScreenHistoryExtrasCustomCollectionDirectory() {
        // used by ScreenHistoryItem.getExtras()
    }

    public ScreenHistoryExtrasCustomCollectionDirectory(long customCollectionId, int scrollPosition) {
        this.customCollectionId = customCollectionId;
        this.scrollPosition = scrollPosition;
    }

    @NonNull
    public List<DtoNavigationHistoryItemExtra> getExtras() {
        verifyRequiredExtras();

        List<DtoNavigationHistoryItemExtra> extrasList = new ArrayList<>();
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_CUSTOM_COLLECTION_ID, customCollectionId));
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_SCROLL_POSITION, scrollPosition));

        return extrasList;
    }

    public void setExtras(@NonNull List<DtoNavigationHistoryItemExtra> extrasList) {
        for (DtoNavigationHistoryItemExtra dtoNavigationHistoryItemExtra : extrasList) {
            switch (dtoNavigationHistoryItemExtra.getKey()) {
                case EXTRA_KEY_CUSTOM_COLLECTION_ID:
                    customCollectionId = dtoNavigationHistoryItemExtra.getValueAsLong();
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
        if (customCollectionId <= 0) {
            throw new InvalidExtraException(EXTRA_KEY_CUSTOM_COLLECTION_ID, customCollectionId);
        }
    }

    public long getCustomCollectionId() {
        return customCollectionId;
    }

    public void setCustomCollectionId(long customCollectionId) {
        this.customCollectionId = customCollectionId;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }
}
